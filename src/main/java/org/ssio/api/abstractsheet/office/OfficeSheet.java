package org.ssio.api.abstractsheet.office;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ssio.api.abstractsheet.SsCellValueJavaType;
import org.ssio.api.abstractsheet.SsSheet;
import org.ssio.api.b2s.DatumError;
import org.ssio.internal.common.office.cellvalue.binder.OfficeCellValueBinder;
import org.ssio.internal.common.office.cellvalue.binder.OfficeCellValueBinderRepo;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;

public class OfficeSheet implements SsSheet {

    private static final Logger logger = LoggerFactory.getLogger(OfficeSheet.class);

    private Sheet poiSheet;

    public OfficeSheet(Sheet poiSheet) {
        this.poiSheet = poiSheet;
    }

    @Override
    public void createHeaderRow(Map<String, String> headerMap) {
        createHeaderRowInPoi(headerMap);

    }

    @Override
    public <BEAN> void createDataRow(Map<String, String> headerMap, BEAN bean, int recordIndex, int rowIndex, String datumErrPlaceholder, List<DatumError> datumErrors) {
        createDataRowInPoi(headerMap, bean, recordIndex, rowIndex, datumErrPlaceholder, datumErrors);
    }


    private Row createHeaderRowInPoi(Map<String, String> headerMap) {
        CellStyle style = poiSheet.getWorkbook().createCellStyle();
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);

        Row header = poiSheet.createRow(0);
        int columnIndex = 0;
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            String headerText = StringUtils.defaultString(entry.getValue());
            Cell cell = header.createCell(columnIndex);
            cell.setCellValue(headerText);
            cell.setCellStyle(style);
            poiSheet.autoSizeColumn(columnIndex);
            columnIndex++;
        }
        return header;
    }


    private <BEAN> Row createDataRowInPoi(Map<String, String> headerMap, BEAN bean, int recordIndex, int rowIndex, String datumErrPlaceholder, List<DatumError> datumErrors) {
        Row row = poiSheet.createRow(rowIndex);
        int columnIndex = 0;
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {

            Cell cell = row.createCell(columnIndex);

            String propName = entry.getKey();

            try {
                PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(bean, propName);
                SsCellValueJavaType javaType = SsCellValueJavaType.fromRealType(pd.getPropertyType());
                String nonSupportMsg = "Unsupported real java type to write to an office cell. The type is " + pd.getPropertyType().getName();
                if (javaType == null) {
                    throw new IllegalStateException(nonSupportMsg);
                }
                OfficeCellValueBinder cellValueBinder = OfficeCellValueBinderRepo.getOfficeCellValueBinder(javaType);
                if (cellValueBinder == null) {
                    throw new IllegalStateException(nonSupportMsg);
                }

                Object propValue = PropertyUtils.getProperty(bean, propName);
                if (propValue != null) {
                    cellValueBinder.setNonNullValue(cell, propValue);
                }

            } catch (Exception e) {

                logger.warn("Datum error", e);

                DatumError de = new DatumError();
                de.setPropName(propName);
                de.setRecordIndex(recordIndex);
                de.setCause(e);
                datumErrors.add(de);


                cell.setCellValue(datumErrPlaceholder);

                CellStyle errStyle = poiSheet.getWorkbook().createCellStyle();
                errStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                errStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cell.setCellStyle(errStyle);

            }
            columnIndex++;
        }

        return row;
    }


}
