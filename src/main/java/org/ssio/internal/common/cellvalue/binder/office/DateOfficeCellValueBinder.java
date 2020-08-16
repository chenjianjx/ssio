package org.ssio.internal.common.cellvalue.binder.office;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.ssio.api.common.SsioConstants;
import org.ssio.internal.common.office.PoiSupport;

import java.text.ParseException;
import java.util.Date;

public class DateOfficeCellValueBinder implements OfficeCellValueBinder {
    @Override
    public void setNonNullValue(Cell cell, Object value) {
        cell.setCellValue(((Date) value));


        PoiSupport.setDateCellStyle(cell, SsioConstants.DEFAULT_LOCAL_DATE_TIME_PATTERN_FOR_SPREADSHEET);

    }

    @Override
    public Date getValue(Cell poiCell) {
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
                return string == null ? null : parseDate(string);
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


    private Date parseDate(String string) {
        try {
            return DateUtils.parseDate(string, SsioConstants.DEFAULT_LOCAL_DATE_TIME_PATTERN);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
