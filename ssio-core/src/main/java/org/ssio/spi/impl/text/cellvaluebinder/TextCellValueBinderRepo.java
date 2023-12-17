package org.ssio.spi.impl.text.cellvaluebinder;

import org.ssio.api.interfaces.typing.SimpleType;
import org.ssio.spi.impl.text.cellvaluebinder.bytype.BigDecimalTextCellValueBinder;
import org.ssio.spi.impl.text.cellvaluebinder.bytype.BigIntegerTextCellValueBinder;
import org.ssio.spi.impl.text.cellvaluebinder.bytype.BooleanTextCellValueBinder;
import org.ssio.spi.impl.text.cellvaluebinder.bytype.DateTextCellValueBinder;
import org.ssio.spi.impl.text.cellvaluebinder.bytype.DoubleTextCellValueBinder;
import org.ssio.spi.impl.text.cellvaluebinder.bytype.EnumTextCellValueBinder;
import org.ssio.spi.impl.text.cellvaluebinder.bytype.FloatTextCellValueBinder;
import org.ssio.spi.impl.text.cellvaluebinder.bytype.IntegerTextCellValueBinder;
import org.ssio.spi.impl.text.cellvaluebinder.bytype.LocalDateTextCellValueBinder;
import org.ssio.spi.impl.text.cellvaluebinder.bytype.LocalDateTimeTextCellValueBinder;
import org.ssio.spi.impl.text.cellvaluebinder.bytype.LongTextCellValueBinder;
import org.ssio.spi.impl.text.cellvaluebinder.bytype.PrimitiveBooleanTextCellValueBinder;
import org.ssio.spi.impl.text.cellvaluebinder.bytype.PrimitiveDoubleTextCellValueBinder;
import org.ssio.spi.impl.text.cellvaluebinder.bytype.PrimitiveFloatTextCellValueBinder;
import org.ssio.spi.impl.text.cellvaluebinder.bytype.PrimitiveIntTextCellValueBinder;
import org.ssio.spi.impl.text.cellvaluebinder.bytype.PrimitiveLongTextCellValueBinder;
import org.ssio.spi.impl.text.cellvaluebinder.bytype.PrimitiveShortTextCellValueBinder;
import org.ssio.spi.impl.text.cellvaluebinder.bytype.ShortTextCellValueBinder;
import org.ssio.spi.impl.text.cellvaluebinder.bytype.StringTextCellValueBinder;
import org.ssio.util.lang.SsioReflectionUtils;

import java.util.LinkedHashMap;
import java.util.Map;


public final class TextCellValueBinderRepo {
    private static Map<SimpleType, Class<? extends TextCellValueBinder>> javaTypeToBinderType = new LinkedHashMap<>();


    static {
        javaTypeToBinderType.put(SimpleType.PrimitiveBoolean, PrimitiveBooleanTextCellValueBinder.class);
        javaTypeToBinderType.put(SimpleType.PrimitiveShort, PrimitiveShortTextCellValueBinder.class);
        javaTypeToBinderType.put(SimpleType.PrimitiveInt, PrimitiveIntTextCellValueBinder.class);
        javaTypeToBinderType.put(SimpleType.PrimitiveLong, PrimitiveLongTextCellValueBinder.class);
        javaTypeToBinderType.put(SimpleType.PrimitiveFloat, PrimitiveFloatTextCellValueBinder.class);
        javaTypeToBinderType.put(SimpleType.PrimitiveDouble, PrimitiveDoubleTextCellValueBinder.class);

        javaTypeToBinderType.put(SimpleType.Boolean, BooleanTextCellValueBinder.class);
        javaTypeToBinderType.put(SimpleType.Short, ShortTextCellValueBinder.class);
        javaTypeToBinderType.put(SimpleType.Integer, IntegerTextCellValueBinder.class);
        javaTypeToBinderType.put(SimpleType.Long, LongTextCellValueBinder.class);
        javaTypeToBinderType.put(SimpleType.Float, FloatTextCellValueBinder.class);
        javaTypeToBinderType.put(SimpleType.Double, DoubleTextCellValueBinder.class);

        javaTypeToBinderType.put(SimpleType.BigInteger, BigIntegerTextCellValueBinder.class);
        javaTypeToBinderType.put(SimpleType.BigDecimal, BigDecimalTextCellValueBinder.class);


        javaTypeToBinderType.put(SimpleType.Date, DateTextCellValueBinder.class);
        javaTypeToBinderType.put(SimpleType.LocalDate, LocalDateTextCellValueBinder.class);
        javaTypeToBinderType.put(SimpleType.LocalDateTime, LocalDateTimeTextCellValueBinder.class);

        javaTypeToBinderType.put(SimpleType.String, StringTextCellValueBinder.class);

        javaTypeToBinderType.put(SimpleType.Enum, EnumTextCellValueBinder.class);
    }

    public static TextCellValueBinder getBinder(SimpleType javaType, Class<Enum<?>> enumClassIfEnum) {
        Class<? extends TextCellValueBinder> binderType = javaTypeToBinderType.get(javaType);
        if (binderType == null) {
            return null;
        }
        if (binderType.equals(EnumTextCellValueBinder.class)) {
            return new EnumTextCellValueBinder(enumClassIfEnum);
        } else {
            return SsioReflectionUtils.createInstance(binderType);
        }
    }
}
