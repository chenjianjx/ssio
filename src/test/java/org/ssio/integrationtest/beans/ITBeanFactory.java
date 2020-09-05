package org.ssio.integrationtest.beans;

import org.apache.commons.lang3.time.DateUtils;
import org.ssio.api.external.SsioApiConstants;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class ITBeanFactory {

    private static Date dateNormal = parseDate("2011-11-11T11:11:11");
    private static LocalDate localDateNormal = LocalDate.parse("2020-12-12");
    private static LocalDateTime localDateTimeNormal = LocalDateTime.parse("2020-10-10T10:10:10");


    private static Date dateEndOfWorld = parseDate("3000-11-11T11:11:11");
    private static LocalDate localDateEndOfWorld = LocalDate.parse("3000-12-12");
    private static LocalDateTime localDateTimeEndOfWorld = LocalDateTime.parse("3000-10-10T10:10:10");


    public static ITBean allEmpty() {
        return new ITBean();
    }

    public static ITBean bigValues() {
        ITBean bean = new ITBean();

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


        bean.setDate(dateEndOfWorld);
        bean.setLocalDate(localDateEndOfWorld);
        bean.setLocalDateTime(localDateTimeEndOfWorld);
        bean.setStr("A very very very very very very very very very very very very \n very very very very very very very very very very long string with line breaks");
        bean.setEnumeration(ITEnum.ENUM_VALUE_A);

        return bean;
    }


    public static ITBean normalValues() {
        ITBean bean = new ITBean();

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
        bean.setBigDecimal(BigDecimal.valueOf(-888.0));

        bean.setDate(dateNormal);
        bean.setLocalDate(localDateNormal);
        bean.setLocalDateTime(localDateTimeNormal);

        bean.setStr("A normal string");
        bean.setEnumeration(ITEnum.ENUM_VALUE_A);

        return bean;
    }

    private static Date parseDate(String ddd) {
        try {
            return DateUtils.parseDate(ddd, SsioApiConstants.DEFAULT_LOCAL_DATE_TIME_PATTERN);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
}