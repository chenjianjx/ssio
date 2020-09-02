package org.ssio.api.s2b;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.ssio.api.SpreadsheetFileType;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SheetToBeansParamBuilderTest {

    @Test
    void build_allWrong() {
        SheetToBeansParamBuilder builder = new SheetToBeansParamBuilder().setSheetLocator(null).setPropFromColumnMappingMode(null);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, builder::build);

        assertTrue(e.getMessage().contains("beanClass cannot be null"));
        assertTrue(e.getMessage().contains("propFromColumnMappingMode cannot be null"));
        assertTrue(e.getMessage().contains("spreadsheetInput cannot be null"));
        assertTrue(e.getMessage().contains("fileType cannot be null"));
        assertTrue(e.getMessage().contains("sheetLocator cannot be null"));
    }


    @ParameterizedTest
    @ValueSource(classes = {OneArgumentConstructorBean.class, NonAccessibleConstructorBean.class})
    void build_invalidBean(Class<?> beanClass){
        SheetToBeansParamBuilder builder = new SheetToBeansParamBuilder().setBeanClass(beanClass);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, builder::build);
        assertTrue(e.getMessage().contains("doesn't have an accessible zero-argument constructor"));
    }

    @Test
    void build_inputCharsetNullForCsv() {
        SheetToBeansParamBuilder builder = new SheetToBeansParamBuilder().setFileType(SpreadsheetFileType.CSV).setInputCharset(null);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, builder::build);
        assertTrue(e.getMessage().contains("the inputCharset is required"));
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

}