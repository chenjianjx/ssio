package org.ssio.internal.common.cellvalue.binder.office.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.ssio.internal.common.cellvalue.binder.office.OfficeCellValueBinder;

import java.math.BigInteger;

public class BigIntegerOfficeCellValueBinder extends OfficeCellValueBinder {
    @Override
    protected void setNonNullValueToPoiCell(Cell poiCell, Object value) {
        poiCell.setCellValue(((BigInteger)value).doubleValue());
    }

    @Override
    protected BigInteger getValueFromPoiCell(Cell poiCell) {
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