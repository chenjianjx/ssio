package org.ssio.api.common.abstractsheet.model;

import org.ssio.internal.common.cellvalue.binder.SsCellValueBinder;

public interface SsCell {

    /**
     * read the value as a target java type
     *
     * @throws RuntimeException if the cell value and the java type are not compatible with each other
     */
    default Object readValueAsType(SsCellValueJavaType targetType, Class<Enum<?>> enumClassIfEnum, String format) throws RuntimeException {
        SsCellValueBinder cellValueBinder = this.getCellValueBinder(targetType, enumClassIfEnum);
        if (cellValueBinder == null) {
            throw new IllegalStateException();
        }
        return cellValueBinder.getValue(this, format);
    }

    /**
     * Write value of some type, to this cell
     *
     * @param valueType
     * @param valueEnumClassIfEnum
     * @param format only for some value types that involves a format
     * @param value
     * @throws RuntimeException
     */
    default void writeValueAsType(SsCellValueJavaType valueType, Class<Enum<?>> valueEnumClassIfEnum, String format, Object value) throws RuntimeException {
        SsCellValueBinder cellValueBinder = this.getCellValueBinder(valueType, valueEnumClassIfEnum);
        if (cellValueBinder == null) {
            throw new IllegalStateException();
        }

        if (value == null) {
            cellValueBinder.setNullValue(this);
        } else {
            cellValueBinder.setNonNullValue(this, format, value);
        }
    }

    SsCellValueBinder getCellValueBinder(SsCellValueJavaType javaType, Class<Enum<?>> enumClassIfEnum);

    void styleAsError();

    void styleAsHeader();
}
