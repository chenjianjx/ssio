package org.ssio.api.interfaces.htmltable.save;



import org.ssio.api.interfaces.save.DatumError;
import org.ssio.api.interfaces.save.SaveParam;

import java.io.OutputStream;
import java.util.Collection;
import java.util.function.Function;

public class HtmlTableSaveParam <BEAN> extends SaveParam<BEAN> {

    private String outputCharset;

    private HtmlElementAttributes htmlElementAttributes;

    /**
     * Please use the builder to create an instance
     */
    protected HtmlTableSaveParam(Collection<BEAN> beans, Class<BEAN> beanClass, OutputStream outputTarget,
                                 boolean createHeader, boolean stillSaveIfDataError,
                                 HtmlElementAttributes htmlElementAttributes,
                                 Function<DatumError, String> datumErrDisplayFunction, String outputCharset) {
        super(beans, beanClass, outputTarget, createHeader, stillSaveIfDataError, datumErrDisplayFunction);
        this.htmlElementAttributes = htmlElementAttributes;
        this.outputCharset = outputCharset;
    }

    public String getOutputCharset() {
        return outputCharset;
    }

    public HtmlElementAttributes getHtmlElementAttributes() {
        return htmlElementAttributes;
    }
}