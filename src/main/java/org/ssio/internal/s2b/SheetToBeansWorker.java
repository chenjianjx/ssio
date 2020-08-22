package org.ssio.internal.s2b;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ssio.api.common.abstractsheet.model.DefaultSsFactory;
import org.ssio.api.common.abstractsheet.model.SsCell;
import org.ssio.api.common.abstractsheet.model.SsCellValueHelper;
import org.ssio.api.common.abstractsheet.model.SsCellValueJavaType;
import org.ssio.api.common.abstractsheet.model.SsFactory;
import org.ssio.api.common.abstractsheet.model.SsRow;
import org.ssio.api.common.abstractsheet.model.SsSheet;
import org.ssio.api.common.abstractsheet.model.SsWorkbook;
import org.ssio.api.s2b.CellError;
import org.ssio.api.s2b.SheetToBeansParam;
import org.ssio.api.s2b.SheetToBeansResult;
import org.ssio.internal.temp.HeaderUtils;
import org.ssio.internal.util.SsioReflectionHelper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.ssio.internal.util.SsioReflectionHelper.getPropertyEnumClassIfEnum;

public class SheetToBeansWorker {
    private static final Logger logger = LoggerFactory.getLogger(SheetToBeansWorker.class);

    public <BEAN> SheetToBeansResult<BEAN> doWork(SheetToBeansParam<BEAN> param) throws IOException {
        SheetToBeansResult<BEAN> result = new SheetToBeansResult<BEAN>();
        LinkedHashMap<String, String> reversedHeaderMap = HeaderUtils.generateReverseHeaderMapFromProps(param.getBeanClass());

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


        // key = columnIndex, value= {propName, headerText}
        LinkedHashMap<Integer, ColumnMeta> columnMetaMap = parseHeader(reversedHeaderMap, sheet.getRow(0));
        if (columnMetaMap.isEmpty()) {
            throw new RuntimeException("Invalid header");
        }
        logger.info("Header row is parsed");

        // now do the data rows
        int numberOfRows = sheet.getNumberOfRows();
        logger.info("There are " + (numberOfRows - 1) + " data rows in the spreadsheet");
        for (int rowIndex = 1; rowIndex < numberOfRows; rowIndex++) {
            int rowIndexForLogging = rowIndex + 1;
            logger.debug("Parsing row " + rowIndexForLogging + "/" + numberOfRows);
            SsRow row = sheet.getRow(rowIndex);
            if (row == null) {
                logger.warn("Row " + rowIndexForLogging + " is a null row. No bean won't be created for it");
                continue;
            }

            BEAN bean = parseDataRow(columnMetaMap, row, rowIndex, param.getBeanClass(), result.getCellErrors());
            result.getBeans().add(bean);
        }


        return result;
    }


    /**
     * to get <columnIndex, column info>
     */
    private LinkedHashMap<Integer, ColumnMeta> parseHeader(Map<String, String> reverseHeaderMap, SsRow row) {
        LinkedHashMap<Integer, ColumnMeta> columnMetaMap = new LinkedHashMap<>();


        for (int columnIndex = 0; columnIndex < row.getNumberOfCells(); columnIndex++) {
            SsCell cell = row.getCell(columnIndex);

            String headerText = StringUtils.trimToNull((String) cell.readValueAsType(SsCellValueJavaType.String, null));
            if (headerText == null) {
                continue;
            }
            String propName = reverseHeaderMap.get(headerText);
            if (propName == null) {
                continue;
            }

            ColumnMeta cm = new ColumnMeta();
            cm.headerText = headerText;
            cm.propName = propName;
            columnMetaMap.put(columnIndex, cm);
        }
        return columnMetaMap;
    }

    private <BEAN> BEAN parseDataRow(Map<Integer, ColumnMeta> columnMetaMap,
                                     SsRow row, int rowIndex, Class<BEAN> beanClass,
                                     List<CellError> cellErrors) {

        BEAN bean = SsioReflectionHelper.createInstance(beanClass);
        for (int columnIndex = 0; columnIndex < row.getNumberOfCells(); columnIndex++) {
            ColumnMeta columnMeta = columnMetaMap.get(columnIndex);
            if (columnMeta == null || columnMeta.propName == null) {
                continue;
            }
            String propName = columnMeta.propName;
            SsCell cell = row.getCell(columnIndex);

            try {
                SsCellValueJavaType javaType = SsCellValueHelper.resolveJavaTypeOfPropertyOrThrow(bean, propName);
                Class<Enum<?>> enumClassIfEnum = getPropertyEnumClassIfEnum(bean, propName);
                Object value = cell.readValueAsType(javaType, enumClassIfEnum);
                PropertyUtils.setProperty(bean, propName, value);
            } catch (Exception e) {
                if (cellErrors != null) {
                    CellError ce = new CellError();
                    ce.setColumnIndex(columnIndex);
                    ce.setHeaderText(columnMeta.headerText);
                    ce.setPropName(propName);
                    ce.setRowIndex(rowIndex);
                    ce.setCause(e);
                    cellErrors.add(ce);
                }
            }
        }

        return bean;
    }

    /**
     * meta info about a column
     */
    private static class ColumnMeta {
        public String propName;
        public String headerText;

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }
    }


}
