package org.ssio.api.interfaces.office.parse;

import org.ssio.api.impl.common.sheetlocate.SheetLocator;
import org.ssio.api.interfaces.parse.ParseParamBuilder;
import org.ssio.api.interfaces.parse.PropFromColumnMappingMode;
import org.ssio.util.code.BuilderPatternHelper;

import java.io.InputStream;
import java.util.List;

public class OfficeParseParamBuilder<BEAN> extends ParseParamBuilder<BEAN, OfficeParseParamBuilder<BEAN>> {
    private SheetLocator sheetLocator = SheetLocator.byIndexLocator(0);


    /**
     * which sheet to load the data from?  By default, it's the sheet at index 0
     */
    public OfficeParseParamBuilder<BEAN> setSheetLocator(SheetLocator sheetLocator) {
        this.sheetLocator = sheetLocator;
        return this;
    }

    @Override
    protected OfficeParseParam<BEAN> fileTypeSpecificBuild(Class<BEAN> beanClass, PropFromColumnMappingMode propFromColumnMappingMode, InputStream spreadsheetInput, boolean sheetHasHeader) {
        return new OfficeParseParam<>(beanClass, propFromColumnMappingMode, spreadsheetInput, sheetHasHeader, sheetLocator);
    }

    @Override
    protected void fileTypeSpecificValidate(List<String> errors) {
        BuilderPatternHelper builderHelper = new BuilderPatternHelper();
        builderHelper.validateFieldNotNull("sheetLocator", sheetLocator, errors);
    }


}
