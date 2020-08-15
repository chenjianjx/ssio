package org.ssio.api.s2b;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

public class SheetToBeansResult<T> {

    private List<T> beans = new ArrayList<>();

    /**
     * the errors of data rows (not including header row) found while being parsed. The error here can tell you which cell is wrong.
     */
    private List<CellError> cellErrors = new ArrayList<>();

    public boolean hasCellErrors(){
        return this.cellErrors.size() > 0;
    }

    public List<T> getBeans() {
        return beans;
    }

    public List<CellError> getCellErrors() {
        return cellErrors;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
