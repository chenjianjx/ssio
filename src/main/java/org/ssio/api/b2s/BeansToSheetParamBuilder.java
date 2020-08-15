package org.ssio.api.b2s;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.ssio.api.SpreadsheetFileType;
import org.ssio.api.common.BuilderPatternHelper;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BeansToSheetParamBuilder<BEAN> {
    private Collection<BEAN> beans;
    private Class<BEAN> beanClass;
    private OutputStream outputTarget;
    private SpreadsheetFileType fileType;
    private String sheetName;
    private boolean stillSaveIfDataError;
    private String datumErrPlaceholder;

    public BeansToSheetParamBuilder setBeans(Collection<BEAN> beans) {
        this.beans = beans;
        return this;
    }

    public BeansToSheetParamBuilder setBeanClass(Class<BEAN> beanClass) {
        this.beanClass = beanClass;
        return this;
    }

    public BeansToSheetParamBuilder setOutputTarget(OutputStream outputTarget) {
        this.outputTarget = outputTarget;
        return this;
    }

    public BeansToSheetParamBuilder setFileType(SpreadsheetFileType fileType) {
        this.fileType = fileType;
        return this;
    }

    public BeansToSheetParamBuilder setSheetName(String sheetName) {
        this.sheetName = sheetName;
        return this;
    }

    public BeansToSheetParamBuilder setStillSaveIfDataError(boolean stillSaveIfDataError) {
        this.stillSaveIfDataError = stillSaveIfDataError;
        return this;
    }

    public BeansToSheetParamBuilder setDatumErrPlaceholder(String datumErrPlaceholder) {
        this.datumErrPlaceholder = datumErrPlaceholder;
        return this;
    }

    public List<String> validate() {
        BuilderPatternHelper builderHelper = new BuilderPatternHelper();

        List<String> errors = new ArrayList<>();
        builderHelper.validateFieldNotNull("beans", beans, errors);
        builderHelper.validateFieldNotNull("beanClass", beanClass, errors);
        builderHelper.validateFieldNotNull("outputTarget", outputTarget, errors);
        builderHelper.validateFieldNotNull("fileType", fileType, errors);
        return errors;
    }

    public BeansToSheetParam build() {
        List<String> errors = this.validate();
        if (errors.size() > 0) {
            throw new IllegalArgumentException("Cannot build an object because of the following errors: " + StringUtils.join(errors, "\n"));
        }
        return new BeansToSheetParam(beans, beanClass, outputTarget, fileType, sheetName, stillSaveIfDataError, datumErrPlaceholder);
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}