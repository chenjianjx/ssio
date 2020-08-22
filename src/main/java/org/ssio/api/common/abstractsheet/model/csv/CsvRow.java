package org.ssio.api.common.abstractsheet.model.csv;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.ssio.api.common.abstractsheet.model.SsCell;
import org.ssio.api.common.abstractsheet.model.SsRow;

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
        if (columnIndex != currentSize) {
            throw new IllegalArgumentException(String.format("Current there are %s cells. So the next cell should start with %s, but the input columnIndex is %s", currentSize, currentSize, columnIndex));
        }
        CsvCell cell = CsvCell.createEmptyCell();
        cells.add(cell);
        return cell;
    }

    public void acceptPrinting(CSVPrinter csvPrinter) throws IOException {
        List<String> contentList = cells.stream().map(CsvCell::getContent).collect(Collectors.toList());
        csvPrinter.printRecord(contentList.toArray());

    }
}
