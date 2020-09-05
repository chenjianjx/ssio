package org.ssio.spi.developerexternal.abstractsheet.model;

/**
 * file-type-independent sheet
 */
public interface SsSheet {

    int getNumberOfRows();

    /**
     * @param rowIndex 0-based
     * @return
     */
    SsRow getRow(int rowIndex);

    /**
     * @param rowIndex 0-based
     * @return
     */
    SsRow createNewRow(int rowIndex);

    void autoSizeColumn(int columnIndex);

    String getSheetName();
}
