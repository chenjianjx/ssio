package org.ssio.api.s2b;

import org.ssio.api.common.annotation.SsColumn;

/**
 * Given a bean property "foo",  from which column to get its value?
 */
public enum PropFromColumnMappingMode {
    /**
     * Get the value from a column with a particular name. The name should be set using  {@link SsColumn#name()}
     */
    BY_NAME,

    /**
     * Get the value from an column with a particular index. The index should be set using  {@link SsColumn#index()}
     */
    BY_INDEX
}
