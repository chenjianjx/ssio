package org.ssio.spi.clientexternal.filetypespecific.office.save;

import org.ssio.api.external.save.DatumError;
import org.ssio.api.external.save.SaveParamBuilder;

import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class OfficeSaveParamBuilder<BEAN> extends SaveParamBuilder<BEAN, OfficeSaveParamBuilder<BEAN>> {

    private String sheetName;

    /**
     * nullable.
     */
    public OfficeSaveParamBuilder<BEAN> setSheetName(String sheetName) {
        this.sheetName = sheetName;
        return this;
    }

    @Override
    protected void fileTypeSpecificValidate(List<String> errors) {
        //nothing more to validate
    }

    @Override
    protected OfficeSaveParam<BEAN> fileTypeSpecificBuild(Collection<BEAN> beans, Class<BEAN> beanClass, OutputStream outputTarget, boolean createHeader, boolean stillSaveIfDataError, Function<DatumError, String> datumErrDisplayFunction) {
        return new OfficeSaveParam<>(beans, beanClass, outputTarget, createHeader, stillSaveIfDataError, datumErrDisplayFunction, sheetName);
    }
}
