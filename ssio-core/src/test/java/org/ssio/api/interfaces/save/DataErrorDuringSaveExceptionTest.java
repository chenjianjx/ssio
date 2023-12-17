package org.ssio.api.interfaces.save;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DataErrorDuringSaveExceptionTest {

    @Test
    void constructorShouldInitializeDatumErrors() {
        // Arrange
        List<DatumError> sampleErrors = Collections.singletonList(new DatumError());

        // Act
        DataErrorDuringSaveException exception = new DataErrorDuringSaveException(sampleErrors);

        // Assert
        assertNotNull(exception.getDatumErrors());
        assertEquals(sampleErrors, exception.getDatumErrors());
    }

    @Test
    void constructorShouldHandleNullDatumErrors() {
        // Act
        DataErrorDuringSaveException exception = new DataErrorDuringSaveException(null);

        // Assert
        assertNotNull(exception.getDatumErrors());
        assertEquals(Collections.emptyList(), exception.getDatumErrors());
    }

    @Test
    void getMessageShouldReturnFormattedErrorMessages() {
        // Arrange
        List<DatumError> sampleErrors = Arrays.asList(
                new DatumError(),
                new DatumError()
        );
        DataErrorDuringSaveException exception = new DataErrorDuringSaveException(sampleErrors);
        // Assert
        assertNotNull(exception.getMessage());
    }
}
