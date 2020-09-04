package org.ssio.api.interfaces.typing;

import org.ssio.api.interfaces.SsioApiConstants;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

/**
 * only these types can be written to a cell.  If your field value is not in here, you need to convert it to one of these
 */
public enum SsioSimpleTypeEnum {


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


    SsioSimpleTypeEnum(Class<?> realType) {
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
    public static SsioSimpleTypeEnum fromRealType(Class<?> realType) {
        if (realType.isEnum()) {
            return Enum;
        }

        return Arrays.stream(SsioSimpleTypeEnum.values()).filter(t -> t.getRealType().equals(realType)).findFirst().orElse(null);
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
