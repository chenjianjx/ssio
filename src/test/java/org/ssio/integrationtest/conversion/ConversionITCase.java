package org.ssio.integrationtest.conversion;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
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
import static org.junit.jupiter.api.Assertions.fail;

class ConversionITCase {
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
        FileUtils.writeByteArrayToFile(createSpreadsheetFile("beansToSheet_positiveTest", decideTargetFileExtension(spreadsheetFileType)), spreadsheet);

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
        FileUtils.writeByteArrayToFile(createSpreadsheetFile("beansToSheet_datumError", decideTargetFileExtension(spreadsheetFileType)), spreadsheet);

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

        // do a save for human eye check
        byte[] spreadsheet = outputStream.toByteArray();
        assertEquals(0, spreadsheet.length);

    }


    private String decideTargetFileExtension(SpreadsheetFileType spreadsheetFileType) {
        return spreadsheetFileType == SpreadsheetFileType.CSV ? ".csv" : ".xlsx";
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