package org.ssio.api.external.save;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

public class SaveResult {

    private List<DatumError> datumErrors = new ArrayList<>();

    public boolean hasDatumErrors() {
        return datumErrors.size() > 0;
    }

    public List<DatumError> getDatumErrors() {
        return datumErrors;
    }


    public boolean hasNoDatumErrors() {
        return !this.hasDatumErrors();
    }

    public String getStats() {
        return "Number of datumErrors is  " + datumErrors.size();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }


}
