package org.ssio.spi.impl.text.cellvaluebinder;


import org.ssio.spi.impl.text.model.read.TextReadableCell;
import org.ssio.spi.impl.text.model.write.TextWritableCell;
import org.ssio.spi.interfaces.cellvaluebinder.CellValueBinder;

public abstract class TextCellValueBinder<RC extends TextReadableCell, RW extends TextWritableCell> implements CellValueBinder<RC, RW> {

    @Override
    public void setNonNullValue(RW cell, String format, Object value) {
        cell.setContent(this.convertNonNullValueToCellText(format, value));
    }

    @Override
    public void setNullValue(RW cell) {
        cell.setContent(null);
    }

    @Override
    public Object getValue(RC cell, String format) {
        return this.parseFromCellText(format, cell.getContent());
    }

    protected abstract String convertNonNullValueToCellText(String format, Object value);


    /**
     * @return the java type of the value will be the curated type of the binder
     */
    protected abstract Object parseFromCellText(String format, String text);

}
