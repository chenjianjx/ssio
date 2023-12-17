package org.ssio.spi.impl.csv.model.write;

import org.apache.commons.csv.CSVPrinter;
import org.ssio.spi.interfaces.model.write.WritableSheet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.ssio.spi.impl.support.SpiModelHelper.checkRowSizeWhenCreatingNewRow;

public class CsvWritableSheet implements WritableSheet {

    private List<CsvWritableRow> rows = new ArrayList<>();
    private CsvWritableSheet() {
    }


    public static CsvWritableSheet createEmptySheet() {
        return new CsvWritableSheet();
    }

    @Override
    public void autoSizeColumn(int columnIndex) {
        //do nothing;
    }

    @Override
    public CsvWritableRow createNewRow(int rowIndex) {
        int currentSize = rows.size();
        checkRowSizeWhenCreatingNewRow(rowIndex, currentSize);
        CsvWritableRow row = CsvWritableRow.createEmptyRow();
        rows.add(row);
        return row;
    }


    public void acceptPrinting(CSVPrinter csvPrinter) throws IOException {
        for (CsvWritableRow row : rows) {
            row.acceptPrinting(csvPrinter);
        }
    }
}
