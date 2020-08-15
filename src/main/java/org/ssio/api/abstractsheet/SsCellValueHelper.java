package org.ssio.api.abstractsheet;

import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

public class SsCellValueHelper {

    public static <BEAN> SsCellValueJavaType resolveJavaTypeOfPropertyOrThrow(BEAN bean, String propName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(bean, propName);
        SsCellValueJavaType javaType = SsCellValueJavaType.fromRealType(pd.getPropertyType());
        String nonSupportMsg = "Unsupported real java type to write to an cell. The type is " + pd.getPropertyType().getName();
        if (javaType == null) {
            throw new IllegalStateException(nonSupportMsg);
        }
        return javaType;
    }
}