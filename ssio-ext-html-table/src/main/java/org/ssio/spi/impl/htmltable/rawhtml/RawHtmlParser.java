package org.ssio.spi.impl.htmltable.rawhtml;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class RawHtmlParser {

    public Element getFirstTable(Reader reader) {
        Document document = Jsoup.parse(toString(reader));
        return document.selectFirst("table");
    }

    public List<Element> toRows(Element tableElement) {
        return tableElement.select("tr");
    }

    public List<Element> toCells(Element rowElement) {
        return rowElement.select("td, th");
    }

    public String getContent(Element cell) {
        return cell.wholeText();
    }

    private static String toString(Reader reader) {
        try {
            return IOUtils.toString(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}