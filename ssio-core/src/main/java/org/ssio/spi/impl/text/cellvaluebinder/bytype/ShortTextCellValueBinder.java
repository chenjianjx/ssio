package org.ssio.spi.impl.text.cellvaluebinder.bytype;

import org.apache.commons.lang3.StringUtils;
import org.ssio.spi.impl.text.cellvaluebinder.TextCellValueBinder;

public class ShortTextCellValueBinder extends TextCellValueBinder {

    @Override
    protected String convertNonNullValueToCellText(String format, Object value) {
        return value.toString();
    }

    @Override
    protected Object parseFromCellText(String format, String text) {
        String string = StringUtils.trimToNull(text);
        return string == null ? null : Short.parseShort(string);
    }
}
