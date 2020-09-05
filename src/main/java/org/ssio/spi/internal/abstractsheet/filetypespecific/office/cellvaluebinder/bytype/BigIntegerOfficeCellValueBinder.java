package org.ssio.spi.internal.abstractsheet.filetypespecific.office.cellvaluebinder.bytype;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.ssio.spi.internal.abstractsheet.filetypespecific.office.cellvaluebinder.OfficeCellValueBinder;

import java.math.BigInteger;

public class BigIntegerOfficeCellValueBinder extends OfficeCellValueBinder {
    @Override
    protected void setNonNullValueToPoiCell(Cell poiCell, String format, Object value) {
        poiCell.setCellValue(((BigInteger)value).doubleValue());
    }

    @Override
    protected BigInteger getValueFromPoiCell(Cell poiCell, String format) {
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
                return string == null ? null : new BigInteger(string);
            }
            case BOOLEAN: {
                throw numericValueFromBooleanCellNotAllowedException();
            }
            case NUMERIC: {
                double v = poiCell.getNumericCellValue();
                return BigInteger.valueOf((long)v);
            }

            default:
                throw new UnsupportedOperationException("Unsupported cell type: " + poiCell.getCellType());
        }
    }
}