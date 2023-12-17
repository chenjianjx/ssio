package org.ssio.spi.impl.htmltable.model.write;

import org.jsoup.nodes.Element;
import org.ssio.spi.impl.htmltable.rawhtml.RawHtmlCreator;
import org.ssio.spi.interfaces.model.write.WritableCell;
import org.ssio.spi.interfaces.model.write.WritableRow;

import java.util.ArrayList;
import java.util.List;

public class HtmlTableWritableRow implements WritableRow {
    private List<HtmlTableWritableCell> cells = new ArrayList<>();

    public static HtmlTableWritableRow createEmptyRow() {
        return new HtmlTableWritableRow();
    }

    @Override
    public WritableCell createCell(int columnIndex) {
        int currentSize = cells.size();
        int nextIndex = currentSize;
        HtmlTableWritableCell emptyCellSupplier = HtmlTableWritableCell.createEmptyCell();
        HtmlTableWritableCell newCell = null;
        for (int i = nextIndex; i <= columnIndex; i++) {
            newCell = emptyCellSupplier;
            cells.add(newCell);
        }
        return newCell;
    }


    public void buildRawHtml(RawHtmlCreator rawHtmlCreator, Element tableElement) {
        Element rowElement = rawHtmlCreator.createRow(tableElement);
        for (HtmlTableWritableCell cell : cells) {
            cell.buildRawHtml(rawHtmlCreator, rowElement);
        }
    }
}
