package org.ssio.spi.impl.htmltable.factory;

import org.ssio.api.interfaces.htmltable.parse.HtmlTableParseParam;
import org.ssio.api.interfaces.htmltable.save.HtmlTableSaveParam;
import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.api.interfaces.save.SaveParam;
import org.ssio.spi.impl.htmltable.model.read.HtmlTableReadableWorkbook;
import org.ssio.spi.impl.htmltable.model.write.HtmlTableWritableWorkbook;
import org.ssio.spi.interfaces.factory.WorkbookFactory;

import java.io.IOException;
import java.io.InputStreamReader;

public class HtmlTableWorkbookFactory implements WorkbookFactory<HtmlTableReadableWorkbook, HtmlTableWritableWorkbook> {

    private static HtmlTableWorkbookFactory DEFAULT_INSTANCE = new HtmlTableWorkbookFactory();

    public static WorkbookFactory defaultInstance() {
        return DEFAULT_INSTANCE;
    }


    @Override
    public HtmlTableWritableWorkbook newWorkbookForSave(SaveParam p) {
        HtmlTableSaveParam param = (HtmlTableSaveParam) p;
        return HtmlTableWritableWorkbook.createNewWorkbook(param.getOutputCharset(), param.getHtmlElementAttributes());
    }

    @Override
    public HtmlTableReadableWorkbook loadWorkbookToParse(ParseParam p) throws IOException {
        HtmlTableParseParam param = (HtmlTableParseParam) p;
        return HtmlTableReadableWorkbook.newInstanceFromInput(new InputStreamReader(param.getSpreadsheetInput(), param.getInputCharset()));
    }
}
