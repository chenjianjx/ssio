//package org.ssio.integrationtest.conversion;
//
//import org.apache.commons.io.FileUtils;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.EnumSource;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.junit.jupiter.params.provider.ValueSource;
//import org.ssio.api.impl.ConversionManagerImpl;
//import org.ssio.api.interfaces.SpreadsheetFileType;
//import org.ssio.api.interfaces.b2s.BeansToSheetParam;
//import org.ssio.api.interfaces.b2s.BeansToSheetParamBuilder;
//import org.ssio.api.interfaces.b2s.BeansToSheetResult;
//import org.ssio.api.interfaces.b2s.DatumError;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.function.Function;
//import java.util.stream.Stream;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.junit.jupiter.api.Assertions.fail;
//
//class BeansToSheetITCase {
//    ConversionManagerImpl manager = new ConversionManagerImpl();
//
//    @BeforeAll
//    public static void init() {
//        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG");
//    }
//
//    @ParameterizedTest
//    @EnumSource(SpreadsheetFileType.class)
//    void beansToSheet_positiveTest(SpreadsheetFileType spreadsheetFileType) throws IOException {
//
//        Collection<ITBean> beans = Arrays.asList(
//                ITBeanFactory.allEmpty(),
//                ITBeanFactory.normalValues(),
//                ITBeanFactory.bigValues());
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        BeansToSheetParam<ITBean> param =
//                new BeansToSheetParamBuilder<ITBean>()
//                        .setBeanClass(ITBean.class)
//                        .setBeans(beans)
//                        .setFileType(spreadsheetFileType)
//                        .setOutputCharset("utf8") //for csv only
//                        .setOutputTarget(outputStream)
//                        .setSheetName("first sheet")
//                        .build();
//
//
//        // save it
//        BeansToSheetResult result = manager.beansToSheet(param);
//
//        // do a save for human eye check
//        byte[] spreadsheet = outputStream.toByteArray();
//        assertTrue(spreadsheet.length > 0);
//        FileUtils.writeByteArrayToFile(createSpreadsheetFile("beansToSheet_positiveTest", ITTestHelper.decideTargetFileExtension(spreadsheetFileType)), spreadsheet);
//
//        if (result.hasDatumErrors()) {
//            for (DatumError datumError : result.getDatumErrors()) {
//                System.err.println(datumError);
//            }
//            fail("There should not be datum errors");
//        }
//
//    }
//
//
//    @ParameterizedTest
//    @EnumSource(SpreadsheetFileType.class)
//    void beansToSheet_strangeAnnotationTest(SpreadsheetFileType spreadsheetFileType) throws IOException {
//
//        Collection<ITStrangeAnnotationBean> beans = Arrays.asList(new ITStrangeAnnotationBean());
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        BeansToSheetParam<ITStrangeAnnotationBean> param =
//                new BeansToSheetParamBuilder<ITStrangeAnnotationBean>()
//                        .setBeanClass(ITStrangeAnnotationBean.class)
//                        .setBeans(beans)
//                        .setFileType(spreadsheetFileType)
//                        .setOutputCharset("utf8") //for csv only
//                        .setOutputTarget(outputStream)
//                        .setSheetName("first sheet")
//                        .build();
//
//
//        // save it
//        BeansToSheetResult result = manager.beansToSheet(param);
//
//        // do a save for human eye check
//        byte[] spreadsheet = outputStream.toByteArray();
//        assertTrue(spreadsheet.length > 0);
//        FileUtils.writeByteArrayToFile(createSpreadsheetFile("beansToSheet_strangeAnnotationTest", ITTestHelper.decideTargetFileExtension(spreadsheetFileType)), spreadsheet);
//
//        if (result.hasDatumErrors()) {
//            for (DatumError datumError : result.getDatumErrors()) {
//                System.err.println(datumError);
//            }
//            fail("There should not be datum errors");
//        }
//
//    }
//
//
//    @ParameterizedTest
//    @MethodSource("beansToSheet_datumError_provider")
//    void beansToSheet_datumError(SpreadsheetFileType spreadsheetFileType, Function<DatumError, String> datumErrDisplayFunction, String datumErrDisplayFunctionName) throws IOException {
//
//        Collection<ITSickBean> beans = Arrays.asList(
//                new ITSickBean(), new ITSickBean());
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        BeansToSheetParam<ITSickBean> param =
//                new BeansToSheetParamBuilder<ITSickBean>()
//                        .setBeanClass(ITSickBean.class)
//                        .setBeans(beans)
//                        .setFileType(spreadsheetFileType)
//                        .setOutputCharset("utf8") //for csv only
//                        .setOutputTarget(outputStream)
//                        .setSheetName("sick sheet")
//                        .setDatumErrDisplayFunction(datumErrDisplayFunction)
//                        .build();
//
//
//        // save it
//        BeansToSheetResult result = manager.beansToSheet(param);
//
//        // do a save for human eye check
//        byte[] spreadsheet = outputStream.toByteArray();
//        assertTrue(spreadsheet.length > 0);
//        FileUtils.writeByteArrayToFile(createSpreadsheetFile("beansToSheet_datumError_" + datumErrDisplayFunctionName, ITTestHelper.decideTargetFileExtension(spreadsheetFileType)), spreadsheet);
//
//        if (result.hasNoDatumErrors()) {
//            fail("There should be datum errors");
//        }
//
//    }
//
//    static Stream<Arguments> beansToSheet_datumError_provider() {
//        return Stream.of(
//
//                Arguments.of(SpreadsheetFileType.OFFICE, BeansToSheetParam.DATUM_ERR_BLANK_DISPLAY_FUNCTION, "DATUM_ERR_BLANK_DISPLAY_FUNCTION"),
//                Arguments.of(SpreadsheetFileType.OFFICE, BeansToSheetParam.DEFAULT_DATUM_ERR_DISPLAY_FUNCTION, "DEFAULT_DATUM_ERR_DISPLAY_FUNCTION"),
//                Arguments.of(SpreadsheetFileType.OFFICE, BeansToSheetParam.DATUM_ERR_DISPLAY_STACKTRACE_FUNCTION, "DATUM_ERR_DISPLAY_STACKTRACE_FUNCTION"),
//                Arguments.of(SpreadsheetFileType.CSV, BeansToSheetParam.DATUM_ERR_BLANK_DISPLAY_FUNCTION, "DATUM_ERR_BLANK_DISPLAY_FUNCTION"),
//                Arguments.of(SpreadsheetFileType.CSV, BeansToSheetParam.DEFAULT_DATUM_ERR_DISPLAY_FUNCTION, "DEFAULT_DATUM_ERR_DISPLAY_FUNCTION"),
//                Arguments.of(SpreadsheetFileType.CSV, BeansToSheetParam.DATUM_ERR_DISPLAY_STACKTRACE_FUNCTION, "DATUM_ERR_DISPLAY_STACKTRACE_FUNCTION")
//        );
//    }
//
//
//    @ParameterizedTest
//    @EnumSource(SpreadsheetFileType.class)
//    void beansToSheet_datumError_noSave(SpreadsheetFileType spreadsheetFileType) throws IOException {
//
//        Collection<ITSickBean> beans = Arrays.asList(new ITSickBean());
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        BeansToSheetParam<ITSickBean> param =
//                new BeansToSheetParamBuilder<ITSickBean>()
//                        .setBeanClass(ITSickBean.class)
//                        .setBeans(beans)
//                        .setFileType(spreadsheetFileType)
//                        .setOutputCharset("utf8") //for csv only
//                        .setOutputTarget(outputStream)
//                        .setSheetName("no save")
//                        .setStillSaveIfDataError(false)
//                        .build();
//
//
//        // convert it
//        BeansToSheetResult result = manager.beansToSheet(param);
//
//
//        byte[] spreadsheet = outputStream.toByteArray();
//        assertEquals(0, spreadsheet.length);
//
//    }
//
//
//    @ParameterizedTest
//    @ValueSource(chars = {',', '\t'})
//    void beansToSheet_csvSeparator(char cellSeparator) throws IOException {
//
//        Collection<ITSimpleBean> beans = Arrays.asList(new ITSimpleBean());
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        BeansToSheetParam<ITSimpleBean> param =
//                new BeansToSheetParamBuilder<ITSimpleBean>()
//                        .setBeanClass(ITSimpleBean.class)
//                        .setBeans(beans)
//                        .setFileType(SpreadsheetFileType.CSV)
//                        .setOutputCharset("utf8") //for csv only
//                        .setCellSeparator(cellSeparator)
//                        .setOutputTarget(outputStream)
//                        .build();
//
//
//        // convert it
//        BeansToSheetResult result = manager.beansToSheet(param);
//
//        // do a save for human eye check
//        byte[] spreadsheet = outputStream.toByteArray();
//        assertTrue(spreadsheet.length > 0);
//        FileUtils.writeByteArrayToFile(createSpreadsheetFile("beansToSheet_csvSeparator_" + getSeparatorName(cellSeparator), ITTestHelper.decideTargetFileExtension(SpreadsheetFileType.CSV)), spreadsheet);
//
//        if (result.hasDatumErrors()) {
//            fail("There should be no datum errors");
//        }
//
//    }
//
//    private String getSeparatorName(char cellSeparator) {
//        if (cellSeparator == ',') {
//            return "comma";
//        }
//        if (cellSeparator == '\t') {
//            return "tab";
//        }
//        throw new UnsupportedOperationException("Don't know the name of this separator: " + cellSeparator);
//    }
//
//
//    @ParameterizedTest
//    @EnumSource(SpreadsheetFileType.class)
//    void beansToSheet_noHeader(SpreadsheetFileType spreadsheetFileType) throws IOException {
//
//        Collection<ITSimpleBean> beans = Arrays.asList(new ITSimpleBean(), new ITSimpleBean());
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        BeansToSheetParam<ITSimpleBean> param =
//                new BeansToSheetParamBuilder<ITSimpleBean>()
//                        .setBeanClass(ITSimpleBean.class)
//                        .setBeans(beans)
//                        .setFileType(spreadsheetFileType)
//                        .setOutputCharset("utf8") //for csv only
//                        .setOutputTarget(outputStream)
//                        .setCreateHeader(false)
//                        .build();
//
//
//        // save it
//        BeansToSheetResult result = manager.beansToSheet(param);
//
//        // do a save for human eye check
//        byte[] spreadsheet = outputStream.toByteArray();
//        assertTrue(spreadsheet.length > 0);
//        FileUtils.writeByteArrayToFile(createSpreadsheetFile("beansToSheet_noHeader", ITTestHelper.decideTargetFileExtension(spreadsheetFileType)), spreadsheet);
//
//        if (result.hasDatumErrors()) {
//            fail("There should be no datum errors");
//        }
//
//    }
//
//
//    @ParameterizedTest
//    @EnumSource(SpreadsheetFileType.class)
//    void beansToSheet_formatTest(SpreadsheetFileType spreadsheetFileType) throws IOException {
//
//        Collection<ITFormatTestBean> beans = Arrays.asList(
//                ITFormatTestBean.firstDayOfEveryMonthIn2020());
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        BeansToSheetParam<ITFormatTestBean> param =
//                new BeansToSheetParamBuilder<ITFormatTestBean>()
//                        .setBeanClass(ITFormatTestBean.class)
//                        .setBeans(beans)
//                        .setFileType(spreadsheetFileType)
//                        .setOutputCharset("utf8") //for csv only
//                        .setOutputTarget(outputStream)
//                        .build();
//
//
//        // save it
//        BeansToSheetResult result = manager.beansToSheet(param);
//
//        // do a save for human eye check
//        byte[] spreadsheet = outputStream.toByteArray();
//        assertTrue(spreadsheet.length > 0);
//        FileUtils.writeByteArrayToFile(createSpreadsheetFile("beansToSheet_formatTest", ITTestHelper.decideTargetFileExtension(spreadsheetFileType)), spreadsheet);
//
//        if (result.hasDatumErrors()) {
//            for (DatumError datumError : result.getDatumErrors()) {
//                System.err.println(datumError);
//            }
//            fail("There should not be datum errors");
//        }
//
//    }
//
//
//    @ParameterizedTest
//    @EnumSource(SpreadsheetFileType.class)
//    void beansToSheet_typeHandlerTest(SpreadsheetFileType spreadsheetFileType) throws IOException {
//
//        Collection<ITTypeHandlerTestBean> beans = Arrays.asList(ITTypeHandlerTestBean.beckham(), ITTypeHandlerTestBean.nobody());
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        BeansToSheetParam<ITTypeHandlerTestBean> param =
//                new BeansToSheetParamBuilder<ITTypeHandlerTestBean>()
//                        .setBeanClass(ITTypeHandlerTestBean.class)
//                        .setBeans(beans)
//                        .setFileType(spreadsheetFileType)
//                        .setOutputCharset("utf8") //for csv only
//                        .setOutputTarget(outputStream)
//                        .build();
//
//
//        // save it
//        BeansToSheetResult result = manager.beansToSheet(param);
//
//        // do a save for human eye check
//        byte[] spreadsheet = outputStream.toByteArray();
//        assertTrue(spreadsheet.length > 0);
//        FileUtils.writeByteArrayToFile(createSpreadsheetFile("beansToSheet_typeHandlerTest", ITTestHelper.decideTargetFileExtension(spreadsheetFileType)), spreadsheet);
//
//        if (result.hasDatumErrors()) {
//            for (DatumError datumError : result.getDatumErrors()) {
//                System.err.println(datumError);
//            }
//            fail("There should not be datum errors");
//        }
//
//    }
//
//
//
//    /**
//     * @param prefix
//     * @param extension including the dot "."
//     * @return
//     */
//    private File createSpreadsheetFile(String prefix, String extension) {
//        File dir = new File(System.getProperty("java.io.tmpdir"), "/ssio-it-test");
//        dir.mkdirs();
//        String filename = prefix + "-" + System.nanoTime() + extension;
//        File file = new File(dir, filename);
//        System.out.println("File created: " + file.getAbsolutePath());
//        return file;
//    }
//
//}