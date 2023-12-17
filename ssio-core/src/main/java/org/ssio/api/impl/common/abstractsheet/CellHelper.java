package org.ssio.api.impl.common.abstractsheet;

import org.ssio.api.interfaces.typing.SimpleType;
import org.ssio.spi.interfaces.cellvaluebinder.CellValueBinder;
import org.ssio.spi.interfaces.model.read.ReadableCell;
import org.ssio.spi.interfaces.model.write.WritableCell;

/**
 * some file-type-independent logic
 */
public class CellHelper {

    /**
     * read the value into a target java type
     *
     * @throws RuntimeException if the cell value and the java type are not compatible with each other
     */
    public Object readValueAsType(ReadableCell cell, SimpleType targetType, Class<Enum<?>> enumClassIfEnum, String format) throws RuntimeException {
        CellValueBinder cellValueBinder = cell.getCellValueBinder(targetType, enumClassIfEnum);
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
    public void writeValueAsType(WritableCell cell, SimpleType valueType, Class<Enum<?>> valueEnumClassIfEnum, String format, Object value) throws RuntimeException {
        CellValueBinder cellValueBinder = cell.getCellValueBinder(valueType, valueEnumClassIfEnum);
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
