package org.ssio.spi.impl.htmltable.rawhtml;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Safelist;
import org.ssio.api.interfaces.htmltable.save.HtmlElementAttributes;

import java.util.HashMap;
import java.util.Map;

public class RawHtmlCreator {

    /**
     * @param tableAttributes not null
     
     */
    public Element createTableElement(Map<String, String> tableAttributes) {
        Element table = new Element("table");
        for (Map.Entry<String, String> ta : tableAttributes.entrySet()) {
            table.attr(ta.getKey(), ta.getValue());
        }
        return table;
    }

    public Element createRow(Element tableElement) {
        Element row = new Element("tr");
        tableElement.appendChild(row);
        return row;
    }

    public Element createCell(Element rowElement, String content, boolean header) {
        Element cell = new Element(header ? "th" : "td");
        if (content != null) {
            cell.text(content);
        }
        rowElement.appendChild(cell);
        return cell;
    }

    public String toHtml(Document document, HtmlElementAttributes htmlElementAttributes) {
        Map<String, String> tableAttributes = htmlElementAttributes == null ? new HashMap<>() : htmlElementAttributes.getTableAttributes();

        document.outputSettings(new Document.OutputSettings().prettyPrint(false));//makes html() preserve linebreaks and spacing
        document.select("br").append("\\n");
        document.select("p").prepend("\\n\\n");
        String s = document.html().replaceAll("\\\\n", "\n");
        Safelist safeList = Safelist.relaxed();

        for (String taKey : tableAttributes.keySet()) {
            safeList.addAttributes("table", taKey);
        }

        return Jsoup.clean(s, "", safeList, new Document.OutputSettings().prettyPrint(false));
    }
}
