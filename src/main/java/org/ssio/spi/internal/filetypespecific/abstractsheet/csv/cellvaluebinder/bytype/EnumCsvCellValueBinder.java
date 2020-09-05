package org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.bytype;

import org.apache.commons.lang3.StringUtils;
import org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.CsvCellValueBinder;

public class EnumCsvCellValueBinder<T extends Enum<T>> extends CsvCellValueBinder {
    private final Class<T> enumClass;

    public EnumCsvCellValueBinder(Class<T> enumClass) {
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
