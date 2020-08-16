package org.ssio.api.abstractsheet.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.IOUtils;
import org.ssio.api.abstractsheet.SsSheet;
import org.ssio.api.abstractsheet.SsWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;

public class CsvWorkbook implements SsWorkbook {
    private final CSVPrinter csvPrinter;

    public CsvWorkbook(char cellSeparator) {
        StringWriter out = new StringWriter();
        try {
            CSVFormat csvFormat = CSVFormat.EXCEL.withDelimiter(cellSeparator);
            this.csvPrinter = new CSVPrinter(out, csvFormat);
        } catch (IOException e) {
            //shouldn't happen
            throw new IllegalStateException(e);
        }
    }

    @Override
    public SsSheet createSheet(String sheetName) {
        return new CsvSheet(csvPrinter);
    }

    @Override
    public void write(OutputStream outputTarget) throws IOException {
        StringWriter out = (StringWriter) this.csvPrinter.getOut();
        StringReader reader = new StringReader(out.toString());
        IOUtils.copy(reader, outputTarget, "utf8");
    }

    @Override
    public int getNumberOfSheets() {
        return 0;
    }

    @Override
    public SsSheet getSheetByName(String sheetName) {
        return null;
    }

    @Override
    public SsSheet getSheetAt(int sheetIndex) {
        return null;
    }
}
