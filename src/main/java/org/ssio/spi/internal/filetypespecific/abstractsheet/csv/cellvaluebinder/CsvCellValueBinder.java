package org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder;


import org.ssio.spi.developerexternal.abstractsheet.cellvaluebinder.SsCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.csv.model.CsvCell;

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
