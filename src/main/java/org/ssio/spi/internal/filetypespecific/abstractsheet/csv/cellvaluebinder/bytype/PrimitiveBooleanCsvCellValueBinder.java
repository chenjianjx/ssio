package org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.bytype;

import org.apache.commons.lang3.StringUtils;
import org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.CsvCellValueBinder;

public class PrimitiveBooleanCsvCellValueBinder extends CsvCellValueBinder {

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
            return Boolean.parseBoolean(string);
        }
    }


}
