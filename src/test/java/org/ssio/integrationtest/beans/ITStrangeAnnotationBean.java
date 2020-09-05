package org.ssio.integrationtest.beans;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.ssio.api.external.annotation.SsColumn;

import java.util.Objects;


public class ITStrangeAnnotationBean {

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ITStrangeAnnotationBean that = (ITStrangeAnnotationBean) o;
        return Objects.equals(foo, that.foo) &&
                Objects.equals(bar, that.bar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foo, bar);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
