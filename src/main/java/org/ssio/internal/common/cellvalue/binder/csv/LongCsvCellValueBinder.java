package org.ssio.internal.common.cellvalue.binder.csv;

import org.apache.poi.ss.usermodel.Cell;

public class LongCsvCellValueBinder implements CsvCellValueBinder {

    @Override
    public String nonNullValueToCellText(Object value) {
        return value.toString();
    }
}
