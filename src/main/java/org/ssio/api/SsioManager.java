package org.ssio.api;

import org.ssio.api.b2s.BeansToSheetParam;
import org.ssio.api.b2s.BeansToSheetResult;
import org.ssio.api.s2b.SheetToBeansParam;
import org.ssio.api.s2b.SheetToBeansResult;

/**
 * The facade.
 * It's state-less, hence thread-safe
 */
public class SsioManager {
    /**
     * beans to sheet
     *
     * @param param Please use {@link org.ssio.api.b2s.BeansToSheetParamBuilder} to create the param
     */
    public <BEAN_TYPE> BeansToSheetResult beansToSheet(BeansToSheetParam<BEAN_TYPE> param) {
        return new BeansToSheetResult();
    }

    /**
     * sheet to beans
     * @param param Please use {@link SheetToBeansParam} to create the param
     * @param <BEAN_TYPE>
     * @return
     */
    public <BEAN_TYPE> SheetToBeansResult<BEAN_TYPE> sheetToBeans(SheetToBeansParam<BEAN_TYPE> param) {
        return new SheetToBeansResult<>();
    }
}
