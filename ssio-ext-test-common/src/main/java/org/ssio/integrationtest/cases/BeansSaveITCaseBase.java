package org.ssio.integrationtest.cases;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.ssio.api.interfaces.save.DatumError;
import org.ssio.api.interfaces.save.SaveParam;
import org.ssio.api.interfaces.save.SaveParamBuilder;
import org.ssio.api.interfaces.save.SaveResult;
import org.ssio.integrationtest.beans.ITBean;
import org.ssio.integrationtest.beans.ITBeanFactory;
import org.ssio.integrationtest.beans.ITFormatTestBean;
import org.ssio.integrationtest.beans.ITSickBean;
import org.ssio.integrationtest.beans.ITSimpleBean;
import org.ssio.integrationtest.beans.ITStrangeAnnotationBean;
import org.ssio.integrationtest.beans.ITTypeHandlerTestBean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.ssio.integrationtest.support.SsioITHelper.createSpreadsheetFile;

public abstract class BeansSaveITCaseBase extends SaveParseITCaseBase {

    protected abstract SaveParamBuilder newSaveParamBuilder();

    @Test
    void save_positiveTest() throws IOException {
        Collection<ITBean> beans = Arrays.asList(
                ITBeanFactory.allEmpty(),
                ITBeanFactory.normalValues(),
                ITBeanFactory.bigValues());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        SaveParam<ITBean> param =
                newSaveParamBuilder()
                        .setBeanClass(ITBean.class)
                        .setBeans(beans)
                        .setOutputTarget(outputStream)
                        .build();


        // save it
        SaveResult result = manager.save(param);

        // do a save for human eye check
        byte[] spreadsheet = outputStream.toByteArray();
        assertTrue(spreadsheet.length > 0);
        FileUtils.writeByteArrayToFile(createSpreadsheetFile("save_positiveTest", getSpreadsheetFileExtension()), spreadsheet);

        if (result.hasDatumErrors()) {
            for (DatumError datumError : result.getDatumErrors()) {
                System.err.println(datumError);
            }
            fail("There should not be datum errors");
        }

    }


    @Test
    void save_strangeAnnotationTest() throws IOException {
        Collection<ITStrangeAnnotationBean> beans = Arrays.asList(new ITStrangeAnnotationBean());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        SaveParam<ITStrangeAnnotationBean> param =
                newSaveParamBuilder()
                        .setBeanClass(ITStrangeAnnotationBean.class)
                        .setBeans(beans)
                        .setOutputTarget(outputStream)
                        .build();


        // save it
        SaveResult result = manager.save(param);

        // do a save for human eye check
        byte[] spreadsheet = outputStream.toByteArray();
        assertTrue(spreadsheet.length > 0);
        FileUtils.writeByteArrayToFile(createSpreadsheetFile("save_strangeAnnotationTest", getSpreadsheetFileExtension()), spreadsheet);

        if (result.hasDatumErrors()) {
            for (DatumError datumError : result.getDatumErrors()) {
                System.err.println(datumError);
            }
            fail("There should not be datum errors");
        }

    }


    @ParameterizedTest
    @MethodSource("save_datumError_provider")
    void save_datumError(Function<DatumError, String> datumErrDisplayFunction, String datumErrDisplayFunctionName) throws IOException {
        Collection<ITSickBean> beans = Arrays.asList(
                new ITSickBean(), new ITSickBean());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        SaveParam<ITSickBean> param =
                newSaveParamBuilder()
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
        FileUtils.writeByteArrayToFile(createSpreadsheetFile("save_datumError_" + datumErrDisplayFunctionName, getSpreadsheetFileExtension()), spreadsheet);

        if (result.hasNoDatumErrors()) {
            fail("There should be datum errors");
        }

    }

    static Stream<Arguments> save_datumError_provider() {
        return Stream.of(

                Arguments.of(SaveParam.DATUM_ERR_BLANK_DISPLAY_FUNCTION, "DATUM_ERR_BLANK_DISPLAY_FUNCTION"),
                Arguments.of(SaveParam.DEFAULT_DATUM_ERR_DISPLAY_FUNCTION, "DEFAULT_DATUM_ERR_DISPLAY_FUNCTION"),
                Arguments.of(SaveParam.DATUM_ERR_DISPLAY_STACKTRACE_FUNCTION, "DATUM_ERR_DISPLAY_STACKTRACE_FUNCTION")
        );
    }


    @Test
    void save_datumError_noSave() throws IOException {

        Collection<ITSickBean> beans = Arrays.asList(new ITSickBean());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        SaveParam<ITSickBean> param =
                newSaveParamBuilder()
                        .setBeanClass(ITSickBean.class)
                        .setBeans(beans)
                        .setOutputTarget(outputStream)
                        .setStillSaveIfDataError(false)
                        .build();


        // convert it
        manager.save(param);


        byte[] spreadsheet = outputStream.toByteArray();
        assertEquals(0, spreadsheet.length);

    }

    @Test
    void save_noHeader() throws IOException {
        Collection<ITSimpleBean> beans = Arrays.asList(new ITSimpleBean(), new ITSimpleBean());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        SaveParam<ITSimpleBean> param =
                newSaveParamBuilder()
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
        FileUtils.writeByteArrayToFile(createSpreadsheetFile("save_noHeader", getSpreadsheetFileExtension()), spreadsheet);

        if (result.hasDatumErrors()) {
            fail("There should be no datum errors");
        }

    }


    @Test
    void save_formatTest() throws IOException {
        Collection<ITFormatTestBean> beans = Arrays.asList(
                ITFormatTestBean.firstDayOfEveryMonthIn2020());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        SaveParam<ITFormatTestBean> param =
                newSaveParamBuilder()
                        .setBeanClass(ITFormatTestBean.class)
                        .setBeans(beans)
                        .setOutputTarget(outputStream)
                        .build();


        // save it
        SaveResult result = manager.save(param);

        // do a save for human eye check
        byte[] spreadsheet = outputStream.toByteArray();
        assertTrue(spreadsheet.length > 0);
        FileUtils.writeByteArrayToFile(createSpreadsheetFile("save_formatTest", getSpreadsheetFileExtension()), spreadsheet);

        if (result.hasDatumErrors()) {
            for (DatumError datumError : result.getDatumErrors()) {
                System.err.println(datumError);
            }
            fail("There should not be datum errors");
        }

    }


    @Test
    void save_typeHandlerTest() throws IOException {
        Collection<ITTypeHandlerTestBean> beans = Arrays.asList(ITTypeHandlerTestBean.beckham(), ITTypeHandlerTestBean.nobody());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        SaveParam<ITTypeHandlerTestBean> param =
                newSaveParamBuilder()
                        .setBeanClass(ITTypeHandlerTestBean.class)
                        .setBeans(beans)
                        .setOutputTarget(outputStream)
                        .build();


        // save it
        SaveResult result = manager.save(param);

        // do a save for human eye check
        byte[] spreadsheet = outputStream.toByteArray();
        assertTrue(spreadsheet.length > 0);
        FileUtils.writeByteArrayToFile(createSpreadsheetFile("save_typeHandlerTest", getSpreadsheetFileExtension()), spreadsheet);

        if (result.hasDatumErrors()) {
            for (DatumError datumError : result.getDatumErrors()) {
                System.err.println(datumError);
            }
            fail("There should not be datum errors");
        }

    }

}