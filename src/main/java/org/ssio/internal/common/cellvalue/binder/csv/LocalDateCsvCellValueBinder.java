package org.ssio.internal.common.cellvalue.binder.csv;

import org.ssio.api.common.SsioConstants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateCsvCellValueBinder implements CsvCellValueBinder {

    @Override
    public String nonNullValueToCellText(Object value) {
        LocalDate date = (LocalDate) value;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(SsioConstants.DEFAULT_LOCAL_DATE_PATTERN);
        return formatter.format(date);
    }
}
