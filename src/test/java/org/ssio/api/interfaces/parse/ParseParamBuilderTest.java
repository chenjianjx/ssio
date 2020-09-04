package org.ssio.api.interfaces.parse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.ssio.api.interfaces.SpreadsheetFileType;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParseParamBuilderTest {


    @Test
    void build_allWrong() {
        ParseParamBuilder builder = new ParseParamBuilder().setSheetLocator(null).setPropFromColumnMappingMode(null);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, builder::build);

        assertTrue(e.getMessage().contains("beanClass cannot be null"));
        assertTrue(e.getMessage().contains("propFromColumnMappingMode cannot be null"));
        assertTrue(e.getMessage().contains("spreadsheetInput cannot be null"));
        assertTrue(e.getMessage().contains("fileType cannot be null"));
        assertTrue(e.getMessage().contains("sheetLocator cannot be null"));
    }

    @ParameterizedTest
    @ValueSource(classes = {OneArgumentConstructorBean.class, NonAccessibleConstructorBean.class})
    void build_invalidBean(Class<?> beanClass) {
        ParseParamBuilder builder = new ParseParamBuilder().setBeanClass(beanClass);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, builder::build);
        assertTrue(e.getMessage().contains("doesn't have an accessible zero-argument constructor"));
    }

    @Test
    void build_inputCharsetNullForCsv() {
        ParseParamBuilder builder = new ParseParamBuilder().setFileType(SpreadsheetFileType.CSV).setInputCharset(null);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, builder::build);
        assertTrue(e.getMessage().contains("the inputCharset is required"));
    }

    @Test
    void build_sheetHasNoHeaderButModeIsByName() {
        ParseParamBuilder builder = new ParseParamBuilder()
                .setBeanClass(SimpleBean.class).setSheetHasHeader(false).setPropFromColumnMappingMode(PropFromColumnMappingMode.BY_NAME);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, builder::build);
        assertTrue(e.getMessage().contains("If the sheet has no header, then propFromColumnMappingMode"));
        assertTrue(e.getMessage().contains("If the propFromColumnMappingMode is BY_NAME"));
    }


    public static class OneArgumentConstructorBean {
        private String str;

        public OneArgumentConstructorBean(String str) {
            this.str = str;
        }
    }

    public static class NonAccessibleConstructorBean {
        private NonAccessibleConstructorBean() {

        }
    }

    public static class SimpleBean {
        private String str;

        public void setStr(String str) {
            this.str = str;
        }

        public String getStr() {
            return str;
        }
    }

}