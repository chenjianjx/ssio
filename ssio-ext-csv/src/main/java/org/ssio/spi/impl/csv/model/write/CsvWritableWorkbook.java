package org.ssio.spi.impl.csv.model.write;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.IOUtils;
import org.ssio.spi.interfaces.model.write.WritableWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;

import static org.ssio.spi.impl.csv.support.CsvHelper.getCsvFormat;

public class CsvWritableWorkbook implements WritableWorkbook {
    private CSVPrinter csvPrinter;
    private String charset;
    private CsvWritableSheet sheet;

    private CsvWritableWorkbook() {
    }

    @Override
    public CsvWritableSheet createNewSheet() {
        this.sheet = CsvWritableSheet.createEmptySheet();
        return this.sheet;
    }


    @Override
    public void write(OutputStream outputTarget) throws IOException {
        sheet.acceptPrinting(this.csvPrinter);

        try (StringWriter out = (StringWriter) this.csvPrinter.getOut();
             StringReader reader = new StringReader(out.toString())) {
            IOUtils.copy(reader, outputTarget, charset);
        }

    }


    /**
     * create an empty workbook to write out to somewhere
     *
     * @param cellSeparator
     
     */
    public static CsvWritableWorkbook createNewWorkbook(char cellSeparator, String charsetForSave) {
        CsvWritableWorkbook workbook = new CsvWritableWorkbook();

        StringWriter out = new StringWriter();
        try {
            CSVFormat csvFormat = getCsvFormat(cellSeparator);
            workbook.csvPrinter = new CSVPrinter(out, csvFormat);
        } catch (IOException e) {
            //shouldn't happen
            throw new IllegalStateException(e);
        }

        workbook.charset = charsetForSave;
        return workbook;
    }
}
