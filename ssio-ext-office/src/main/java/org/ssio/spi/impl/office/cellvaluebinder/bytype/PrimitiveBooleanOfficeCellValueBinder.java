package org.ssio.spi.impl.office.cellvaluebinder.bytype;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.ssio.spi.impl.office.cellvaluebinder.OfficeCellValueBinder;

public class PrimitiveBooleanOfficeCellValueBinder extends OfficeCellValueBinder {
    @Override
    protected void setNonNullValueToPoiCell(Cell poiCell, String format, Object value) {
        poiCell.setCellValue((boolean) value);
    }

    /**
     * @return won't be null
     */
    @Override
    public Boolean getValueFromPoiCell(Cell poiCell, String format) {

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
                    return Boolean.parseBoolean(string);
                }
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
