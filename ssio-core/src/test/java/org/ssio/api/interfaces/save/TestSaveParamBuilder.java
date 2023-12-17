package org.ssio.api.interfaces.save;

import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class TestSaveParamBuilder<BEAN> extends SaveParamBuilder<BEAN, TestSaveParamBuilder<BEAN>> {

    @Override
    protected void fileTypeSpecificValidate(List errors) {

    }

    @Override
    protected TestSaveParam fileTypeSpecificBuild(Collection collection, Class aClass, OutputStream outputTarget,
                                                  boolean createHeader,
                                                  boolean stillSaveIfDataError, Function datumErrDisplayFunction) {
        return new TestSaveParam(collection, aClass, outputTarget, createHeader, stillSaveIfDataError, datumErrDisplayFunction);
    }
}
