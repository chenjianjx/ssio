package org.ssio.internal.s2b;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ssio.api.common.BeanClassInspector;
import org.ssio.api.common.abstractsheet.model.DefaultSsFactory;
import org.ssio.api.common.abstractsheet.model.SsCell;
import org.ssio.api.common.abstractsheet.model.SsCellValueHelper;
import org.ssio.api.common.abstractsheet.model.SsCellValueJavaType;
import org.ssio.api.common.abstractsheet.model.SsFactory;
import org.ssio.api.common.abstractsheet.model.SsRow;
import org.ssio.api.common.abstractsheet.model.SsSheet;
import org.ssio.api.common.abstractsheet.model.SsWorkbook;
import org.ssio.api.common.mapping.PropAndColumn;
import org.ssio.api.s2b.CellError;
import org.ssio.api.s2b.PropFromColumnMappingMode;
import org.ssio.api.s2b.SheetToBeansParam;
import org.ssio.api.s2b.SheetToBeansResult;
import org.ssio.internal.util.SsioReflectionHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.ssio.internal.util.SsioReflectionHelper.getPropertyEnumClassIfEnum;

public class SheetToBeansWorker {
    private static final Logger logger = LoggerFactory.getLogger(SheetToBeansWorker.class);
    private BeanClassInspector beanClassInspector = new BeanClassInspector();

    public <BEAN> SheetToBeansResult<BEAN> doWork(SheetToBeansParam<BEAN> param) throws IOException {
        SheetToBeansResult<BEAN> result = new SheetToBeansResult<>();

        ArrayList<String> beanClassErrors = new ArrayList<>();
        List<PropAndColumn> propAndColumnList = beanClassInspector.getPropAndColumnMappingsForSheet2Beans(param.getBeanClass(), param.getPropFromColumnMappingMode(), beanClassErrors);
        if (beanClassErrors.size() > 0) {
            //shouldn't happen here. The validation has been done before this is executed
            throw new IllegalArgumentException(beanClassErrors.toString());
        }


        SsFactory ssFactory = new DefaultSsFactory();
        SsWorkbook workbook = ssFactory.createWorkbookFromInput(param.getFileType(), param.getSpreadsheetInput(), param.getInputCharset(), param.getCellSeparator());
        if (workbook.getNumberOfSheets() <= 0) {
            logger.warn("There are no sheets in the spreadsheet");
            return result;
        }

        SsSheet sheet = param.getSheetLocator().getSheet(workbook);

        if (sheet == null) {
            throw new IllegalArgumentException("No sheet found using locator: " + param.getSheetLocator().getDesc());
        }


        //if map by name, find the column indexes
        if (param.getPropFromColumnMappingMode() == PropFromColumnMappingMode.BY_NAME) {

            if (!param.isSheetHasHeader()) {
                //shouldn't happen here. The validation has been done before here
                throw new IllegalArgumentException();
            }

            // key = columnName, value = columnIndex
            LinkedHashMap<String, Integer> headerColumnNameAndIndex = parseHeader(sheet.getRow(0));
            propAndColumnList.forEach(pac -> {
                Integer columnIndex = headerColumnNameAndIndex.get(pac.getColumnName());
                if (columnIndex == null) {
                    pac.setColumnIndex(-1);
                } else {
                    pac.setColumnIndex(columnIndex);
                }
            });
            //filter out those who don't have a column in the sheet
            propAndColumnList = propAndColumnList.stream().filter(pac -> pac.getColumnIndex() >= 0).collect(Collectors.toList());
            logger.info("Header row is parsed");
        }


        int dataRowIndex = param.isSheetHasHeader() ? 1 : 0;

        // now do the data rows
        int numberOfRows = sheet.getNumberOfRows();
        logger.info("There are " + (numberOfRows - dataRowIndex) + " data rows in the spreadsheet");
        for (int rowIndex = dataRowIndex; rowIndex < numberOfRows; rowIndex++) {
            int rowIndexOneBased = rowIndex + 1;
            logger.debug("Parsing row " + rowIndexOneBased + "/" + numberOfRows);
            SsRow row = sheet.getRow(rowIndex);
            if (row == null) {
                logger.warn("Row " + rowIndexOneBased + " is a null row. No bean will be created for it");
                continue;
            }

            BEAN bean = parseDataRow(propAndColumnList, row, rowIndex, param.getBeanClass(), result.getCellErrors());
            result.getBeans().add(bean);
        }
        
        return result;
    }


    /**
     * to get <columnName, columnIndex>
     */
    private LinkedHashMap<String, Integer> parseHeader(SsRow row) {
        LinkedHashMap<String, Integer> headerMap = new LinkedHashMap<>();


        for (int columnIndex = 0; columnIndex < row.getNumberOfCells(); columnIndex++) {
            SsCell cell = row.getCell(columnIndex);

            String headerText = StringUtils.trimToNull((String) cell.readValueAsType(SsCellValueJavaType.String, null, null));
            if (headerText == null) {
                continue;
            }
            headerMap.put(headerText, columnIndex);
        }
        return headerMap;
    }


    /**
     * @param propAndColumnList the columnIndex in each PropAndColumn should be guaranteed to be >= 0
     * @return
     */
    private <BEAN> BEAN parseDataRow(List<PropAndColumn> propAndColumnList,
                                     SsRow row, int rowIndex, Class<BEAN> beanClass,
                                     List<CellError> cellErrors) {


        BEAN bean = SsioReflectionHelper.createInstance(beanClass);
        for (PropAndColumn propAndColumn : propAndColumnList) {
            String propName = propAndColumn.getPropName();
            int columnIndex = propAndColumn.getColumnIndex();

            if (columnIndex >= row.getNumberOfCells()) {
                continue;
            }

            SsCell cell = row.getCell(columnIndex);

            try {
                SsCellValueJavaType javaType = SsCellValueHelper.resolveJavaTypeOfPropertyOrThrow(bean, propName);
                Class<Enum<?>> enumClassIfEnum = getPropertyEnumClassIfEnum(bean, propName);
                Object value = cell.readValueAsType(javaType, enumClassIfEnum, propAndColumn.getFormat());
                PropertyUtils.setProperty(bean, propName, value);
            } catch (Exception e) {
                if (cellErrors != null) {
                    CellError ce = new CellError();
                    ce.setColumnIndex(columnIndex);
                    ce.setColumnName(propAndColumn.getColumnName());
                    ce.setPropName(propName);
                    ce.setRowIndex(rowIndex);
                    ce.setCause(e);
                    cellErrors.add(ce);
                }
            }
        }

        return bean;
    }


}
