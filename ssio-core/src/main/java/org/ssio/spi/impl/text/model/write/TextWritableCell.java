package org.ssio.spi.impl.text.model.write;

import org.ssio.spi.interfaces.model.write.WritableCell;

/**
 * A text cell has a text content, not binary
 */
public interface TextWritableCell extends WritableCell {
    void setContent(String content);
}
