package org.ssio.api.interfaces.save;

import java.io.OutputStream;
import java.util.Collection;
import java.util.function.Function;

public class TestSaveParam<BEAN> extends SaveParam<BEAN> {

    protected TestSaveParam(Collection collection, Class aClass, OutputStream outputTarget, boolean createHeader, boolean stillSaveIfDataError, Function datumErrDisplayFunction) {
        super(collection, aClass, outputTarget, createHeader, stillSaveIfDataError, datumErrDisplayFunction);
    }
}