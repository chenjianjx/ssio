package org.ssio.integrationtest.conversion;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.ssio.api.ConversionManager;
import org.ssio.api.SpreadsheetFileType;
import org.ssio.api.common.abstractsheet.helper.SsSheetLocator;
import org.ssio.api.s2b.CellError;
import org.ssio.api.s2b.PropFromColumnMappingMode;
import org.ssio.api.s2b.SheetToBeansParam;
import org.ssio.api.s2b.SheetToBeansParamBuilder;
import org.ssio.api.s2b.SheetToBeansResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.ssio.integrationtest.conversion.ConversionITTestHelper.decideTargetFileExtension;

public class SheetToBeansITCase {
    ConversionManager manager = new ConversionManager();


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
            SheetToBeansParam<ConversionITBean> param =
                    new SheetToBeansParamBuilder<ConversionITBean>()
                            .setBeanClass(ConversionITBean.class)
                            .setFileType(spreadsheetFileType)
                            .setSpreadsheetInput(input)
                            .setInputCharset("utf8") //for csv only
                            .setPropFromColumnMappingMode(propFromColumnMappingMode)
                            .build();

            SheetToBeansResult<ConversionITBean> result = manager.sheetToBeans(param);
            printResult(result);

            assertEquals(3, result.getBeans().size());
            assertFalse(result.hasCellErrors());

            assertEquals(ConversionITBeanFactory.allEmpty(), result.getBeans().get(0));
            assertEquals(ConversionITBeanFactory.normalValues(), result.getBeans().get(1));
            assertEquals(ConversionITBeanFactory.bigValues(), result.getBeans().get(2));

            result.getBeans().forEach(b -> System.out.println(b));
        }

    }




    @ParameterizedTest
    @MethodSource("fileType_mappingMode_provider")
    void sheetToBeans_strangeAnnotationTest(SpreadsheetFileType spreadsheetFileType, PropFromColumnMappingMode propFromColumnMappingMode) throws IOException {

        String inputResourceClasspath = "/integration-test/StrangeAnnotationBean" + decideTargetFileExtension(spreadsheetFileType);
        try (InputStream input = this.getClass().getResourceAsStream(inputResourceClasspath)) {
            SheetToBeansParam<ConversionITStrangeAnnotationBean> param =
                    new SheetToBeansParamBuilder<ConversionITStrangeAnnotationBean>()
                            .setBeanClass(ConversionITStrangeAnnotationBean.class)
                            .setFileType(spreadsheetFileType)
                            .setSpreadsheetInput(input)
                            .setInputCharset("utf8") //for csv only
                            .setPropFromColumnMappingMode(propFromColumnMappingMode)
                            .build();

            SheetToBeansResult<ConversionITStrangeAnnotationBean> result = manager.sheetToBeans(param);
            printResult(result);

            assertEquals(1, result.getBeans().size());
            assertFalse(result.hasCellErrors());

            assertEquals(new ConversionITStrangeAnnotationBean(), result.getBeans().get(0));
            result.getBeans().forEach(b -> System.out.println(b));
        }

    }





    @ParameterizedTest
    @EnumSource(SpreadsheetFileType.class)
    void sheetToBeans_cellErrors(SpreadsheetFileType spreadsheetFileType) throws IOException {

        String inputResourceClasspath = "/integration-test/SickBean" + decideTargetFileExtension(spreadsheetFileType);
        try (InputStream input = this.getClass().getResourceAsStream(inputResourceClasspath)) {
            SheetToBeansParam<ConversionITSickBean> param =
                    new SheetToBeansParamBuilder<ConversionITSickBean>()
                            .setBeanClass(ConversionITSickBean.class)
                            .setFileType(spreadsheetFileType)
                            .setSpreadsheetInput(input)
                            .setInputCharset("utf8") //for csv only
                            .build();

            SheetToBeansResult<ConversionITSickBean> result = manager.sheetToBeans(param);
            printResult(result);

            assertEquals(1, result.getBeans().size());
            ConversionITSickBean bean = result.getBeans().get(0);
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
            SheetToBeansParam<ConversionITSimpleBean> param =
                    new SheetToBeansParamBuilder<ConversionITSimpleBean>()
                            .setBeanClass(ConversionITSimpleBean.class)
                            .setFileType(SpreadsheetFileType.CSV)
                            .setSpreadsheetInput(input)
                            .setInputCharset("utf8")
                            .setCellSeparator(cellSeparator)
                            .build();

            SheetToBeansResult<ConversionITSimpleBean> result = manager.sheetToBeans(param);
            printResult(result);

            assertEquals(1, result.getBeans().size());
            assertFalse(result.hasCellErrors());

            ConversionITSimpleBean bean = result.getBeans().get(0);
            assertEquals("another string", bean.getStr());
            assertEquals(200, bean.getPrimInt());
        }


    }


    @ParameterizedTest
    @MethodSource("sheetToBeans_notTheFirstSheet_provider")
    void sheetToBeans_notTheFirstSheet(SsSheetLocator sheetLocator) throws IOException {

        String inputResourceClasspath = "/integration-test/SimpleBean-content-not-in-first-sheet.xlsx";
        try (InputStream input = this.getClass().getResourceAsStream(inputResourceClasspath)) {
            SheetToBeansParam<ConversionITSimpleBean> param =
                    new SheetToBeansParamBuilder<ConversionITSimpleBean>()
                            .setBeanClass(ConversionITSimpleBean.class)
                            .setFileType(SpreadsheetFileType.OFFICE)
                            .setSpreadsheetInput(input)
                            .setSheetLocator(sheetLocator)
                            .build();

            SheetToBeansResult<ConversionITSimpleBean> result = manager.sheetToBeans(param);
            printResult(result);

            assertEquals(1, result.getBeans().size());
            assertFalse(result.hasCellErrors());
        }


    }

    static Stream<SsSheetLocator> sheetToBeans_notTheFirstSheet_provider() {
        return Stream.of(
                SsSheetLocator.byIndexLocator(1),
                SsSheetLocator.byNameLocator("The real sheet")
        );
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
