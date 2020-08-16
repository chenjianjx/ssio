package org.ssio.api.abstractsheet;

import java.io.IOException;
import java.io.OutputStream;

/**
 * file-type-independent workbook
 */
public interface SsWorkbook {
    SsSheet createSheet(String sheetName);

    void write(OutputStream outputTarget) throws IOException;

    int getNumberOfSheets();

    /**
     * Find the sheet using the name
     *
     * @return null if not found
     */
    SsSheet getSheetByName(String sheetName);

    /**
     * @param sheetIndex
     * @throws IllegalArgumentException if the index is out of range
     */
    SsSheet getSheetAt(int sheetIndex);
}