package org.ssio.api.interfaces.htmltable.save;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HtmlTableSaveParamBuilderTest {
    @Test
    void build_outputCharsetNull() {
        HtmlTableSaveParamBuilder builder = new HtmlTableSaveParamBuilder().setOutputCharset(null);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, builder::build);
        assertTrue(e.getMessage().contains("the outputCharset is required"));
    }
}