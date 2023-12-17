package org.ssio.spi.interfaces.cellvaluebinder;

import org.ssio.spi.interfaces.model.read.ReadableCell;
import org.ssio.spi.interfaces.model.write.WritableCell;

/**
 * Tell ssio how to read a value from a cell and how to write a value to a cell, according to the java type
 * Note: the implementations may be stateful (non-thread safe)
 */
public interface CellValueBinder<RC extends ReadableCell, WC extends WritableCell> {

    /**
     * set value to a cell
     *
     * @param cell
     * @param format
     * @param value  the java type of the value must be the curated type of the binder
     */
    void setNonNullValue(WC cell, String format, Object value);

    /**
     * set null value to a cell
     *
     * @param cell
     */
    void setNullValue(WC cell);

    /**
     * @param cell
     * @param format
     * @return the java type of the value will be the curated type of the binder
     */
    Object getValue(RC cell, String format);

    default RuntimeException primitiveValueFromEmptyCellNotAllowedException() {
        throw new RuntimeException("Cannot read primitive value (boolean, int, double etc.) from an empty cell");
    }


}
