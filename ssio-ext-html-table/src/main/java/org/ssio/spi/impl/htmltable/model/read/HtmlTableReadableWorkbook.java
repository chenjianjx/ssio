package org.ssio.spi.impl.htmltable.model.read;

import org.jsoup.nodes.Element;
import org.ssio.spi.impl.htmltable.rawhtml.RawHtmlParser;
import org.ssio.spi.interfaces.model.read.ReadableSheet;
import org.ssio.spi.interfaces.model.read.ReadableWorkbook;

import java.io.Reader;

public class HtmlTableReadableWorkbook implements ReadableWorkbook {

    private HtmlTableReadableSheet sheet;

    private RawHtmlParser rawHtmlParser;

    private HtmlTableReadableWorkbook() {
    }

    public static HtmlTableReadableWorkbook newInstanceFromInput(Reader reader) {
        HtmlTableReadableWorkbook workbook = new HtmlTableReadableWorkbook();
        workbook.rawHtmlParser = new RawHtmlParser();
        createSheetToParse(reader, workbook);
        return workbook;
    }

    private static void createSheetToParse(Reader reader, HtmlTableReadableWorkbook workbook) {
        Element tableElement = workbook.rawHtmlParser.getFirstTable(reader);
        if (tableElement == null) {
            workbook.sheet = HtmlTableReadableSheet.newInstance();
        } else {
            workbook.sheet = HtmlTableReadableSheet.newInstanceFromRawTable(workbook.rawHtmlParser, tableElement);
        }
    }


    @Override
    public ReadableSheet getSheetToParse() {
        return this.sheet;
    }


    @Override
    public ReadableSheet getSheetByName(String sheetName) {
        return this.sheet;
    }

    @Override
    public ReadableSheet getSheetAt(int sheetIndex) {
        return this.sheet;
    }
}
