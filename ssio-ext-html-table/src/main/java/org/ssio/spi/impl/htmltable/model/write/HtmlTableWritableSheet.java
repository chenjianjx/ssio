package org.ssio.spi.impl.htmltable.model.write;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.ssio.api.interfaces.htmltable.save.HtmlElementAttributes;
import org.ssio.spi.impl.htmltable.rawhtml.RawHtmlCreator;
import org.ssio.spi.interfaces.model.write.WritableSheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.ssio.spi.impl.support.SpiModelHelper.checkRowSizeWhenCreatingNewRow;

public class HtmlTableWritableSheet implements WritableSheet {

    private List<HtmlTableWritableRow> rows = new ArrayList<>();

    public static HtmlTableWritableSheet createEmptySheet() {
        return new HtmlTableWritableSheet();
    }

    @Override
    public void autoSizeColumn(int columnIndex) {
        //do nothing;
    }

 

    @Override
    public HtmlTableWritableRow createNewRow(int rowIndex) {
        int currentSize = rows.size();
        checkRowSizeWhenCreatingNewRow(rowIndex, currentSize);
        HtmlTableWritableRow row = HtmlTableWritableRow.createEmptyRow();
        rows.add(row);
        return row;
    }

    public void buildRawHtml(Document document, RawHtmlCreator rawHtmlCreator, HtmlElementAttributes htmlElementAttributes) {
        Element tableElement = rawHtmlCreator.createTableElement(htmlElementAttributes == null ? new HashMap<>() : htmlElementAttributes.getTableAttributes());
        document.appendChild(tableElement);
        for (HtmlTableWritableRow row : rows) {
            row.buildRawHtml(rawHtmlCreator, tableElement);
        }
    }
}
