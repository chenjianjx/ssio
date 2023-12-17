package org.ssio.spi.impl.text.cellvaluebinder.bytype;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PrimitiveBooleanTextCellValueBinderTest {

    PrimitiveBooleanTextCellValueBinder binder = new PrimitiveBooleanTextCellValueBinder();
    @Test
    void parseFromCellText() {
        assertThrows(RuntimeException.class, () -> binder.parseFromCellText("any format", "   "));
    }
}