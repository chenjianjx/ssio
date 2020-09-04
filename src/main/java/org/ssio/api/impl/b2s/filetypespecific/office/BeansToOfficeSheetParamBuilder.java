package org.ssio.api.impl.b2s.filetypespecific.office;

import org.ssio.api.interfaces.b2s.BeansToSheetParamBuilder;
import org.ssio.api.interfaces.b2s.DatumError;

import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class BeansToOfficeSheetParamBuilder<BEAN> extends BeansToSheetParamBuilder<BEAN, BeansToOfficeSheetParam<BEAN>, BeansToOfficeSheetParamBuilder> {

    private String sheetName;

    /**
     * nullable.
     * will be ignored by csv
     */
    public BeansToOfficeSheetParamBuilder<BEAN> setSheetName(String sheetName) {
        this.sheetName = sheetName;
        return this;
    }

    @Override
    protected void fileTypeSpecificValidate(List<String> errors) {
        //nothing more to validate
    }

    @Override
    protected BeansToOfficeSheetParam fileTypeSpecificBuild(Collection<BEAN> beans, Class<BEAN> beanClass, OutputStream outputTarget, boolean createHeader, boolean stillSaveIfDataError, Function<DatumError, String> datumErrDisplayFunction) {
        return new BeansToOfficeSheetParam(beans, beanClass, outputTarget, createHeader, stillSaveIfDataError, datumErrDisplayFunction, sheetName);
    }
}
