package org.ssio.util.lang;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenjianjx
 */
public class SsioReflectionUtils {

    /**
     * create instance without checked exceptions
     */
    public static <T> T createInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> boolean hasAccessibleZeroArgumentConstructor(Class<T> clazz) {
        Constructor<T> constructor = ConstructorUtils.getAccessibleConstructor(clazz, new Class<?>[0]);
        return constructor != null;

    }


    /**
     * Extract property name and type from a supposed-to-be getter/setter method
     *
     * @param shouldBeGetterOrSetter should be, but doesn't have to be. If it is, null will be returned
     * @return won't be null, but the left and the right can both be null
     */

    public static Pair<String, Class<?>> extractPropertyNameAndType(Method shouldBeGetterOrSetter) {
        String methodName = shouldBeGetterOrSetter.getName();

        String propName = null;
        Class<?> propType = null;
        if (methodName.startsWith("is") && shouldBeGetterOrSetter.getParameterCount() == 0 && boolean.class.equals(shouldBeGetterOrSetter.getReturnType())) {
            propName = methodName.substring("is".length());
            propType = shouldBeGetterOrSetter.getReturnType();
        }

        if (methodName.startsWith("get") && shouldBeGetterOrSetter.getParameterCount() == 0
                && !void.class.equals(shouldBeGetterOrSetter.getReturnType())
                && !boolean.class.equals(shouldBeGetterOrSetter.getReturnType())
        ) {
            propName = methodName.substring("get".length());
            propType = shouldBeGetterOrSetter.getReturnType();
        }

        if (methodName.startsWith("set") && shouldBeGetterOrSetter.getParameterCount() == 1) {
            propName = methodName.substring("set".length());
            propType = shouldBeGetterOrSetter.getParameterTypes()[0];
        }

        if (propName == null) {
            return Pair.of(null, null);
        }

        return Pair.of(StringUtils.uncapitalize(propName), propType);
    }


    /**
     * copied from https://stackoverflow.com/a/3567901/301447
     *
     * @param clazz
     * @return
     */
    public static List<Field> getDeclaredFieldsFromClassAndAncestors(Class<?> clazz) {
        List<Field> result = new ArrayList<Field>();

        Class<?> i = clazz;
        while (i != null && i != Object.class) {
            for (Field field : i.getDeclaredFields()) {
                if (!field.isSynthetic()) {
                    result.add(field);
                }
            }
            i = i.getSuperclass();
        }

        return result;
    }
}
