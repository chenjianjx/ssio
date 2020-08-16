package org.ssio.api.abstractsheet;

public interface SsRow {
    int getNumberOfCells();

    /**
     *
     * @param columnIndex 0-based
     * @return
     */
    SsCell getCell(int columnIndex);
}
