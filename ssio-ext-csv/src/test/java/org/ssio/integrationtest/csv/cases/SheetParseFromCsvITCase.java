package org.ssio.integrationtest.csv.cases;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.ssio.api.interfaces.csv.parse.CsvParseParamBuilder;
import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.api.interfaces.parse.ParseParamBuilder;
import org.ssio.api.interfaces.parse.ParseResult;
import org.ssio.integrationtest.beans.ITSimpleBean;
import org.ssio.integrationtest.cases.SheetParseITCaseBase;
import org.ssio.integrationtest.csv.support.CsvITHelper;
import org.ssio.spi.interfaces.factory.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class SheetParseFromCsvITCase extends SheetParseITCaseBase {

    @Override
    protected ParseParamBuilder newParseParamBuilder() {
        return new CsvParseParamBuilder().setInputCharset("utf8");
    }

    @ParameterizedTest
    @ValueSource(chars = {',', '\t'})
    void sheetToBeans_csvSeparator(char cellSeparator) throws IOException {
        String inputResourceClasspath = "/integration-test/SimpleBean-separated-by-" + CsvITHelper.getSeparatorName(cellSeparator) + ".csv";
        try (InputStream input = this.getClass().getResourceAsStream(inputResourceClasspath)) {
            ParseParam<ITSimpleBean> param =
                    new CsvParseParamBuilder<ITSimpleBean>()
                            .setBeanClass(ITSimpleBean.class)
                            .setSpreadsheetInput(input)
                            .setInputCharset("utf8")
                            .setCellSeparator(cellSeparator)
                            .build();

            ParseResult<ITSimpleBean> result = manager.parse(param);
            printResult(result);

            assertEquals(1, result.getBeans().size());
            assertFalse(result.hasCellErrors());

            ITSimpleBean bean = result.getBeans().get(0);
            assertEquals("another string", bean.getStr());
            assertEquals(200, bean.getPrimInt());
        }
    }

    @Override
    public String getSpreadsheetFileExtension() {
        return CsvITHelper.getSpreadsheetFileExtension();
    }

    @Override
    public WorkbookFactory getWorkbookFactory() {
        return CsvITHelper.getWorkbookFactory();
    }

}
