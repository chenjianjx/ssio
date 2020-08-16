package org.ssio.internal.common.cellvalue.binder.office;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;

public class PrimitiveIntOfficeCellValueBinder implements OfficeCellValueBinder {
    @Override
    public void setNonNullValue(Cell cell, Object value) {
        cell.setCellValue((int) value);
    }

    /**
     * @return won't be null
     */
    @Override
    public Integer getValue(Cell poiCell) {
        switch (poiCell.getCellType()) {
            case _NONE: {
                throw primitiveValueFromEmptyCellNotAllowedException();
            }
            case BLANK: {
                throw primitiveValueFromEmptyCellNotAllowedException();
            }
            case ERROR: {
                throw cannotReadFormulaErrorCellException();
            }
            case FORMULA: {
                throw noSupportToReadFormulaCellException();
            }
            case STRING: {
                String string = StringUtils.trimToNull(poiCell.getStringCellValue());
                if (string == null) {
                    throw primitiveValueFromEmptyCellNotAllowedException();
                } else {
                    return Integer.parseInt(string);
                }
            }
            case BOOLEAN: {
                throw numericValueFromBooleanCellNotAllowedException();
            }
            case NUMERIC: {
                double v = poiCell.getNumericCellValue();
                return (int) v;
            }

            default:
                throw new UnsupportedOperationException("Unsupported cell type: " + poiCell.getCellType());
        }
    }
}
