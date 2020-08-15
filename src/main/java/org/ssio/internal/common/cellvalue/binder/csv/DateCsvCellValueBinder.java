package org.ssio.internal.common.cellvalue.binder.csv;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.ssio.api.common.SsioConstants;

import java.util.Date;

public class DateCsvCellValueBinder implements CsvCellValueBinder {

    @Override
    public String nonNullValueToCellText(Object value) {
        Date date = (Date) value;
        return DateFormatUtils.format(date, SsioConstants.DEFAULT_LOCAL_DATE_TIME_PATTERN);
    }
}
