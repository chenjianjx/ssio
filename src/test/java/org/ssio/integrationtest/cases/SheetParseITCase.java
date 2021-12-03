package org.ssio.integrationtest.cases;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.ssio.api.external.SsioManager;
import org.ssio.api.external.annotation.SsColumn;
import org.ssio.api.external.parse.CellError;
import org.ssio.api.external.parse.ParseParam;
import org.ssio.api.external.parse.ParseParamBuilder;
import org.ssio.api.external.parse.ParseResult;
import org.ssio.api.external.parse.PropFromColumnMappingMode;
import org.ssio.api.factory.SsioManagerFactory;
import org.ssio.api.internal.common.sheetlocate.SsSheetLocator;
import org.ssio.integrationtest.beans.*;
import org.ssio.spi.clientexternal.filetypespecific.SsBuiltInFileTypes;
import org.ssio.spi.clientexternal.filetypespecific.csv.parse.CsvParseParamBuilder;
import org.ssio.spi.clientexternal.filetypespecific.office.parse.OfficeParseParamBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.ssio.integrationtest.helper.ITTestHelper.decideTargetFileExtension;


public class SheetParseITCase {
    SsioManager manager = SsioManagerFactory.newInstance();


    @BeforeAll
    public static void init() {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG");
    }

    static Stream<String> fileTypeProvider() {
        return Stream.of(SsBuiltInFileTypes.CSV, SsBuiltInFileTypes.OFFICE);
    }

    static Stream<Arguments> fileType_mappingMode_provider() {
        return Stream.of(
                Arguments.of(SsBuiltInFileTypes.OFFICE, PropFromColumnMappingMode.BY_INDEX),
                Arguments.of(SsBuiltInFileTypes.OFFICE, PropFromColumnMappingMode.BY_NAME),
                Arguments.of(SsBuiltInFileTypes.CSV, PropFromColumnMappingMode.BY_INDEX),
                Arguments.of(SsBuiltInFileTypes.CSV, PropFromColumnMappingMode.BY_NAME)
        );
    }


    @ParameterizedTest
    @MethodSource("fileType_mappingMode_provider")
    void sheetToBeans_positiveTest(String spreadsheetFileType, PropFromColumnMappingMode propFromColumnMappingMode) throws IOException {

        String inputResourceClasspath = "/integration-test/ITBeans-empty-normal-bigValue" + decideTargetFileExtension(spreadsheetFileType);
        try (InputStream input = this.getClass().getResourceAsStream(inputResourceClasspath)) {
            ParseParam<ITBean> param =
                    newParseParamBuilder(spreadsheetFileType)
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
    void sheetToBeans_byIndex_allHit(String spreadsheetFileType, boolean sheetHasHeader) throws IOException {

        String inputResourceClasspath = "/integration-test/just-a-plain-spreadsheet" + (sheetHasHeader ? "" : "-no-header") + decideTargetFileExtension(spreadsheetFileType);
        try (InputStream input = this.getClass().getResourceAsStream(inputResourceClasspath)) {
            ParseParam<PlainSpreadSheetByIndexBean> param =
                    newParseParamBuilder(spreadsheetFileType)
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
                Arguments.of(SsBuiltInFileTypes.OFFICE, true),
                Arguments.of(SsBuiltInFileTypes.CSV, true),
                Arguments.of(SsBuiltInFileTypes.OFFICE, false),
                Arguments.of(SsBuiltInFileTypes.CSV, false)
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
    @MethodSource("fileTypeProvider")
    void sheetToBeans_byName_allHit(String spreadsheetFileType) throws IOException {

        String inputResourceClasspath = "/integration-test/just-a-plain-spreadsheet" + decideTargetFileExtension(spreadsheetFileType);
        try (InputStream input = this.getClass().getResourceAsStream(inputResourceClasspath)) {
            ParseParam<PlainSpreadSheetByNameBean> param =
                    newParseParamBuilder(spreadsheetFileType)
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


    @ParameterizedTest
    @MethodSource("fileTypeProvider")
    void sheetToBeans_byIndex_partiallyHit(String spreadsheetFileType) throws IOException {

        String inputResourceClasspath = "/integration-test/just-a-plain-spreadsheet" + decideTargetFileExtension(spreadsheetFileType);
        try (InputStream input = this.getClass().getResourceAsStream(inputResourceClasspath)) {
            ParseParam<PlainSpreadSheetByIndexPartiallyHitBean> param =
                    newParseParamBuilder(spreadsheetFileType)
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


    @ParameterizedTest
    @MethodSource("fileTypeProvider")
    void sheetToBeans_byName_partiallyHit(String spreadsheetFileType) throws IOException {

        String inputResourceClasspath = "/integration-test/just-a-plain-spreadsheet" + decideTargetFileExtension(spreadsheetFileType);
        try (InputStream input = this.getClass().getResourceAsStream(inputResourceClasspath)) {
            ParseParam<PlainSpreadSheetByNamePartiallyHitBean> param =
                    newParseParamBuilder(spreadsheetFileType)
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
    @MethodSource("fileType_mappingMode_provider")
    void sheetToBeans_strangeAnnotationTest(String spreadsheetFileType, PropFromColumnMappingMode propFromColumnMappingMode) throws IOException {

        String inputResourceClasspath = "/integration-test/StrangeAnnotationBean" + decideTargetFileExtension(spreadsheetFileType);
        try (InputStream input = this.getClass().getResourceAsStream(inputResourceClasspath)) {
            ParseParam<ITStrangeAnnotationBean> param =
                    newParseParamBuilder(spreadsheetFileType)
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


    @ParameterizedTest
    @MethodSource("fileTypeProvider")
    void sheetToBeans_cellErrors(String spreadsheetFileType) throws IOException {

        String inputResourceClasspath = "/integration-test/SickBean" + decideTargetFileExtension(spreadsheetFileType);
        try (InputStream input = this.getClass().getResourceAsStream(inputResourceClasspath)) {
            ParseParam<ITSickBean> param =
                    newParseParamBuilder(spreadsheetFileType)
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


    @ParameterizedTest
    @ValueSource(chars = {',', '\t'})
    void sheetToBeans_csvSeparator(char cellSeparator) throws IOException {

        String inputResourceClasspath = "/integration-test/SimpleBean-separated-by-" + getSeparatorName(cellSeparator) + ".csv";
        try (InputStream input = this.getClass().getResourceAsStream(inputResourceClasspath)) {
            ParseParam<ITSimpleBean> param =
                    new CsvParseParamBuilder<ITSimpleBean>()
                            .setBeanClass(ITSimpleBean.class)
                            .setSpreadsheetInput(input)
                            .setInputCharset("utf8")
                            .setCellSeparator(cellSeparator)
                            .build();

            ParseResult<ITSimpleBean> result = manager.parse(param);
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
            ParseParam<ITSimpleBean> param =
                    new OfficeParseParamBuilder<ITSimpleBean>()
                            .setBeanClass(ITSimpleBean.class)
                            .setSpreadsheetInput(input)
                            .setSheetLocator(sheetLocator)
                            .build();

            ParseResult<ITSimpleBean> result = manager.parse(param);
            printResult(result);

            assertEquals(1, result.getBeans().size());
            assertFalse(result.hasCellErrors());
        }


    }


    @ParameterizedTest
    @MethodSource("fileTypeProvider")
    void sheetToBeans_formatTest(String spreadsheetFileType) throws IOException {

        String inputResourceClasspath = "/integration-test/FormatTestBean" + decideTargetFileExtension(spreadsheetFileType);
        try (InputStream input = this.getClass().getResourceAsStream(inputResourceClasspath)) {
            ParseParam<ITFormatTestBean> param =
                    newParseParamBuilder(spreadsheetFileType)
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

    static Stream<SsSheetLocator> sheetToBeans_notTheFirstSheet_provider() {
        return Stream.of(
                SsSheetLocator.byIndexLocator(1),
                SsSheetLocator.byNameLocator("The real sheet")
        );
    }


    @ParameterizedTest
    @MethodSource("fileTypeProvider")
    void sheetToBeans_typeHandlerTest(String spreadsheetFileType) throws IOException {

        String inputResourceClasspath = "/integration-test/TypeHandlerTestBean-beckham-nobody" + decideTargetFileExtension(spreadsheetFileType);
        try (InputStream input = this.getClass().getResourceAsStream(inputResourceClasspath)) {
            ParseParam<ITTypeHandlerTestBean> param =
                    newParseParamBuilder(spreadsheetFileType)
                            .setBeanClass(ITTypeHandlerTestBean.class)
                            .setSpreadsheetInput(input)
                            .build();

            ParseResult<ITTypeHandlerTestBean> result = manager.parse(param);
            printResult(result);

            assertEquals(2, result.getBeans().size());
            assertFalse(result.hasCellErrors());

            assertEquals(ITTypeHandlerTestBean.beckham(), result.getBeans().get(0));
            assertEquals(ITTypeHandlerTestBean.nobody(), result.getBeans().get(1));


        }

    }

    @ParameterizedTest
    @MethodSource("fileTypeProvider")
    void sheetToBeans_withEmptyRowsTest(String spreadsheetFileType) throws IOException {
        String inputResourceClasspath = "/integration-test/SimpleBean-with-empty-rows" + decideTargetFileExtension(spreadsheetFileType);
        try (InputStream input = this.getClass().getResourceAsStream(inputResourceClasspath)) {
            ParseParam<ITSimpleBean> param =
                    newParseParamBuilder(spreadsheetFileType)
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



    private ParseParamBuilder newParseParamBuilder(String fileType) {
        switch (fileType) {
            case SsBuiltInFileTypes.OFFICE: {
                return new OfficeParseParamBuilder();
            }
            case SsBuiltInFileTypes.CSV: {
                return new CsvParseParamBuilder().setInputCharset("utf8");
            }
            default:
                throw new IllegalStateException();
        }
    }


    private void printResult(ParseResult<?> result) {
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
