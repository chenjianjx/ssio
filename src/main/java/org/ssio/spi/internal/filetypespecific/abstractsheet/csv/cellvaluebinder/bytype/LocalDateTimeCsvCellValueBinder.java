package org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.bytype;

import org.apache.commons.lang3.StringUtils;
import org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.CsvCellValueBinder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeCsvCellValueBinder extends CsvCellValueBinder {

    @Override
    protected String convertNonNullValueToCellText(String format, Object value) {
        LocalDateTime date = (LocalDateTime) value;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(date);
    }

    @Override
    protected Object parseFromCellText(String format, String text) {
        String string = StringUtils.trimToNull(text);
        return string == null ? null : parseLocalDateTime(string, format);
    }


    private LocalDateTime parseLocalDateTime(String string, String format) {
        return LocalDateTime.parse(string, DateTimeFormatter.ofPattern(format));
    }

}
