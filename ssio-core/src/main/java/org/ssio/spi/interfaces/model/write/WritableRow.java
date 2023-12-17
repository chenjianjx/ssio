package org.ssio.spi.interfaces.model.write;

public interface WritableRow {
    /**
     * @param columnIndex 0-based
     
     */
    WritableCell createCell(int columnIndex);

}
