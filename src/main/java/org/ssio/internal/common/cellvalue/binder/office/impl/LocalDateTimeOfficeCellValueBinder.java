package org.ssio.internal.common.cellvalue.binder.office.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.ssio.api.common.SsioConstants;
import org.ssio.internal.common.cellvalue.binder.office.OfficeCellValueBinder;
import org.ssio.internal.common.office.PoiSupport;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class LocalDateTimeOfficeCellValueBinder extends OfficeCellValueBinder {
    @Override
    protected void setNonNullValueToPoiCell(Cell poiCell, Object value) {
        poiCell.setCellValue((LocalDateTime) value);

        PoiSupport.setDateCellStyle(poiCell, SsioConstants.DEFAULT_LOCAL_DATE_TIME_PATTERN_FOR_SPREADSHEET);
    }

    @Override
    public LocalDateTime getValueFromPoiCell(Cell poiCell) {
        switch (poiCell.getCellType()) {
            case _NONE: {
                return null;
            }
            case BLANK: {
                return null;
            }
            case ERROR: {
                throw cannotReadFormulaErrorCellException();
            }
            case FORMULA: {
                throw noSupportToReadFormulaCellException();
            }
            case STRING: {
                String string = StringUtils.trimToNull(poiCell.getStringCellValue());
                return string == null ? null : parseLocalDateTime(string);
            }
            case BOOLEAN: {
                throw dateValueFromBooleanCellNotAllowedException();
            }
            case NUMERIC: {
                if (DateUtil.isCellDateFormatted(poiCell)) {
                    Date date = poiCell.getDateCellValue();
                    return date == null ? null : convertToLocalDateTime(date);
                } else {
                    throw dateValueFromVanilaNumericCellNotAllowedException();
                }
            }


            default:
                throw new UnsupportedOperationException("Unsupported cell type: " + poiCell.getCellType());
        }

    }

    private LocalDateTime parseLocalDateTime(String string) {
        return LocalDateTime.parse(string, DateTimeFormatter.ofPattern(SsioConstants.DEFAULT_LOCAL_DATE_TIME_PATTERN));
    }

    public LocalDateTime convertToLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
