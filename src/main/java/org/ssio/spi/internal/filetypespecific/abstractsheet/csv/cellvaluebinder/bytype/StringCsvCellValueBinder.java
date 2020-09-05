package org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.bytype;

import org.apache.commons.lang3.StringUtils;
import org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.CsvCellValueBinder;

public class StringCsvCellValueBinder extends CsvCellValueBinder {

    @Override
    protected String convertNonNullValueToCellText(String format, Object value) {
        return value.toString();
    }

    @Override
    protected Object parseFromCellText(String format, String text) {
        if (StringUtils.isEmpty(text)) {
            return null;  // "" should be treated as null
        } else {
            return text; //no trimming
        }

    }
}
