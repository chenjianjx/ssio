package org.ssio.api.abstractsheet.office;

import org.apache.poi.ss.usermodel.Cell;
import org.ssio.api.abstractsheet.SsCell;
import org.ssio.api.abstractsheet.SsCellValueJavaType;
import org.ssio.internal.common.cellvalue.binder.office.OfficeCellValueBinder;
import org.ssio.internal.common.cellvalue.binder.office.OfficeCellValueBinderRepo;

public class OfficeCell implements SsCell {
    private final Cell poiCell;

    public OfficeCell(Cell poiCell) {
        this.poiCell = poiCell;
    }

    @Override
    public String readValueAsString() {
        return poiCell.getStringCellValue();
    }

    @Override
    public Object readValueAsType(SsCellValueJavaType targetType, Class<Enum<?>> enumClassIfEnum) throws RuntimeException {
        OfficeCellValueBinder cellValueBinder = OfficeCellValueBinderRepo.getOfficeCellValueBinder(targetType, enumClassIfEnum);
        if (cellValueBinder == null) {
            throw new IllegalStateException();
        }
        return cellValueBinder.getValue(poiCell);
    }
}
