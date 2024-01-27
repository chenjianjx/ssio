package org.ssio.integrationtest.htmltable.cases;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.ssio.api.interfaces.htmltable.HtmlTableSsioTemplate;
import org.ssio.api.interfaces.htmltable.save.HtmlElementAttributes;
import org.ssio.integrationtest.beans.ITBean;
import org.ssio.integrationtest.beans.ITBeanFactory;
import org.ssio.integrationtest.beans.ITSimpleBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.ssio.integrationtest.cases.SheetParseITCaseBase.IT_BEANS_RESOURCE_PATH_WITHOUT_EXT;
import static org.ssio.integrationtest.support.SsioITHelper.createSpreadsheetFile;

class HtmlTableSsioTemplateITCase {

    List<ITBean> theBeans = Arrays.asList(
            ITBeanFactory.allEmpty(),
            ITBeanFactory.normalValues(),
            ITBeanFactory.bigValues());

    @Test
    void beansToHtmlPage() throws IOException {
        File targetFile = createSpreadsheetFile("beansToHtmlPage", "html");
        try (OutputStream outputStream = new FileOutputStream(targetFile)) {
            HtmlTableSsioTemplate.defaultInstance().toHtmlPage(ITBeanFactory.allBeans(), ITBean.class, outputStream, "utf8", false);
        }
    }

    @Test
    void beansToHtmlPage_simpleBean() throws IOException {
        File targetFile = createSpreadsheetFile("beansToHtmlPage_simpleBean", "html");
        try (OutputStream outputStream = new FileOutputStream(targetFile)) {
            HtmlTableSsioTemplate.defaultInstance().toHtmlPage(Arrays.asList(new ITSimpleBean()), ITSimpleBean.class, outputStream, "utf8", false);
        }
    }

    @Test
    void beansToHtmlPage_customAttributes() throws IOException {
        File targetFile = createSpreadsheetFile("beansToHtmlPage_customAttributes", "html");
        String ownStyles = IOUtils.toString(this.getClass().getResource("/integration-test/ownStyles.css"), "utf8");

        try (OutputStream outputStream = new FileOutputStream(targetFile)) {
            HtmlTableSsioTemplate.defaultInstance().toHtmlPage(ITBeanFactory.allBeans(), ITBean.class, outputStream, "utf8",
                    "some-title", //title for the html page
                    new HashMap<String, String>() {{
                        //assign an ID to the table html element
                        put("id", "some-id");
                        //assign a CSS class to the table html element
                        put("class", "my-own-table");
                    }},
                    ownStyles, //my own css definition string
                    false);
        }
    }

    @Test
    void beansToHtmlTableString() throws IOException {
        File targetFile = createSpreadsheetFile("beansToHtmlTableString", "html");
        String tableHtml = HtmlTableSsioTemplate.defaultInstance().toHtmlTableString(ITBeanFactory.allBeans(),
                ITBean.class,
                HtmlElementAttributes.neoBuilder().setOneTableAttribute("border", "1").build(),
                false);
        FileUtils.writeStringToFile(targetFile, tableHtml, "utf8");
    }

    @Test
    void htmlStringToBeans() throws IOException {
        String htmlString = IOUtils.toString(HtmlTableSsioTemplateITCase.class.getResource(IT_BEANS_RESOURCE_PATH_WITHOUT_EXT + ".html"), "utf8");
        List<ITBean> actualBeans = HtmlTableSsioTemplate.defaultInstance().toBeans(htmlString, ITBean.class, true);
        assertArrayEquals(theBeans.toArray(), actualBeans.toArray());
    }

}
