package org.ssio.api.external.parse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.ssio.api.internal.common.BeanClassInspector;
import org.ssio.util.code.BuilderPatternHelper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.ssio.util.lang.SsioReflectionUtils.hasAccessibleZeroArgumentConstructor;

public abstract class ParseParamBuilder<BEAN, BUILDER extends ParseParamBuilder<BEAN, BUILDER>> {
    private Class<BEAN> beanClass;
    private PropFromColumnMappingMode propFromColumnMappingMode = PropFromColumnMappingMode.BY_NAME;
    private InputStream spreadsheetInput;
    private boolean sheetHasHeader = true;


    /**
     * not nullable
     */
    public BUILDER setBeanClass(Class<BEAN> beanClass) {
        this.beanClass = beanClass;
        return self();
    }

    /**
     * not nullable. Default is by column name
     */
    public BUILDER setPropFromColumnMappingMode(PropFromColumnMappingMode propFromColumnMappingMode) {
        this.propFromColumnMappingMode = propFromColumnMappingMode;
        return self();
    }

    /**
     * not nullable
     */
    public BUILDER setSpreadsheetInput(InputStream spreadsheetInput) {
        this.spreadsheetInput = spreadsheetInput;
        return self();
    }


    /**
     * Does the sheet have a header?  default true.
     *
     * @return
     */
    public BUILDER setSheetHasHeader(boolean sheetHasHeader) {
        this.sheetHasHeader = sheetHasHeader;
        return self();
    }

    private BUILDER self() {
        return (BUILDER) this;
    }

    private List<String> validate() {
        BuilderPatternHelper builderHelper = new BuilderPatternHelper();

        List<String> errors = new ArrayList<>();
        builderHelper.validateFieldNotNull("beanClass", beanClass, errors);
        builderHelper.validateFieldNotNull("spreadsheetInput", spreadsheetInput, errors);


        builderHelper.validateFieldNotNull("propFromColumnMappingMode", propFromColumnMappingMode, errors);

        if (beanClass != null && !hasAccessibleZeroArgumentConstructor(beanClass)) {
            errors.add("The beanClass doesn't have an accessible zero-argument constructor: " + beanClass.getName());
        }


        if (propFromColumnMappingMode == PropFromColumnMappingMode.BY_NAME && !sheetHasHeader) {
            errors.add("If the propFromColumnMappingMode is " + PropFromColumnMappingMode.BY_NAME + ", then the sheet must have a header");
        }

        if (!sheetHasHeader && propFromColumnMappingMode != PropFromColumnMappingMode.BY_INDEX) {
            errors.add("If the sheet has no header, then propFromColumnMappingMode has to be " + PropFromColumnMappingMode.BY_INDEX);
        }

        fileTypeSpecificValidate(errors);

        if (beanClass != null && propFromColumnMappingMode != null) {
            new BeanClassInspector().getPropAndColumnMappingsForParseMode(beanClass, propFromColumnMappingMode, errors);
        }


        return errors;
    }

    public ParseParam build() {
        List<String> errors = this.validate();
        if (errors.size() > 0) {
            throw new IllegalArgumentException("Cannot build an object because of the following errors: \n" + StringUtils.join(errors, "\n"));
        }
        return fileTypeSpecificBuild(beanClass, propFromColumnMappingMode, spreadsheetInput, sheetHasHeader);
    }

    protected abstract ParseParam fileTypeSpecificBuild(Class<BEAN> beanClass, PropFromColumnMappingMode propFromColumnMappingMode, InputStream spreadsheetInput, boolean sheetHasHeader);

    protected abstract void fileTypeSpecificValidate(List<String> errors);


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}