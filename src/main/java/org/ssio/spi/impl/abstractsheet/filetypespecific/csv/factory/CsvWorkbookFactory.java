package org.ssio.spi.impl.abstractsheet.filetypespecific.csv.factory;

import org.ssio.api.impl.filetypespecific.csv.parse.CsvParseParam;
import org.ssio.api.impl.filetypespecific.csv.save.CsvSaveParam;
import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.api.interfaces.save.SaveParam;
import org.ssio.spi.impl.abstractsheet.filetypespecific.csv.model.CsvWorkbook;
import org.ssio.spi.interfaces.abstractsheet.model.SsWorkbookFactory;

import java.io.IOException;
import java.io.InputStreamReader;

public class CsvWorkbookFactory implements SsWorkbookFactory<CsvWorkbook> {


    @Override
    public CsvWorkbook newWorkbookForSave(SaveParam p) {
        CsvSaveParam param = (CsvSaveParam) p;
        return CsvWorkbook.createNewWorkbook(param.getCellSeparator(), param.getOutputCharset());
    }

    @Override
    public CsvWorkbook loadWorkbookToParse(ParseParam p) throws IOException {
        CsvParseParam param = (CsvParseParam) p;
        return CsvWorkbook.createFromInput(new InputStreamReader(param.getSpreadsheetInput(), param.getInputCharset()), param.getCellSeparator());
    }
}
