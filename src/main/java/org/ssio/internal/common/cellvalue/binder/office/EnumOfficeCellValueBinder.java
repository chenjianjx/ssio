package org.ssio.internal.common.cellvalue.binder.office;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;

public class EnumOfficeCellValueBinder<T extends Enum<T>> implements OfficeCellValueBinder {
    private final Class<T> enumClass;

    public EnumOfficeCellValueBinder(Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public void setNonNullValue(Cell cell, Object value) {
        cell.setCellValue(((Enum<?>) value).name());
    }

    @Override
    public Enum<?> getValue(Cell poiCell) {
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
