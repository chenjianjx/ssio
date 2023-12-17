package org.ssio.api.interfaces.parse;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CellsErrorDuringParseExceptionTest {
    @Test
    void constructorShouldInitializeCellErrors() {
        // Arrange
        List<CellError> sampleErrors = Collections.singletonList(new CellError());
        // Act
        CellsErrorDuringParseException exception = new CellsErrorDuringParseException(sampleErrors);
        // Assert
        assertNotNull(exception.getCellErrors());
        assertEquals(sampleErrors, exception.getCellErrors());
    }

    @Test
    void constructorShouldHandleNullCellErrors() {
        // Act
        CellsErrorDuringParseException exception = new CellsErrorDuringParseException(null);

        // Assert
        assertNotNull(exception.getCellErrors());
        assertEquals(Collections.emptyList(), exception.getCellErrors());
    }

    @Test
    void getMessageShouldReturnFormattedErrorMessages() {
        // Arrange
        List<CellError> sampleErrors = Arrays.asList(
                new CellError(),
                new CellError()
        );
        CellsErrorDuringParseException exception = new CellsErrorDuringParseException(sampleErrors);
        // Assert
        assertNotNull(exception.getMessage());
    }
}