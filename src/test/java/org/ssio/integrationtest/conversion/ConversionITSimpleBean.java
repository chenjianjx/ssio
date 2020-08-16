package org.ssio.integrationtest.conversion;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A bean that throws exceptions when properties are accessed
 */
public class ConversionITSimpleBean {

    private String str = "some string";

    private int primInt = 100;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public int getPrimInt() {
        return primInt;
    }

    public void setPrimInt(int primInt) {
        this.primInt = primInt;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
