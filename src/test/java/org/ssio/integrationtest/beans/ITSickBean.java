package org.ssio.integrationtest.beans;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.ssio.api.external.annotation.SsColumn;

/**
 * A bean that throws exceptions when properties are accessed
 */
public class ITSickBean {

    @SsColumn(index = 0)
    private String healthyField = "defaultHealthyField";

    @SsColumn(index = 1)
    public String unhealthyField = "defaultUnhealthyField";


    public String getHealthyField() {
        return healthyField;
    }

    public void setHealthyField(String healthyField) {
        this.healthyField = healthyField;
    }

    public String getUnhealthyField() {
        throw new IllegalStateException("I am a sick getter");
    }

    public void setUnhealthyField(String unhealthyField) {
        throw new IllegalStateException("I am a sick setter");
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
