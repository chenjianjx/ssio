package org.ssio.spi.impl.csv.model.read;

import org.apache.commons.csv.CSVRecord;
import org.ssio.spi.interfaces.model.read.ReadableCell;
import org.ssio.spi.interfaces.model.read.ReadableRow;

import java.util.ArrayList;
import java.util.List;

public class CsvReadableRow implements ReadableRow {
    private List<CsvReadableCell> cells = new ArrayList<>();

    private CsvReadableRow() {

    }


    public static CsvReadableRow newInstanceFromAcsRecord(CSVRecord acsRecord) {
        CsvReadableRow row = new CsvReadableRow();
        for (String s : acsRecord) {
            row.cells.add(CsvReadableCell.newInstanceFromContent(s));
        }
        return row;
    }

    @Override
    public int getNumberOfCells() {
        return cells.size();
    }

    @Override
    public ReadableCell getCell(int columnIndex) {
        return cells.get(columnIndex);
    }


}
