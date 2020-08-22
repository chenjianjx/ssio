package org.ssio.internal.common.cellvalue.binder.csv;

import org.ssio.api.common.abstractsheet.model.SsCellValueJavaType;
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
    private static Map<SsCellValueJavaType, Class<? extends CsvCellValueBinder>> javaTypeToBinderType = new LinkedHashMap<>();


    static {
        javaTypeToBinderType.put(SsCellValueJavaType.PrimitiveBoolean, PrimitiveBooleanCsvCellValueBinder.class);
        javaTypeToBinderType.put(SsCellValueJavaType.PrimitiveShort, PrimitiveShortCsvCellValueBinder.class);
        javaTypeToBinderType.put(SsCellValueJavaType.PrimitiveInt, PrimitiveIntCsvCellValueBinder.class);
        javaTypeToBinderType.put(SsCellValueJavaType.PrimitiveLong, PrimitiveLongCsvCellValueBinder.class);
        javaTypeToBinderType.put(SsCellValueJavaType.PrimitiveFloat, PrimitiveFloatCsvCellValueBinder.class);
        javaTypeToBinderType.put(SsCellValueJavaType.PrimitiveDouble, PrimitiveDoubleCsvCellValueBinder.class);

        javaTypeToBinderType.put(SsCellValueJavaType.Boolean, BooleanCsvCellValueBinder.class);
        javaTypeToBinderType.put(SsCellValueJavaType.Short, ShortCsvCellValueBinder.class);
        javaTypeToBinderType.put(SsCellValueJavaType.Integer, IntegerCsvCellValueBinder.class);
        javaTypeToBinderType.put(SsCellValueJavaType.Long, LongCsvCellValueBinder.class);
        javaTypeToBinderType.put(SsCellValueJavaType.Float, FloatCsvCellValueBinder.class);
        javaTypeToBinderType.put(SsCellValueJavaType.Double, DoubleCsvCellValueBinder.class);

        javaTypeToBinderType.put(SsCellValueJavaType.BigInteger, BigIntegerCsvCellValueBinder.class);
        javaTypeToBinderType.put(SsCellValueJavaType.BigDecimal, BigDecimalCsvCellValueBinder.class);


        javaTypeToBinderType.put(SsCellValueJavaType.Date, DateCsvCellValueBinder.class);
        javaTypeToBinderType.put(SsCellValueJavaType.LocalDate, LocalDateCsvCellValueBinder.class);
        javaTypeToBinderType.put(SsCellValueJavaType.LocalDateTime, LocalDateTimeCsvCellValueBinder.class);

        javaTypeToBinderType.put(SsCellValueJavaType.String, StringCsvCellValueBinder.class);

        javaTypeToBinderType.put(SsCellValueJavaType.Enum, EnumCsvCellValueBinder.class);
    }

    public static CsvCellValueBinder getCsvCellValueBinder(SsCellValueJavaType javaType, Class<Enum<?>> enumClassIfEnum) {
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
