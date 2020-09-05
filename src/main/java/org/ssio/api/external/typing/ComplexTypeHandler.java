package org.ssio.api.external.typing;

/**
 * Note:  please make the implementation classes "light-weighted", which means initialisation should be very quick.
 *
 * @param <C> The complex type
 * @param <S> The simplified type
 */
public interface ComplexTypeHandler<C, S> {

    /**
     * The complex type will be reduced to this simple type, so that it can be handled by ssio
     *
     * @return
     */
    Class<S> getTargetSimpleType();


    /**
     * Convert the complex value to a simple-typed value so that it can be saved onto a sheet. Make an empty implementation if you don't need to save
     *
     * @param complexTypeValue keep in mind this can be null
     * @return The type of the value must be consistent with the return type of {@link #getTargetSimpleType()}
     */
    S nonNullValueToSimple(C complexTypeValue);

    S nullValueToSimple();


    /**
     * Convert simple-typed value to the complex-typed value, which is what you want in your javabeans. Make an empty implementation if you don't need to parse
     *
     * @param simpleTypeValue keep in mind this can be null
     * @return
     */
    C fromNonNullSimpleTypeValue(S simpleTypeValue);

    C fromNullSimpleTypeValue();


    class NO_HANDLING implements ComplexTypeHandler<Object, Object> {


        @Override
        public Class<Object> getTargetSimpleType() {
            return null;
        }

        @Override
        public Object nonNullValueToSimple(Object complexTypeValue) {
            return null;
        }

        @Override
        public Object nullValueToSimple() {
            return null;
        }

        @Override
        public Object fromNonNullSimpleTypeValue(Object simpleTypeValue) {
            return null;
        }

        @Override
        public Object fromNullSimpleTypeValue() {
            return null;
        }
    }
}
