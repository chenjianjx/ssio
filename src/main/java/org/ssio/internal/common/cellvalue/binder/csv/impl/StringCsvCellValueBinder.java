package org.ssio.internal.common.cellvalue.binder.csv.impl;

import org.ssio.internal.common.cellvalue.binder.csv.CsvCellValueBinder;

public class StringCsvCellValueBinder extends CsvCellValueBinder {

    @Override
    protected String convertNonNullValueToCellText(Object value) {
        return value.toString();
    }

    @Override
    protected Object parseFromCellText(String text) {
        return text; //no trimming
    }
}
