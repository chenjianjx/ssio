package org.ssio.api.interfaces.parse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ParseParamBuilderTest {

    @Test
    void build_allWrong() {
        ParseParamBuilder builder = new TestParseParamBuilder().setPropFromColumnMappingMode(null);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, builder::build);

        assertTrue(e.getMessage().contains("beanClass cannot be null"));
        assertTrue(e.getMessage().contains("propFromColumnMappingMode cannot be null"));
        assertTrue(e.getMessage().contains("spreadsheetInput cannot be null"));

    }

    @ParameterizedTest
    @ValueSource(classes = {OneArgumentConstructorBean.class, NonAccessibleConstructorBean.class})
    void build_invalidBean(Class<?> beanClass) {
        ParseParamBuilder builder = new TestParseParamBuilder().setBeanClass(beanClass);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, builder::build);
        assertTrue(e.getMessage().contains("doesn't have an accessible zero-argument constructor"));
    }

//

    @Test
    void build_sheetHasNoHeaderButModeIsByName() {
        ParseParamBuilder builder = new TestParseParamBuilder()
                .setBeanClass(SimpleBean.class).setSheetHasHeader(false).setPropFromColumnMappingMode(PropFromColumnMappingMode.BY_NAME);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, builder::build);
        assertTrue(e.getMessage().contains("If the sheet has no header, then propFromColumnMappingMode"));
        assertTrue(e.getMessage().contains("If the propFromColumnMappingMode is BY_NAME"));
    }

    @Test
    void toStringTest(){
        ParseParamBuilder builder = new TestParseParamBuilder();
        assertNotNull(builder.toString());
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