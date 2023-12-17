package org.ssio.integrationtest.office.cases;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.ssio.api.impl.common.sheetlocate.SheetLocator;
import org.ssio.api.interfaces.office.parse.OfficeParseParamBuilder;
import org.ssio.api.interfaces.parse.ParseParam;
import org.ssio.api.interfaces.parse.ParseParamBuilder;
import org.ssio.api.interfaces.parse.ParseResult;
import org.ssio.integrationtest.beans.ITSimpleBean;
import org.ssio.integrationtest.cases.SheetParseITCaseBase;
import org.ssio.integrationtest.office.support.OfficeITHelper;
import org.ssio.spi.interfaces.factory.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class SheetParseFromOfficeITCase extends SheetParseITCaseBase {

    @Override
    protected ParseParamBuilder newParseParamBuilder() {
        return new OfficeParseParamBuilder();
    }

    private static Stream<SheetLocator> sheetToBeans_notTheFirstSheet_provider() {
        return Stream.of(
                SheetLocator.byIndexLocator(1),
                SheetLocator.byNameLocator("The real sheet")
        );
    }
    @ParameterizedTest
    @MethodSource("sheetToBeans_notTheFirstSheet_provider")
    void sheetToBeans_notTheFirstSheet(SheetLocator sheetLocator) throws IOException {

        String inputResourceClasspath = "/integration-test/SimpleBean-content-not-in-first-sheet.xlsx";
        try (InputStream input = this.getClass().getResourceAsStream(inputResourceClasspath)) {
            ParseParam<ITSimpleBean> param =
                    new OfficeParseParamBuilder<ITSimpleBean>()
                            .setBeanClass(ITSimpleBean.class)
                            .setSpreadsheetInput(input)
                            .setSheetLocator(sheetLocator)
                            .build();

            ParseResult<ITSimpleBean> result = manager.parse(param);
            printResult(result);

            assertEquals(1, result.getBeans().size());
            assertFalse(result.hasCellErrors());
        }
    }

    @Override
    protected String getSpreadsheetFileExtension() {
        return OfficeITHelper.getSpreadsheetFileExtension();
    }

    @Override
    protected WorkbookFactory getWorkbookFactory() {
        return OfficeITHelper.getWorkbookFactory();
    }

}
