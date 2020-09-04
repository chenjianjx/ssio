package org.ssio.internal.common.cellvalue.binder.csv;

import org.ssio.api.common.typing.SsioSimpleTypeEnum;
import org.ssio.internal.common.cellvalue.binder.csv.impl.BigDecimalCsvCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.csv.impl.BigIntegerCsvCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.csv.impl.BooleanCsvCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.csv.impl.DateCsvCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.csv.impl.DoubleCsvCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.csv.impl.EnumCsvCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.csv.impl.FloatCsvCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.csv.impl.IntegerCsvCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.csv.impl.LocalDateCsvCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.csv.impl.LocalDateTimeCsvCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.csv.impl.LongCsvCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.csv.impl.PrimitiveBooleanCsvCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.csv.impl.PrimitiveDoubleCsvCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.csv.impl.PrimitiveFloatCsvCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.csv.impl.PrimitiveIntCsvCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.csv.impl.PrimitiveLongCsvCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.csv.impl.PrimitiveShortCsvCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.csv.impl.ShortCsvCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.csv.impl.StringCsvCellValueBinder;
import org.ssio.internal.util.SsioReflectionHelper;

import java.util.LinkedHashMap;
import java.util.Map;


public final class CsvCellValueBinderRepo {
    private static Map<SsioSimpleTypeEnum, Class<? extends CsvCellValueBinder>> javaTypeToBinderType = new LinkedHashMap<>();


    static {
        javaTypeToBinderType.put(SsioSimpleTypeEnum.PrimitiveBoolean, PrimitiveBooleanCsvCellValueBinder.class);
        javaTypeToBinderType.put(SsioSimpleTypeEnum.PrimitiveShort, PrimitiveShortCsvCellValueBinder.class);
        javaTypeToBinderType.put(SsioSimpleTypeEnum.PrimitiveInt, PrimitiveIntCsvCellValueBinder.class);
        javaTypeToBinderType.put(SsioSimpleTypeEnum.PrimitiveLong, PrimitiveLongCsvCellValueBinder.class);
        javaTypeToBinderType.put(SsioSimpleTypeEnum.PrimitiveFloat, PrimitiveFloatCsvCellValueBinder.class);
        javaTypeToBinderType.put(SsioSimpleTypeEnum.PrimitiveDouble, PrimitiveDoubleCsvCellValueBinder.class);

        javaTypeToBinderType.put(SsioSimpleTypeEnum.Boolean, BooleanCsvCellValueBinder.class);
        javaTypeToBinderType.put(SsioSimpleTypeEnum.Short, ShortCsvCellValueBinder.class);
        javaTypeToBinderType.put(SsioSimpleTypeEnum.Integer, IntegerCsvCellValueBinder.class);
        javaTypeToBinderType.put(SsioSimpleTypeEnum.Long, LongCsvCellValueBinder.class);
        javaTypeToBinderType.put(SsioSimpleTypeEnum.Float, FloatCsvCellValueBinder.class);
        javaTypeToBinderType.put(SsioSimpleTypeEnum.Double, DoubleCsvCellValueBinder.class);

        javaTypeToBinderType.put(SsioSimpleTypeEnum.BigInteger, BigIntegerCsvCellValueBinder.class);
        javaTypeToBinderType.put(SsioSimpleTypeEnum.BigDecimal, BigDecimalCsvCellValueBinder.class);


        javaTypeToBinderType.put(SsioSimpleTypeEnum.Date, DateCsvCellValueBinder.class);
        javaTypeToBinderType.put(SsioSimpleTypeEnum.LocalDate, LocalDateCsvCellValueBinder.class);
        javaTypeToBinderType.put(SsioSimpleTypeEnum.LocalDateTime, LocalDateTimeCsvCellValueBinder.class);

        javaTypeToBinderType.put(SsioSimpleTypeEnum.String, StringCsvCellValueBinder.class);

        javaTypeToBinderType.put(SsioSimpleTypeEnum.Enum, EnumCsvCellValueBinder.class);
    }

    public static CsvCellValueBinder getCsvCellValueBinder(SsioSimpleTypeEnum javaType, Class<Enum<?>> enumClassIfEnum) {
        Class<? extends CsvCellValueBinder> binderType = javaTypeToBinderType.get(javaType);
        if (binderType == null) {
            return null;
        }
        if (binderType.equals(EnumCsvCellValueBinder.class)) {
            return new EnumCsvCellValueBinder(enumClassIfEnum);
        } else {
            return SsioReflectionHelper.createInstance(binderType);
        }
    }
}
