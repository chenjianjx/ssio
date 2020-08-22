package org.ssio.internal.common.cellvalue.binder.csv;


import org.ssio.api.common.abstractsheet.model.csv.CsvCell;
import org.ssio.internal.common.cellvalue.binder.SsCellValueBinder;

public abstract class CsvCellValueBinder implements SsCellValueBinder<CsvCell> {

    @Override
    public void setNonNullValue(CsvCell cell, Object value) {
        cell.setContent(this.convertNonNullValueToCellText(value));
    }

    @Override
    public void setNullValue(CsvCell cell) {
        cell.setContent(null);
    }

    @Override
    public Object getValue(CsvCell cell) {
        return this.parseFromCellText(cell.getContent());
    }

    protected abstract String convertNonNullValueToCellText(Object value);


    /**
     * @return the java type of the value will be the curated type of the binder
     */
    protected abstract Object parseFromCellText(String text);

}
