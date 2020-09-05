package org.ssio.api.external.save;

import org.junit.jupiter.api.Test;

import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SaveParamBuilderTest {

    public static class TestParam<BEAN> extends SaveParam<BEAN> {

        protected TestParam(Collection collection, Class aClass, OutputStream outputTarget, boolean createHeader, boolean stillSaveIfDataError, Function datumErrDisplayFunction) {
            super(collection, aClass, outputTarget, createHeader, stillSaveIfDataError, datumErrDisplayFunction);
        }

        @Override
        public String getSpreadsheetFileType() {
            return "anything";
        }
    }


    public static class TestParamBuilder<BEAN> extends SaveParamBuilder<BEAN, TestParamBuilder<BEAN>> {

        @Override
        protected void fileTypeSpecificValidate(List errors) {

        }

        @Override
        protected TestParam fileTypeSpecificBuild(Collection collection, Class aClass, OutputStream outputTarget, boolean createHeader, boolean stillSaveIfDataError, Function datumErrDisplayFunction) {
            return new TestParam(collection, aClass, outputTarget, createHeader, stillSaveIfDataError, datumErrDisplayFunction);
        }
    }

    @Test
    void build_allNull() {
        TestParamBuilder<String> builder = new TestParamBuilder<String>().setDatumErrDisplayFunction(null);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, builder::build);
        assertTrue(e.getMessage().contains("beans cannot be null"));
        assertTrue(e.getMessage().contains("beanClass cannot be null"));
        assertTrue(e.getMessage().contains("outputTarget cannot be null"));
        assertTrue(e.getMessage().contains("datumErrDisplayFunction cannot be null"));
    }

}