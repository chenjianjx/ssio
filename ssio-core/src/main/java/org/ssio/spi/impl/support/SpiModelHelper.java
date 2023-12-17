package org.ssio.spi.impl.support;

public class SpiModelHelper {

    public static void checkRowSizeWhenCreatingNewRow(int rowIndex, int currentSize) {
        if (rowIndex != currentSize) {
            throw new IllegalArgumentException(String.format("Currently there are %s rows. So the next row should start with %s, but the input rowIndex is %s", currentSize, currentSize, rowIndex));
        }
    }
}
