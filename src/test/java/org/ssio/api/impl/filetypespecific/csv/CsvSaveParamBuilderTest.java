package org.ssio.api.impl.filetypespecific.csv;

import org.junit.jupiter.api.Test;
import org.ssio.api.impl.filetypespecific.csv.save.CsvSaveParamBuilder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CsvSaveParamBuilderTest {

    @Test
    void build_outputCharsetNull() {
        CsvSaveParamBuilder builder = new CsvSaveParamBuilder().setOutputCharset(null);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, builder::build);
        assertTrue(e.getMessage().contains("the outputCharset is required"));
    }
}