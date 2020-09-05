package org.ssio.spi.developerexternal.abstractsheet.model;

import java.io.IOException;
import java.io.OutputStream;

/**
 * file-type-independent workbook
 */
public interface SsWorkbook {
    SsSheet createNewSheet();

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

    void write(OutputStream outputTarget) throws IOException;

    SsSheet getSheetToParse();
}