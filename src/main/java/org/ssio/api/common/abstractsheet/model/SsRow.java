package org.ssio.api.common.abstractsheet.model;

public interface SsRow {
    int getNumberOfCells();

    /**
     *
     * @param columnIndex 0-based
     * @return
     */
    SsCell getCell(int columnIndex);

    SsCell createCell(int columnIndex);
}
