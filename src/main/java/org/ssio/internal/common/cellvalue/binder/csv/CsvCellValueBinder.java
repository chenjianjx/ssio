package org.ssio.internal.common.cellvalue.binder.csv;


import org.ssio.api.common.abstractsheet.model.csv.CsvCell;
import org.ssio.internal.common.cellvalue.binder.SsCellValueBinder;

public abstract class CsvCellValueBinder implements SsCellValueBinder<CsvCell> {

    @Override
    public void setNonNullValue(CsvCell cell, String format, Object value) {
        cell.setContent(this.convertNonNullValueToCellText(format, value));
    }

    @Override
    public void setNullValue(CsvCell cell) {
        cell.setContent(null);
    }

    @Override
    public Object getValue(CsvCell cell, String format) {
        return this.parseFromCellText(format, cell.getContent());
    }

    protected abstract String convertNonNullValueToCellText(String format, Object value);


    /**
     * @return the java type of the value will be the curated type of the binder
     */
    protected abstract Object parseFromCellText(String format, String text);

}
