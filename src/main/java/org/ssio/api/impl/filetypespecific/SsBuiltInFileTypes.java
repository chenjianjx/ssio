package org.ssio.api.impl.filetypespecific;

/**
 * built-in spreadsheet file types.
 * This is not defined as enum, so extension is possible
 */
public interface SsBuiltInFileTypes {
    /**
     * microsoft office / open office/ WPS spreadsheet files
     */
    String OFFICE = "OFFICE";

    /**
     * plain csv text files
     */
    String CSV = "CSV";
}
