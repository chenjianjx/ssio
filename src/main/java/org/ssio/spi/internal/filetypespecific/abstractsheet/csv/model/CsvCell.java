package org.ssio.spi.internal.filetypespecific.abstractsheet.csv.model;

import org.ssio.api.external.typing.SimpleTypeEnum;
import org.ssio.spi.developerexternal.abstractsheet.cellvaluebinder.SsCellValueBinder;
import org.ssio.spi.developerexternal.abstractsheet.model.SsCell;
import org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.CsvCellValueBinderRepo;

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
