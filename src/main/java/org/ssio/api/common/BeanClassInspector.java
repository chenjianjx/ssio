package org.ssio.api.common;

import org.apache.commons.beanutils.PropertyUtils;
import org.ssio.api.common.annotation.SsColumn;
import org.ssio.api.common.mapping.PropAndColumn;
import org.ssio.internal.temp.SepStringHelper;
import org.ssio.internal.util.SsioReflectionHelper;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BeanClassInspector {

    /**
     * inspect the bean class to find mappings for beans2sheet. Also, do validation
     *
     * @param beanClass
     * @param errors
     * @return
     */
    public List<PropAndColumn> getMappingsForBeans2Sheet(Class<?> beanClass, List<String> errors) {
        List<Field> fields = SsioReflectionHelper.getDeclaredFieldsFromClassAndAncestors(beanClass).stream().filter(f -> f.isAnnotationPresent(SsColumn.class)).collect(Collectors.toList());
        List<Method> methods = Arrays.stream(beanClass.getMethods()).filter(m -> m.isAnnotationPresent(SsColumn.class)).collect(Collectors.toList());
        List<Object> fieldsAndMethods = new ArrayList<>();
        fieldsAndMethods.addAll(fields);
        fieldsAndMethods.addAll(methods);


        //there should be at least one member
        if (fieldsAndMethods.isEmpty()) {
            errors.add(String.format("There should be at least one @%s annotation applied on the fields or getters/setters of the bean class (or its ancestors)", SsColumn.class.getSimpleName()));
        }

        PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(beanClass);
        List<PropAndColumn> pacList = new ArrayList<>();

        for (Object o : fieldsAndMethods) {
            String propName;
            SsColumn annotation;
            if (o instanceof Field) {
                propName = ((Field) o).getName();
                annotation = ((Field) o).getAnnotation(SsColumn.class);
            } else {
                Method method = (Method) o;
                propName = SsioReflectionHelper.extractPropertyName(method);
                if (propName == null) {
                    errors.add(String.format("Method '%s' is not a getter/setter method and can't be mapped to a column", method.getName()));
                    continue;
                }

                annotation = (method).getAnnotation(SsColumn.class);
            }

            //check property
            if (!hasGetterMethodForProp(propertyDescriptors, propName)) {
                errors.add(String.format("'%s' is not a readable property. Does it have a public getter method? ", propName));
                continue;
            }

            //check annotation
            if (annotation.index() < 0) {
                errors.add("Invalid column annotation:  " + desc(annotation) + " .  The index should be provided and non-negative.");
                continue;
            }

            //build a pac
            PropAndColumn pac = new PropAndColumn();
            pac.setPropName(propName);

            String columnName = annotation.name();
            if (columnName.equals(SsColumn.NAME_UNKNOWN)) {
                columnName = SepStringHelper.camelCaseToCapitalizedWords(propName);
            }

            pac.setColumnName(columnName);
            pac.setColumnIndex(annotation.index());

            pacList.add(pac);

        }

        //check collision among the members
        List<Integer> duplicateIndexes =
                pacList.stream().collect(Collectors.groupingBy(PropAndColumn::getColumnIndex))
                        .entrySet()
                        .stream()
                        .filter(e -> e.getValue().size() > 1)
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());
        if (duplicateIndexes.size() > 0) {
            errors.add("There are duplicated column indexes in the bean class (and its ancestors). The indexes are  " + duplicateIndexes);
        }

        //sort before return
        pacList = pacList.stream().sorted(Comparator.comparing(PropAndColumn::getColumnIndex)).collect(Collectors.toList());
        return pacList;
    }


    private boolean hasGetterMethodForProp(PropertyDescriptor[] propertyDescriptors, String propName) {
        return Arrays.stream(propertyDescriptors).filter(pd -> pd.getName().equals(propName) && pd.getReadMethod() != null).findAny().isPresent();
    }

    private String desc(SsColumn annotation) {
        return String.format("[index = %s, name = %s]", annotation.index(), annotation.name());
    }
}
