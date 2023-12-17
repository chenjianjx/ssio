package org.ssio.spi.impl.htmltable.model.read;

import org.jsoup.nodes.Element;
import org.ssio.spi.impl.htmltable.rawhtml.RawHtmlParser;
import org.ssio.spi.impl.text.model.read.TextReadableCell;

public class HtmlTableReadableCell implements TextReadableCell {

    private String content;

    public static HtmlTableReadableCell newInstanceFromRawCell(RawHtmlParser rawHtmlParser, Element rawCell) {
        HtmlTableReadableCell cell = new HtmlTableReadableCell();
        cell.content = rawHtmlParser.getContent(rawCell);
        return cell;
    }


    @Override
    public String getContent() {
        return content;
    }



}
