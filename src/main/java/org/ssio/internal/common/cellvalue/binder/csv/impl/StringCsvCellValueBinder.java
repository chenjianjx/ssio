package org.ssio.internal.common.cellvalue.binder.csv.impl;

import org.apache.commons.lang3.StringUtils;
import org.ssio.internal.common.cellvalue.binder.csv.CsvCellValueBinder;

public class StringCsvCellValueBinder extends CsvCellValueBinder {

    @Override
    protected String convertNonNullValueToCellText(Object value) {
        return value.toString();
    }

    @Override
    protected Object parseFromCellText(String text) {
        if (StringUtils.isEmpty(text)) {
            return null;  // "" should be treated as null
        } else {
            return text; //no trimming
        }

    }
}
