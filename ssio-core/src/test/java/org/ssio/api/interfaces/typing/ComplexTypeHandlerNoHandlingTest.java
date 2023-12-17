package org.ssio.api.interfaces.typing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class ComplexTypeHandlerNoHandlingTest {

    ComplexTypeHandler.NO_HANDLING handler = new ComplexTypeHandler.NO_HANDLING();

    @Test
    void allNull() {
        assertNull(handler.getTargetSimpleType());
        assertNull(handler.nonNullValueToSimple(new Object()));
        assertNull(handler.nullValueToSimple());
        assertNull(handler.fromNonNullSimpleTypeValue(new Object()));
        assertNull(handler.fromNullSimpleTypeValue());
    }
}