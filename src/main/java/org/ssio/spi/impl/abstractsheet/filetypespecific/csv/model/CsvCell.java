package org.ssio.spi.impl.abstractsheet.filetypespecific.csv.model;

import org.ssio.api.interfaces.typing.SimpleTypeEnum;
import org.ssio.spi.impl.abstractsheet.filetypespecific.csv.cellvaluebinder.CsvCellValueBinderRepo;
import org.ssio.spi.interfaces.abstractsheet.cellvaluebinder.SsCellValueBinder;
import org.ssio.spi.interfaces.abstractsheet.model.SsCell;

public class CsvCell implements SsCell {

    private String content;

    private CsvCell() {

    }

    public static CsvCell createEmptyCell() {
        CsvCell cell = new CsvCell();
        return cell;
    }

    public static CsvCell createWithContent(String content) {
        CsvCell cell = new CsvCell();
        cell.content = content;
        return cell;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public SsCellValueBinder getCellValueBinder(SimpleTypeEnum javaType, Class<Enum<?>> enumClassIfEnum) {
        return CsvCellValueBinderRepo.getCsvCellValueBinder(javaType, enumClassIfEnum);
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
