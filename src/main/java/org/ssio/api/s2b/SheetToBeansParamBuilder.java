package org.ssio.api.s2b;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.ssio.api.SpreadsheetFileType;
import org.ssio.api.common.BuilderPatternHelper;
import org.ssio.api.common.abstractsheet.helper.SsSheetLocator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.ssio.internal.util.SsioReflectionHelper.hasAccessibleZeroArgumentConstructor;

public class SheetToBeansParamBuilder<BEAN> {
    private Class<BEAN> beanClass;
    private InputStream spreadsheetInput;
    private SpreadsheetFileType fileType;
    private SsSheetLocator sheetLocator = SsSheetLocator.byIndexLocator(0);


    public SheetToBeansParamBuilder setBeanClass(Class<BEAN> beanClass) {
        this.beanClass = beanClass;
        return this;
    }

    public SheetToBeansParamBuilder setSpreadsheetInput(InputStream spreadsheetInput) {
        this.spreadsheetInput = spreadsheetInput;
        return this;
    }

    public SheetToBeansParamBuilder setFileType(SpreadsheetFileType fileType) {
        this.fileType = fileType;
        return this;
    }

    public SheetToBeansParamBuilder<BEAN> setSheetLocator(SsSheetLocator sheetLocator) {
        this.sheetLocator = sheetLocator;
        return this;
    }

    private List<String> validate() {
        BuilderPatternHelper builderHelper = new BuilderPatternHelper();

        List<String> errors = new ArrayList<>();
        builderHelper.validateFieldNotNull("beanClass", beanClass, errors);
        builderHelper.validateFieldNotNull("spreadsheetInput", spreadsheetInput, errors);
        builderHelper.validateFieldNotNull("fileType", fileType, errors);
        builderHelper.validateFieldNotNull("sheetLocator", sheetLocator, errors);

        if (beanClass != null && !hasAccessibleZeroArgumentConstructor(beanClass)) {
            errors.add("The beanClass doesn't have an accessible zero-argument constructor: " + beanClass.getName());
        }


        return errors;
    }

    public SheetToBeansParam build() {
        List<String> errors = this.validate();
        if (errors.size() > 0) {
            throw new IllegalArgumentException("Cannot build an object because of the following errors: \n" + StringUtils.join(errors, "\n"));
        }
        return new SheetToBeansParam(beanClass, spreadsheetInput, fileType, sheetLocator);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}