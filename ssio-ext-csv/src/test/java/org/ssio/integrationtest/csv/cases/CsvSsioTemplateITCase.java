package org.ssio.integrationtest.csv.cases;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.ssio.api.interfaces.csv.CsvSsioTemplate;
import org.ssio.integrationtest.beans.ITBean;
import org.ssio.integrationtest.beans.ITBeanFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.ssio.integrationtest.cases.SheetParseITCaseBase.IT_BEANS_RESOURCE_PATH_WITHOUT_EXT;
import static org.ssio.integrationtest.support.SsioITHelper.createSpreadsheetFile;

class CsvSsioTemplateITCase {

    List<ITBean> theBeans = Arrays.asList(
            ITBeanFactory.allEmpty(),
            ITBeanFactory.normalValues(),
            ITBeanFactory.bigValues());

    @Test
    void beansToCsvString() throws IOException {
        String csvString = CsvSsioTemplate.defaultInstance().toCsvString(theBeans, ITBean.class, false);
        FileUtils.writeStringToFile(createSpreadsheetFile("beansToCsvString", "csv"), csvString);
    }

    @Test
    void csvStringToBeans() throws IOException {
        String csvString = IOUtils.toString(CsvSsioTemplateITCase.class.getResource(IT_BEANS_RESOURCE_PATH_WITHOUT_EXT + ".csv"), "utf8");
        List<ITBean> actualBeans = CsvSsioTemplate.defaultInstance().toBeans(csvString, ITBean.class, true);
        assertArrayEquals(theBeans.toArray(), actualBeans.toArray());
    }

}
