package org.ssio.spi.impl.csv.parse;

import org.junit.jupiter.api.Test;
import org.ssio.api.interfaces.csv.parse.CsvParseParamBuilder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CsvParseParamBuilderTest {
    @Test
    void build_inputCharsetNull() {
        CsvParseParamBuilder builder = new CsvParseParamBuilder().setInputCharset(null);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, builder::build);
        assertTrue(e.getMessage().contains("the inputCharset is required"));
    }
}