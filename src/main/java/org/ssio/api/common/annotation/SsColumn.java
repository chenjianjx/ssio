package org.ssio.api.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation to map a java bean property <=> a column in the sheet.
 * Please put it on a field, or on a getter/setter method
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface SsColumn {
    int INDEX_UNKNOWN = -1;
    String NAME_UNKNOWN = "";

    /**
     * The column index in the sheet, 0-based.
     * The index is required for beans2sheet.  Please provide one and make sure they don't duplicate in a single class hierarchy
     *
     * @return
     */
    int index() default INDEX_UNKNOWN;


    /**
     * The column name in the sheet.
     * The name is not required for beans2sheet. If it is not supplied, the name will be derived from the java property name, e.g.  fooBar => Foo Bar
     *
     * @return
     */
    String name() default NAME_UNKNOWN;
}
