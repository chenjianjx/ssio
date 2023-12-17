package org.ssio.api.impl.common.sheetlocate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.ssio.spi.interfaces.model.read.ReadableSheet;
import org.ssio.spi.interfaces.model.read.ReadableWorkbook;

/**
 * locate a sheet inside a workbook
 */
public interface SheetLocator {
    static SheetLocator byIndexLocator(int index) {
        return new SheetByIndexLocator(index);
    }

    static SheetLocator byNameLocator(String name) {
        return new SheetByNameLocator(name);
    }

    ReadableSheet getSheet(ReadableWorkbook workbook);

    /**
     * useful in debugging or exception messages
     *
     
     */
    default String getDesc() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }


}
