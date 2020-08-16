package org.ssio.api.abstractsheet;

public interface SsCell {
    String readValueAsString();


    /**
     * read the value as a target java type
     * @throws RuntimeException if the cell value and the java type are not compatible with each other
     */
    Object readValueAsType(SsCellValueJavaType targetType, Class<Enum<?>> enumClassIfEnum) throws RuntimeException;
}
