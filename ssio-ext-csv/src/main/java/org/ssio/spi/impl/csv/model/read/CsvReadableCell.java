package org.ssio.spi.impl.csv.model.read;

import org.ssio.spi.impl.text.model.read.TextReadableCell;

public class CsvReadableCell implements TextReadableCell {

    private String content;

    private CsvReadableCell() {

    }

    public static CsvReadableCell newInstanceFromContent(String content) {
        CsvReadableCell cell = new CsvReadableCell();
        cell.content = content;
        return cell;
    }

    @Override
    public String getContent() {
        return content;
    }
}
