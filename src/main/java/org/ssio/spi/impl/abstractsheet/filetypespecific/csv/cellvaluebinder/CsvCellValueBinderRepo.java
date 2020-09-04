package org.ssio.spi.impl.abstractsheet.filetypespecific.csv.cellvaluebinder;

import org.ssio.api.interfaces.typing.SsioSimpleTypeEnum;
import org.ssio.spi.impl.abstractsheet.filetypespecific.csv.cellvaluebinder.bytype.BigDecimalCsvCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.csv.cellvaluebinder.bytype.BigIntegerCsvCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.csv.cellvaluebinder.bytype.BooleanCsvCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.csv.cellvaluebinder.bytype.DateCsvCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.csv.cellvaluebinder.bytype.DoubleCsvCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.csv.cellvaluebinder.bytype.EnumCsvCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.csv.cellvaluebinder.bytype.FloatCsvCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.csv.cellvaluebinder.bytype.IntegerCsvCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.csv.cellvaluebinder.bytype.LocalDateCsvCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.csv.cellvaluebinder.bytype.LocalDateTimeCsvCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.csv.cellvaluebinder.bytype.LongCsvCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.csv.cellvaluebinder.bytype.PrimitiveBooleanCsvCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.csv.cellvaluebinder.bytype.PrimitiveDoubleCsvCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.csv.cellvaluebinder.bytype.PrimitiveFloatCsvCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.csv.cellvaluebinder.bytype.PrimitiveIntCsvCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.csv.cellvaluebinder.bytype.PrimitiveLongCsvCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.csv.cellvaluebinder.bytype.PrimitiveShortCsvCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.csv.cellvaluebinder.bytype.ShortCsvCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.csv.cellvaluebinder.bytype.StringCsvCellValueBinder;
import org.ssio.util.lang.SsioReflectionUtils;

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
            return SsioReflectionUtils.createInstance(binderType);
        }
    }
}
