package org.ssio.api.impl.b2s.filetypespecific.office;

import org.ssio.api.interfaces.b2s.BeansToSheetParam;
import org.ssio.api.interfaces.b2s.DatumError;

import java.io.OutputStream;
import java.util.Collection;
import java.util.function.Function;

public class BeansToOfficeSheetParam<BEAN> extends BeansToSheetParam<BEAN> {

    private String sheetName;

    /**
     * Please use the builder to create an instance
     */
    protected BeansToOfficeSheetParam(Collection<BEAN> beans, Class<BEAN> beanClass, OutputStream outputTarget,
                                   boolean createHeader, boolean stillSaveIfDataError, Function<DatumError, String> datumErrDisplayFunction,
                                   String sheetName) {
        super(beans, beanClass, outputTarget, createHeader, stillSaveIfDataError, datumErrDisplayFunction);
        this.sheetName = sheetName;
    }

    public String getSheetName() {
        return sheetName;
    }
}
