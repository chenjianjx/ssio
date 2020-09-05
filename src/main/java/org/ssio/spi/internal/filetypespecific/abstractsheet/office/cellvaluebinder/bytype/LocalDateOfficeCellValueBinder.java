package org.ssio.spi.internal.filetypespecific.abstractsheet.office.cellvaluebinder.bytype;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.ssio.spi.internal.filetypespecific.abstractsheet.office.cellvaluebinder.OfficeCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.office.helper.PoiSupport;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class LocalDateOfficeCellValueBinder extends OfficeCellValueBinder {
    @Override
    protected void setNonNullValueToPoiCell(Cell poiCell, String format, Object value) {
        poiCell.setCellValue((LocalDate) value);


        PoiSupport.setDateCellStyle(poiCell, PoiSupport.adaptSimpleDateFormatForPoi(format));
    }

    @Override
    public LocalDate getValueFromPoiCell(Cell poiCell, String format) {
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
                return string == null ? null : parseLocalDate(string, format);
            }
            case BOOLEAN: {
                throw dateValueFromBooleanCellNotAllowedException();
            }
            case NUMERIC: {
                if (DateUtil.isCellDateFormatted(poiCell)) {
                    Date date = poiCell.getDateCellValue();
                    return date == null ? null : convertToLocalDate(date);
                } else {
                    throw dateValueFromVanilaNumericCellNotAllowedException();
                }
            }

            default:
                throw new UnsupportedOperationException("Unsupported cell type: " + poiCell.getCellType());
        }
    }

    private LocalDate parseLocalDate(String string, String format) {
        return LocalDate.parse(string, DateTimeFormatter.ofPattern(format));
    }

    public LocalDate convertToLocalDate(Date date) {

        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
