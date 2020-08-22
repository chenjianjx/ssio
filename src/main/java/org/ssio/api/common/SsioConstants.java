package org.ssio.api.common;

public interface SsioConstants {
    String DEFAULT_LOCAL_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    String DEFAULT_LOCAL_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * it doesn't allow 'T', but only T
     */
    String DEFAULT_LOCAL_DATE_TIME_PATTERN_FOR_SPREADSHEET = "yyyy-MM-ddTHH:mm:ss";
    char DEFAULT_CSV_CELL_SEPARATOR = ',';
}
