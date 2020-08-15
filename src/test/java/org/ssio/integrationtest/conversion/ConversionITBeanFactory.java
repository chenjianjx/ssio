package org.ssio.integrationtest.conversion;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class ConversionITBeanFactory {

    public static ConversionITBean allEmpty(){
        return new ConversionITBean();
    }

    public static ConversionITBean allFilled(){
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
        bean.setStr("some string");
        bean.setEnumeration(ConversionITEnum.ENUM_VALUE_A);

        return bean;
    }

}