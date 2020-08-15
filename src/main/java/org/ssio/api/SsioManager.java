package org.ssio.api;

import org.ssio.api.b2s.BeansToSheetParam;
import org.ssio.api.b2s.BeansToSheetResult;
import org.ssio.api.s2b.SheetToBeansParam;
import org.ssio.api.s2b.SheetToBeansResult;
import org.ssio.internal.b2s.BeansToSheetWorker;

import java.io.IOException;

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
    public <BEAN> BeansToSheetResult beansToSheet(BeansToSheetParam<BEAN> param) throws IOException {
        if (param == null) {
            throw new IllegalArgumentException();
        }

        BeansToSheetWorker worker = new BeansToSheetWorker();
        return worker.doWork(param);
    }

    /**
     * sheet to beans
     *
     * @param param Please use {@link SheetToBeansParam} to create the param
     * @return
     */
    public <BEAN> SheetToBeansResult<BEAN> sheetToBeans(SheetToBeansParam<BEAN> param) {
        return new SheetToBeansResult<>();
    }
}
