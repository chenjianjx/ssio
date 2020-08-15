package org.ssio.integrationtest.conversion;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class ConversionITBeanFactory {

    public static ConversionITBean allEmpty() {
        return new ConversionITBean();
    }

    public static ConversionITBean bigValues() {
        ConversionITBean bean = new ConversionITBean();

        bean.setPrimBoolean(true);
        bean.setPrimShort(Short.MAX_VALUE);
        bean.setPrimInt(Integer.MAX_VALUE);
        bean.setPrimLong(Long.MAX_VALUE);
        bean.setPrimFloat(Float.MAX_VALUE);
        bean.setPrimDouble(Double.MAX_VALUE);
        bean.setObjBoolean(true);
        bean.setObjShort(Short.MIN_VALUE);
        bean.setObjInt(Integer.MIN_VALUE);
        bean.setObjLong(Long.MIN_VALUE);
        bean.setObjFloat(Float.MIN_VALUE);
        bean.setObjDouble(Double.MIN_VALUE);
        bean.setBigInteger(BigInteger.valueOf(Long.MAX_VALUE));
        bean.setBigDecimal(BigDecimal.valueOf(Double.MAX_VALUE));
        bean.setDate(new Date());
        bean.setLocalDate(LocalDate.now());
        bean.setLocalDateTime(LocalDateTime.now());
        bean.setStr("A very very very very very very very very very very very very \n very very very very very very very very very very long string with line breaks");
        bean.setEnumeration(ConversionITEnum.ENUM_VALUE_A);

        return bean;
    }


    public static ConversionITBean normalValues() {
        ConversionITBean bean = new ConversionITBean();

        bean.setPrimBoolean(true);
        bean.setPrimShort((short) 123);
        bean.setPrimInt(9999);
        bean.setPrimLong(1000000001);
        bean.setPrimFloat(123.456f);
        bean.setPrimDouble(456.789);
        bean.setObjBoolean(true);
        bean.setObjShort((short) -123);
        bean.setObjInt(-9999);
        bean.setObjLong(-1000000001l);
        bean.setObjFloat(-123.456f);
        bean.setObjDouble(-456.789);
        bean.setBigInteger(BigInteger.valueOf(777));
        bean.setBigDecimal(BigDecimal.valueOf(-888));
        bean.setDate(new Date());
        bean.setLocalDate(LocalDate.now());
        bean.setLocalDateTime(LocalDateTime.now());
        bean.setStr("A normal string");
        bean.setEnumeration(ConversionITEnum.ENUM_VALUE_A);

        return bean;
    }
}