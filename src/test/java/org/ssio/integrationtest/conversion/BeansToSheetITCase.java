package org.ssio.integrationtest.conversion;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.ssio.api.ConversionManager;
import org.ssio.api.SpreadsheetFileType;
import org.ssio.api.b2s.BeansToSheetParam;
import org.ssio.api.b2s.BeansToSheetParamBuilder;
import org.ssio.api.b2s.BeansToSheetResult;
import org.ssio.api.b2s.DatumError;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class BeansToSheetITCase {
    ConversionManager manager = new ConversionManager();

    @BeforeAll
    public static void init() {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG");
    }

    @ParameterizedTest
    @EnumSource(SpreadsheetFileType.class)
    void beansToSheet_positiveTest(SpreadsheetFileType spreadsheetFileType) throws IOException {

        Collection<ConversionITBean> beans = Arrays.asList(
                ConversionITBeanFactory.allEmpty(),
                ConversionITBeanFactory.normalValues(),
                ConversionITBeanFactory.bigValues());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        BeansToSheetParam<ConversionITBean> param =
                new BeansToSheetParamBuilder<ConversionITBean>()
                        .setBeanClass(ConversionITBean.class)
                        .setBeans(beans)
                        .setFileType(spreadsheetFileType)
                        .setOutputTarget(outputStream)
                        .setSheetName("first sheet")
                        .build();


        // save it
        BeansToSheetResult result = manager.beansToSheet(param);

        // do a save for human eye check
        byte[] spreadsheet = outputStream.toByteArray();
        assertTrue(spreadsheet.length > 0);
        FileUtils.writeByteArrayToFile(createSpreadsheetFile("beansToSheet_positiveTest", ConversionITTestHelper.decideTargetFileExtension(spreadsheetFileType)), spreadsheet);

        if (result.hasDatumErrors()) {
            for (DatumError datumError : result.getDatumErrors()) {
                System.err.println(datumError);
            }
            fail("There should not be datum errors");
        }

    }


    @ParameterizedTest
    @EnumSource(SpreadsheetFileType.class)
    void beansToSheet_strangeAnnotationTest(SpreadsheetFileType spreadsheetFileType) throws IOException {

        Collection<ConversionITStrangeAnnotationBean> beans = Arrays.asList(new ConversionITStrangeAnnotationBean());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        BeansToSheetParam<ConversionITStrangeAnnotationBean> param =
                new BeansToSheetParamBuilder<ConversionITStrangeAnnotationBean>()
                        .setBeanClass(ConversionITStrangeAnnotationBean.class)
                        .setBeans(beans)
                        .setFileType(spreadsheetFileType)
                        .setOutputTarget(outputStream)
                        .setSheetName("first sheet")
                        .build();


        // save it
        BeansToSheetResult result = manager.beansToSheet(param);

        // do a save for human eye check
        byte[] spreadsheet = outputStream.toByteArray();
        assertTrue(spreadsheet.length > 0);
        FileUtils.writeByteArrayToFile(createSpreadsheetFile("beansToSheet_strangeAnnotationTest", ConversionITTestHelper.decideTargetFileExtension(spreadsheetFileType)), spreadsheet);

        if (result.hasDatumErrors()) {
            for (DatumError datumError : result.getDatumErrors()) {
                System.err.println(datumError);
            }
            fail("There should not be datum errors");
        }

    }


    @ParameterizedTest
    @MethodSource("beansToSheet_datumError_provider")
    void beansToSheet_datumError(SpreadsheetFileType spreadsheetFileType, Function<DatumError, String> datumErrDisplayFunction) throws IOException {

        Collection<ConversionITSickBean> beans = Arrays.asList(
                new ConversionITSickBean(), new ConversionITSickBean());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        BeansToSheetParam<ConversionITSickBean> param =
                new BeansToSheetParamBuilder<ConversionITSickBean>()
                        .setBeanClass(ConversionITSickBean.class)
                        .setBeans(beans)
                        .setFileType(spreadsheetFileType)
                        .setOutputTarget(outputStream)
                        .setSheetName("sick sheet")
                        .setDatumErrDisplayFunction(datumErrDisplayFunction)
                        .build();


        // save it
        BeansToSheetResult result = manager.beansToSheet(param);

        // do a save for human eye check
        byte[] spreadsheet = outputStream.toByteArray();
        assertTrue(spreadsheet.length > 0);
        FileUtils.writeByteArrayToFile(createSpreadsheetFile("beansToSheet_datumError", ConversionITTestHelper.decideTargetFileExtension(spreadsheetFileType)), spreadsheet);

        if (result.hasNoDatumErrors()) {
            fail("There should be datum errors");
        }

    }

    static Stream<Arguments> beansToSheet_datumError_provider() {
        return Stream.of(

                Arguments.of(SpreadsheetFileType.OFFICE, BeansToSheetParam.DATUM_ERR_BLANK_DISPLAY_FUNCTION),
                Arguments.of(SpreadsheetFileType.OFFICE, BeansToSheetParam.DEFAULT_DATUM_ERR_DISPLAY_FUNCTION),
                Arguments.of(SpreadsheetFileType.OFFICE, BeansToSheetParam.DATUM_ERR_DISPLAY_STACKTRACE_FUNCTION),
                Arguments.of(SpreadsheetFileType.CSV, BeansToSheetParam.DATUM_ERR_BLANK_DISPLAY_FUNCTION),
                Arguments.of(SpreadsheetFileType.CSV, BeansToSheetParam.DEFAULT_DATUM_ERR_DISPLAY_FUNCTION),
                Arguments.of(SpreadsheetFileType.CSV, BeansToSheetParam.DATUM_ERR_DISPLAY_STACKTRACE_FUNCTION)
        );
    }


    @ParameterizedTest
    @EnumSource(SpreadsheetFileType.class)
    void beansToSheet_datumError_noSave(SpreadsheetFileType spreadsheetFileType) throws IOException {

        Collection<ConversionITSickBean> beans = Arrays.asList(new ConversionITSickBean());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        BeansToSheetParam<ConversionITSickBean> param =
                new BeansToSheetParamBuilder<ConversionITSickBean>()
                        .setBeanClass(ConversionITSickBean.class)
                        .setBeans(beans)
                        .setFileType(spreadsheetFileType)
                        .setOutputTarget(outputStream)
                        .setSheetName("no save")
                        .setStillSaveIfDataError(false)
                        .build();


        // convert it
        BeansToSheetResult result = manager.beansToSheet(param);


        byte[] spreadsheet = outputStream.toByteArray();
        assertEquals(0, spreadsheet.length);

    }


    @ParameterizedTest
    @ValueSource(chars = {',', '\t'})
    void beansToSheet_csvSeparator(char cellSeparator) throws IOException {

        Collection<ConversionITSimpleBean> beans = Arrays.asList(new ConversionITSimpleBean());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        BeansToSheetParam<ConversionITSimpleBean> param =
                new BeansToSheetParamBuilder<ConversionITSimpleBean>()
                        .setBeanClass(ConversionITSimpleBean.class)
                        .setBeans(beans)
                        .setFileType(SpreadsheetFileType.CSV)
                        .setCellSeparator(cellSeparator)
                        .setOutputTarget(outputStream)
                        .build();


        // convert it
        BeansToSheetResult result = manager.beansToSheet(param);

        // do a save for human eye check
        byte[] spreadsheet = outputStream.toByteArray();
        assertTrue(spreadsheet.length > 0);
        FileUtils.writeByteArrayToFile(createSpreadsheetFile("beansToSheet_csvSeparator", ConversionITTestHelper.decideTargetFileExtension(SpreadsheetFileType.CSV)), spreadsheet);

        if (result.hasDatumErrors()) {
            fail("There should be no datum errors");
        }

    }


    @ParameterizedTest
    @EnumSource(SpreadsheetFileType.class)
    void beansToSheet_noHeader(SpreadsheetFileType spreadsheetFileType) throws IOException {

        Collection<ConversionITSimpleBean> beans = Arrays.asList(new ConversionITSimpleBean(), new ConversionITSimpleBean());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        BeansToSheetParam<ConversionITSimpleBean> param =
                new BeansToSheetParamBuilder<ConversionITSimpleBean>()
                        .setBeanClass(ConversionITSimpleBean.class)
                        .setBeans(beans)
                        .setFileType(spreadsheetFileType)
                        .setOutputTarget(outputStream)
                        .setCreateHeader(false)
                        .build();


        // save it
        BeansToSheetResult result = manager.beansToSheet(param);

        // do a save for human eye check
        byte[] spreadsheet = outputStream.toByteArray();
        assertTrue(spreadsheet.length > 0);
        FileUtils.writeByteArrayToFile(createSpreadsheetFile("beansToSheet_noHeader", ConversionITTestHelper.decideTargetFileExtension(spreadsheetFileType)), spreadsheet);

        if (result.hasDatumErrors()) {
            fail("There should be no datum errors");
        }

    }


    /**
     * @param prefix
     * @param extension including the dot "."
     * @return
     */
    private File createSpreadsheetFile(String prefix, String extension) {
        File dir = new File(System.getProperty("java.io.tmpdir"), "/ssio-it-test");
        dir.mkdirs();
        String filename = prefix + "-" + System.nanoTime() + extension;
        File file = new File(dir, filename);
        System.out.println("File created: " + file.getAbsolutePath());
        return file;
    }

}