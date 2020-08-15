package org.ssio.api.abstractsheet.office;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.ssio.api.abstractsheet.SsSheet;
import org.ssio.api.b2s.DatumError;
import org.ssio.internal.util.SsioReflectionHelper;

import java.util.List;
import java.util.Map;

public class OfficeSheet implements SsSheet {

    private Sheet poiSheet;

    public OfficeSheet(Sheet poiSheet) {
        this.poiSheet = poiSheet;
    }

    @Override
    public OfficeRow createHeaderRow(Map<String, String> headerMap) {
        Row header = createHeaderRowInPoi(headerMap);
        return new OfficeRow(header);
    }

    @Override
    public <BEAN> OfficeRow createDataRow(Map<String, String> headerMap, BEAN bean, int recordIndex, int rowIndex, String datumErrPlaceholder, List<DatumError> datumErrors) {
        Row poiRow = createDataRowInPoi(headerMap, bean, recordIndex, rowIndex, datumErrPlaceholder, datumErrors);
        return new OfficeRow(poiRow);
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
            boolean hasDatumError = false;
            String propName = entry.getKey();
            Object propValue;
            try {
                propValue = SsioReflectionHelper.getProperty(bean, propName);
            } catch (Exception e) {
                if (datumErrors != null) {
                    DatumError de = new DatumError();
                    de.setPropName(propName);
                    de.setRecordIndex(recordIndex);
                    de.setCause(e);
                    datumErrors.add(de);
                }
                hasDatumError = true;
                propValue = datumErrPlaceholder;
            }
            String propValueText = (propValue == null ? null : propValue
                    .toString());
            Cell cell = row.createCell(columnIndex);
            cell.setCellValue(StringUtils.defaultString(propValueText));

            if (hasDatumError) {
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
