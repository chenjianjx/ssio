package org.ssio.api.abstractsheet;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

/**
 * only these types can be written to a cell.  If your field value is not in here, you need to convert it to one of these
 */
public enum SsCellValueJavaType {


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

    SsCellValueJavaType(Class<?> realType) {
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
    public static SsCellValueJavaType fromRealType(Class<?> realType) {
        if (realType.isEnum()) {
            return Enum;
        }
        return Arrays.stream(SsCellValueJavaType.values()).filter(t -> t.getRealType().equals(realType)).findFirst().orElse(null);
    }



}
