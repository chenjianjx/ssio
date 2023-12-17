package org.ssio.spi.impl.csv.model.write;

import org.apache.commons.csv.CSVPrinter;
import org.ssio.spi.interfaces.model.write.WritableCell;
import org.ssio.spi.interfaces.model.write.WritableRow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CsvWritableRow implements WritableRow {
    private List<CsvWritableCell> cells = new ArrayList<>();

    private CsvWritableRow() {

    }

    public static CsvWritableRow createEmptyRow() {
        return new CsvWritableRow();
    }


    @Override
    public WritableCell createCell(int columnIndex) {
        int currentSize = cells.size();
        int nextIndex = currentSize;
        CsvWritableCell newCell = null;
        for (int i = nextIndex; i <= columnIndex; i++) {
            newCell = CsvWritableCell.createEmptyCell();
            cells.add(newCell);
        }
        return newCell;
    }


    public void acceptPrinting(CSVPrinter csvPrinter) throws IOException {
        List<String> contentList = cells.stream().map(CsvWritableCell::getContent).collect(Collectors.toList());
        csvPrinter.printRecord(contentList.toArray());

    }
}
