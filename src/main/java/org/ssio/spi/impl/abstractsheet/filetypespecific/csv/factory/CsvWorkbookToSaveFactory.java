package org.ssio.spi.impl.abstractsheet.filetypespecific.csv.factory;

import org.ssio.api.impl.filetypespecific.csv.save.CsvSaveParam;
import org.ssio.spi.impl.abstractsheet.filetypespecific.csv.model.CsvWorkbook;
import org.ssio.spi.interfaces.abstractsheet.factory.WorkbookToSaveFactory;

public class CsvWorkbookToSaveFactory implements WorkbookToSaveFactory<CsvSaveParam, CsvWorkbook> {

    @Override
    public CsvWorkbook newWorkbook(CsvSaveParam param) {
        return CsvWorkbook.createNewWorkbook(param.getCellSeparator(), param.getOutputCharset());
    }
}
