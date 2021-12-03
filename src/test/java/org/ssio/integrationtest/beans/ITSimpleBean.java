package org.ssio.integrationtest.beans;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.ssio.api.external.annotation.SsColumn;

public class ITSimpleBean {

    @SsColumn(index = 0)
    private String str = "some string";

    @SsColumn(index = 1)
    private int primInt = 99;

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
