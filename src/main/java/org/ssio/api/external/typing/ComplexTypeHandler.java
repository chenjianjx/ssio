package org.ssio.api.external.typing;

/**
 * Convert complex types to simple types so that they can be handled.
 * <p>
 * Note:
 * * Please make the implementation classes "light-weighted", which means initialisation should be very quick.
 * * Please make the class stateless.   Ssio MIGHT use only a single instance of a property for all rows
 *
 * @param <C> The complex type
 * @param <S> The simple type
 */
public interface ComplexTypeHandler<C, S> {

    /**
     * The complex type will be reduced to this simple type, so that it can be handled by ssio
     *
     * @return
     */
    Class<S> getTargetSimpleType();


    /**
     * Convert the complex value to a simple-typed value so that it can be saved onto a sheet. Make an empty implementation if you don't are not doing save
     */
    S nonNullValueToSimple(C complexTypeValue);

    S nullValueToSimple();


    /**
     * Convert simple-typed value to complex-typed value, which is what you want in your javabeans. Make an empty implementation if you don't are not doing parse
     *
     * @return
     */
    C fromNonNullSimpleTypeValue(S simpleTypeValue);

    C fromNullSimpleTypeValue();


    /**
     * Only used by the related annotation, which doesn't accept null as the default value
     */
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
