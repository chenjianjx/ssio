package org.ssio.spi.clientexternal.spiregistry;

import org.ssio.spi.clientexternal.filetypespecific.SsBuiltInFileTypes;
import org.ssio.spi.developerexternal.abstractsheet.factory.SsWorkbookFactory;
import org.ssio.spi.internal.filetypespecific.abstractsheet.csv.factory.CsvWorkbookFactory;
import org.ssio.spi.internal.filetypespecific.abstractsheet.office.factory.OfficeWorkbookFactory;

import java.util.LinkedHashMap;
import java.util.Map;

public class SsWorkbookFactoryRegistry {

    private Map<String, SsWorkbookFactory> factoryMap = new LinkedHashMap<>();

    public SsWorkbookFactoryRegistry() {
        //built-in supports for office-like spreadsheets and csv
        factoryMap.put(SsBuiltInFileTypes.CSV, new CsvWorkbookFactory());
        factoryMap.put(SsBuiltInFileTypes.OFFICE, new OfficeWorkbookFactory());
    }

    public void register(String spreadSheetFileType, SsWorkbookFactory factory) {
        factoryMap.put(spreadSheetFileType, factory);
    }


    public SsWorkbookFactory getFactory(String spreadSheetFileType) {
        return factoryMap.get(spreadSheetFileType);
    }

}
