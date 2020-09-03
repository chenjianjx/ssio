package org.ssio.internal.common.cellvalue.binder.csv.impl;

import org.apache.commons.lang3.StringUtils;
import org.ssio.api.common.SsioConstants;
import org.ssio.internal.common.cellvalue.binder.csv.CsvCellValueBinder;

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
    protected Object parseFromCellText(String text) {
        String string = StringUtils.trimToNull(text);
        return string == null ? null : parseLocalDate(string);
    }

    private LocalDate parseLocalDate(String string) {
        return LocalDate.parse(string, DateTimeFormatter.ofPattern(SsioConstants.DEFAULT_LOCAL_DATE_PATTERN));
    }

}
