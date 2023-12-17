package org.ssio.integrationtest.cases;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.ssio.api.interfaces.annotation.SsColumn;
import org.ssio.api.interfaces.parse.CellError;
import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.api.interfaces.parse.ParseParamBuilder;
import org.ssio.api.interfaces.parse.ParseResult;
import org.ssio.api.interfaces.parse.PropFromColumnMappingMode;
import org.ssio.integrationtest.beans.ITBean;
import org.ssio.integrationtest.beans.ITBeanFactory;
import org.ssio.integrationtest.beans.ITFormatTestBean;
import org.ssio.integrationtest.beans.ITSickBean;
import org.ssio.integrationtest.beans.ITSimpleBean;
import org.ssio.integrationtest.beans.ITStrangeAnnotationBean;
import org.ssio.integrationtest.beans.ITTypeHandlerTestBean;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


public abstract class SheetParseITCaseBase extends SaveParseITCaseBase {

    public static final String IT_BEANS_RESOURCE_PATH_WITHOUT_EXT = "/integration-test/ITBeans-empty-normal-bigValue";

    protected abstract ParseParamBuilder newParseParamBuilder();

    static Stream<Arguments> mappingMode_provider() {
        return Stream.of(
                Arguments.of(PropFromColumnMappingMode.BY_INDEX),
                Arguments.of(PropFromColumnMappingMode.BY_NAME)
        );
    }


    @ParameterizedTest
    @MethodSource("mappingMode_provider")
    void sheetToBeans_positiveTest(PropFromColumnMappingMode propFromColumnMappingMode) throws IOException {
        try (InputStream input = getInputResourceAsStream(IT_BEANS_RESOURCE_PATH_WITHOUT_EXT)) {
            ParseParam<ITBean> param =
                    newParseParamBuilder()
                            .setBeanClass(ITBean.class)
                            .setSpreadsheetInput(input)
                            .setPropFromColumnMappingMode(propFromColumnMappingMode)
                            .build();

            ParseResult<ITBean> result = manager.parse(param);
            printResult(result);

            assertEquals(3, result.getBeans().size());
            assertFalse(result.hasCellErrors());

            assertEquals(ITBeanFactory.allEmpty(), result.getBeans().get(0));
            assertEquals(ITBeanFactory.normalValues(), result.getBeans().get(1));
            assertEquals(ITBeanFactory.bigValues(), result.getBeans().get(2));

            result.getBeans().forEach(b -> System.out.println(b));
        }

    }

    public static class PlainSpreadSheetByIndexBean {
        @SsColumn(index = 0)
        private String foo;
        @SsColumn(index = 1)
        private String bar;

        public String getFoo() {
            return foo;
        }

        public void setFoo(String foo) {
            this.foo = foo;
        }

        public String getBar() {
            return bar;
        }

        public void setBar(String bar) {
            this.bar = bar;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }
    }


    @ParameterizedTest
    @MethodSource("sheetToBeans_byIndex_allHit_provider")
    void sheetToBeans_byIndex_allHit(boolean sheetHasHeader) throws IOException {

        try (InputStream input = getInputResourceAsStream("/integration-test/just-a-plain-spreadsheet" + (sheetHasHeader ? "" : "-no-header"))) {
            ParseParam<PlainSpreadSheetByIndexBean> param =
                    newParseParamBuilder()
                            .setBeanClass(PlainSpreadSheetByIndexBean.class)
                            .setSpreadsheetInput(input)
                            .setPropFromColumnMappingMode(PropFromColumnMappingMode.BY_INDEX)
                            .setSheetHasHeader(sheetHasHeader)
                            .build();

            ParseResult<PlainSpreadSheetByIndexBean> result = manager.parse(param);
            printResult(result);

            assertEquals(1, result.getBeans().size());
            assertFalse(result.hasCellErrors());

            assertEquals("some foo", result.getBeans().get(0).getFoo());
            assertEquals("some bar", result.getBeans().get(0).getBar());

        }

    }

    static Stream<Arguments> sheetToBeans_byIndex_allHit_provider() {
        return Stream.of(
                Arguments.of(true),
                Arguments.of(false)
        );
    }


    public static class PlainSpreadSheetByNameBean {
        @SsColumn(name = "foo header")
        private String foo;
        @SsColumn(name = "bar header")
        private String bar;

        public String getFoo() {
            return foo;
        }

        public void setFoo(String foo) {
            this.foo = foo;
        }

        public String getBar() {
            return bar;
        }

        public void setBar(String bar) {
            this.bar = bar;
        }
    }


    @Test
    void sheetToBeans_byName_allHit() throws IOException {

        try (InputStream input = getInputResourceAsStream("/integration-test/just-a-plain-spreadsheet")) {
            ParseParam<PlainSpreadSheetByNameBean> param =
                    newParseParamBuilder()
                            .setBeanClass(PlainSpreadSheetByNameBean.class)
                            .setSpreadsheetInput(input)
                            .setPropFromColumnMappingMode(PropFromColumnMappingMode.BY_NAME)
                            .build();

            ParseResult<PlainSpreadSheetByNameBean> result = manager.parse(param);

            assertEquals(1, result.getBeans().size());
            assertFalse(result.hasCellErrors());

            assertEquals("some foo", result.getBeans().get(0).getFoo());
            assertEquals("some bar", result.getBeans().get(0).getBar());

        }

    }


    public static class PlainSpreadSheetByIndexPartiallyHitBean {
        @SsColumn(index = 0)
        private String foo;
        @SsColumn(index = 3)
        private String alien;

        public String getFoo() {
            return foo;
        }

        public void setFoo(String foo) {
            this.foo = foo;
        }

        public String getAlien() {
            return alien;
        }

        public void setAlien(String alien) {
            this.alien = alien;
        }
    }


    @Test
    void sheetToBeans_byIndex_partiallyHit() throws IOException {

        try (InputStream input = getInputResourceAsStream("/integration-test/just-a-plain-spreadsheet")) {
            ParseParam<PlainSpreadSheetByIndexPartiallyHitBean> param =
                    newParseParamBuilder()
                            .setBeanClass(PlainSpreadSheetByIndexPartiallyHitBean.class)
                            .setSpreadsheetInput(input)
                            .setPropFromColumnMappingMode(PropFromColumnMappingMode.BY_INDEX)
                            .build();

            ParseResult<PlainSpreadSheetByIndexPartiallyHitBean> result = manager.parse(param);

            assertEquals(1, result.getBeans().size());
            assertFalse(result.hasCellErrors());

            assertEquals("some foo", result.getBeans().get(0).getFoo());
            assertEquals(null, result.getBeans().get(0).getAlien());

        }

    }


    public static class PlainSpreadSheetByNamePartiallyHitBean {
        @SsColumn(name = "foo header")
        private String foo;
        @SsColumn(name = "alien header")
        private String alien;

        public String getFoo() {
            return foo;
        }

        public void setFoo(String foo) {
            this.foo = foo;
        }

        public String getAlien() {
            return alien;
        }

        public void setAlien(String alien) {
            this.alien = alien;
        }
    }


    @Test
    void sheetToBeans_byName_partiallyHit() throws IOException {

        try (InputStream input = getInputResourceAsStream("/integration-test/just-a-plain-spreadsheet")) {
            ParseParam<PlainSpreadSheetByNamePartiallyHitBean> param =
                    newParseParamBuilder()
                            .setBeanClass(PlainSpreadSheetByNamePartiallyHitBean.class)
                            .setSpreadsheetInput(input)
                            .setPropFromColumnMappingMode(PropFromColumnMappingMode.BY_NAME)
                            .build();

            ParseResult<PlainSpreadSheetByNamePartiallyHitBean> result = manager.parse(param);

            assertEquals(1, result.getBeans().size());
            assertFalse(result.hasCellErrors());

            assertEquals("some foo", result.getBeans().get(0).getFoo());
            assertEquals(null, result.getBeans().get(0).getAlien());

        }

    }


    @ParameterizedTest
    @MethodSource("mappingMode_provider")
    void sheetToBeans_strangeAnnotationTest(PropFromColumnMappingMode propFromColumnMappingMode) throws IOException {
        try (InputStream input = getInputResourceAsStream("/integration-test/StrangeAnnotationBean")) {
            ParseParam<ITStrangeAnnotationBean> param =
                    newParseParamBuilder()
                            .setBeanClass(ITStrangeAnnotationBean.class)
                            .setSpreadsheetInput(input)
                            .setPropFromColumnMappingMode(propFromColumnMappingMode)
                            .build();

            ParseResult<ITStrangeAnnotationBean> result = manager.parse(param);
            printResult(result);

            assertEquals(1, result.getBeans().size());
            assertFalse(result.hasCellErrors());

            assertEquals(new ITStrangeAnnotationBean(), result.getBeans().get(0));
            result.getBeans().forEach(b -> System.out.println(b));
        }

    }


    @Test
    void sheetToBeans_cellErrors() throws IOException {
        try (InputStream input = getInputResourceAsStream("/integration-test/SickBean")) {
            ParseParam<ITSickBean> param =
                    newParseParamBuilder()
                            .setBeanClass(ITSickBean.class)
                            .setSpreadsheetInput(input)
                            .build();

            ParseResult<ITSickBean> result = manager.parse(param);
            printResult(result);

            assertEquals(1, result.getBeans().size());
            ITSickBean bean = result.getBeans().get(0);
            assertEquals("random text", bean.getHealthyField());
            assertEquals("defaultUnhealthyField", bean.unhealthyField);

            assertEquals(1, result.getCellErrors().size());
            CellError cellError = result.getCellErrors().get(0);
            assertEquals(1, cellError.getRowIndex());
            assertEquals(1, cellError.getColumnIndex());
            assertEquals("unhealthyField", cellError.getPropName());
            assertEquals("Unhealthy Field", cellError.getColumnName());
        }
    }


    @Test
    void sheetToBeans_formatTest() throws IOException {

        try (InputStream input = getInputResourceAsStream("/integration-test/FormatTestBean")) {
            ParseParam<ITFormatTestBean> param =
                    newParseParamBuilder()
                            .setBeanClass(ITFormatTestBean.class)
                            .setSpreadsheetInput(input)
                            .build();

            ParseResult<ITFormatTestBean> result = manager.parse(param);
            printResult(result);

            assertEquals(1, result.getBeans().size());
            assertFalse(result.hasCellErrors());

            assertEquals(ITFormatTestBean.firstDayOfEveryMonthIn2020(), result.getBeans().get(0));


            result.getBeans().forEach(b -> System.out.println(b));
        }

    }


    @Test
    void sheetToBeans_typeHandlerTest() throws IOException {
        try (InputStream input = getInputResourceAsStream("/integration-test/TypeHandlerTestBean-beckham-nobody")) {
            ParseParam<ITTypeHandlerTestBean> param =
                    newParseParamBuilder()
                            .setBeanClass(ITTypeHandlerTestBean.class)
                            .setSpreadsheetInput(input)
                            .build();

            ParseResult<ITTypeHandlerTestBean> result = manager.parse(param);
            printResult(result);

            assertEquals(1, result.getBeans().size());
            assertFalse(result.hasCellErrors());

            assertEquals(ITTypeHandlerTestBean.beckham(), result.getBeans().get(0));
        }

    }

    @Test
    void sheetToBeans_withEmptyRowsTest() throws IOException {
        try (InputStream input = getInputResourceAsStream("/integration-test/SimpleBean-with-empty-rows")) {
            ParseParam<ITSimpleBean> param =
                    newParseParamBuilder()
                            .setBeanClass(ITSimpleBean.class)
                            .setSpreadsheetInput(input)
                            .build();

            ParseResult<ITSimpleBean> result = manager.parse(param);
            printResult(result);

            assertEquals(2, result.getBeans().size());
            assertFalse(result.hasCellErrors());

            ITSimpleBean bean1 = result.getBeans().get(0);
            assertEquals("string1", bean1.getStr());
            assertEquals(100, bean1.getPrimInt());


            ITSimpleBean bean2 = result.getBeans().get(1);
            assertEquals("string2", bean2.getStr());
            assertEquals(200, bean2.getPrimInt());
        }
    }


    protected void printResult(ParseResult<?> result) {
        result.getBeans().forEach(b -> System.out.println(b));
        result.getCellErrors().forEach(e -> System.err.println(e));
    }

    private InputStream getInputResourceAsStream(String inputResourceClasspath) {
        String fullClasspath = inputResourceClasspath + "." + getSpreadsheetFileExtension();
        InputStream input = this.getClass().getResourceAsStream(fullClasspath);
        if (input == null) {
            throw new IllegalArgumentException("Cannot find input resource: " + fullClasspath);
        }
        return input;
    }

}
