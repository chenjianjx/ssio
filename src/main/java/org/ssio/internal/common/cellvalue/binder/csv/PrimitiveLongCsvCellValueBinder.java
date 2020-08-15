package org.ssio.internal.common.cellvalue.binder.csv;

public class PrimitiveLongCsvCellValueBinder implements CsvCellValueBinder {

    @Override
    public String nonNullValueToCellText(Object value) {
        return value.toString();
    }
}
