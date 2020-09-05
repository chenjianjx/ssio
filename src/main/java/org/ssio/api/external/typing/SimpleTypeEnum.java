package org.ssio.api.external.typing;

import org.ssio.api.external.SsioApiConstants;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

/**
 * Only these types can be written to a cell.
 * All cells can only be parsed as a value in one of these types.
 * If your property's type is not in here, you need to convert it to one of these types.  See {@link ComplexTypeHandler}
 */
public enum SimpleTypeEnum {


    PrimitiveBoolean(boolean.class),
    PrimitiveShort(short.class),
    PrimitiveInt(int.class),
    PrimitiveLong(long.class),
    PrimitiveFloat(float.class),
    PrimitiveDouble(double.class),

    Boolean(Boolean.class),
    Short(Short.class),
    Integer(Integer.class),
    Long(Long.class),
    Float(Float.class),
    Double(Double.class),

    BigInteger(BigInteger.class),
    BigDecimal(BigDecimal.class),


    Date(Date.class),
    LocalDate(LocalDate.class),
    LocalDateTime(LocalDateTime.class),

    String(String.class),

    Enum(Enum.class);


    private Class<?> realType;


    SimpleTypeEnum(Class<?> realType) {
        this.realType = realType;
    }

    public Class<?> getRealType() {
        return realType;
    }

    /**
     * if not found, return null
     *
     * @param realType
     * @return
     */
    public static SimpleTypeEnum fromRealType(Class<?> realType) {
        if (realType.isEnum()) {
            return Enum;
        }

        return Arrays.stream(SimpleTypeEnum.values()).filter(t -> t.getRealType().equals(realType)).findFirst().orElse(null);
    }


    public boolean isDateRelated() {
        return this == Date || this == LocalDate || this == LocalDateTime;
    }

    /**
     * only call this if {@link #isDateRelated()} returns true
     *
     * @return
     */
    public String getDefaultDateFormat() {
        if (this == Date || this == LocalDateTime) {
            return SsioApiConstants.DEFAULT_LOCAL_DATE_TIME_PATTERN;
        }
        if (this == LocalDate) {
            return SsioApiConstants.DEFAULT_LOCAL_DATE_PATTERN;
        }
        throw new IllegalStateException();
    }
}
