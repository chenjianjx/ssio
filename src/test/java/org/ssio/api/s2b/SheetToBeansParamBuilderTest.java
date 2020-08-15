package org.ssio.api.s2b;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SheetToBeansParamBuilderTest {

    @Test
    void build_allNull() {
        SheetToBeansParamBuilder builder = new SheetToBeansParamBuilder();

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, builder::build);
        
        assertTrue(e.getMessage().contains("beanClass cannot be null"));
        assertTrue(e.getMessage().contains("spreadsheetInput cannot be null"));
        assertTrue(e.getMessage().contains("fileType cannot be null"));
    }
}