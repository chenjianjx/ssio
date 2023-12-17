package org.ssio.spi.impl.htmltable.model.write;

import org.jsoup.nodes.Element;
import org.ssio.api.interfaces.typing.SimpleType;
import org.ssio.spi.impl.htmltable.rawhtml.RawHtmlCreator;
import org.ssio.spi.impl.text.cellvaluebinder.TextCellValueBinderRepo;
import org.ssio.spi.impl.text.model.write.TextWritableCell;
import org.ssio.spi.interfaces.cellvaluebinder.CellValueBinder;


public class HtmlTableWritableCell implements TextWritableCell {

    private String content;

    private boolean header;

    public static HtmlTableWritableCell createEmptyCell() {
        HtmlTableWritableCell cell = new HtmlTableWritableCell();
        return cell;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
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
        this.header = true;
    }

    public void buildRawHtml(RawHtmlCreator rawHtmlCreator, Element rowElement) {
        rawHtmlCreator.createCell(rowElement, content, header);
    }
}
