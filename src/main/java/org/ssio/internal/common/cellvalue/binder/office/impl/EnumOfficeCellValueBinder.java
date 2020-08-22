package org.ssio.internal.common.cellvalue.binder.office.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.ssio.internal.common.cellvalue.binder.office.OfficeCellValueBinder;

public class EnumOfficeCellValueBinder<T extends Enum<T>> extends OfficeCellValueBinder {
    private final Class<T> enumClass;

    public EnumOfficeCellValueBinder(Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    protected void setNonNullValueToPoiCell(Cell poiCell, Object value) {
        poiCell.setCellValue(((Enum<?>) value).name());
    }

    @Override
    public Enum<?> getValueFromPoiCell(Cell poiCell) {
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
                return string == null ? null : Enum.valueOf(enumClass, string);
            }
            case BOOLEAN: {
                throw enumValueFromBooleanCellNotAllowedException();
            }
            case NUMERIC: {
                throw enumValueFromNumericCellNotAllowedException();
            }

            default:
                throw new UnsupportedOperationException("Unsupported cell type: " + poiCell.getCellType());
        }
    }
}
