package org.ssio.integrationtest.csv.cases;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.ssio.api.interfaces.csv.save.CsvSaveParamBuilder;
import org.ssio.api.interfaces.save.SaveParam;
import org.ssio.api.interfaces.save.SaveParamBuilder;
import org.ssio.api.interfaces.save.SaveResult;
import org.ssio.integrationtest.beans.ITSimpleBean;
import org.ssio.integrationtest.cases.BeansSaveITCaseBase;
import org.ssio.integrationtest.csv.support.CsvITHelper;
import org.ssio.spi.interfaces.factory.WorkbookFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.ssio.integrationtest.support.SsioITHelper.createSpreadsheetFile;

class BeansSaveToCsvITCase extends BeansSaveITCaseBase {


    @Override
    protected SaveParamBuilder newSaveParamBuilder() {
        return new CsvSaveParamBuilder().setOutputCharset("utf8");
    }

    @ParameterizedTest
    @ValueSource(chars = {',', '\t'})
    void save_csvSeparator(char cellSeparator) throws IOException {
        Collection<ITSimpleBean> beans = Arrays.asList(new ITSimpleBean());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


        SaveParam<ITSimpleBean> param = new CsvSaveParamBuilder<ITSimpleBean>()
                .setOutputCharset("utf8")
                .setBeanClass(ITSimpleBean.class)
                .setBeans(beans)
                .setCellSeparator(cellSeparator)
                .setOutputTarget(outputStream)
                .build();


        // convert it
        SaveResult result = manager.save(param);

        // do a save for human eye check
        byte[] spreadsheet = outputStream.toByteArray();
        assertTrue(spreadsheet.length > 0);
        FileUtils.writeByteArrayToFile(
                createSpreadsheetFile("save_csvSeparator_" + CsvITHelper.getSeparatorName(cellSeparator),
                        getSpreadsheetFileExtension()), spreadsheet);

        if (result.hasDatumErrors()) {
            fail("There should be no datum errors");
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
