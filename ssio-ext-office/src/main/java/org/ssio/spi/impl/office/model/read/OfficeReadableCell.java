package org.ssio.spi.impl.office.model.read;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.ssio.api.interfaces.typing.SimpleType;
import org.ssio.spi.impl.office.cellvaluebinder.OfficeCellValueBinderRepo;
import org.ssio.spi.interfaces.cellvaluebinder.CellValueBinder;
import org.ssio.spi.interfaces.model.read.ReadableCell;


public class OfficeReadableCell implements ReadableCell {
    /**
     * note: can be null
     */
    private Cell poiCell;

    private OfficeReadableCell() {

    }

    public static OfficeReadableCell newInstanceFromPoiCell(Cell poiCell) {
        OfficeReadableCell cell = new OfficeReadableCell();
        cell.poiCell = poiCell;
        return cell;
    }


    public Cell getPoiCell() {
        return poiCell;
    }


    @Override
    public CellValueBinder getCellValueBinder(SimpleType javaType, Class<Enum<?>> enumClassIfEnum) {
        return OfficeCellValueBinderRepo.getOfficeCellValueBinder(javaType, enumClassIfEnum);
    }



    @Override
    public boolean isBlank() {
        if(poiCell == null){
            return false;
        }

        switch (poiCell.getCellType()) {
            case _NONE: {
                return true;
            }
            case BLANK: {
                return true;
            }
            case STRING: {
                StringUtils.isBlank(poiCell.getStringCellValue());
            }
            case BOOLEAN: {
                return false;
            }
            case NUMERIC: {
                return false;
            }

            default:
                throw new UnsupportedOperationException("Unsupported cell type: " + poiCell.getCellType());
        }

    }
}
