package org.ssio.api.impl;

import org.ssio.api.impl.b2s.BeansToSheetWorker;
import org.ssio.api.impl.s2b.SheetToBeansWorker;
import org.ssio.api.interfaces.ConversionManager;
import org.ssio.api.interfaces.b2s.BeansToSheetParam;
import org.ssio.api.interfaces.b2s.BeansToSheetResult;
import org.ssio.api.interfaces.s2b.SheetToBeansParam;
import org.ssio.api.interfaces.s2b.SheetToBeansResult;

import java.io.IOException;

/**
 * The facade.
 * It's state-less, hence thread-safe
 */
public class ConversionManagerImpl implements ConversionManager {

    private BeansToSheetWorker beansToSheetWorker;

    private SheetToBeansWorker sheetToBeansWorker;

    @Override
    public <BEAN> BeansToSheetResult beansToSheet(BeansToSheetParam<BEAN> param) throws IOException {
        if (param == null) {
            throw new IllegalArgumentException();
        }

        return beansToSheetWorker.doWork(param);
    }


    @Override
    public <BEAN> SheetToBeansResult<BEAN> sheetToBeans(SheetToBeansParam<BEAN> param) throws IOException {
        if (param == null) {
            throw new IllegalArgumentException();
        }
        SheetToBeansWorker worker = new SheetToBeansWorker();
        return worker.doWork(param);
    }
}
