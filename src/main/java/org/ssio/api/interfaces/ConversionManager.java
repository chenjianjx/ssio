package org.ssio.api.interfaces;

import org.ssio.api.interfaces.b2s.BeansToSheetParam;
import org.ssio.api.interfaces.b2s.BeansToSheetResult;
import org.ssio.api.interfaces.s2b.SheetToBeansParam;
import org.ssio.api.interfaces.s2b.SheetToBeansResult;

import java.io.IOException;

public interface ConversionManager {

    /**
     * beans to sheet
     *
     * @param param Please use {@link org.ssio.api.interfaces.b2s.BeansToSheetParamBuilder} to create the param
     */
    <BEAN> BeansToSheetResult beansToSheet(BeansToSheetParam<BEAN> param) throws IOException;


    /**
     * sheet to beans
     *
     * @param param Please use {@link SheetToBeansParam} to create the param
     * @return
     */
    <BEAN> SheetToBeansResult<BEAN> sheetToBeans(SheetToBeansParam<BEAN> param) throws IOException;
}
