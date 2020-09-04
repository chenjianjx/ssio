package org.ssio.spi.interfaces.abstractsheet.factory;

import org.ssio.api.interfaces.b2s.BeansToSheetParam;
import org.ssio.api.interfaces.s2b.SheetToBeansParam;
import org.ssio.spi.interfaces.abstractsheet.model.SsWorkbook;

import java.util.LinkedHashMap;
import java.util.Map;

public class SsWorkbookFactoryRegistry {

    private Map<Class<? extends BeansToSheetParam>, WorkbookForBeansToSheetFactory<? extends BeansToSheetParam, ?>> beansToSheetMap = new LinkedHashMap<>();

    private Map<Class<? extends SheetToBeansParam>, WorkbookForSheetToBeansFactory<? extends SheetToBeansParam, ?>> sheetToBeansMap = new LinkedHashMap<>();

    public <P extends BeansToSheetParam, W extends SsWorkbook> void registerBeansToSheetFactory(Class<P> beansToSheetParamClass, WorkbookForBeansToSheetFactory<P, W> factory) {
        beansToSheetMap.put(beansToSheetParamClass, factory);
    }

    public <P extends SheetToBeansParam, W extends SsWorkbook> void registerSheetToBeansFactory(Class<P> sheetToBeansParamClass, WorkbookForSheetToBeansFactory<P, W> factory) {
        sheetToBeansMap.put(sheetToBeansParamClass, factory);
    }
}
