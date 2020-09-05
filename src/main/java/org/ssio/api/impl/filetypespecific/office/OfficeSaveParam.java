package org.ssio.api.impl.filetypespecific.office;

import org.ssio.api.impl.filetypespecific.SsBuiltInFileTypes;
import org.ssio.api.interfaces.save.SaveParam;
import org.ssio.api.interfaces.save.DatumError;

import java.io.OutputStream;
import java.util.Collection;
import java.util.function.Function;

public class OfficeSaveParam<BEAN> extends SaveParam<BEAN> {

    private String sheetName;

    /**
     * Please use the builder to create an instance
     */
    protected OfficeSaveParam(Collection<BEAN> beans, Class<BEAN> beanClass, OutputStream outputTarget,
                              boolean createHeader, boolean stillSaveIfDataError, Function<DatumError, String> datumErrDisplayFunction,
                              String sheetName) {
        super(beans, beanClass, outputTarget, createHeader, stillSaveIfDataError, datumErrDisplayFunction);
        this.sheetName = sheetName;
    }

    public String getSheetName() {
        return sheetName;
    }

    @Override
    protected String getSpreadsheetFileType() {
        return SsBuiltInFileTypes.OFFICE;
    }
}
