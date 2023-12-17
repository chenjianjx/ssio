package org.ssio.spi.impl.office.cellvaluebinder;

import org.ssio.api.interfaces.typing.SimpleType;
import org.ssio.spi.impl.office.cellvaluebinder.bytype.BigDecimalOfficeCellValueBinder;
import org.ssio.spi.impl.office.cellvaluebinder.bytype.BigIntegerOfficeCellValueBinder;
import org.ssio.spi.impl.office.cellvaluebinder.bytype.BooleanOfficeCellValueBinder;
import org.ssio.spi.impl.office.cellvaluebinder.bytype.DateOfficeCellValueBinder;
import org.ssio.spi.impl.office.cellvaluebinder.bytype.DoubleOfficeCellValueBinder;
import org.ssio.spi.impl.office.cellvaluebinder.bytype.EnumOfficeCellValueBinder;
import org.ssio.spi.impl.office.cellvaluebinder.bytype.FloatOfficeCellValueBinder;
import org.ssio.spi.impl.office.cellvaluebinder.bytype.IntegerOfficeCellValueBinder;
import org.ssio.spi.impl.office.cellvaluebinder.bytype.LocalDateOfficeCellValueBinder;
import org.ssio.spi.impl.office.cellvaluebinder.bytype.LocalDateTimeOfficeCellValueBinder;
import org.ssio.spi.impl.office.cellvaluebinder.bytype.LongOfficeCellValueBinder;
import org.ssio.spi.impl.office.cellvaluebinder.bytype.PrimitiveBooleanOfficeCellValueBinder;
import org.ssio.spi.impl.office.cellvaluebinder.bytype.PrimitiveDoubleOfficeCellValueBinder;
import org.ssio.spi.impl.office.cellvaluebinder.bytype.PrimitiveFloatOfficeCellValueBinder;
import org.ssio.spi.impl.office.cellvaluebinder.bytype.PrimitiveIntOfficeCellValueBinder;
import org.ssio.spi.impl.office.cellvaluebinder.bytype.PrimitiveLongOfficeCellValueBinder;
import org.ssio.spi.impl.office.cellvaluebinder.bytype.PrimitiveShortOfficeCellValueBinder;
import org.ssio.spi.impl.office.cellvaluebinder.bytype.ShortOfficeCellValueBinder;
import org.ssio.spi.impl.office.cellvaluebinder.bytype.StringOfficeCellValueBinder;
import org.ssio.util.lang.SsioReflectionUtils;

import java.util.LinkedHashMap;
import java.util.Map;


public final class OfficeCellValueBinderRepo {
    private static Map<SimpleType, Class<? extends OfficeCellValueBinder>> javaTypeToBinderType = new LinkedHashMap<>();


    static {
        javaTypeToBinderType.put(SimpleType.PrimitiveBoolean, PrimitiveBooleanOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SimpleType.PrimitiveShort, PrimitiveShortOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SimpleType.PrimitiveInt, PrimitiveIntOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SimpleType.PrimitiveLong, PrimitiveLongOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SimpleType.PrimitiveFloat, PrimitiveFloatOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SimpleType.PrimitiveDouble, PrimitiveDoubleOfficeCellValueBinder.class);

        javaTypeToBinderType.put(SimpleType.Boolean, BooleanOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SimpleType.Short, ShortOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SimpleType.Integer, IntegerOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SimpleType.Long, LongOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SimpleType.Float, FloatOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SimpleType.Double, DoubleOfficeCellValueBinder.class);

        javaTypeToBinderType.put(SimpleType.BigInteger, BigIntegerOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SimpleType.BigDecimal, BigDecimalOfficeCellValueBinder.class);


        javaTypeToBinderType.put(SimpleType.Date, DateOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SimpleType.LocalDate, LocalDateOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SimpleType.LocalDateTime, LocalDateTimeOfficeCellValueBinder.class);

        javaTypeToBinderType.put(SimpleType.String, StringOfficeCellValueBinder.class);

        javaTypeToBinderType.put(SimpleType.Enum, EnumOfficeCellValueBinder.class);
    }

    public static OfficeCellValueBinder getOfficeCellValueBinder(SimpleType javaType, Class<Enum<?>> enumClassIfEnum) {
        Class<? extends OfficeCellValueBinder> binderType = javaTypeToBinderType.get(javaType);
        if (binderType == null) {
            return null;
        }
        if (binderType.equals(EnumOfficeCellValueBinder.class)) {
            return new EnumOfficeCellValueBinder(enumClassIfEnum);
        } else {
            return SsioReflectionUtils.createInstance(binderType);
        }
    }
}
