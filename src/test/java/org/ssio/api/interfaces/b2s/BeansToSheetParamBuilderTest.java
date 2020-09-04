package org.ssio.api.interfaces.b2s;

import org.junit.jupiter.api.Test;

import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BeansToSheetParamBuilderTest {

    public static class TestParam<BEAN> extends BeansToSheetParam<BEAN> {

        protected TestParam(Collection collection, Class aClass, OutputStream outputTarget, boolean createHeader, boolean stillSaveIfDataError, Function datumErrDisplayFunction) {
            super(collection, aClass, outputTarget, createHeader, stillSaveIfDataError, datumErrDisplayFunction);
        }
    }


    public static class TestParamBuilder<BEAN> extends BeansToSheetParamBuilder<BEAN, TestParam<BEAN>, TestParamBuilder> {

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