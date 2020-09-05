package org.ssio.api.external.annotation;

import org.ssio.api.external.SsioApiConstants;
import org.ssio.api.external.typing.ComplexTypeHandler;
import org.ssio.api.external.typing.SimpleTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * The annotation to map a java bean property to or from a column in the sheet.
 * Please put the annotation on a field, or on a getter/setter method.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface SsColumn {
    int INDEX_UNKNOWN = -1;
    String NAME_UNKNOWN = "";
    String FORMAT_UNKNOWN = "";

    /**
     * The column index in the sheet, 0-based.
     * For save:  the index is required.  Please provide one, and make sure the indexes don't duplicate in a single class hierarchy
     * For parse:
     * * If the column mapping mode is by index, then it is required.
     * * If the column mapping mode is not by index, the index here will be ignored
     *
     * @return
     */
    int index() default INDEX_UNKNOWN;


    /**
     * The column name in the sheet.
     * For save: If it is not supplied, the name will be derived from the java property name, e.g.  fooBar => Foo Bar
     * For parse:
     * * If the column mapping mode is by name and the name is not supplied here, the name the name will be derived from the java property name, e.g. column "Foo Bar" will be mapped to "fooBar"
     * * If the column mapping mode is not by name, the name here will be ignored
     *
     * @return
     */
    String name() default NAME_UNKNOWN;


    /**
     * The format in spreadsheet if the property type is one of [{@link Date}, {@link LocalDate}, {@link LocalDateTime} ] .
     * The format should be {@link java.text.SimpleDateFormat}-compatible pattern definition.
     * Default values are:
     * * For {@link Date} and {@link LocalDateTime}, it is {@link SsioApiConstants#DEFAULT_LOCAL_DATE_TIME_PATTERN};
     * * For {@link LocalDate}, it is {@link SsioApiConstants#DEFAULT_LOCAL_DATE_PATTERN};
     * <p>
     * For other types, this format will be ignored.
     *
     * @return
     */
    String format() default "";


    /**
     * If the property is not of a simple type supported by {@link SimpleTypeEnum}, you need to provider a handler here to make conversions between your complex type and a supported simple type.
     * You can also use it for simple-typed properties, for example, if you want save a LocalDate as a number
     *
     * @return
     */
    Class<? extends ComplexTypeHandler> typeHandler() default ComplexTypeHandler.NO_HANDLING.class;

}
