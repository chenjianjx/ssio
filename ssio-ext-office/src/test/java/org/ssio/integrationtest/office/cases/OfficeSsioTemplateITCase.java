package org.ssio.integrationtest.office.cases;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.ssio.api.interfaces.office.OfficeSsioTemplate;
import org.ssio.integrationtest.beans.ITBean;
import org.ssio.integrationtest.beans.ITBeanFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.ssio.integrationtest.cases.SheetParseITCaseBase.IT_BEANS_RESOURCE_PATH_WITHOUT_EXT;
import static org.ssio.integrationtest.support.SsioITHelper.createSpreadsheetFile;

class OfficeSsioTemplateITCase {


    @Test
    void beansToOfficeSheet() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OfficeSsioTemplate.defaultInstance().toSheet(ITBeanFactory.allBeans(), ITBean.class, outputStream, false);
        FileUtils.writeByteArrayToFile(createSpreadsheetFile("beansToOfficeSheet", "xlsx"), outputStream.toByteArray());
    }

    @Test
    void officeSheetToBeans() throws IOException {
        byte[] sheetBytes = IOUtils.toByteArray(OfficeSsioTemplateITCase.class.getResource(IT_BEANS_RESOURCE_PATH_WITHOUT_EXT + ".xlsx"));
        List<ITBean> actualBeans = OfficeSsioTemplate.defaultInstance().toBeans(new ByteArrayInputStream(sheetBytes), ITBean.class, true);
        assertArrayEquals(ITBeanFactory.allBeans().toArray(), actualBeans.toArray());
    }
}
