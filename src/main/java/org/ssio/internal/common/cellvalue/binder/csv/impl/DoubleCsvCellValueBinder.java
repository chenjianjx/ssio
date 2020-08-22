package org.ssio.internal.common.cellvalue.binder.csv.impl;

import org.apache.commons.lang3.StringUtils;
import org.ssio.internal.common.cellvalue.binder.csv.CsvCellValueBinder;

public class DoubleCsvCellValueBinder extends CsvCellValueBinder {

    @Override
    protected String convertNonNullValueToCellText(Object value) {
        return value.toString();
    }

    @Override
    protected Object parseFromCellText(String text) {
        String string = StringUtils.trimToNull(text);
        return string == null ? null : Double.parseDouble(string);
    }

}
