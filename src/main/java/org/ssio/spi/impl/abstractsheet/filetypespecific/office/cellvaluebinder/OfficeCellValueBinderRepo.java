package org.ssio.spi.impl.abstractsheet.filetypespecific.office.cellvaluebinder;

import org.ssio.api.interfaces.typing.SsioSimpleTypeEnum;
import org.ssio.spi.impl.abstractsheet.filetypespecific.office.cellvaluebinder.bytype.IntegerOfficeCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.office.cellvaluebinder.bytype.ShortOfficeCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.office.cellvaluebinder.bytype.BigDecimalOfficeCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.office.cellvaluebinder.bytype.BigIntegerOfficeCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.office.cellvaluebinder.bytype.BooleanOfficeCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.office.cellvaluebinder.bytype.DateOfficeCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.office.cellvaluebinder.bytype.DoubleOfficeCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.office.cellvaluebinder.bytype.EnumOfficeCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.office.cellvaluebinder.bytype.FloatOfficeCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.office.cellvaluebinder.bytype.LocalDateOfficeCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.office.cellvaluebinder.bytype.LocalDateTimeOfficeCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.office.cellvaluebinder.bytype.LongOfficeCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.office.cellvaluebinder.bytype.PrimitiveBooleanOfficeCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.office.cellvaluebinder.bytype.PrimitiveDoubleOfficeCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.office.cellvaluebinder.bytype.PrimitiveFloatOfficeCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.office.cellvaluebinder.bytype.PrimitiveIntOfficeCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.office.cellvaluebinder.bytype.PrimitiveLongOfficeCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.office.cellvaluebinder.bytype.PrimitiveShortOfficeCellValueBinder;
import org.ssio.spi.impl.abstractsheet.filetypespecific.office.cellvaluebinder.bytype.StringOfficeCellValueBinder;
import org.ssio.util.lang.SsioReflectionUtils;

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
            return SsioReflectionUtils.createInstance(binderType);
        }
    }
}
