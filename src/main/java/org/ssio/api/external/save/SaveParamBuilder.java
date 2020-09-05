package org.ssio.api.external.save;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.ssio.api.internal.common.BeanClassInspector;
import org.ssio.util.code.BuilderPatternHelper;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;


public abstract class SaveParamBuilder<BEAN, BUILDER extends SaveParamBuilder<BEAN, BUILDER>> {
    private Collection<BEAN> beans;
    private Class<BEAN> beanClass;
    private OutputStream outputTarget;
    private boolean createHeader = true;
    private boolean stillSaveIfDataError = true;
    private Function<DatumError, String> datumErrDisplayFunction = SaveParam.DEFAULT_DATUM_ERR_DISPLAY_FUNCTION;


    /**
     * not nullable
     */
    public BUILDER setBeans(Collection<BEAN> beans) {
        this.beans = beans;
        return self();
    }


    /**
     * not nullable
     */
    public BUILDER setBeanClass(Class<BEAN> beanClass) {
        this.beanClass = beanClass;
        return self();
    }

    /**
     * not nullable
     */
    public BUILDER setOutputTarget(OutputStream outputTarget) {
        this.outputTarget = outputTarget;
        return self();
    }


    /**
     * whether to create a header row. Default is true.
     */
    public BUILDER setCreateHeader(boolean createHeader) {
        this.createHeader = createHeader;
        return self();
    }


    /**
     * output the sheet if there are data error?
     * default true
     */
    public BUILDER setStillSaveIfDataError(boolean stillSaveIfDataError) {
        this.stillSaveIfDataError = stillSaveIfDataError;
        return self();
    }

    /**
     * if some datum is wrong, write some string to the cell.
     * default is to return {@link SaveParam#DEFAULT_DATUM_ERR_PLACEHOLDER}
     */
    public BUILDER setDatumErrDisplayFunction(Function<DatumError, String> datumErrDisplayFunction) {
        this.datumErrDisplayFunction = datumErrDisplayFunction;
        return self();
    }

    private BUILDER self() {
        return (BUILDER) this;
    }


    protected List<String> validate() {
        BuilderPatternHelper builderHelper = new BuilderPatternHelper();

        List<String> errors = new ArrayList<>();
        builderHelper.validateFieldNotNull("beans", beans, errors);
        builderHelper.validateFieldNotNull("beanClass", beanClass, errors);
        builderHelper.validateFieldNotNull("outputTarget", outputTarget, errors);
        builderHelper.validateFieldNotNull("datumErrDisplayFunction", datumErrDisplayFunction, errors);
        if (beanClass != null) {
            new BeanClassInspector().getPropAndColumnMappingsForSaveMode(beanClass, errors);
        }
        //for child class
        fileTypeSpecificValidate(errors);

        return errors;
    }


    public SaveParam<BEAN> build() {
        List<String> errors = this.validate();
        if (errors.size() > 0) {
            throw new IllegalArgumentException("Cannot build an object because of the following errors: \n" + StringUtils.join(errors, "\n"));
        }
        return fileTypeSpecificBuild(beans, beanClass, outputTarget, createHeader, stillSaveIfDataError, datumErrDisplayFunction);
    }

    protected abstract void fileTypeSpecificValidate(List<String> errors);

    protected abstract SaveParam<BEAN> fileTypeSpecificBuild(Collection<BEAN> beans, Class<BEAN> beanClass, OutputStream outputTarget, boolean createHeader, boolean stillSaveIfDataError, Function<DatumError, String> datumErrDisplayFunction);


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}