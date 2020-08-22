package org.ssio.integrationtest.conversion;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.ssio.api.ConversionManager;
import org.ssio.api.SpreadsheetFileType;
import org.ssio.api.s2b.SheetToBeansParam;
import org.ssio.api.s2b.SheetToBeansParamBuilder;
import org.ssio.api.s2b.SheetToBeansResult;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.ssio.integrationtest.conversion.ConversionITTestHelper.decideTargetFileExtension;

public class SheetToBeansITCase {
    ConversionManager manager = new ConversionManager();



    @BeforeAll
    public static void init() {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG");
    }


    @ParameterizedTest
    @EnumSource(SpreadsheetFileType.class)
    void sheetToBeans_positiveTest(SpreadsheetFileType spreadsheetFileType) throws IOException {

        String inputResourceClasspath = "/integration-test/ITBeans-empty-normal-bigValue" + decideTargetFileExtension(spreadsheetFileType);
        try (InputStream input = this.getClass().getResourceAsStream(inputResourceClasspath)) {
            SheetToBeansParam<ConversionITBean> param =
                    new SheetToBeansParamBuilder<ConversionITBean>()
                            .setBeanClass(ConversionITBean.class)
                            .setFileType(spreadsheetFileType)
                            .setSpreadsheetInput(input)
                            .setInputCharset("utf8") //for csv only
                            .build();

            SheetToBeansResult<ConversionITBean> result = manager.sheetToBeans(param);
            printResult(result);

            assertEquals(3, result.getBeans().size());
            assertFalse(result.hasCellErrors());

            assertEquals(ConversionITBeanFactory.allEmpty(), result.getBeans().get(0));
            assertEquals(ConversionITBeanFactory.normalValues(), result.getBeans().get(1));
            assertEquals(ConversionITBeanFactory.bigValues(), result.getBeans().get(2));

            result.getBeans().forEach(b -> System.out.println(b));
        }


    }

    private void printResult(SheetToBeansResult<ConversionITBean> result) {
        result.getBeans().forEach(b -> System.out.println(b));
        result.getCellErrors().forEach(e -> System.err.println(e));
    }

}
