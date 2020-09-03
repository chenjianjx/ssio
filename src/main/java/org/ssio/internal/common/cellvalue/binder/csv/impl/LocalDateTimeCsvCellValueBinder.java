package org.ssio.internal.common.cellvalue.binder.csv.impl;

import org.apache.commons.lang3.StringUtils;
import org.ssio.api.common.SsioConstants;
import org.ssio.internal.common.cellvalue.binder.csv.CsvCellValueBinder;

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
    protected Object parseFromCellText(String text) {
        String string = StringUtils.trimToNull(text);
        return string == null ? null : parseLocalDateTime(string);
    }


    private LocalDateTime parseLocalDateTime(String string) {
        return LocalDateTime.parse(string, DateTimeFormatter.ofPattern(SsioConstants.DEFAULT_LOCAL_DATE_TIME_PATTERN));
    }

}
