package org.ssio.integrationtest.conversion;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.ssio.api.impl.ConversionManagerImpl;
import org.ssio.api.interfaces.SpreadsheetFileType;
import org.ssio.api.impl.common.sheetlocate.SsSheetLocator;
import org.ssio.api.interfaces.annotation.SsColumn;
import org.ssio.api.interfaces.s2b.CellError;
import org.ssio.api.interfaces.s2b.PropFromColumnMappingMode;
import org.ssio.api.interfaces.s2b.SheetToBeansParam;
import org.ssio.api.interfaces.s2b.SheetToBeansParamBuilder;
import org.ssio.api.interfaces.s2b.SheetToBeansResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.ssio.integrationtest.conversion.ITTestHelper.decideTargetFileExtension;

public class SheetToBeansITCase {
    ConversionManagerImpl manager = new ConversionManagerImpl();


    @BeforeAll
    public static void init() {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG");
    }


    static Stream<Arguments> fileType_mappingMode_provider() {
        return Stream.of(
                Arguments.of(SpreadsheetFileType.OFFICE, PropFromColumnMappingMode.BY_INDEX),
                Arguments.of(SpreadsheetFileType.OFFICE, PropFromColumnMappingMode.BY_NAME),
                Arguments.of(SpreadsheetFileType.CSV, PropFromColumnMappingMode.BY_INDEX),
                Arguments.of(SpreadsheetFileType.CSV, PropFromColumnMappingMode.BY_NAME)
        );
    }


    @ParameterizedTest
    @MethodSource("fileType_mappingMode_provider")
    void sheetToBeans_positiveTest(SpreadsheetFileType spreadsheetFileType, PropFromColumnMappingMode propFromColumnMappingMode) throws IOException {

        String inputResourceClasspath = "/integration-test/ITBeans-empty-normal-bigValue" + decideTargetFileExtension(spreadsheetFileType);
        try (InputStream input = this.getClass().getResourceAsStream(inputResourceClasspath)) {
            SheetToBeansParam<ITBean> param =
                    new SheetToBeansParamBuilder<ITBean>()
                            .setBeanClass(ITBean.class)
                            .setFileType(spreadsheetFileType)
                            .setSpreadsheetInput(input)
                            .setInputCharset("utf8") //for csv only
                            .setPropFromColumnMappingMode(propFromColumnMappingMode)
                            .build();

            SheetToBeansResult<ITBean> result = manager.sheetToBeans(param);
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
    void sheetToBeans_byIndex_allHit(SpreadsheetFileType spreadsheetFileType, boolean sheetHasHeader) throws IOException {

        String inputResourceClasspath = "/integration-test/just-a-plain-spreadsheet" + (sheetHasHeader ? "" : "-no-header") + decideTargetFileExtension(spreadsheetFileType);
        try (InputStream input = this.getClass().getResourceAsStream(inputResourceClasspath)) {
            SheetToBeansParam<PlainSpreadSheetByIndexBean> param =
                    new SheetToBeansParamBuilder<PlainSpreadSheetByIndexBean>()
                            .setBeanClass(PlainSpreadSheetByIndexBean.class)
                            .setFileType(spreadsheetFileType)
                            .setSpreadsheetInput(input)
                            .setInputCharset("utf8") //for csv only
                            .setPropFromColumnMappingMode(PropFromColumnMappingMode.BY_INDEX)
                            .setSheetHasHeader(sheetHasHeader)
                            .build();

            SheetToBeansResult<PlainSpreadSheetByIndexBean> result = manager.sheetToBeans(param);
            printResult(result);

            assertEquals(1, result.getBeans().size());
            assertFalse(result.hasCellErrors());

            assertEquals("some foo", result.getBeans().get(0).getFoo());
            assertEquals("some bar", result.getBeans().get(0).getBar());

        }

    }

    static Stream<Arguments> sheetToBeans_byIndex_allHit_provider() {
        return Stream.of(
                Arguments.of(SpreadsheetFileType.OFFICE, true),
                Arguments.of(SpreadsheetFileType.CSV, true),
                Arguments.of(SpreadsheetFileType.OFFICE, false),
                Arguments.of(SpreadsheetFileType.CSV, false)
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


    @ParameterizedTest
    @EnumSource(SpreadsheetFileType.class)
    void sheetToBeans_byName_allHit(SpreadsheetFileType spreadsheetFileType) throws IOException {

        String inputResourceClasspath = "/integration-test/just-a-plain-spreadsheet" + decideTargetFileExtension(spreadsheetFileType);
        try (InputStream input = this.getClass().getResourceAsStream(inputResourceClasspath)) {
            SheetToBeansParam<PlainSpreadSheetByNameBean> param =
                    new SheetToBeansParamBuilder<PlainSpreadSheetByNameBean>()
                            .setBeanClass(PlainSpreadSheetByNameBean.class)
                            .setFileType(spreadsheetFileType)
                            .setSpreadsheetInput(input)
                            .setInputCharset("utf8") //for csv only
                            .setPropFromColumnMappingMode(PropFromColumnMappingMode.BY_NAME)
                            .build();

            SheetToBeansResult<PlainSpreadSheetByNameBean> result = manager.sheetToBeans(param);

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


    @ParameterizedTest
    @EnumSource(SpreadsheetFileType.class)
    void sheetToBeans_byIndex_partiallyHit(SpreadsheetFileType spreadsheetFileType) throws IOException {

        String inputResourceClasspath = "/integration-test/just-a-plain-spreadsheet" + decideTargetFileExtension(spreadsheetFileType);
        try (InputStream input = this.getClass().getResourceAsStream(inputResourceClasspath)) {
            SheetToBeansParam<PlainSpreadSheetByIndexPartiallyHitBean> param =
                    new SheetToBeansParamBuilder<PlainSpreadSheetByIndexPartiallyHitBean>()
                            .setBeanClass(PlainSpreadSheetByIndexPartiallyHitBean.class)
                            .setFileType(spreadsheetFileType)
                            .setSpreadsheetInput(input)
                            .setInputCharset("utf8") //for csv only
                            .setPropFromColumnMappingMode(PropFromColumnMappingMode.BY_INDEX)
                            .build();

            SheetToBeansResult<PlainSpreadSheetByIndexPartiallyHitBean> result = manager.sheetToBeans(param);

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


    @ParameterizedTest
    @EnumSource(SpreadsheetFileType.class)
    void sheetToBeans_byName_partiallyHit(SpreadsheetFileType spreadsheetFileType) throws IOException {

        String inputResourceClasspath = "/integration-test/just-a-plain-spreadsheet" + decideTargetFileExtension(spreadsheetFileType);
        try (InputStream input = this.getClass().getResourceAsStream(inputResourceClasspath)) {
            SheetToBeansParam<PlainSpreadSheetByNamePartiallyHitBean> param =
                    new SheetToBeansParamBuilder<PlainSpreadSheetByNamePartiallyHitBean>()
                            .setBeanClass(PlainSpreadSheetByNamePartiallyHitBean.class)
                            .setFileType(spreadsheetFileType)
                            .setSpreadsheetInput(input)
                            .setInputCharset("utf8") //for csv only
                            .setPropFromColumnMappingMode(PropFromColumnMappingMode.BY_NAME)
                            .build();

            SheetToBeansResult<PlainSpreadSheetByNamePartiallyHitBean> result = manager.sheetToBeans(param);

            assertEquals(1, result.getBeans().size());
            assertFalse(result.hasCellErrors());

            assertEquals("some foo", result.getBeans().get(0).getFoo());
            assertEquals(null, result.getBeans().get(0).getAlien());

        }

    }


    @ParameterizedTest
    @MethodSource("fileType_mappingMode_provider")
    void sheetToBeans_strangeAnnotationTest(SpreadsheetFileType spreadsheetFileType, PropFromColumnMappingMode propFromColumnMappingMode) throws IOException {

        String inputResourceClasspath = "/integration-test/StrangeAnnotationBean" + decideTargetFileExtension(spreadsheetFileType);
        try (InputStream input = this.getClass().getResourceAsStream(inputResourceClasspath)) {
            SheetToBeansParam<ITStrangeAnnotationBean> param =
                    new SheetToBeansParamBuilder<ITStrangeAnnotationBean>()
                            .setBeanClass(ITStrangeAnnotationBean.class)
                            .setFileType(spreadsheetFileType)
                            .setSpreadsheetInput(input)
                            .setInputCharset("utf8") //for csv only
                            .setPropFromColumnMappingMode(propFromColumnMappingMode)
                            .build();

            SheetToBeansResult<ITStrangeAnnotationBean> result = manager.sheetToBeans(param);
            printResult(result);

            assertEquals(1, result.getBeans().size());
            assertFalse(result.hasCellErrors());

            assertEquals(new ITStrangeAnnotationBean(), result.getBeans().get(0));
            result.getBeans().forEach(b -> System.out.println(b));
        }

    }


    @ParameterizedTest
    @EnumSource(SpreadsheetFileType.class)
    void sheetToBeans_cellErrors(SpreadsheetFileType spreadsheetFileType) throws IOException {

        String inputResourceClasspath = "/integration-test/SickBean" + decideTargetFileExtension(spreadsheetFileType);
        try (InputStream input = this.getClass().getResourceAsStream(inputResourceClasspath)) {
            SheetToBeansParam<ITSickBean> param =
                    new SheetToBeansParamBuilder<ITSickBean>()
                            .setBeanClass(ITSickBean.class)
                            .setFileType(spreadsheetFileType)
                            .setSpreadsheetInput(input)
                            .setInputCharset("utf8") //for csv only
                            .build();

            SheetToBeansResult<ITSickBean> result = manager.sheetToBeans(param);
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


    @ParameterizedTest
    @ValueSource(chars = {',', '\t'})
    void sheetToBeans_csvSeparator(char cellSeparator) throws IOException {

        String inputResourceClasspath = "/integration-test/SimpleBean-separated-by-" + getSeparatorName(cellSeparator) + ".csv";
        try (InputStream input = this.getClass().getResourceAsStream(inputResourceClasspath)) {
            SheetToBeansParam<ITSimpleBean> param =
                    new SheetToBeansParamBuilder<ITSimpleBean>()
                            .setBeanClass(ITSimpleBean.class)
                            .setFileType(SpreadsheetFileType.CSV)
                            .setSpreadsheetInput(input)
                            .setInputCharset("utf8")
                            .setCellSeparator(cellSeparator)
                            .build();

            SheetToBeansResult<ITSimpleBean> result = manager.sheetToBeans(param);
            printResult(result);

            assertEquals(1, result.getBeans().size());
            assertFalse(result.hasCellErrors());

            ITSimpleBean bean = result.getBeans().get(0);
            assertEquals("another string", bean.getStr());
            assertEquals(200, bean.getPrimInt());
        }
    }


    @ParameterizedTest
    @MethodSource("sheetToBeans_notTheFirstSheet_provider")
    void sheetToBeans_notTheFirstSheet(SsSheetLocator sheetLocator) throws IOException {

        String inputResourceClasspath = "/integration-test/SimpleBean-content-not-in-first-sheet.xlsx";
        try (InputStream input = this.getClass().getResourceAsStream(inputResourceClasspath)) {
            SheetToBeansParam<ITSimpleBean> param =
                    new SheetToBeansParamBuilder<ITSimpleBean>()
                            .setBeanClass(ITSimpleBean.class)
                            .setFileType(SpreadsheetFileType.OFFICE)
                            .setSpreadsheetInput(input)
                            .setSheetLocator(sheetLocator)
                            .build();

            SheetToBeansResult<ITSimpleBean> result = manager.sheetToBeans(param);
            printResult(result);

            assertEquals(1, result.getBeans().size());
            assertFalse(result.hasCellErrors());
        }


    }


    @ParameterizedTest
    @EnumSource(SpreadsheetFileType.class)
    void sheetToBeans_formatTest(SpreadsheetFileType spreadsheetFileType) throws IOException {

        String inputResourceClasspath = "/integration-test/FormatTestBean" + decideTargetFileExtension(spreadsheetFileType);
        try (InputStream input = this.getClass().getResourceAsStream(inputResourceClasspath)) {
            SheetToBeansParam<ITFormatTestBean> param =
                    new SheetToBeansParamBuilder<ITFormatTestBean>()
                            .setBeanClass(ITFormatTestBean.class)
                            .setFileType(spreadsheetFileType)
                            .setSpreadsheetInput(input)
                            .setInputCharset("utf8") //for csv only
                            .build();

            SheetToBeansResult<ITFormatTestBean> result = manager.sheetToBeans(param);
            printResult(result);

            assertEquals(1, result.getBeans().size());
            assertFalse(result.hasCellErrors());

            assertEquals(ITFormatTestBean.firstDayOfEveryMonthIn2020(), result.getBeans().get(0));


            result.getBeans().forEach(b -> System.out.println(b));
        }

    }

    static Stream<SsSheetLocator> sheetToBeans_notTheFirstSheet_provider() {
        return Stream.of(
                SsSheetLocator.byIndexLocator(1),
                SsSheetLocator.byNameLocator("The real sheet")
        );
    }



    @ParameterizedTest
    @EnumSource(SpreadsheetFileType.class)
    void sheetToBeans_typeHandlerTest(SpreadsheetFileType spreadsheetFileType) throws IOException {

        String inputResourceClasspath = "/integration-test/TypeHandlerTestBean-beckham-nobody" + decideTargetFileExtension(spreadsheetFileType);
        try (InputStream input = this.getClass().getResourceAsStream(inputResourceClasspath)) {
            SheetToBeansParam<ITTypeHandlerTestBean> param =
                    new SheetToBeansParamBuilder<ITTypeHandlerTestBean>()
                            .setBeanClass(ITTypeHandlerTestBean.class)
                            .setFileType(spreadsheetFileType)
                            .setSpreadsheetInput(input)
                            .setInputCharset("utf8") //for csv only
                            .build();

            SheetToBeansResult<ITTypeHandlerTestBean> result = manager.sheetToBeans(param);
            printResult(result);

            assertEquals(2, result.getBeans().size());
            assertFalse(result.hasCellErrors());

            assertEquals(ITTypeHandlerTestBean.beckham(), result.getBeans().get(0));
            assertEquals(ITTypeHandlerTestBean.nobody(), result.getBeans().get(1));



        }

    }


    private void printResult(SheetToBeansResult<?> result) {
        result.getBeans().forEach(b -> System.out.println(b));
        result.getCellErrors().forEach(e -> System.err.println(e));
    }

    private String getSeparatorName(char cellSeparator) {
        if (cellSeparator == ',') {
            return "comma";
        }
        if (cellSeparator == '\t') {
            return "tab";
        }
        throw new IllegalArgumentException("No name for " + cellSeparator);
    }

}
