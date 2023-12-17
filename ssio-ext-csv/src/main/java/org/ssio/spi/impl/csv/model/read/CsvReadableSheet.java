package org.ssio.spi.impl.csv.model.read;

import org.apache.commons.csv.CSVRecord;
import org.ssio.spi.interfaces.model.read.ReadableRow;
import org.ssio.spi.interfaces.model.read.ReadableSheet;

import java.util.ArrayList;
import java.util.List;

public class CsvReadableSheet implements ReadableSheet {

    private CsvReadableSheet() {
    }


    private List<CsvReadableRow> rows = new ArrayList<>();


    /**
     * Acs = Apache Commons CSV
     *
     * @param acsRecords
     
     */
    public static CsvReadableSheet newInstanceFromAcsRecords(Iterable<CSVRecord> acsRecords) {
        CsvReadableSheet sheet = new CsvReadableSheet();
        for (CSVRecord acsRecord : acsRecords) {
            sheet.rows.add(CsvReadableRow.newInstanceFromAcsRecord(acsRecord));
        }
        return sheet;
    }


    @Override
    public String getSheetName() {
        return null;
    }

    @Override
    public ReadableRow getRow(int rowIndex) {
        return rows.get(rowIndex);
    }


    @Override
    public int getNumberOfRows() {
        return rows.size();
    }
}
