package org.ssio.internal.common.cellvalue.binder.office.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.ssio.api.common.SsioConstants;
import org.ssio.internal.common.cellvalue.binder.office.OfficeCellValueBinder;
import org.ssio.internal.common.office.PoiSupport;

import java.text.ParseException;
import java.util.Date;

public class DateOfficeCellValueBinder extends OfficeCellValueBinder {
    @Override
    protected void setNonNullValueToPoiCell(Cell poiCell, Object value) {
        poiCell.setCellValue(((Date) value));
        PoiSupport.setDateCellStyle(poiCell, SsioConstants.DEFAULT_LOCAL_DATE_TIME_PATTERN_FOR_SPREADSHEET);
    }

    @Override
    public Date getValueFromPoiCell(Cell poiCell) {
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
