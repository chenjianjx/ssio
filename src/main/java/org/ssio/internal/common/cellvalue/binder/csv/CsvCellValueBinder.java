package org.ssio.internal.common.cellvalue.binder.csv;

/**
 * Specify how to read a value from a csv cell and how to write a value to an csv cell, according to the java type
 * Note: the implementations has to be stateless (thread safe)
 */
public interface CsvCellValueBinder {
    String nonNullValueToCellText(Object value);
}
