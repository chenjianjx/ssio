package org.ssio.api.interfaces.htmltable.parse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HtmlTableParseParamBuilderTest {
    @Test
    void build_inputCharsetNull() {
        HtmlTableParseParamBuilder builder = new HtmlTableParseParamBuilder().setInputCharset(null);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, builder::build);
        assertTrue(e.getMessage().contains("the inputCharset is required"));
    }
}