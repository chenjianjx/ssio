package org.ssio.api.common.typing;

public interface SsioComplexTypeHandler<T> {

    /**
     * The complex type will be reduced to this simple type, so that it can be handled by ssio
     *
     * @return
     */
    Class<?> getTargetSimpleType();

    /**
     * Reduce the original value to a simple-typed value so that it can be saved onto a sheet. Make an empty implementation if you don't need beans2Sheet
     *
     * @param originalValue can be null
     * @return The type of the value must be consistent with the return type of {@link #getTargetSimpleType()}
     */
    Object toSimpleTypeValue(T originalValue);


    /**
     * Convert simple-typed value to the complex-typed value, which is what you want in your javabeans. Make an empty implementation if you don't need sheet2beans
     *
     * @param simpleTypeValue can be null
     * @return
     */
    T fromSimpleTypeValue(Object simpleTypeValue);


    class NO_HANDLING implements SsioComplexTypeHandler<Object> {

        @Override
        public Class<?> getTargetSimpleType() {
            return null;
        }

        @Override
        public Object toSimpleTypeValue(Object originalValue) {
            return null;
        }

        @Override
        public Object fromSimpleTypeValue(Object simpleTypeValue) {
            return null;
        }
    }
}
