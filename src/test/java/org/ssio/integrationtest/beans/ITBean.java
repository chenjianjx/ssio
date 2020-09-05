package org.ssio.integrationtest.beans;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.ssio.api.external.annotation.SsColumn;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class ITBean {

    @SsColumn(index = 0, name = "Primitive Boolean")
    private boolean primBoolean;
    @SsColumn(index = 1, name = "Primitive Short")
    private short primShort;
    @SsColumn(index = 2, name = "Primitive Int")
    private int primInt;
    @SsColumn(index = 3, name = "Primitive Long")
    private long primLong;
    @SsColumn(index = 4, name = "Primitive Float")
    private float primFloat;
    @SsColumn(index = 5, name = "Primitive Double")
    private double primDouble;

    @SsColumn(index = 6, name = "Object Boolean")
    private Boolean objBoolean;
    @SsColumn(index = 7, name = "Object Short")
    private Short objShort;
    @SsColumn(index = 8, name = "Object Int")
    private Integer objInt;
    @SsColumn(index = 9, name = "Object Long")
    private Long objLong;
    @SsColumn(index = 10, name = "Object Float")
    private Float objFloat;
    @SsColumn(index = 11, name = "Object Double")
    private Double objDouble;

    @SsColumn(index = 12)
    private BigInteger bigInteger;
    @SsColumn(index = 13)
    private BigDecimal bigDecimal;

    @SsColumn(index = 14)
    private Date date;
    @SsColumn(index = 15)
    private LocalDate localDate;
    @SsColumn(index = 16)
    private LocalDateTime localDateTime;

    @SsColumn(index = 17, name = "String")
    private String str;
    @SsColumn(index = 18)
    private ITEnum enumeration;


    public boolean isPrimBoolean() {
        return primBoolean;
    }

    public void setPrimBoolean(boolean primBoolean) {
        this.primBoolean = primBoolean;
    }

    public short getPrimShort() {
        return primShort;
    }

    public void setPrimShort(short primShort) {
        this.primShort = primShort;
    }

    public int getPrimInt() {
        return primInt;
    }

    public void setPrimInt(int primInt) {
        this.primInt = primInt;
    }

    public long getPrimLong() {
        return primLong;
    }

    public void setPrimLong(long primLong) {
        this.primLong = primLong;
    }

    public float getPrimFloat() {
        return primFloat;
    }

    public void setPrimFloat(float primFloat) {
        this.primFloat = primFloat;
    }

    public double getPrimDouble() {
        return primDouble;
    }

    public void setPrimDouble(double primDouble) {
        this.primDouble = primDouble;
    }

    public Boolean getObjBoolean() {
        return objBoolean;
    }

    public void setObjBoolean(Boolean objBoolean) {
        this.objBoolean = objBoolean;
    }

    public Short getObjShort() {
        return objShort;
    }

    public void setObjShort(Short objShort) {
        this.objShort = objShort;
    }

    public Integer getObjInt() {
        return objInt;
    }

    public void setObjInt(Integer objInt) {
        this.objInt = objInt;
    }

    public Long getObjLong() {
        return objLong;
    }

    public void setObjLong(Long objLong) {
        this.objLong = objLong;
    }

    public Float getObjFloat() {
        return objFloat;
    }

    public void setObjFloat(Float objFloat) {
        this.objFloat = objFloat;
    }

    public Double getObjDouble() {
        return objDouble;
    }

    public void setObjDouble(Double objDouble) {
        this.objDouble = objDouble;
    }

    public BigInteger getBigInteger() {
        return bigInteger;
    }

    public void setBigInteger(BigInteger bigInteger) {
        this.bigInteger = bigInteger;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public ITEnum getEnumeration() {
        return enumeration;
    }

    public void setEnumeration(ITEnum enumeration) {
        this.enumeration = enumeration;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ITBean that = (ITBean) o;
        return primBoolean == that.primBoolean &&
                primShort == that.primShort &&
                primInt == that.primInt &&
                primLong == that.primLong &&
                Float.compare(that.primFloat, primFloat) == 0 &&
                Double.compare(that.primDouble, primDouble) == 0 &&
                Objects.equals(objBoolean, that.objBoolean) &&
                Objects.equals(objShort, that.objShort) &&
                Objects.equals(objInt, that.objInt) &&
                Objects.equals(objLong, that.objLong) &&
                Objects.equals(objFloat, that.objFloat) &&
                Objects.equals(objDouble, that.objDouble) &&
                Objects.equals(bigInteger, that.bigInteger) &&
                Objects.equals(bigDecimal, that.bigDecimal) &&
                Objects.equals(date, that.date) &&
                Objects.equals(localDate, that.localDate) &&
                Objects.equals(localDateTime, that.localDateTime) &&
                Objects.equals(unixLineBreak(str), unixLineBreak(that.str)) &&
                enumeration == that.enumeration;
    }

    private String unixLineBreak(String str) {
        if (str == null) {
            return null;
        }

        return str.replaceAll("\\r\\n", "\n");
    }

    @Override
    public int hashCode() {
        return Objects.hash(primBoolean, primShort, primInt, primLong, primFloat, primDouble, objBoolean, objShort, objInt, objLong, objFloat, objDouble, bigInteger, bigDecimal, date, localDate, localDateTime, str, enumeration);
    }
}
