package org.ssio.internal.common.cellvalue.binder.office;

import org.ssio.api.common.typing.SsioSimpleTypeEnum;
import org.ssio.internal.common.cellvalue.binder.office.impl.BigDecimalOfficeCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.office.impl.BigIntegerOfficeCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.office.impl.BooleanOfficeCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.office.impl.DateOfficeCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.office.impl.DoubleOfficeCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.office.impl.EnumOfficeCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.office.impl.FloatOfficeCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.office.impl.IntegerOfficeCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.office.impl.LocalDateOfficeCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.office.impl.LocalDateTimeOfficeCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.office.impl.LongOfficeCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.office.impl.PrimitiveBooleanOfficeCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.office.impl.PrimitiveDoubleOfficeCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.office.impl.PrimitiveFloatOfficeCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.office.impl.PrimitiveIntOfficeCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.office.impl.PrimitiveLongOfficeCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.office.impl.PrimitiveShortOfficeCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.office.impl.ShortOfficeCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.office.impl.StringOfficeCellValueBinder;
import org.ssio.internal.util.SsioReflectionHelper;

import java.util.LinkedHashMap;
import java.util.Map;


public final class OfficeCellValueBinderRepo {
    private static Map<SsioSimpleTypeEnum, Class<? extends OfficeCellValueBinder>> javaTypeToBinderType = new LinkedHashMap<>();


    static {
        javaTypeToBinderType.put(SsioSimpleTypeEnum.PrimitiveBoolean, PrimitiveBooleanOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SsioSimpleTypeEnum.PrimitiveShort, PrimitiveShortOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SsioSimpleTypeEnum.PrimitiveInt, PrimitiveIntOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SsioSimpleTypeEnum.PrimitiveLong, PrimitiveLongOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SsioSimpleTypeEnum.PrimitiveFloat, PrimitiveFloatOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SsioSimpleTypeEnum.PrimitiveDouble, PrimitiveDoubleOfficeCellValueBinder.class);

        javaTypeToBinderType.put(SsioSimpleTypeEnum.Boolean, BooleanOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SsioSimpleTypeEnum.Short, ShortOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SsioSimpleTypeEnum.Integer, IntegerOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SsioSimpleTypeEnum.Long, LongOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SsioSimpleTypeEnum.Float, FloatOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SsioSimpleTypeEnum.Double, DoubleOfficeCellValueBinder.class);

        javaTypeToBinderType.put(SsioSimpleTypeEnum.BigInteger, BigIntegerOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SsioSimpleTypeEnum.BigDecimal, BigDecimalOfficeCellValueBinder.class);


        javaTypeToBinderType.put(SsioSimpleTypeEnum.Date, DateOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SsioSimpleTypeEnum.LocalDate, LocalDateOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SsioSimpleTypeEnum.LocalDateTime, LocalDateTimeOfficeCellValueBinder.class);

        javaTypeToBinderType.put(SsioSimpleTypeEnum.String, StringOfficeCellValueBinder.class);

        javaTypeToBinderType.put(SsioSimpleTypeEnum.Enum, EnumOfficeCellValueBinder.class);
    }

    public static OfficeCellValueBinder getOfficeCellValueBinder(SsioSimpleTypeEnum javaType, Class<Enum<?>> enumClassIfEnum) {
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
