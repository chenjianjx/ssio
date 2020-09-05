package org.ssio.api.external.parse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ParseParamBuilderTest {

    public static class TestParam<BEAN> extends ParseParam<BEAN> {

        public TestParam(Class<BEAN> beanClass, PropFromColumnMappingMode propFromColumnMappingMode, InputStream spreadsheetInput, boolean sheetHasHeader) {
            super(beanClass, propFromColumnMappingMode, spreadsheetInput, sheetHasHeader);
        }

        @Override
        public String getSpreadsheetFileType() {
            return "anything";
        }
    }

    public static class TestParamBuilder<BEAN> extends ParseParamBuilder<BEAN, ParseParamBuilderTest.TestParamBuilder<BEAN>> {

        @Override
        protected TestParam fileTypeSpecificBuild(Class<BEAN> beanClass, PropFromColumnMappingMode propFromColumnMappingMode, InputStream spreadsheetInput, boolean sheetHasHeader) {
            return new TestParam(beanClass, propFromColumnMappingMode, spreadsheetInput, sheetHasHeader);
        }

        @Override
        protected void fileTypeSpecificValidate(List<String> errors) {
        }
    }

    @Test
    void build_allWrong() {
        ParseParamBuilder builder = new TestParamBuilder().setPropFromColumnMappingMode(null);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, builder::build);

        assertTrue(e.getMessage().contains("beanClass cannot be null"));
        assertTrue(e.getMessage().contains("propFromColumnMappingMode cannot be null"));
        assertTrue(e.getMessage().contains("spreadsheetInput cannot be null"));

    }

    @ParameterizedTest
    @ValueSource(classes = {OneArgumentConstructorBean.class, NonAccessibleConstructorBean.class})
    void build_invalidBean(Class<?> beanClass) {
        ParseParamBuilder builder = new TestParamBuilder().setBeanClass(beanClass);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, builder::build);
        assertTrue(e.getMessage().contains("doesn't have an accessible zero-argument constructor"));
    }

//

    @Test
    void build_sheetHasNoHeaderButModeIsByName() {
        ParseParamBuilder builder = new TestParamBuilder()
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