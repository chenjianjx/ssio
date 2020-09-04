package org.ssio.api.interfaces.b2s;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.ssio.api.interfaces.SpreadsheetFileType;
import org.ssio.api.impl.common.BeanClassInspector;
import org.ssio.util.code.BuilderPatternHelper;
import org.ssio.api.interfaces.SsioApiConstants;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;



public class BeansToSheetParamBuilder<BEAN> {
    private Collection<BEAN> beans;
    private Class<BEAN> beanClass;
    private OutputStream outputTarget;
    private SpreadsheetFileType fileType;
    private String outputCharset;
    private char cellSeparator = SsioApiConstants.DEFAULT_CSV_CELL_SEPARATOR;
    private boolean createHeader = true;
    private String sheetName;
    private boolean stillSaveIfDataError = true;
    private Function<DatumError, String> datumErrDisplayFunction = BeansToSheetParam.DEFAULT_DATUM_ERR_DISPLAY_FUNCTION;


    /**
     * not nullable
     */
    public BeansToSheetParamBuilder setBeans(Collection<BEAN> beans) {
        this.beans = beans;
        return this;
    }

    /**
     * not nullable
     */
    public BeansToSheetParamBuilder setBeanClass(Class<BEAN> beanClass) {
        this.beanClass = beanClass;
        return this;
    }

    /**
     * not nullable
     */
    public BeansToSheetParamBuilder setOutputTarget(OutputStream outputTarget) {
        this.outputTarget = outputTarget;
        return this;
    }

    /**
     * not nullable
     */
    public BeansToSheetParamBuilder setFileType(SpreadsheetFileType fileType) {
        this.fileType = fileType;
        return this;
    }

    /**
     * required for csv, ignored by office-like spreadsheet
     *
     * @return
     */
    public BeansToSheetParamBuilder<BEAN> setOutputCharset(String outputCharset) {
        this.outputCharset = outputCharset;
        return this;
    }

    /**
     * Only used for CSV.  Default is ","
     */
    public BeansToSheetParamBuilder<BEAN> setCellSeparator(char cellSeparator) {
        this.cellSeparator = cellSeparator;
        return this;
    }

    /**
     * whether to create a header row. Default is true.
     */
    public BeansToSheetParamBuilder<BEAN> setCreateHeader(boolean createHeader) {
        this.createHeader = createHeader;
        return this;
    }

    /**
     * nullable.
     * will be ignored by csv
     */
    public BeansToSheetParamBuilder setSheetName(String sheetName) {
        this.sheetName = sheetName;
        return this;
    }

    /**
     * output the sheet if there are data error?
     * default true
     */
    public BeansToSheetParamBuilder setStillSaveIfDataError(boolean stillSaveIfDataError) {
        this.stillSaveIfDataError = stillSaveIfDataError;
        return this;
    }

    /**
     * if some datum is wrong, write some string to the cell.
     * default is to return {@link BeansToSheetParam#DEFAULT_DATUM_ERR_PLACEHOLDER}
     */
    public BeansToSheetParamBuilder setDatumErrDisplayFunction(Function<DatumError, String> datumErrDisplayFunction) {
        this.datumErrDisplayFunction = datumErrDisplayFunction;
        return this;
    }

    public List<String> validate() {
        BuilderPatternHelper builderHelper = new BuilderPatternHelper();

        List<String> errors = new ArrayList<>();
        builderHelper.validateFieldNotNull("beans", beans, errors);
        builderHelper.validateFieldNotNull("beanClass", beanClass, errors);
        builderHelper.validateFieldNotNull("outputTarget", outputTarget, errors);
        builderHelper.validateFieldNotNull("fileType", fileType, errors);
        builderHelper.validateFieldNotNull("datumErrDisplayFunction", datumErrDisplayFunction, errors);

        if (fileType == SpreadsheetFileType.CSV && outputCharset == null) {
            errors.add("For CSV output the outputCharset is required");
        }

        if (beanClass != null) {
            new BeanClassInspector().getPropAndColumnMappingsForBeans2Sheet(beanClass, errors);
        }


        return errors;
    }

    public BeansToSheetParam build() {
        List<String> errors = this.validate();
        if (errors.size() > 0) {
            throw new IllegalArgumentException("Cannot build an object because of the following errors: \n" + StringUtils.join(errors, "\n"));
        }
        return new BeansToSheetParam(beans, beanClass, outputTarget, fileType, outputCharset, cellSeparator, createHeader, sheetName, stillSaveIfDataError, datumErrDisplayFunction);
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}