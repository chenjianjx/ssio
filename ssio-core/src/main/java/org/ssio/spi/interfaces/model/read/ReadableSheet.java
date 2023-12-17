package org.ssio.spi.interfaces.model.read;

public interface ReadableSheet {

    int getNumberOfRows();

    /**
     * @param rowIndex 0-based
     
     */
    ReadableRow getRow(int rowIndex);


    String getSheetName();
}
