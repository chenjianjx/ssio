package org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.bytype;

import org.apache.commons.lang3.StringUtils;
import org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.CsvCellValueBinder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateCsvCellValueBinder extends CsvCellValueBinder {

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
