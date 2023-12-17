package org.ssio.spi.impl.support;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SpiModelHelperTest {

    @Test
    void checkRowSizeWhenCreatingNewRow_ShouldThrowExceptionWhenRowIndexNotEqualCurrentSize() {
        // Arrange
        int rowIndex = 3;
        int currentSize = 2;

        // Act and Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                SpiModelHelper.checkRowSizeWhenCreatingNewRow(rowIndex, currentSize)
        );
    }

    @Test
    void checkRowSizeWhenCreatingNewRow_ShouldNotThrowExceptionWhenRowIndexEqualsCurrentSize() {
        // Arrange
        int rowIndex = 3;
        int currentSize = 3;

        // Act and Assert
        SpiModelHelper.checkRowSizeWhenCreatingNewRow(rowIndex, currentSize);

    }


}
