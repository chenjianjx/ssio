package org.ssio.spi.internal.filetypespecific.abstractsheet.csv.model;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.ssio.spi.developerexternal.abstractsheet.model.SsCell;
import org.ssio.spi.developerexternal.abstractsheet.model.SsRow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CsvRow implements SsRow {
    private List<CsvCell> cells = new ArrayList<>();

    private CsvRow() {

    }

    public static CsvRow createEmptyRow() {
        return new CsvRow();
    }

    public static CsvRow createFromAcsRecord(CSVRecord acsRecord) {
        CsvRow row = new CsvRow();
        for (String s : acsRecord) {
            row.cells.add(CsvCell.createWithContent(s));
        }
        return row;
    }

    @Override
    public int getNumberOfCells() {
        return cells.size();
    }

    @Override
    public SsCell getCell(int columnIndex) {
        return cells.get(columnIndex);
    }

    @Override
    public SsCell createCell(int columnIndex) {
        int currentSize = cells.size();
        int nextIndex = currentSize;
        CsvCell newCell = null;
        for (int i = nextIndex; i <= columnIndex; i++) {
            newCell = CsvCell.createEmptyCell();
            cells.add(newCell);
        }
        return newCell;
    }

    public void acceptPrinting(CSVPrinter csvPrinter) throws IOException {
        List<String> contentList = cells.stream().map(CsvCell::getContent).collect(Collectors.toList());
        csvPrinter.printRecord(contentList.toArray());

    }
}
