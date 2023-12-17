package org.ssio.spi.impl.csv.model.write;

import org.ssio.api.interfaces.typing.SimpleType;
import org.ssio.spi.impl.text.cellvaluebinder.TextCellValueBinderRepo;
import org.ssio.spi.impl.text.model.write.TextWritableCell;
import org.ssio.spi.interfaces.cellvaluebinder.CellValueBinder;

public class CsvWritableCell implements TextWritableCell {

    private String content;

    private CsvWritableCell() {

    }

    public static CsvWritableCell createEmptyCell() {
        CsvWritableCell cell = new CsvWritableCell();
        return cell;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public CellValueBinder getCellValueBinder(SimpleType javaType, Class<Enum<?>> enumClassIfEnum) {
        return TextCellValueBinderRepo.getBinder(javaType, enumClassIfEnum);
    }

    @Override
    public void styleAsError() {
        //do nothing
    }

    @Override
    public void styleAsHeader() {
        //do nothing
    }

}
