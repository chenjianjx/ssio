package org.ssio.internal.common.cellvalue.binder.office.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.ssio.internal.common.cellvalue.binder.office.OfficeCellValueBinder;

public class BooleanOfficeCellValueBinder extends OfficeCellValueBinder {
    @Override
    protected void setNonNullValueToPoiCell(Cell poiCell, String format, Object value) {
        poiCell.setCellValue(((Boolean) value));
    }

    @Override
    public Boolean getValueFromPoiCell(Cell poiCell) {
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
                return string == null ? null : Boolean.parseBoolean(string);
            }
            case BOOLEAN: {
                return poiCell.getBooleanCellValue();
            }
            case NUMERIC: {
                throw booleanValueFromNumericCellNotAllowedException();
            }

            default:
                throw new UnsupportedOperationException("Unsupported cell type: " + poiCell.getCellType());
        }
    }
}
