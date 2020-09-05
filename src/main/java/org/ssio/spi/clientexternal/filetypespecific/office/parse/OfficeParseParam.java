package org.ssio.spi.clientexternal.filetypespecific.office.parse;

import org.ssio.api.external.parse.ParseParam;
import org.ssio.api.external.parse.PropFromColumnMappingMode;
import org.ssio.api.internal.common.sheetlocate.SsSheetLocator;
import org.ssio.spi.clientexternal.filetypespecific.SsBuiltInFileTypes;

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
