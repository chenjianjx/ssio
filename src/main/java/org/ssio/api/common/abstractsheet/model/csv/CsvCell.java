package org.ssio.api.common.abstractsheet.model.csv;

import org.ssio.api.common.abstractsheet.model.SsCell;
import org.ssio.api.common.abstractsheet.model.SsCellValueJavaType;
import org.ssio.internal.common.cellvalue.binder.SsCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.csv.CsvCellValueBinderRepo;

public class CsvCell implements SsCell {

    private String content;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public SsCellValueBinder getCellValueBinder(SsCellValueJavaType javaType, Class<Enum<?>> enumClassIfEnum) {
        return CsvCellValueBinderRepo.getCsvCellValueBinder(javaType, enumClassIfEnum);
    }

    @Override
    public void styleAsError() {
        //do nothing
    }

    @Override
    public void styleAsHeader() {
        //do nothing
    }


}
