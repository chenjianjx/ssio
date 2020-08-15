package org.ssio.integrationtest.conversion;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.ssio.api.ConversionManager;
import org.ssio.api.SpreadsheetFileType;
import org.ssio.api.b2s.BeansToSheetParam;
import org.ssio.api.b2s.BeansToSheetParamBuilder;
import org.ssio.api.b2s.BeansToSheetResult;
import org.ssio.api.b2s.DatumError;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.fail;

class ConversionITCase {
    ConversionManager manager = new ConversionManager();

    @BeforeAll
    public static void init() {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG");
    }

    @ParameterizedTest
    @EnumSource(SpreadsheetFileType.class)
    void beansToSheet_sanityTest(SpreadsheetFileType spreadsheetFileType) throws IOException {

        Collection<ConversionITBean> beans = Arrays.asList(ConversionITBeanFactory.allEmpty(), ConversionITBeanFactory.allFilled());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        BeansToSheetParam<ConversionITBean> param =
                new BeansToSheetParamBuilder<ConversionITBean>()
                        .setBeanClass(ConversionITBean.class)
                        .setBeans(beans)
                        .setFileType(spreadsheetFileType)
                        .setOutputTarget(outputStream)
                        .setSheetName("first sheet")
                        .build();


        // save it
        BeansToSheetResult result = manager.beansToSheet(param);

        // do a save for human eye check
        byte[] spreadsheet = outputStream.toByteArray();
        String extension = spreadsheetFileType == SpreadsheetFileType.CSV ? ".csv" : ".xlsx";
        FileUtils.writeByteArrayToFile(createSpreadsheetFile("beansToSheet_sanityTest", extension), spreadsheet);

        if (result.hasDatumErrors()) {
            for (DatumError datumError : result.getDatumErrors()) {
                System.err.println(datumError);
            }
            fail("There should not be datum errors");
        }

    }

    /**
     *
     * @param prefix
     * @param extension including the dot "."
     * @return
     */
    private File createSpreadsheetFile(String prefix, String extension) {
        File dir = new File(System.getProperty("java.io.tmpdir"), "/ssio-it-test");
        dir.mkdirs();
        String filename = prefix + "-" + System.nanoTime() + extension;
        File file = new File(dir, filename);
        System.out.println("File created: " + file.getAbsolutePath());
        return file;
    }

}