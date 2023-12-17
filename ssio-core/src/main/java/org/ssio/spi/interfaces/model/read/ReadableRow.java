package org.ssio.spi.interfaces.model.read;



public interface ReadableRow {
    int getNumberOfCells();

    /**
     * @param columnIndex 0-based
     
     */
    ReadableCell getCell(int columnIndex);

    default boolean isBlank() {
        boolean blank = true;
        for (int i = 0; i < getNumberOfCells(); i++) {
            blank = blank && getCell(i).isBlank();
        }
        return blank;
    }
}
