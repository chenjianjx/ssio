package org.ssio.spi.impl.htmltable.model.read;

import org.jsoup.nodes.Element;
import org.ssio.spi.impl.htmltable.rawhtml.RawHtmlParser;
import org.ssio.spi.interfaces.model.read.ReadableRow;
import org.ssio.spi.interfaces.model.read.ReadableSheet;

import java.util.ArrayList;
import java.util.List;

public class HtmlTableReadableSheet implements ReadableSheet {

    private List<HtmlTableReadableRow> rows = new ArrayList<>();

    /**
     * @param tableElement
     
     */
    public static HtmlTableReadableSheet newInstanceFromRawTable(RawHtmlParser rawHtmlParser, Element tableElement) {

        HtmlTableReadableSheet sheet = new HtmlTableReadableSheet();
        List<Element> rowElements = rawHtmlParser.toRows(tableElement);
        for (Element rowElement : rowElements) {
            sheet.rows.add(HtmlTableReadableRow.newInstanceFromRawRow(rawHtmlParser, rowElement));
        }
        return sheet;
    }

    public static HtmlTableReadableSheet newInstance(){
        return new HtmlTableReadableSheet();
    }


    @Override
    public String getSheetName() {
        return null;
    }

    @Override
    public ReadableRow getRow(int rowIndex) {
        return rows.get(rowIndex);
    }


    @Override
    public int getNumberOfRows() {
        return rows.size();
    }

}
