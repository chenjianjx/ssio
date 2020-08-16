package org.ssio.integrationtest.conversion;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.ssio.api.common.SsioConstants;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class ConversionITBean {

    private boolean primBoolean;
    private short primShort;
    private int primInt;
    private long primLong;
    private float primFloat;
    private double primDouble;


    private Boolean objBoolean;
    private Short objShort;
    private Integer objInt;
    private Long objLong;
    private Float objFloat;
    private Double objDouble;


    private BigInteger bigInteger;
    private BigDecimal bigDecimal;


    private Date date;
    private LocalDate localDate;
    private LocalDateTime localDateTime;


    private String str;
    private ConversionITEnum enumeration;


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

    public ConversionITEnum getEnumeration() {
        return enumeration;
    }

    public void setEnumeration(ConversionITEnum enumeration) {
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
        ConversionITBean that = (ConversionITBean) o;
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
                Objects.equals(str, that.str) &&
                enumeration == that.enumeration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(primBoolean, primShort, primInt, primLong, primFloat, primDouble, objBoolean, objShort, objInt, objLong, objFloat, objDouble, bigInteger, bigDecimal, date, localDate, localDateTime, str, enumeration);
    }
}
