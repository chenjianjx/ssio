package org.ssio.api.b2s;

import org.junit.jupiter.api.Test;
import org.ssio.api.SpreadsheetFileType;
import org.ssio.api.s2b.SheetToBeansParamBuilder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BeansToSheetParamBuilderTest {

    @Test
    void build_allNull() {
        BeansToSheetParamBuilder builder = new BeansToSheetParamBuilder().setDatumErrDisplayFunction(null);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, builder::build);
        assertTrue(e.getMessage().contains("beans cannot be null"));
        assertTrue(e.getMessage().contains("beanClass cannot be null"));
        assertTrue(e.getMessage().contains("outputTarget cannot be null"));
        assertTrue(e.getMessage().contains("fileType cannot be null"));
        assertTrue(e.getMessage().contains("datumErrDisplayFunction cannot be null"));
    }

    @Test
    void build_inputCharsetNullForCsv() {
        BeansToSheetParamBuilder builder = new BeansToSheetParamBuilder().setFileType(SpreadsheetFileType.CSV).setOutputCharset(null);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, builder::build);
        assertTrue(e.getMessage().contains("the outputCharset is required"));
    }
}