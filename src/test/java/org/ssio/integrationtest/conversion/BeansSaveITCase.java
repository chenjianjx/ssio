package org.ssio.integrationtest.conversion;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.ssio.api.impl.SsioManagerImpl;
import org.ssio.api.impl.filetypespecific.csv.save.CsvSaveParamBuilder;
import org.ssio.api.impl.filetypespecific.office.save.OfficeSaveParamBuilder;
import org.ssio.api.impl.filetypespecific.SsBuiltInFileTypes;
import org.ssio.api.interfaces.SsioManager;
import org.ssio.api.interfaces.save.DatumError;
import org.ssio.api.interfaces.save.SaveParam;
import org.ssio.api.interfaces.save.SaveParamBuilder;
import org.ssio.api.interfaces.save.SaveResult;

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

class BeansSaveITCase {
    SsioManager manager = new SsioManagerImpl();

    @BeforeAll
    public static void init() {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG");
    }

    @ParameterizedTest
    @EnumSource(SsBuiltInFileTypes.class)
    void save_positiveTest(SsBuiltInFileTypes spreadsheetFileType) throws IOException {

        Collection<ITBean> beans = Arrays.asList(
                ITBeanFactory.allEmpty(),
                ITBeanFactory.normalValues(),
                ITBeanFactory.bigValues());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        SaveParam<ITBean> param =
                newSaveParamBuilder(spreadsheetFileType)
                        .setBeanClass(ITBean.class)
                        .setBeans(beans)
                        .setOutputTarget(outputStream)
                        .build();


        // save it
        SaveResult result = manager.save(param);

        // do a save for human eye check
        byte[] spreadsheet = outputStream.toByteArray();
        assertTrue(spreadsheet.length > 0);
        FileUtils.writeByteArrayToFile(createSpreadsheetFile("save_positiveTest", ITTestHelper.decideTargetFileExtension(spreadsheetFileType)), spreadsheet);

        if (result.hasDatumErrors()) {
            for (DatumError datumError : result.getDatumErrors()) {
                System.err.println(datumError);
            }
            fail("There should not be datum errors");
        }

    }


    @ParameterizedTest
    @EnumSource(SsBuiltInFileTypes.class)
    void save_strangeAnnotationTest(SsBuiltInFileTypes spreadsheetFileType) throws IOException {

        Collection<ITStrangeAnnotationBean> beans = Arrays.asList(new ITStrangeAnnotationBean());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        SaveParam<ITStrangeAnnotationBean> param =
                newSaveParamBuilder(spreadsheetFileType)
                        .setBeanClass(ITStrangeAnnotationBean.class)
                        .setBeans(beans)
                        .setOutputTarget(outputStream)
                        .build();


        // save it
        SaveResult result = manager.save(param);

        // do a save for human eye check
        byte[] spreadsheet = outputStream.toByteArray();
        assertTrue(spreadsheet.length > 0);
        FileUtils.writeByteArrayToFile(createSpreadsheetFile("save_strangeAnnotationTest", ITTestHelper.decideTargetFileExtension(spreadsheetFileType)), spreadsheet);

        if (result.hasDatumErrors()) {
            for (DatumError datumError : result.getDatumErrors()) {
                System.err.println(datumError);
            }
            fail("There should not be datum errors");
        }

    }


    @ParameterizedTest
    @MethodSource("save_datumError_provider")
    void save_datumError(SsBuiltInFileTypes spreadsheetFileType, Function<DatumError, String> datumErrDisplayFunction, String datumErrDisplayFunctionName) throws IOException {

        Collection<ITSickBean> beans = Arrays.asList(
                new ITSickBean(), new ITSickBean());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        SaveParam<ITSickBean> param =
                newSaveParamBuilder(spreadsheetFileType)
                        .setBeanClass(ITSickBean.class)
                        .setBeans(beans)
                        .setOutputTarget(outputStream)
                        .setDatumErrDisplayFunction(datumErrDisplayFunction)
                        .build();


        // save it
        SaveResult result = manager.save(param);

        // do a save for human eye check
        byte[] spreadsheet = outputStream.toByteArray();
        assertTrue(spreadsheet.length > 0);
        FileUtils.writeByteArrayToFile(createSpreadsheetFile("save_datumError_" + datumErrDisplayFunctionName, ITTestHelper.decideTargetFileExtension(spreadsheetFileType)), spreadsheet);

        if (result.hasNoDatumErrors()) {
            fail("There should be datum errors");
        }

    }

    static Stream<Arguments> save_datumError_provider() {
        return Stream.of(

                Arguments.of(SsBuiltInFileTypes.OFFICE, SaveParam.DATUM_ERR_BLANK_DISPLAY_FUNCTION, "DATUM_ERR_BLANK_DISPLAY_FUNCTION"),
                Arguments.of(SsBuiltInFileTypes.OFFICE, SaveParam.DEFAULT_DATUM_ERR_DISPLAY_FUNCTION, "DEFAULT_DATUM_ERR_DISPLAY_FUNCTION"),
                Arguments.of(SsBuiltInFileTypes.OFFICE, SaveParam.DATUM_ERR_DISPLAY_STACKTRACE_FUNCTION, "DATUM_ERR_DISPLAY_STACKTRACE_FUNCTION"),
                Arguments.of(SsBuiltInFileTypes.CSV, SaveParam.DATUM_ERR_BLANK_DISPLAY_FUNCTION, "DATUM_ERR_BLANK_DISPLAY_FUNCTION"),
                Arguments.of(SsBuiltInFileTypes.CSV, SaveParam.DEFAULT_DATUM_ERR_DISPLAY_FUNCTION, "DEFAULT_DATUM_ERR_DISPLAY_FUNCTION"),
                Arguments.of(SsBuiltInFileTypes.CSV, SaveParam.DATUM_ERR_DISPLAY_STACKTRACE_FUNCTION, "DATUM_ERR_DISPLAY_STACKTRACE_FUNCTION")
        );
    }


    @ParameterizedTest
    @EnumSource(SsBuiltInFileTypes.class)
    void save_datumError_noSave(SsBuiltInFileTypes spreadsheetFileType) throws IOException {

        Collection<ITSickBean> beans = Arrays.asList(new ITSickBean());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        SaveParam<ITSickBean> param =
                newSaveParamBuilder(spreadsheetFileType)
                        .setBeanClass(ITSickBean.class)
                        .setBeans(beans)
                        .setOutputTarget(outputStream)
                        .setStillSaveIfDataError(false)
                        .build();


        // convert it
        SaveResult result = manager.save(param);


        byte[] spreadsheet = outputStream.toByteArray();
        assertEquals(0, spreadsheet.length);

    }


    @ParameterizedTest
    @ValueSource(chars = {',', '\t'})
    void save_csvSeparator(char cellSeparator) throws IOException {

        Collection<ITSimpleBean> beans = Arrays.asList(new ITSimpleBean());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


        SaveParam<ITSimpleBean> param = new CsvSaveParamBuilder<ITSimpleBean>()
                .setOutputCharset("utf8")
                .setBeanClass(ITSimpleBean.class)
                .setBeans(beans)
                .setCellSeparator(cellSeparator)
                .setOutputTarget(outputStream)
                .build();


        // convert it
        SaveResult result = manager.save(param);

        // do a save for human eye check
        byte[] spreadsheet = outputStream.toByteArray();
        assertTrue(spreadsheet.length > 0);
        FileUtils.writeByteArrayToFile(createSpreadsheetFile("save_csvSeparator_" + getSeparatorName(cellSeparator), ITTestHelper.decideTargetFileExtension(SsBuiltInFileTypes.CSV)), spreadsheet);

        if (result.hasDatumErrors()) {
            fail("There should be no datum errors");
        }

    }

    private String getSeparatorName(char cellSeparator) {
        if (cellSeparator == ',') {
            return "comma";
        }
        if (cellSeparator == '\t') {
            return "tab";
        }
        throw new UnsupportedOperationException("Don't know the name of this separator: " + cellSeparator);
    }


    @ParameterizedTest
    @EnumSource(SsBuiltInFileTypes.class)
    void save_noHeader(SsBuiltInFileTypes spreadsheetFileType) throws IOException {

        Collection<ITSimpleBean> beans = Arrays.asList(new ITSimpleBean(), new ITSimpleBean());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        SaveParam<ITSimpleBean> param =
                newSaveParamBuilder(spreadsheetFileType)
                        .setBeanClass(ITSimpleBean.class)
                        .setBeans(beans)
                        .setOutputTarget(outputStream)
                        .setCreateHeader(false)
                        .build();


        // save it
        SaveResult result = manager.save(param);

        // do a save for human eye check
        byte[] spreadsheet = outputStream.toByteArray();
        assertTrue(spreadsheet.length > 0);
        FileUtils.writeByteArrayToFile(createSpreadsheetFile("save_noHeader", ITTestHelper.decideTargetFileExtension(spreadsheetFileType)), spreadsheet);

        if (result.hasDatumErrors()) {
            fail("There should be no datum errors");
        }

    }


    @ParameterizedTest
    @EnumSource(SsBuiltInFileTypes.class)
    void save_formatTest(SsBuiltInFileTypes spreadsheetFileType) throws IOException {

        Collection<ITFormatTestBean> beans = Arrays.asList(
                ITFormatTestBean.firstDayOfEveryMonthIn2020());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        SaveParam<ITFormatTestBean> param =
                newSaveParamBuilder(spreadsheetFileType)
                        .setBeanClass(ITFormatTestBean.class)
                        .setBeans(beans)
                        .setOutputTarget(outputStream)
                        .build();


        // save it
        SaveResult result = manager.save(param);

        // do a save for human eye check
        byte[] spreadsheet = outputStream.toByteArray();
        assertTrue(spreadsheet.length > 0);
        FileUtils.writeByteArrayToFile(createSpreadsheetFile("save_formatTest", ITTestHelper.decideTargetFileExtension(spreadsheetFileType)), spreadsheet);

        if (result.hasDatumErrors()) {
            for (DatumError datumError : result.getDatumErrors()) {
                System.err.println(datumError);
            }
            fail("There should not be datum errors");
        }

    }


    @ParameterizedTest
    @EnumSource(SsBuiltInFileTypes.class)
    void save_typeHandlerTest(SsBuiltInFileTypes spreadsheetFileType) throws IOException {

        Collection<ITTypeHandlerTestBean> beans = Arrays.asList(ITTypeHandlerTestBean.beckham(), ITTypeHandlerTestBean.nobody());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        SaveParam<ITTypeHandlerTestBean> param =
                newSaveParamBuilder(spreadsheetFileType)
                        .setBeanClass(ITTypeHandlerTestBean.class)
                        .setBeans(beans)
                        .setOutputTarget(outputStream)
                        .build();


        // save it
        SaveResult result = manager.save(param);

        // do a save for human eye check
        byte[] spreadsheet = outputStream.toByteArray();
        assertTrue(spreadsheet.length > 0);
        FileUtils.writeByteArrayToFile(createSpreadsheetFile("save_typeHandlerTest", ITTestHelper.decideTargetFileExtension(spreadsheetFileType)), spreadsheet);

        if (result.hasDatumErrors()) {
            for (DatumError datumError : result.getDatumErrors()) {
                System.err.println(datumError);
            }
            fail("There should not be datum errors");
        }

    }


    private SaveParamBuilder newSaveParamBuilder(SsBuiltInFileTypes fileType) {
        switch (fileType) {
            case OFFICE: {
                return new OfficeSaveParamBuilder().setSheetName("first sheet");
            }
            case CSV: {
                return new CsvSaveParamBuilder().setOutputCharset("utf8");
            }
            default:
                throw new IllegalStateException();
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