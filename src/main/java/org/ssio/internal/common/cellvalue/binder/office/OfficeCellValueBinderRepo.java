package org.ssio.internal.common.cellvalue.binder.office;

import org.ssio.api.abstractsheet.SsCellValueJavaType;

import java.util.LinkedHashMap;
import java.util.Map;


public final class OfficeCellValueBinderRepo {
    private static Map<SsCellValueJavaType, OfficeCellValueBinder> typeToBinder = new LinkedHashMap<>();


    static {
        typeToBinder.put(SsCellValueJavaType.PrimitiveBoolean, new PrimitiveBooleanOfficeCellValueBinder());
        typeToBinder.put(SsCellValueJavaType.PrimitiveShort, new PrimitiveShortOfficeCellValueBinder());
        typeToBinder.put(SsCellValueJavaType.PrimitiveInt, new PrimitiveIntOfficeCellValueBinder());
        typeToBinder.put(SsCellValueJavaType.PrimitiveLong, new PrimitiveLongOfficeCellValueBinder());
        typeToBinder.put(SsCellValueJavaType.PrimitiveFloat, new PrimitiveFloatOfficeCellValueBinder());
        typeToBinder.put(SsCellValueJavaType.PrimitiveDouble, new PrimitiveDoubleOfficeCellValueBinder());

        typeToBinder.put(SsCellValueJavaType.Boolean, new BooleanOfficeCellValueBinder());
        typeToBinder.put(SsCellValueJavaType.Short, new ShortOfficeCellValueBinder());
        typeToBinder.put(SsCellValueJavaType.Integer, new IntegerOfficeCellValueBinder());
        typeToBinder.put(SsCellValueJavaType.Long, new LongOfficeCellValueBinder());
        typeToBinder.put(SsCellValueJavaType.Float, new FloatOfficeCellValueBinder());
        typeToBinder.put(SsCellValueJavaType.Double, new DoubleOfficeCellValueBinder());

        typeToBinder.put(SsCellValueJavaType.BigInteger, new BigIntegerOfficeCellValueBinder());
        typeToBinder.put(SsCellValueJavaType.BigDecimal, new BigDecimalOfficeCellValueBinder());


        typeToBinder.put(SsCellValueJavaType.Date, new DateOfficeCellValueBinder());
        typeToBinder.put(SsCellValueJavaType.LocalDate, new LocalDateOfficeCellValueBinder());
        typeToBinder.put(SsCellValueJavaType.LocalDateTime, new LocalDateTimeOfficeCellValueBinder());

        typeToBinder.put(SsCellValueJavaType.String, new StringOfficeCellValueBinder());

        typeToBinder.put(SsCellValueJavaType.Enum, new EnumOfficeCellValueBinder());
    }

    public static OfficeCellValueBinder getOfficeCellValueBinder(SsCellValueJavaType javaType) {
        return typeToBinder.get(javaType);
    }
}
