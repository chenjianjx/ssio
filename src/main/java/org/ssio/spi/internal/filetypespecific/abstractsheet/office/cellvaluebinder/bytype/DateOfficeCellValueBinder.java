package org.ssio.spi.internal.filetypespecific.abstractsheet.office.cellvaluebinder.bytype;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.ssio.spi.internal.filetypespecific.abstractsheet.office.cellvaluebinder.OfficeCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.office.helper.PoiSupport;

import java.text.ParseException;
import java.util.Date;

public class DateOfficeCellValueBinder extends OfficeCellValueBinder {
    @Override
    protected void setNonNullValueToPoiCell(Cell poiCell, String format, Object value) {
        poiCell.setCellValue(((Date) value));
        PoiSupport.setDateCellStyle(poiCell, PoiSupport.adaptSimpleDateFormatForPoi(format));
    }

    @Override
    public Date getValueFromPoiCell(Cell poiCell, String format) {
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
                return string == null ? null : parseDate(string, format);
            }
            case BOOLEAN: {
                throw dateValueFromBooleanCellNotAllowedException();
            }
            case NUMERIC: {
                if (DateUtil.isCellDateFormatted(poiCell)) {
                    return poiCell.getDateCellValue();
                } else {
                    throw dateValueFromVanilaNumericCellNotAllowedException();
                }
            }
            default:
                throw new UnsupportedOperationException("Unsupported cell type: " + poiCell.getCellType());
        }
    }


    private Date parseDate(String string, String format) {
        try {
            return DateUtils.parseDate(string, format);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
