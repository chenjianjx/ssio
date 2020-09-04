package org.ssio.spi.impl.abstractsheet.filetypespecific.csv.factory;

import org.ssio.api.impl.b2s.filetypespecific.csv.BeansToCsvSheetParam;
import org.ssio.spi.impl.abstractsheet.filetypespecific.csv.model.CsvWorkbook;
import org.ssio.spi.interfaces.abstractsheet.factory.WorkbookForBeansToSheetFactory;

public class CsvWorkbookForBeansToSheetFactory implements WorkbookForBeansToSheetFactory<BeansToCsvSheetParam, CsvWorkbook> {

    @Override
    public CsvWorkbook newWorkbook(BeansToCsvSheetParam param) {
        return CsvWorkbook.createNewWorkbook(param.getCellSeparator());
    }
}
