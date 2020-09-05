package org.ssio.spi.clientexternal.filetypespecific.office.parse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OfficeParseParamBuilderTest {


    @Test
    void build_sheetLocatorNull() {
        OfficeParseParamBuilder builder = new OfficeParseParamBuilder().setSheetLocator(null);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, builder::build);
        assertTrue(e.getMessage().contains("sheetLocator cannot be null"));
    }

}