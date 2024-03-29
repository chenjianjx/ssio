package org.ssio.spi.impl.text.cellvaluebinder.bytype;

import org.apache.commons.lang3.StringUtils;
import org.ssio.spi.impl.text.cellvaluebinder.TextCellValueBinder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateTextCellValueBinder extends TextCellValueBinder {

    @Override
    protected String convertNonNullValueToCellText(String format, Object value) {
        LocalDate date = (LocalDate) value;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(date);
    }

    @Override
    protected Object parseFromCellText(String format, String text) {
        String string = StringUtils.trimToNull(text);
        return string == null ? null : parseLocalDate(string, format);
    }

    private LocalDate parseLocalDate(String string, String format) {
        return LocalDate.parse(string, DateTimeFormatter.ofPattern(format));
    }

}
