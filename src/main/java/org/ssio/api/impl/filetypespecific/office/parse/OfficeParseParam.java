package org.ssio.api.impl.filetypespecific.office.parse;

import org.ssio.api.impl.common.sheetlocate.SsSheetLocator;
import org.ssio.api.impl.filetypespecific.SsBuiltInFileTypes;
import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.api.interfaces.parse.PropFromColumnMappingMode;

import java.io.InputStream;

public class OfficeParseParam<BEAN> extends ParseParam<BEAN> {

    private SsSheetLocator sheetLocator;

    public OfficeParseParam(Class<BEAN> beanClass, PropFromColumnMappingMode propFromColumnMappingMode, InputStream spreadsheetInput, boolean sheetHasHeader, SsSheetLocator sheetLocator) {
        super(beanClass, propFromColumnMappingMode, spreadsheetInput, sheetHasHeader);
        this.sheetLocator = sheetLocator;
    }


    public SsSheetLocator getSheetLocator() {
        return sheetLocator;
    }

    @Override
    public String getSpreadsheetFileType() {
        return SsBuiltInFileTypes.OFFICE;
    }
}
