package org.ssio.api.interfaces.htmltable;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.ssio.api.interfaces.AbstractSsioTemplate;
import org.ssio.api.interfaces.SsioManager;
import org.ssio.api.interfaces.htmltable.parse.HtmlTableParseParamBuilder;
import org.ssio.api.interfaces.htmltable.save.HtmlElementAttributes;
import org.ssio.api.interfaces.htmltable.save.HtmlTableSaveParamBuilder;
import org.ssio.api.interfaces.parse.CellsErrorDuringParseException;
import org.ssio.api.interfaces.parse.ParseParamBuilder;
import org.ssio.api.interfaces.save.DataErrorDuringSaveException;
import org.ssio.api.interfaces.save.SaveParamBuilder;
import org.ssio.spi.impl.htmltable.factory.HtmlTableWorkbookFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class HtmlTableSsioTemplate extends AbstractSsioTemplate {
    private static final HtmlTableSsioTemplate DEFAULT_INSTANCE =
            new HtmlTableSsioTemplate(SsioManager.newInstance(HtmlTableWorkbookFactory.defaultInstance()));
    private static final String DEFAULT_TABLE_CSS_CLASS = "ssio-table";


    public static HtmlTableSsioTemplate defaultInstance() {
        return DEFAULT_INSTANCE;
    }

    private SsioManager ssioManager;

    public HtmlTableSsioTemplate(SsioManager ssioManager) {
        this.ssioManager = ssioManager;
    }


    /**
     * @param stillSaveIfDataError See {@link AbstractSsioTemplate#save(Supplier, Collection, Class, OutputStream, boolean)}
     */
    public <BEAN> void toHtmlPage(Collection<BEAN> beans,
                                  Class<BEAN> beanClass,
                                  OutputStream outputTarget,
                                  String outputCharset,
                                  boolean stillSaveIfDataError) throws DataErrorDuringSaveException, IOException {

        String documentTitle = beanClass.getSimpleName();
        Map<String, String> tableAttributes = new HashMap<>();
        tableAttributes.put("class", DEFAULT_TABLE_CSS_CLASS);
        String cssStyles = IOUtils.toString(this.getClass().getResource("/defaultStyles.css"), "utf8");

        toHtmlPage(beans, beanClass, outputTarget, outputCharset, documentTitle, tableAttributes, cssStyles, stillSaveIfDataError);
    }

    public <BEAN> void toHtmlPage(Collection<BEAN> beans,
                                  Class<BEAN> beanClass,
                                  OutputStream outputTarget,
                                  String outputCharset,
                                  String documentTitle,
                                  Map<String, String> tableAttributes,
                                  String cssStyles,
                                  boolean stillSaveIfDataError) throws IOException {
        Supplier<SaveParamBuilder> paramBuilderSupplier =
                () -> new HtmlTableSaveParamBuilder()
                        .setOutputCharset(outputCharset)
                        .setHtmlElementAttributes(HtmlElementAttributes.neoBuilder()
                                .setTableAttributes(tableAttributes == null ? new HashMap<>() : tableAttributes)
                                .build()
                        );

        String htmlTemplate = IOUtils.toString(this.getClass().getResource("/html5-template.html"), "utf8");


        String table = toHtmlTableString(paramBuilderSupplier, beans, beanClass, stillSaveIfDataError);
        String html = htmlTemplate;
        html = StringUtils.replace(html, "${documentTitle}", documentTitle);
        html = StringUtils.replace(html, "${internalCss}", cssStyles);
        html = StringUtils.replace(html, "${table}", table);

        IOUtils.write(html, outputTarget, outputCharset);
    }


    /**
     * Generates a table's outer html
     *
     * @param stillSaveIfDataError See {@link AbstractSsioTemplate#save(Supplier, Collection, Class, OutputStream, boolean)}
     */
    public <BEAN> String toHtmlTableString(Collection<BEAN> beans,
                                           Class<BEAN> beanClass,
                                           HtmlElementAttributes htmlElementAttributes,
                                           boolean stillSaveIfDataError) throws DataErrorDuringSaveException, IOException {

        Supplier<SaveParamBuilder> paramBuilderSupplier =
                () -> new HtmlTableSaveParamBuilder()
                        .setOutputCharset(Charset.defaultCharset().toString())
                        .setHtmlElementAttributes(htmlElementAttributes);

        return toHtmlTableString(paramBuilderSupplier, beans, beanClass, stillSaveIfDataError);

    }

    private <BEAN> String toHtmlTableString(Supplier<SaveParamBuilder> paramBuilderSupplier, Collection<BEAN> beans,
                                            Class<BEAN> beanClass,
                                            boolean stillSaveIfDataError) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            super.save(paramBuilderSupplier,
                    beans, beanClass, byteArrayOutputStream, stillSaveIfDataError);
            return byteArrayOutputStream.toString(Charset.defaultCharset().toString());
        }
    }


    /**
     * @param input             Can be a table html or a full html page that contains a table
     * @param throwIfCellsError See {@link AbstractSsioTemplate#parse(Supplier, InputStream, Class, boolean)}
     */
    public <BEAN> List<BEAN> toBeans(InputStream input, String inputCharset,
                                     Class<BEAN> beanClass, boolean throwIfCellsError)
            throws CellsErrorDuringParseException, IOException {
        Supplier<ParseParamBuilder> paramBuilderSupplier = () -> new HtmlTableParseParamBuilder().setInputCharset(inputCharset);
        return super.parse(paramBuilderSupplier, input, beanClass, throwIfCellsError);
    }

    /**
     * @param htmlTableString   Can be a table html or a full html page that contains a table
     * @param throwIfCellsError See {@link AbstractSsioTemplate#parse(Supplier, InputStream, Class, boolean)}
     */
    public <BEAN> List<BEAN> toBeans(String htmlTableString,
                                     Class<BEAN> beanClass,
                                     boolean throwIfCellsError) throws CellsErrorDuringParseException, IOException {
        String inputCharset = Charset.defaultCharset().toString();
        try (ByteArrayInputStream input = new ByteArrayInputStream(htmlTableString.getBytes(inputCharset))) {
            return toBeans(input, inputCharset, beanClass, throwIfCellsError);
        }
    }


    @Override
    protected SsioManager getSsioManager() {
        return ssioManager;
    }
}