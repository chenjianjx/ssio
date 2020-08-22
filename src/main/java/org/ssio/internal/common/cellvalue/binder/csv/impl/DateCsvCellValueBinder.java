package org.ssio.internal.common.cellvalue.binder.csv.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.ssio.api.common.SsioConstants;
import org.ssio.internal.common.cellvalue.binder.csv.CsvCellValueBinder;

import java.text.ParseException;
import java.util.Date;

public class DateCsvCellValueBinder extends CsvCellValueBinder {

    @Override
    protected String convertNonNullValueToCellText(Object value) {
        Date date = (Date) value;
        return DateFormatUtils.format(date, SsioConstants.DEFAULT_LOCAL_DATE_TIME_PATTERN);
    }

    @Override
    protected Object parseFromCellText(String text) {
        String string = StringUtils.trimToNull(text);
        return string == null ? null : parseDate(string);
    }

    private Date parseDate(String string) {
        try {
            return DateUtils.parseDate(string, SsioConstants.DEFAULT_LOCAL_DATE_TIME_PATTERN);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
