package org.ssio.spi.impl.csv.model.read;

import org.apache.commons.csv.CSVRecord;
import org.ssio.spi.interfaces.model.read.ReadableSheet;
import org.ssio.spi.interfaces.model.read.ReadableWorkbook;

import java.io.IOException;
import java.io.Reader;

import static org.ssio.spi.impl.csv.support.CsvHelper.getCsvFormat;

public class CsvReadableWorkbook implements ReadableWorkbook {

    private CsvReadableSheet sheet;

    private CsvReadableWorkbook() {

    }


    public static CsvReadableWorkbook newInstanceFromInput(Reader reader, char cellSeparator) throws IOException {
        CsvReadableWorkbook workbook = new CsvReadableWorkbook();
        Iterable<CSVRecord> records = getCsvFormat(cellSeparator).parse(reader);
        CsvReadableSheet sheet = CsvReadableSheet.newInstanceFromAcsRecords(records);
        workbook.sheet = sheet;
        return workbook;
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
