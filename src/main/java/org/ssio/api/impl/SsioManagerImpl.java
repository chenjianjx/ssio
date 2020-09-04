package org.ssio.api.impl;

import org.ssio.api.impl.parse.SheetParser;
import org.ssio.api.impl.save.BeansSaver;
import org.ssio.api.impl.save.filetypespecific.csv.CsvSaveParam;
import org.ssio.api.impl.save.filetypespecific.office.OfficeSaveParam;
import org.ssio.api.interfaces.SsioManager;
import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.api.interfaces.parse.ParseResult;
import org.ssio.api.interfaces.save.SaveParam;
import org.ssio.api.interfaces.save.SaveResult;
import org.ssio.spi.impl.abstractsheet.filetypespecific.csv.factory.CsvWorkbookToParseFactory;
import org.ssio.spi.impl.abstractsheet.filetypespecific.csv.factory.CsvWorkbookToSaveFactory;
import org.ssio.spi.impl.abstractsheet.filetypespecific.office.factory.OfficeWorkbookToParseFactory;
import org.ssio.spi.impl.abstractsheet.filetypespecific.office.factory.OfficeWorkbookToSaveFactory;
import org.ssio.spi.interfaces.abstractsheet.factory.SsWorkbookFactoryRegistry;
import org.ssio.spi.interfaces.abstractsheet.factory.defaults.DefaultWorkbookFactoryRegistry;

import java.io.IOException;

/**
 * The facade.
 * It's state-less, hence thread-safe
 */
public class SsioManagerImpl implements SsioManager {

    private BeansSaver beansSaver;

    private SheetParser sheetParser;

    private SsWorkbookFactoryRegistry workbookFactoryRegistry;

    public SsioManagerImpl() {
        /**
         * since this is the facade, let's do the dependency hookup here
         * if you want to hookup new things or hookup in another way, create another implementation for {@link SsioManager }
         */
        workbookFactoryRegistry = new DefaultWorkbookFactoryRegistry();
        //built-in supports for office-like spreadsheets and csv
        workbookFactoryRegistry.registerWorkbookToSaveFactory(CsvSaveParam.class, new CsvWorkbookToSaveFactory());
        workbookFactoryRegistry.registerWorkbookToSaveFactory(OfficeSaveParam.class, new OfficeWorkbookToSaveFactory());

        workbookFactoryRegistry.registerWorkbookToParseFactory(ParseParam.class, new CsvWorkbookToParseFactory());
        workbookFactoryRegistry.registerWorkbookToParseFactory(ParseParam.class, new OfficeWorkbookToParseFactory());

        beansSaver = new BeansSaver(workbookFactoryRegistry);
        sheetParser = new SheetParser(workbookFactoryRegistry);
    }

    @Override
    public <BEAN> SaveResult save(SaveParam<BEAN> param) throws IOException {
        if (param == null) {
            throw new IllegalArgumentException();
        }
        return beansSaver.doSave(param);
    }


    @Override
    public <BEAN> ParseResult<BEAN> parse(ParseParam<BEAN> param) throws IOException {
        if (param == null) {
            throw new IllegalArgumentException();
        }

        return sheetParser.doWork(param);
    }

    /**
     * Expose it, in case you need to add your own registry  (An SPI socket)
     *
     * @return
     */
    public SsWorkbookFactoryRegistry getWorkbookFactoryRegistry() {
        return workbookFactoryRegistry;
    }
}
