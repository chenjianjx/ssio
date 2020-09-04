package org.ssio.api.impl.save.filetypespecific.office;

import org.ssio.api.interfaces.save.SaveParamBuilder;
import org.ssio.api.interfaces.save.DatumError;

import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class OfficeSaveParamBuilder<BEAN> extends SaveParamBuilder<BEAN, OfficeSaveParam<BEAN>, OfficeSaveParamBuilder> {

    private String sheetName;

    /**
     * nullable.
     * will be ignored by csv
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
    protected OfficeSaveParam fileTypeSpecificBuild(Collection<BEAN> beans, Class<BEAN> beanClass, OutputStream outputTarget, boolean createHeader, boolean stillSaveIfDataError, Function<DatumError, String> datumErrDisplayFunction) {
        return new OfficeSaveParam(beans, beanClass, outputTarget, createHeader, stillSaveIfDataError, datumErrDisplayFunction, sheetName);
    }
}
