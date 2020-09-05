package org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder;

import org.ssio.api.external.typing.SimpleTypeEnum;
import org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.bytype.BigDecimalCsvCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.bytype.BigIntegerCsvCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.bytype.BooleanCsvCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.bytype.DateCsvCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.bytype.DoubleCsvCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.bytype.EnumCsvCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.bytype.FloatCsvCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.bytype.IntegerCsvCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.bytype.LocalDateCsvCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.bytype.LocalDateTimeCsvCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.bytype.LongCsvCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.bytype.PrimitiveBooleanCsvCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.bytype.PrimitiveDoubleCsvCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.bytype.PrimitiveFloatCsvCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.bytype.PrimitiveIntCsvCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.bytype.PrimitiveLongCsvCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.bytype.PrimitiveShortCsvCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.bytype.ShortCsvCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.csv.cellvaluebinder.bytype.StringCsvCellValueBinder;
import org.ssio.util.lang.SsioReflectionUtils;

import java.util.LinkedHashMap;
import java.util.Map;


public final class CsvCellValueBinderRepo {
    private static Map<SimpleTypeEnum, Class<? extends CsvCellValueBinder>> javaTypeToBinderType = new LinkedHashMap<>();


    static {
        javaTypeToBinderType.put(SimpleTypeEnum.PrimitiveBoolean, PrimitiveBooleanCsvCellValueBinder.class);
        javaTypeToBinderType.put(SimpleTypeEnum.PrimitiveShort, PrimitiveShortCsvCellValueBinder.class);
        javaTypeToBinderType.put(SimpleTypeEnum.PrimitiveInt, PrimitiveIntCsvCellValueBinder.class);
        javaTypeToBinderType.put(SimpleTypeEnum.PrimitiveLong, PrimitiveLongCsvCellValueBinder.class);
        javaTypeToBinderType.put(SimpleTypeEnum.PrimitiveFloat, PrimitiveFloatCsvCellValueBinder.class);
        javaTypeToBinderType.put(SimpleTypeEnum.PrimitiveDouble, PrimitiveDoubleCsvCellValueBinder.class);

        javaTypeToBinderType.put(SimpleTypeEnum.Boolean, BooleanCsvCellValueBinder.class);
        javaTypeToBinderType.put(SimpleTypeEnum.Short, ShortCsvCellValueBinder.class);
        javaTypeToBinderType.put(SimpleTypeEnum.Integer, IntegerCsvCellValueBinder.class);
        javaTypeToBinderType.put(SimpleTypeEnum.Long, LongCsvCellValueBinder.class);
        javaTypeToBinderType.put(SimpleTypeEnum.Float, FloatCsvCellValueBinder.class);
        javaTypeToBinderType.put(SimpleTypeEnum.Double, DoubleCsvCellValueBinder.class);

        javaTypeToBinderType.put(SimpleTypeEnum.BigInteger, BigIntegerCsvCellValueBinder.class);
        javaTypeToBinderType.put(SimpleTypeEnum.BigDecimal, BigDecimalCsvCellValueBinder.class);


        javaTypeToBinderType.put(SimpleTypeEnum.Date, DateCsvCellValueBinder.class);
        javaTypeToBinderType.put(SimpleTypeEnum.LocalDate, LocalDateCsvCellValueBinder.class);
        javaTypeToBinderType.put(SimpleTypeEnum.LocalDateTime, LocalDateTimeCsvCellValueBinder.class);

        javaTypeToBinderType.put(SimpleTypeEnum.String, StringCsvCellValueBinder.class);

        javaTypeToBinderType.put(SimpleTypeEnum.Enum, EnumCsvCellValueBinder.class);
    }

    public static CsvCellValueBinder getCsvCellValueBinder(SimpleTypeEnum javaType, Class<Enum<?>> enumClassIfEnum) {
        Class<? extends CsvCellValueBinder> binderType = javaTypeToBinderType.get(javaType);
        if (binderType == null) {
            return null;
        }
        if (binderType.equals(EnumCsvCellValueBinder.class)) {
            return new EnumCsvCellValueBinder(enumClassIfEnum);
        } else {
            return SsioReflectionUtils.createInstance(binderType);
        }
    }
}
