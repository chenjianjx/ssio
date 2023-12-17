package org.ssio.spi.interfaces.model.write;

import java.io.IOException;
import java.io.OutputStream;

public interface WritableWorkbook {

    WritableSheet createNewSheet();

    void write(OutputStream outputTarget) throws IOException;
}
