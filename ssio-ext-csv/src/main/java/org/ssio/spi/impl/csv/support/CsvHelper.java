package org.ssio.spi.impl.csv.support;

import org.apache.commons.csv.CSVFormat;

public class CsvHelper {
    public static CSVFormat getCsvFormat(char cellSeparator) {
        return CSVFormat.EXCEL.withDelimiter(cellSeparator);
    }
}
