package org.ssio.spi.internal.filetypespecific.abstractsheet.csv.model;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.ssio.spi.developerexternal.abstractsheet.model.SsRow;
import org.ssio.spi.developerexternal.abstractsheet.model.SsSheet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvSheet implements SsSheet {


    private List<CsvRow> rows = new ArrayList<>();

    private CsvSheet() {

    }

    public static CsvSheet createEmptySheet() {
        return new CsvSheet();
    }

    /**
     * Acs = Apache Commons CSV
     *
     * @param acsRecords
     * @return
     */
    public static CsvSheet createSheetFromAcsRecords(Iterable<CSVRecord> acsRecords) {
        CsvSheet sheet = new CsvSheet();
        for (CSVRecord acsRecord : acsRecords) {
            sheet.rows.add(CsvRow.createFromAcsRecord(acsRecord));
        }
        return sheet;
    }


    @Override
    public void autoSizeColumn(int columnIndex) {
        //do nothing;
    }


    @Override
    public String getSheetName() {
        return null;
    }

    @Override
    public SsRow getRow(int rowIndex) {
        return rows.get(rowIndex);
    }

    @Override
    public SsRow createNewRow(int rowIndex) {
        int currentSize = rows.size();
        if (rowIndex != currentSize) {
            throw new IllegalArgumentException(String.format("Current there are %s rows. So the next row should start with %s, but the input rowIndex is %s", currentSize, currentSize, rowIndex));
        }
        CsvRow row = CsvRow.createEmptyRow();
        rows.add(row);
        return row;
    }

    @Override
    public int getNumberOfRows() {
        return rows.size();
    }


    public void acceptPrinting(CSVPrinter csvPrinter) throws IOException {
        for (CsvRow row : rows) {
            row.acceptPrinting(csvPrinter);
        }
    }
}
