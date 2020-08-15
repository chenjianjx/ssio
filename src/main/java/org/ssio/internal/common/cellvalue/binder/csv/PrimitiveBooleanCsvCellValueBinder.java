package org.ssio.internal.common.cellvalue.binder.csv;

public class PrimitiveBooleanCsvCellValueBinder implements CsvCellValueBinder {

    @Override
    public String nonNullValueToCellText(Object value) {
        return value.toString();
    }
}
