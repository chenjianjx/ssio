package org.ssio.spi.impl.abstractsheet.filetypespecific.csv.cellvaluebinder.bytype;

import org.apache.commons.lang3.StringUtils;
import org.ssio.spi.impl.abstractsheet.filetypespecific.csv.cellvaluebinder.CsvCellValueBinder;

import java.math.BigDecimal;

public class BigDecimalCsvCellValueBinder extends CsvCellValueBinder {

    @Override
    protected String convertNonNullValueToCellText(String format, Object value) {
        return value.toString();
    }

    @Override
    protected Object parseFromCellText(String format, String text) {
        String string = StringUtils.trimToNull(text);
        return string == null ? null : new BigDecimal(string);
    }
}
