package org.ssio.spi.impl.abstractsheet.filetypespecific.csv.model;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;
import org.ssio.spi.interfaces.abstractsheet.model.SsSheet;
import org.ssio.spi.interfaces.abstractsheet.model.SsWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

public class CsvWorkbook implements SsWorkbook {

    private CSVPrinter csvPrinterForOutput;
    private CsvSheet sheet;

    private CsvWorkbook() {

    }

    /**
     * create an empty workbook to write out to somewhere
     *
     * @param cellSeparator
     * @return
     */
    public static CsvWorkbook createNewWorkbook(char cellSeparator) {
        CsvWorkbook workbook = new CsvWorkbook();

        StringWriter out = new StringWriter();
        try {
            CSVFormat csvFormat = getCsvFormat(cellSeparator);
            workbook.csvPrinterForOutput = new CSVPrinter(out, csvFormat);
        } catch (IOException e) {
            //shouldn't happen
            throw new IllegalStateException(e);
        }

        return workbook;
    }

    public static CsvWorkbook createFromInput(Reader reader, char cellSeparator) throws IOException {
        CsvWorkbook workbook = new CsvWorkbook();
        Iterable<CSVRecord> records = getCsvFormat(cellSeparator).parse(reader);
        CsvSheet sheet = CsvSheet.createSheetFromAcsRecords(records);
        workbook.sheet = sheet;
        return workbook;
    }

    private static CSVFormat getCsvFormat(char cellSeparator) {
        return CSVFormat.EXCEL.withDelimiter(cellSeparator);
    }

    @Override
    public SsSheet createNewSheet(String sheetName) {
        this.sheet = CsvSheet.createEmptySheet();
        return this.sheet;
    }

    @Override
    public void write(OutputStream outputTarget, String charset) throws IOException {
        sheet.acceptPrinting(this.csvPrinterForOutput);

        StringWriter out = (StringWriter) this.csvPrinterForOutput.getOut();
        StringReader reader = new StringReader(out.toString());
        IOUtils.copy(reader, outputTarget, charset);
    }

    @Override
    public int getNumberOfSheets() {
        return 1;
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
