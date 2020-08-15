package org.ssio.integrationtest.conversion;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * A bean that throws exceptions when properties are accessed
 */
public class ConversionITSickBean {

    private String healthyField = "random text";

    private String unhealthyField = "whatever";


    public String getHealthyField() {
        return healthyField;
    }

    public void setHealthyField(String healthyField) {
        this.healthyField = healthyField;
    }

    public String getUnhealthyField() {
        throw new IllegalStateException("I am a sick field");
    }

    public void setUnhealthyField(String unhealthyField) {
        this.unhealthyField = unhealthyField;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
