package org.ssio.api.common.abstractsheet.model.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.IOUtils;
import org.ssio.api.common.abstractsheet.model.SsSheet;
import org.ssio.api.common.abstractsheet.model.SsWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;

public class CsvWorkbook implements SsWorkbook {
    private CSVPrinter csvPrinter;
    private CsvSheet sheet;

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
        this.sheet = new CsvSheet();
        return this.sheet;
    }

    @Override
    public void write(OutputStream outputTarget) throws IOException {
        sheet.acceptPrinting(this.csvPrinter);

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
        return this.sheet;
    }

    @Override
    public SsSheet getSheetAt(int sheetIndex) {
        return this.sheet;
    }
}
