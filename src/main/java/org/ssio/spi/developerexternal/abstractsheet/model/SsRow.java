package org.ssio.spi.developerexternal.abstractsheet.model;

public interface SsRow {
    int getNumberOfCells();

    /**
     * @param columnIndex 0-based
     * @return
     */
    SsCell getCell(int columnIndex);

    /**
     * @param columnIndex 0-based
     * @return
     */
    SsCell createCell(int columnIndex);
}
