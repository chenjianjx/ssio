package org.ssio.spi.internal.filetypespecific.abstractsheet.office.cellvaluebinder.bytype;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.ssio.spi.internal.filetypespecific.abstractsheet.office.cellvaluebinder.OfficeCellValueBinder;

public class LongOfficeCellValueBinder extends OfficeCellValueBinder {
    @Override
    protected void setNonNullValueToPoiCell(Cell poiCell, String format, Object value) {
        poiCell.setCellValue(((Long) value).doubleValue());
    }

    @Override
    public Long getValueFromPoiCell(Cell poiCell, String format) {
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
