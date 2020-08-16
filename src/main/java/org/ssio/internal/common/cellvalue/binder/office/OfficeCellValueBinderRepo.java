package org.ssio.internal.common.cellvalue.binder.office;

import org.ssio.api.abstractsheet.SsCellValueJavaType;
import org.ssio.internal.util.SsioReflectionHelper;

import java.util.LinkedHashMap;
import java.util.Map;


public final class OfficeCellValueBinderRepo {
    private static Map<SsCellValueJavaType, Class<? extends OfficeCellValueBinder>> javaTypeToBinderType = new LinkedHashMap<>();


    static {
        javaTypeToBinderType.put(SsCellValueJavaType.PrimitiveBoolean, PrimitiveBooleanOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SsCellValueJavaType.PrimitiveShort, PrimitiveShortOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SsCellValueJavaType.PrimitiveInt, PrimitiveIntOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SsCellValueJavaType.PrimitiveLong, PrimitiveLongOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SsCellValueJavaType.PrimitiveFloat, PrimitiveFloatOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SsCellValueJavaType.PrimitiveDouble, PrimitiveDoubleOfficeCellValueBinder.class);

        javaTypeToBinderType.put(SsCellValueJavaType.Boolean, BooleanOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SsCellValueJavaType.Short, ShortOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SsCellValueJavaType.Integer, IntegerOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SsCellValueJavaType.Long, LongOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SsCellValueJavaType.Float, FloatOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SsCellValueJavaType.Double, DoubleOfficeCellValueBinder.class);

        javaTypeToBinderType.put(SsCellValueJavaType.BigInteger, BigIntegerOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SsCellValueJavaType.BigDecimal, BigDecimalOfficeCellValueBinder.class);


        javaTypeToBinderType.put(SsCellValueJavaType.Date, DateOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SsCellValueJavaType.LocalDate, LocalDateOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SsCellValueJavaType.LocalDateTime, LocalDateTimeOfficeCellValueBinder.class);

        javaTypeToBinderType.put(SsCellValueJavaType.String, StringOfficeCellValueBinder.class);

        javaTypeToBinderType.put(SsCellValueJavaType.Enum, EnumOfficeCellValueBinder.class);
    }

    public static OfficeCellValueBinder getOfficeCellValueBinder(SsCellValueJavaType javaType, Class<Enum<?>> enumClassIfEnum) {
        Class<? extends OfficeCellValueBinder> binderType = javaTypeToBinderType.get(javaType);
        if (binderType == null) {
            return null;
        }
        if (binderType.equals(EnumOfficeCellValueBinder.class)) {
            return new EnumOfficeCellValueBinder(enumClassIfEnum);
        } else {
            return SsioReflectionHelper.createInstance(binderType);
        }
    }
}
