package org.ssio.internal.common.office.cellvalue.binder;

import org.apache.poi.ss.usermodel.Cell;

/**
 * Specify how to read a value from office cell and how to write a value to an office cell, according to the java type
 * Note: the implementations has to be stateless (thread safe)
 */
public interface OfficeCellValueBinder {
    void setNonNullValue(Cell cell, Object value);
}
