package org.ssio.api.b2s;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

public class BeansToSheetResult {

    private List<DatumError> datumErrors = new ArrayList<>();

    public boolean hasDatumErrors() {
        return datumErrors.size() > 0;
    }

    public List<DatumError> getDatumErrors() {
        return datumErrors;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
