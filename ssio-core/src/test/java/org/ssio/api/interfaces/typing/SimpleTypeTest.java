package org.ssio.api.interfaces.typing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class SimpleTypeTest {


    @Test
    void getDefaultDateFormat_notDateRelatedType() {
        assertThrows(IllegalStateException.class, () -> SimpleType.Boolean.getDefaultDateFormat());
    }
}