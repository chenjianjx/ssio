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
     * For beans2sheet:  the index is required.  Please provide one, and make sure the indexes don't duplicate in a single class hierarchy
     * For sheet2beans:
     *    * If the column mapping mode is by index, then it is required.
     *    * If the column mapping mode is not by index, the index here will be ignored
     *
     * @return
     */
    int index() default INDEX_UNKNOWN;


    /**
     * The column name in the sheet.
     * For beans2sheet: If it is not supplied, the name will be derived from the java property name, e.g.  fooBar => Foo Bar
     * For sheet2beans:
     *    * If the column mapping mode is by name and the name is not supplied here, the name the name will be derived from the java property name, e.g. column "Foo Bar" will be mapped to "fooBar"
     *    * If the column mapping mode is not by name, the name here will be ignored
     * @return
     */
    String name() default NAME_UNKNOWN;
}
