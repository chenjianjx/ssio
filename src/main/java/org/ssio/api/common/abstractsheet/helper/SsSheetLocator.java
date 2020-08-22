package org.ssio.api.common.abstractsheet.helper;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.ssio.api.common.abstractsheet.helper.sheetlocate.SsSheetByIndexLocator;
import org.ssio.api.common.abstractsheet.helper.sheetlocate.SsSheetByNameLocator;
import org.ssio.api.common.abstractsheet.model.SsSheet;
import org.ssio.api.common.abstractsheet.model.SsWorkbook;

/**
 * locate a sheet inside a workbook
 */
public interface SsSheetLocator {
    static SsSheetLocator byIndexLocator(int index) {
        return new SsSheetByIndexLocator(index);
    }

    static SsSheetLocator byNameLocator(String name) {
        return new SsSheetByNameLocator(name);
    }

    SsSheet getSheet(SsWorkbook workbook);

    /**
     * useful in debugging or exception messages
     *
     * @return
     */
    default String getDesc() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }


}
