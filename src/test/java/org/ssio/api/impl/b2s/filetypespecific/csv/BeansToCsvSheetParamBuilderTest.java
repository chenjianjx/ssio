package org.ssio.api.impl.b2s.filetypespecific.csv;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BeansToCsvSheetParamBuilderTest {

    @Test
    void build_outputCharsetNull() {
        BeansToCsvSheetParamBuilder builder = new BeansToCsvSheetParamBuilder().setOutputCharset(null);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, builder::build);
        assertTrue(e.getMessage().contains("the outputCharset is required"));
    }
}