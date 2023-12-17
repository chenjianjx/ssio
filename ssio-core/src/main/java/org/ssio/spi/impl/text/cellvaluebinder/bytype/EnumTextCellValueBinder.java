package org.ssio.spi.impl.text.cellvaluebinder.bytype;

import org.apache.commons.lang3.StringUtils;
import org.ssio.spi.impl.text.cellvaluebinder.TextCellValueBinder;

public class EnumTextCellValueBinder<T extends Enum<T>> extends TextCellValueBinder {
    private final Class<T> enumClass;

    public EnumTextCellValueBinder(Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    protected String convertNonNullValueToCellText(String format, Object value) {
        return value.toString();
    }


    @Override
    protected Object parseFromCellText(String format, String text) {
        String string = StringUtils.trimToNull(text);
        return string == null ? null : Enum.valueOf(enumClass, string);
    }
}
