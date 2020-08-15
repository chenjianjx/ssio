package org.ssio.internal.common.cellvalue.binder.csv;

import org.ssio.api.abstractsheet.SsCellValueJavaType;

import java.util.LinkedHashMap;
import java.util.Map;


public final class CsvCellValueBinderRepo {
    private static Map<SsCellValueJavaType, CsvCellValueBinder> typeToBinder = new LinkedHashMap<>();


    static {
        typeToBinder.put(SsCellValueJavaType.PrimitiveBoolean, new PrimitiveBooleanCsvCellValueBinder());
        typeToBinder.put(SsCellValueJavaType.PrimitiveShort, new PrimitiveShortCsvCellValueBinder());
        typeToBinder.put(SsCellValueJavaType.PrimitiveInt, new PrimitiveIntCsvCellValueBinder());
        typeToBinder.put(SsCellValueJavaType.PrimitiveLong, new PrimitiveLongCsvCellValueBinder());
        typeToBinder.put(SsCellValueJavaType.PrimitiveFloat, new PrimitiveFloatCsvCellValueBinder());
        typeToBinder.put(SsCellValueJavaType.PrimitiveDouble, new PrimitiveDoubleCsvCellValueBinder());

        typeToBinder.put(SsCellValueJavaType.Boolean, new BooleanCsvCellValueBinder());
        typeToBinder.put(SsCellValueJavaType.Short, new ShortCsvCellValueBinder());
        typeToBinder.put(SsCellValueJavaType.Integer, new IntegerCsvCellValueBinder());
        typeToBinder.put(SsCellValueJavaType.Long, new LongCsvCellValueBinder());
        typeToBinder.put(SsCellValueJavaType.Float, new FloatCsvCellValueBinder());
        typeToBinder.put(SsCellValueJavaType.Double, new DoubleCsvCellValueBinder());

        typeToBinder.put(SsCellValueJavaType.BigInteger, new BigIntegerCsvCellValueBinder());
        typeToBinder.put(SsCellValueJavaType.BigDecimal, new BigDecimalCsvCellValueBinder());


        typeToBinder.put(SsCellValueJavaType.Date, new DateCsvCellValueBinder());
        typeToBinder.put(SsCellValueJavaType.LocalDate, new LocalDateCsvCellValueBinder());
        typeToBinder.put(SsCellValueJavaType.LocalDateTime, new LocalDateTimeCsvCellValueBinder());

        typeToBinder.put(SsCellValueJavaType.String, new StringCsvCellValueBinder());

        typeToBinder.put(SsCellValueJavaType.Enum, new EnumCsvCellValueBinder());
    }

    public static CsvCellValueBinder getCsvCellValueBinder(SsCellValueJavaType javaType) {
        return typeToBinder.get(javaType);
    }
}
