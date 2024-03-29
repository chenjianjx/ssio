package org.ssio.spi.impl.text.cellvaluebinder.bytype;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.ssio.spi.impl.text.cellvaluebinder.TextCellValueBinder;

import java.text.ParseException;
import java.util.Date;

public class DateTextCellValueBinder extends TextCellValueBinder {

    @Override
    protected String convertNonNullValueToCellText(String format, Object value) {
        Date date = (Date) value;
        return DateFormatUtils.format(date, format);
    }

    @Override
    protected Object parseFromCellText(String format, String text) {
        String string = StringUtils.trimToNull(text);
        return string == null ? null : parseDate(string, format);
    }

    private Date parseDate(String string, String format) {
        try {
            return DateUtils.parseDate(string, format);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
