package org.ssio.api.interfaces.office.parse;

import org.ssio.api.impl.common.sheetlocate.SheetLocator;
import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.api.interfaces.parse.PropFromColumnMappingMode;

import java.io.InputStream;

public class OfficeParseParam<BEAN> extends ParseParam<BEAN> {

    private SheetLocator sheetLocator;

    public OfficeParseParam(Class<BEAN> beanClass, PropFromColumnMappingMode propFromColumnMappingMode, InputStream spreadsheetInput, boolean sheetHasHeader, SheetLocator sheetLocator) {
        super(beanClass, propFromColumnMappingMode, spreadsheetInput, sheetHasHeader);
        this.sheetLocator = sheetLocator;
    }


    public SheetLocator getSheetLocator() {
        return sheetLocator;
    }

}
