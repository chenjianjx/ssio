package org.ssio.spi.impl.htmltable.model.read;

import org.jsoup.nodes.Element;
import org.ssio.spi.impl.htmltable.rawhtml.RawHtmlParser;
import org.ssio.spi.interfaces.model.read.ReadableCell;
import org.ssio.spi.interfaces.model.read.ReadableRow;

import java.util.ArrayList;
import java.util.List;

public class HtmlTableReadableRow implements ReadableRow {
    private List<HtmlTableReadableCell> cells = new ArrayList<>();

    public static HtmlTableReadableRow newInstanceFromRawRow(RawHtmlParser rawHtmlParser, Element rowElement) {
        HtmlTableReadableRow row = new HtmlTableReadableRow();
        List<Element> rawCells = rawHtmlParser.toCells(rowElement);
        for (Element rawCell : rawCells) {
            row.cells.add(HtmlTableReadableCell.newInstanceFromRawCell(rawHtmlParser, rawCell));
        }
        return row;
    }

    @Override
    public int getNumberOfCells() {
        return cells.size();
    }

    @Override
    public ReadableCell getCell(int columnIndex) {
        return cells.get(columnIndex);
    }


}
