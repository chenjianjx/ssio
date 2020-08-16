package org.ssio.internal.common.cellvalue.binder.office;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;

import java.math.BigDecimal;

public class BigDecimalOfficeCellValueBinder implements OfficeCellValueBinder {
    @Override
    public void setNonNullValue(Cell cell, Object value) {
        cell.setCellValue(((BigDecimal) value).doubleValue());
    }

    @Override
    public BigDecimal getValue(Cell poiCell) {
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
                return string == null ? null : new BigDecimal(string);
            }
            case BOOLEAN: {
                throw numericValueFromBooleanCellNotAllowedException();
            }
            case NUMERIC: {
                double v = poiCell.getNumericCellValue();
                return BigDecimal.valueOf(v);
            }

            default:
                throw new UnsupportedOperationException("Unsupported cell type: " + poiCell.getCellType());
        }
    }
}
