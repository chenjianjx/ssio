package org.ssio.api.interfaces.s2b;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.ssio.api.interfaces.SpreadsheetFileType;
import org.ssio.api.impl.common.BeanClassInspector;
import org.ssio.util.code.BuilderPatternHelper;
import org.ssio.api.interfaces.SsioApiConstants;
import org.ssio.api.impl.common.sheetlocate.SsSheetLocator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.ssio.util.lang.SsioReflectionUtils.hasAccessibleZeroArgumentConstructor;

public class SheetToBeansParamBuilder<BEAN> {
    private Class<BEAN> beanClass;
    private PropFromColumnMappingMode propFromColumnMappingMode = PropFromColumnMappingMode.BY_NAME;
    private InputStream spreadsheetInput;
    private String inputCharset;
    private char cellSeparator = SsioApiConstants.DEFAULT_CSV_CELL_SEPARATOR;
    private SpreadsheetFileType fileType;
    private SsSheetLocator sheetLocator = SsSheetLocator.byIndexLocator(0);
    private boolean sheetHasHeader = true;


    /**
     * not nullable
     */
    public SheetToBeansParamBuilder<BEAN> setBeanClass(Class<BEAN> beanClass) {
        this.beanClass = beanClass;
        return this;
    }

    /**
     * not nullable. Default is by column name
     */
    public SheetToBeansParamBuilder<BEAN> setPropFromColumnMappingMode(PropFromColumnMappingMode propFromColumnMappingMode) {
        this.propFromColumnMappingMode = propFromColumnMappingMode;
        return this;
    }

    /**
     * not nullable
     */
    public SheetToBeansParamBuilder<BEAN> setSpreadsheetInput(InputStream spreadsheetInput) {
        this.spreadsheetInput = spreadsheetInput;
        return this;
    }


    /**
     * not nullable
     */
    public SheetToBeansParamBuilder<BEAN> setFileType(SpreadsheetFileType fileType) {
        this.fileType = fileType;
        return this;
    }


    /**
     * which sheet to load the data ?  By default it's the sheet at index 0
     */
    public SheetToBeansParamBuilder<BEAN> setSheetLocator(SsSheetLocator sheetLocator) {
        this.sheetLocator = sheetLocator;
        return this;
    }


    /**
     * Required for CSV input.  Ignored by office(Excel) input
     */
    public SheetToBeansParamBuilder<BEAN> setInputCharset(String inputCharset) {
        this.inputCharset = inputCharset;
        return this;
    }


    /**
     * Only used for CSV.  Default is ","
     */
    public SheetToBeansParamBuilder<BEAN> setCellSeparator(char cellSeparator) {
        this.cellSeparator = cellSeparator;
        return this;
    }

    /**
     * The sheet has a header. default true.
     *
     * @return
     */
    public SheetToBeansParamBuilder<BEAN> setSheetHasHeader(boolean sheetHasHeader) {
        this.sheetHasHeader = sheetHasHeader;
        return this;
    }

    private List<String> validate() {
        BuilderPatternHelper builderHelper = new BuilderPatternHelper();

        List<String> errors = new ArrayList<>();
        builderHelper.validateFieldNotNull("beanClass", beanClass, errors);
        builderHelper.validateFieldNotNull("spreadsheetInput", spreadsheetInput, errors);
        builderHelper.validateFieldNotNull("fileType", fileType, errors);
        builderHelper.validateFieldNotNull("sheetLocator", sheetLocator, errors);
        builderHelper.validateFieldNotNull("propFromColumnMappingMode", propFromColumnMappingMode, errors);

        if (beanClass != null && !hasAccessibleZeroArgumentConstructor(beanClass)) {
            errors.add("The beanClass doesn't have an accessible zero-argument constructor: " + beanClass.getName());
        }

        if (fileType == SpreadsheetFileType.CSV && inputCharset == null) {
            errors.add("For CSV input the inputCharset is required");
        }

        if (propFromColumnMappingMode == PropFromColumnMappingMode.BY_NAME && !sheetHasHeader) {
            errors.add("If the propFromColumnMappingMode is " + PropFromColumnMappingMode.BY_NAME + ", then the sheet must have header");
        }

        if (!sheetHasHeader && propFromColumnMappingMode != PropFromColumnMappingMode.BY_INDEX) {
            errors.add("If the sheet has no header, then propFromColumnMappingMode has to be " + PropFromColumnMappingMode.BY_INDEX);
        }

        if (beanClass != null && propFromColumnMappingMode != null) {
            new BeanClassInspector().getPropAndColumnMappingsForSheet2Beans(beanClass, propFromColumnMappingMode, errors);
        }


        return errors;
    }

    public SheetToBeansParam build() {
        List<String> errors = this.validate();
        if (errors.size() > 0) {
            throw new IllegalArgumentException("Cannot build an object because of the following errors: \n" + StringUtils.join(errors, "\n"));
        }
        return new SheetToBeansParam(beanClass, propFromColumnMappingMode, spreadsheetInput, inputCharset, fileType, cellSeparator, sheetLocator, sheetHasHeader);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}