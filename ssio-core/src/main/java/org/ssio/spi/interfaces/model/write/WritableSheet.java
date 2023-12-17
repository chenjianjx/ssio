package org.ssio.spi.interfaces.model.write;

public interface WritableSheet {

    /**
     * @param rowIndex 0-based
     
     */
    WritableRow createNewRow(int rowIndex);



    void autoSizeColumn(int columnIndex);

}
