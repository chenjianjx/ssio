package org.ssio.internal.common.cellvalue.binder.csv.impl;

import org.apache.commons.lang3.StringUtils;
import org.ssio.internal.common.cellvalue.binder.csv.CsvCellValueBinder;

public class PrimitiveIntCsvCellValueBinder extends CsvCellValueBinder {

    @Override
    protected String convertNonNullValueToCellText(String format, Object value) {
        return value.toString();
    }

    @Override
    protected Object parseFromCellText(String format, String text) {
        String string = StringUtils.trimToNull(text);
        if (string == null) {
            throw primitiveValueFromEmptyCellNotAllowedException();
        } else {
            return Integer.parseInt(string);
        }
    }
}
