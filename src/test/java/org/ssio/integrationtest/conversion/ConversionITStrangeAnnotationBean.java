package org.ssio.integrationtest.conversion;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.ssio.api.common.annotation.SsColumn;


public class ConversionITStrangeAnnotationBean {

    @SsColumn(index = 4, name = "Strange")
    private String foo = "foo";


    @SsColumn(index = 10, name = "Thing")
    private String bar = "bar";

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
