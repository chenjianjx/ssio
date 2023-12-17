package org.ssio.spi.impl.htmltable.model.write;

import org.jsoup.nodes.Document;
import org.ssio.api.interfaces.htmltable.save.HtmlElementAttributes;
import org.ssio.spi.impl.htmltable.rawhtml.RawHtmlCreator;
import org.ssio.spi.interfaces.model.write.WritableWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class HtmlTableWritableWorkbook implements WritableWorkbook {

    private HtmlTableWritableSheet sheet;

    private String charset;

    private HtmlElementAttributes htmlElementAttributes;

    private RawHtmlCreator rawHtmlCreator;


    private HtmlTableWritableWorkbook() {
    }


    public static HtmlTableWritableWorkbook createNewWorkbook(String charset, HtmlElementAttributes htmlElementAttributes) {
        HtmlTableWritableWorkbook workbook = new HtmlTableWritableWorkbook();
        workbook.charset = charset;
        workbook.htmlElementAttributes = htmlElementAttributes;
        workbook.rawHtmlCreator = new RawHtmlCreator();
        return workbook;
    }

    @Override
    public HtmlTableWritableSheet createNewSheet() {
        this.sheet = HtmlTableWritableSheet.createEmptySheet();
        return this.sheet;
    }

    @Override
    public void write(OutputStream outputTarget) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(outputTarget, charset);
        Document document = new Document("");
        sheet.buildRawHtml(document, rawHtmlCreator, htmlElementAttributes);
        writer.write(rawHtmlCreator.toHtml(document, htmlElementAttributes));
        writer.flush();
    }
}
