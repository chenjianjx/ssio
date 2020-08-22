package org.ssio.internal.common.cellvalue.binder;

import org.ssio.api.common.abstractsheet.model.SsCell;

/**
 * Specify how to read a value from a cell and how to write a value to a cell, according to the java type
 * Note: the implementations may be stateful (non-thread safe)
 */
public interface SsCellValueBinder<T extends SsCell> {

    /**
     * set value to a cell
     *
     * @param cell
     * @param value the java type of the value must be the curated type of the binder
     */
    void setNonNullValue(T cell, Object value);

    /**
     * set null value to a cell
     *
     * @param cell
     */
    void setNullValue(T cell);

    /**
     * @param cell
     * @return the java type of the value will be the curated type of the binder
     */
    Object getValue(T cell);

    default RuntimeException primitiveValueFromEmptyCellNotAllowedException() {
        throw new RuntimeException("Cannot read primitive value (boolean, int, double etc.) from an empty cell");
    }


}
