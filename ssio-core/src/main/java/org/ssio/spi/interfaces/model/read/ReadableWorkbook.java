package org.ssio.spi.interfaces.model.read;


public interface ReadableWorkbook {

    /**
     * Find the sheet using the name
     *
     * @return null if not found
     */
    ReadableSheet getSheetByName(String sheetName);

    /**
     * @param sheetIndex
     * @throws IllegalArgumentException if the index is out of range
     */
    ReadableSheet getSheetAt(int sheetIndex);


    ReadableSheet getSheetToParse();
}
