package org.ssio.api.external.parse;


/**
 * Given a bean property "foo",  from which column to get its value?
 */
public enum PropFromColumnMappingMode {
    /**
     * Get the value from a column with a particular name.
     */
    BY_NAME,

    /**
     * Get the value from an column with a particular index.
     */
    BY_INDEX
}
