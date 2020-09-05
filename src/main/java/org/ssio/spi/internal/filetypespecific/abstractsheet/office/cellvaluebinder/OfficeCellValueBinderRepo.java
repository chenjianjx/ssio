package org.ssio.spi.internal.filetypespecific.abstractsheet.office.cellvaluebinder;

import org.ssio.api.external.typing.SimpleTypeEnum;
import org.ssio.spi.internal.filetypespecific.abstractsheet.office.cellvaluebinder.bytype.BigDecimalOfficeCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.office.cellvaluebinder.bytype.BigIntegerOfficeCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.office.cellvaluebinder.bytype.BooleanOfficeCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.office.cellvaluebinder.bytype.DateOfficeCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.office.cellvaluebinder.bytype.DoubleOfficeCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.office.cellvaluebinder.bytype.EnumOfficeCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.office.cellvaluebinder.bytype.FloatOfficeCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.office.cellvaluebinder.bytype.IntegerOfficeCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.office.cellvaluebinder.bytype.LocalDateOfficeCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.office.cellvaluebinder.bytype.LocalDateTimeOfficeCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.office.cellvaluebinder.bytype.LongOfficeCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.office.cellvaluebinder.bytype.PrimitiveBooleanOfficeCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.office.cellvaluebinder.bytype.PrimitiveDoubleOfficeCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.office.cellvaluebinder.bytype.PrimitiveFloatOfficeCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.office.cellvaluebinder.bytype.PrimitiveIntOfficeCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.office.cellvaluebinder.bytype.PrimitiveLongOfficeCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.office.cellvaluebinder.bytype.PrimitiveShortOfficeCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.office.cellvaluebinder.bytype.ShortOfficeCellValueBinder;
import org.ssio.spi.internal.filetypespecific.abstractsheet.office.cellvaluebinder.bytype.StringOfficeCellValueBinder;
import org.ssio.util.lang.SsioReflectionUtils;

import java.util.LinkedHashMap;
import java.util.Map;


public final class OfficeCellValueBinderRepo {
    private static Map<SimpleTypeEnum, Class<? extends OfficeCellValueBinder>> javaTypeToBinderType = new LinkedHashMap<>();


    static {
        javaTypeToBinderType.put(SimpleTypeEnum.PrimitiveBoolean, PrimitiveBooleanOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SimpleTypeEnum.PrimitiveShort, PrimitiveShortOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SimpleTypeEnum.PrimitiveInt, PrimitiveIntOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SimpleTypeEnum.PrimitiveLong, PrimitiveLongOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SimpleTypeEnum.PrimitiveFloat, PrimitiveFloatOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SimpleTypeEnum.PrimitiveDouble, PrimitiveDoubleOfficeCellValueBinder.class);

        javaTypeToBinderType.put(SimpleTypeEnum.Boolean, BooleanOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SimpleTypeEnum.Short, ShortOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SimpleTypeEnum.Integer, IntegerOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SimpleTypeEnum.Long, LongOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SimpleTypeEnum.Float, FloatOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SimpleTypeEnum.Double, DoubleOfficeCellValueBinder.class);

        javaTypeToBinderType.put(SimpleTypeEnum.BigInteger, BigIntegerOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SimpleTypeEnum.BigDecimal, BigDecimalOfficeCellValueBinder.class);


        javaTypeToBinderType.put(SimpleTypeEnum.Date, DateOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SimpleTypeEnum.LocalDate, LocalDateOfficeCellValueBinder.class);
        javaTypeToBinderType.put(SimpleTypeEnum.LocalDateTime, LocalDateTimeOfficeCellValueBinder.class);

        javaTypeToBinderType.put(SimpleTypeEnum.String, StringOfficeCellValueBinder.class);

        javaTypeToBinderType.put(SimpleTypeEnum.Enum, EnumOfficeCellValueBinder.class);
    }

    public static OfficeCellValueBinder getOfficeCellValueBinder(SimpleTypeEnum javaType, Class<Enum<?>> enumClassIfEnum) {
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
