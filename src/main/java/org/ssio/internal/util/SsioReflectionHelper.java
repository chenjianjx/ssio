package org.ssio.internal.util;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenjianjx
 */
public class SsioReflectionHelper {

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
        try {
//            Constructor constructor = clazz.getConstructor(new Class[0]);
//            if (constructor == null) {
//                return false;
//            }
//            return constructor.isAccessible();
        } catch (Exception e) {
            return false;
        }
        Constructor<T> constructor = ConstructorUtils.getAccessibleConstructor(clazz, new Class<?>[0]);
        return constructor != null;

    }

    public static Class<Enum<?>> getPropertyEnumClassIfEnum(Object bean, String propName) {
        try {
            PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(bean, propName);
            Class<?> propertyType = pd.getPropertyType();
            if (propertyType.isEnum()) {
                return (Class<Enum<?>>) propertyType;
            } else {
                return null;
            }

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * get property value through getter methods. A runtime exception will be
     * thrown if no getter found
     *
     * @return
     * @throws RuntimeException
     */
    public static Object getProperty(Object object, String propName) throws RuntimeException {
        if (object == null) {
            throw new IllegalArgumentException("The object cannot be null");
        }
        if (propName == null) {
            throw new IllegalArgumentException("The propName cannot be null");
        }

        Class<?> clazz = object.getClass();
        Method getter = findGetterByPropName(clazz, propName);
        if (getter == null) {
            String err = MessageFormat.format("Class {0} has no getter method for property \"{1}\"", clazz, propName);
            throw new IllegalArgumentException(err);
        }
        return invokeGetter(getter, object);
    }

    /**
     * find a setter by a properti's name and type
     */
    public static Method findSetterByPropNameAndType(Class<?> objClass, String propName, Class<?> propClass) {
        if (objClass == null) {
            throw new IllegalArgumentException("The objClass cannot be null");
        }
        if (propName == null) {
            throw new IllegalArgumentException("The propName cannot be null");
        }
        if (propClass == null) {
            throw new IllegalArgumentException("The propClass cannot be null");
        }

        try {
            return objClass.getMethod("set" + StringUtils.capitalize(propName), new Class<?>[]{propClass});
        } catch (SecurityException e) {
            throw new IllegalStateException(e);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    /**
     * a property may have several setters, each of which take a new parameter
     * type
     */
    public static List<Method> findSettersByPropName(Class<?> objClass, String propName) {
        if (objClass == null) {
            throw new IllegalArgumentException("The objClass cannot be null");
        }
        if (propName == null) {
            throw new IllegalArgumentException("The propName cannot be null");
        }

        List<Method> setters = new ArrayList<Method>();
        Method[] methodArray = objClass.getMethods();
        if (methodArray == null) {
            return setters;
        }
        for (Method method : methodArray) {
            if (method.getName().equals("set" + StringUtils.capitalize(propName))
                    && (method.getParameterTypes() != null && method.getParameterTypes().length == 1)) {
                setters.add(method);
            }
        }
        return setters;
    }

    private static Object invokeGetter(Method getter, Object object) {
        try {
            getter.setAccessible(true);
            return getter.invoke(object, new Object[0]);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * invoke a setter method
     */
    public static void invokeSetter(Method setter, Object object, Object propValue) {
        if (setter == null) {
            throw new IllegalArgumentException("The setter method cannot be null");
        }

        if (object == null) {
            throw new IllegalArgumentException("The object cannot be null");
        }

        try {
            setter.setAccessible(true);
            setter.invoke(object, new Object[]{propValue});
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * find a getter
     *
     * @param clazz
     * @param propName
     * @return
     */
    private static Method findGetterByPropName(Class<?> clazz, String propName) {
        Method get = findGetLiterally(clazz, propName);
        if (get != null) {
            return get;
        }
        Method is = findIsLiterallyForBoolean(clazz, propName);
        if (is != null) {
            return is;
        }
        return null;
    }

    /**
     * find a getter method for primitive boolean
     *
     * @param clazz
     * @param propName
     * @return
     */
    static Method findIsLiterallyForBoolean(Class<?> clazz, String propName) {
        try {
            Method is = clazz.getMethod("is" + StringUtils.capitalize(propName), new Class<?>[0]);
            if (is.getReturnType().equals(boolean.class)) {
                return is;
            }
            return null;
        } catch (SecurityException e) {
            throw new IllegalStateException(e);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    /**
     * find a getter method which starts "get"
     *
     * @param clazz
     * @param propName
     * @return
     */
    static Method findGetLiterally(Class<?> clazz, String propName) {
        try {
            return clazz.getMethod("get" + StringUtils.capitalize(propName), new Class<?>[0]);
        } catch (SecurityException e) {
            throw new IllegalStateException(e);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }


    public static String extractPropertyName(Method shouldBeGetterOrSetter) {
        return extractPropertyNameAndType(shouldBeGetterOrSetter).getLeft();
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
