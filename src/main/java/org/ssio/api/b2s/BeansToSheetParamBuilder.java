package org.ssio.api.b2s;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.ssio.api.SpreadsheetFileType;
import org.ssio.api.common.BeanClassInspector;
import org.ssio.api.common.BuilderPatternHelper;
import org.ssio.api.common.SsioConstants;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static org.ssio.api.b2s.BeansToSheetParam.DEFAULT_DATUM_ERR_DISPLAY_FUNCTION;

public class BeansToSheetParamBuilder<BEAN> {
    private Collection<BEAN> beans;
    private Class<BEAN> beanClass;
    private OutputStream outputTarget;
    private SpreadsheetFileType fileType;
    private char cellSeparator = SsioConstants.DEFAULT_CSV_CELL_SEPARATOR ;
    private boolean createHeader = true;
    private String sheetName;
    private boolean stillSaveIfDataError = true;
    private Function<DatumError, String> datumErrDisplayFunction = DEFAULT_DATUM_ERR_DISPLAY_FUNCTION;

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

    public BeansToSheetParamBuilder<BEAN> setCellSeparator(char cellSeparator) {
        this.cellSeparator = cellSeparator;
        return this;
    }

    public BeansToSheetParamBuilder<BEAN> setCreateHeader(boolean createHeader) {
        this.createHeader = createHeader;
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

        new BeanClassInspector().getMappingsForBeans2Sheet(beanClass, errors);

        return errors;
    }

    public BeansToSheetParam build() {
        List<String> errors = this.validate();
        if (errors.size() > 0) {
            throw new IllegalArgumentException("Cannot build an object because of the following errors: \n" + StringUtils.join(errors, "\n"));
        }
        return new BeansToSheetParam(beans, beanClass, outputTarget, fileType, cellSeparator, createHeader, sheetName, stillSaveIfDataError, datumErrDisplayFunction);
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}