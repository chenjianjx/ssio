package org.ssio.api.interfaces.htmltable.save;


import org.ssio.api.interfaces.save.DatumError;
import org.ssio.api.interfaces.save.SaveParam;
import org.ssio.api.interfaces.save.SaveParamBuilder;

import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;


public class HtmlTableSaveParamBuilder<BEAN> extends SaveParamBuilder<BEAN, HtmlTableSaveParamBuilder<BEAN>> {

    private String outputCharset;

    private HtmlElementAttributes htmlElementAttributes;

    /**
     * not nullable
     *
     
     */
    public HtmlTableSaveParamBuilder<BEAN> setOutputCharset(String outputCharset) {
        this.outputCharset = outputCharset;
        return this;
    }

    /**
     * nullable
     *
     * @param htmlElementAttributes
     
     */
    public HtmlTableSaveParamBuilder<BEAN> setHtmlElementAttributes(HtmlElementAttributes htmlElementAttributes) {
        this.htmlElementAttributes = htmlElementAttributes;
        return this;
    }

    @Override
    protected void fileTypeSpecificValidate(List<String> errors) {
        if (outputCharset == null) {
            errors.add("For html table output the outputCharset is required");
        }
    }


    @Override
    protected SaveParam<BEAN> fileTypeSpecificBuild(Collection<BEAN> beans, Class<BEAN> beanClass,
                                                    OutputStream outputTarget, boolean createHeader,
                                                    boolean stillSaveIfDataError,
                                                    Function<DatumError, String> datumErrDisplayFunction) {
        return new HtmlTableSaveParam<>(beans, beanClass, outputTarget, createHeader, stillSaveIfDataError, htmlElementAttributes, datumErrDisplayFunction, outputCharset);
    }
}
