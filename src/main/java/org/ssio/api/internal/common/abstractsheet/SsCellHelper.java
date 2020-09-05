package org.ssio.api.internal.common.abstractsheet;

import org.ssio.api.external.typing.SimpleTypeEnum;
import org.ssio.spi.developerexternal.abstractsheet.cellvaluebinder.SsCellValueBinder;
import org.ssio.spi.developerexternal.abstractsheet.model.SsCell;

/**
 * some file-type-independent logic
 */
public class SsCellHelper {

    /**
     * read the value into a target java type
     *
     * @throws RuntimeException if the cell value and the java type are not compatible with each other
     */
    public Object readValueAsType(SsCell cell, SimpleTypeEnum targetType, Class<Enum<?>> enumClassIfEnum, String format) throws RuntimeException {
        SsCellValueBinder cellValueBinder = cell.getCellValueBinder(targetType, enumClassIfEnum);
        if (cellValueBinder == null) {
            throw new IllegalStateException();
        }
        return cellValueBinder.getValue(cell, format);
    }

    /**
     * Write some value of some java type, to this cell
     *
     * @param valueType
     * @param valueEnumClassIfEnum
     * @param format               only for some value types that involves a format
     * @param value
     * @throws RuntimeException
     */
    public void writeValueAsType(SsCell cell, SimpleTypeEnum valueType, Class<Enum<?>> valueEnumClassIfEnum, String format, Object value) throws RuntimeException {
        SsCellValueBinder cellValueBinder = cell.getCellValueBinder(valueType, valueEnumClassIfEnum);
        if (cellValueBinder == null) {
            throw new IllegalStateException();
        }

        if (value == null) {
            cellValueBinder.setNullValue(cell);
        } else {
            cellValueBinder.setNonNullValue(cell, format, value);
        }
    }
}
