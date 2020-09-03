package org.ssio.internal.common.cellvalue.binder.office.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.ssio.internal.common.cellvalue.binder.office.OfficeCellValueBinder;

public class StringOfficeCellValueBinder extends OfficeCellValueBinder {
    @Override
    protected void setNonNullValueToPoiCell(Cell poiCell, String format, Object value) {
        poiCell.setCellValue((String) value);
    }

    @Override
    public String getValueFromPoiCell(Cell poiCell) {
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
                String string = poiCell.getStringCellValue(); //no trimming
                return string;
            }
            case BOOLEAN: {
                return String.valueOf(poiCell.getBooleanCellValue());
            }
            case NUMERIC: {
                return String.valueOf(poiCell.getNumericCellValue());
            }

            default:
                throw new UnsupportedOperationException("Unsupported cell type: " + poiCell.getCellType());
        }
    }
}
