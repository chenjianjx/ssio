package org.ssio.internal.common.cellvalue.binder.csv;

import org.ssio.api.common.SsioConstants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeCsvCellValueBinder implements CsvCellValueBinder {

    @Override
    public String nonNullValueToCellText(Object value) {
        LocalDateTime date = (LocalDateTime) value;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(SsioConstants.DEFAULT_LOCAL_DATE_TIME_PATTERN);
        return formatter.format(date);
    }
}
