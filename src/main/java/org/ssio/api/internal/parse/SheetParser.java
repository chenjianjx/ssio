package org.ssio.api.internal.parse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ssio.api.external.parse.CellError;
import org.ssio.api.external.parse.ParseParam;
import org.ssio.api.external.parse.ParseResult;
import org.ssio.api.external.parse.PropFromColumnMappingMode;
import org.ssio.api.external.typing.ComplexTypeHandler;
import org.ssio.api.external.typing.SimpleTypeEnum;
import org.ssio.api.internal.common.BeanClassInspector;
import org.ssio.api.internal.common.PropAndColumn;
import org.ssio.api.internal.common.abstractsheet.SsCellHelper;
import org.ssio.spi.clientexternal.spiregistry.SsWorkbookFactoryRegistry;
import org.ssio.spi.developerexternal.abstractsheet.factory.SsWorkbookFactory;
import org.ssio.spi.developerexternal.abstractsheet.model.SsCell;
import org.ssio.spi.developerexternal.abstractsheet.model.SsRow;
import org.ssio.spi.developerexternal.abstractsheet.model.SsSheet;
import org.ssio.spi.developerexternal.abstractsheet.model.SsWorkbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.ssio.util.lang.SsioReflectionUtils.createInstance;

public class SheetParser {
    private static final Logger logger = LoggerFactory.getLogger(SheetParser.class);
    private final SsWorkbookFactoryRegistry workbookFactoryRegistry;
    private BeanClassInspector beanClassInspector = new BeanClassInspector();

    private SsCellHelper cellHelper = new SsCellHelper();

    public SheetParser(SsWorkbookFactoryRegistry workbookFactoryRegistry) {
        this.workbookFactoryRegistry = workbookFactoryRegistry;
    }

    public <BEAN> ParseResult<BEAN> doWork(ParseParam<BEAN> param) throws IOException {
        ParseResult<BEAN> result = new ParseResult<>();

        ArrayList<String> beanClassErrors = new ArrayList<>();
        List<PropAndColumn> propAndColumnList = beanClassInspector.getPropAndColumnMappingsForParseMode(param.getBeanClass(), param.getPropFromColumnMappingMode(), beanClassErrors);
        if (beanClassErrors.size() > 0) {
            //shouldn't happen here. The validation has been done before here
            throw new IllegalArgumentException(beanClassErrors.toString());
        }


        SsWorkbookFactory workbookFactory = workbookFactoryRegistry.getFactory(param.getSpreadsheetFileType());
        if (workbookFactory == null) {
            throw new IllegalStateException("There is no workbook factory registered for file type: " + param.getSpreadsheetFileType());
        }

        SsWorkbook workbook = workbookFactory.loadWorkbookToParse(param);

        SsSheet sheet = workbook.getSheetToParse();

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

            String headerText = StringUtils.trimToNull((String) cellHelper.readValueAsType(cell, SimpleTypeEnum.String, null, null));
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


        BEAN bean = createInstance(beanClass);
        for (PropAndColumn propAndColumn : propAndColumnList) {
            String propName = propAndColumn.getPropName();
            int columnIndex = propAndColumn.getColumnIndex();

            if (columnIndex >= row.getNumberOfCells()) {
                continue;
            }

            SsCell cell = row.getCell(columnIndex);

            try {
                Object value = cellHelper.readValueAsType(cell, propAndColumn.getSimpleTypeEnum(), propAndColumn.getEnumClassIfEnum(), propAndColumn.getFormat());
                if (propAndColumn.getTypeHandlerClass() != null) {
                    ComplexTypeHandler typeHandler = createInstance(propAndColumn.getTypeHandlerClass());
                    if (value == null) {
                        value = typeHandler.fromNullSimpleTypeValue();
                    } else {
                        value = typeHandler.fromNonNullSimpleTypeValue(value);
                    }
                }
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
