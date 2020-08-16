package org.ssio.internal.common.cellvalue.binder.office;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;

public class LongOfficeCellValueBinder implements OfficeCellValueBinder {
    @Override
    public void setNonNullValue(Cell cell, Object value) {
        cell.setCellValue(((Long) value).doubleValue());
    }

    @Override
    public Long getValue(Cell poiCell) {
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
                return string == null ? null : Long.parseLong(string);
            }
            case BOOLEAN: {
                throw numericValueFromBooleanCellNotAllowedException();
            }
            case NUMERIC: {
                double v = poiCell.getNumericCellValue();
                return (long) v;
            }

            default:
                throw new UnsupportedOperationException("Unsupported cell type: " + poiCell.getCellType());
        }
    }
}
