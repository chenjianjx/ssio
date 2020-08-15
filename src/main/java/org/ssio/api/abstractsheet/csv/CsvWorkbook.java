package org.ssio.api.abstractsheet.csv;

import org.ssio.api.abstractsheet.SsSheet;
import org.ssio.api.abstractsheet.SsWorkbook;

import java.io.OutputStream;

public class CsvWorkbook implements SsWorkbook {
    @Override
    public SsSheet createSheet(String sheetName) {
        return null;
    }

    @Override
    public void write(OutputStream outputTarget) {

    }
}
