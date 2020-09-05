package org.ssio.api.internal.common;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.ssio.api.external.annotation.SsColumn;
import org.ssio.api.external.parse.PropFromColumnMappingMode;
import org.ssio.api.external.typing.ComplexTypeHandler;
import org.ssio.api.external.typing.SimpleTypeEnum;
import org.ssio.util.lang.SsioReflectionUtils;
import org.ssio.util.lang.SsioStringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.ssio.util.lang.SsioReflectionUtils.createInstance;

/**
 * Basically, the class process all {@link SsColumn} annotations in a class
 */
public class BeanClassInspector {


    /**
     * Inspect the bean class to find mappings. Also, do validation
     *
     * @param beanClass
     * @param errors
     * @return
     */
    public List<PropAndColumn> getPropAndColumnMappingsForSaveMode(Class<?> beanClass, List<String> errors) {
        return this.getPropAndColumnMappings(beanClass, SsioMode.SAVE, null, errors);
    }

    /**
     * Inspect the bean class to find mappings. Also, do validation
     */
    public List<PropAndColumn> getPropAndColumnMappingsForParseMode(Class<?> beanClass, PropFromColumnMappingMode propFromColumnMappingMode, List<String> errors) {
        return this.getPropAndColumnMappings(beanClass, SsioMode.PARSE, propFromColumnMappingMode, errors);
    }


    List<PropAndColumn> getPropAndColumnMappings(Class<?> beanClass, SsioMode ssioMode,
                                                 PropFromColumnMappingMode propFromColumnMappingModeIfParse,
                                                 List<String> errors) {
        //do some parameter check first
        if (ssioMode != SsioMode.SAVE && ssioMode != SsioMode.PARSE) {
            throw new IllegalArgumentException("Unsupported mode: " + ssioMode);
        }


        List<Field> fields = SsioReflectionUtils.getDeclaredFieldsFromClassAndAncestors(beanClass).stream().filter(f -> f.isAnnotationPresent(SsColumn.class)).collect(Collectors.toList());
        Method[] methodArray = beanClass.getMethods();
        if (methodArray == null) {
            methodArray = new Method[0];
        }
        List<Method> methods = Arrays.stream(methodArray).filter(m -> m.isAnnotationPresent(SsColumn.class)).collect(Collectors.toList());
        List<Object> fieldsAndMethods = new ArrayList<>();
        fieldsAndMethods.addAll(fields);
        fieldsAndMethods.addAll(methods);


        //there should be at least one annotated property
        if (fieldsAndMethods.isEmpty()) {
            errors.add(String.format("There should be at least one @%s annotation applied on the fields or getters/setters of the bean class (or its ancestors)", SsColumn.class.getSimpleName()));
        }

        PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(beanClass);
        List<PropAndColumn> pacList = new ArrayList<>();


        for (Object o : fieldsAndMethods) {
            String propName;
            Class<?> propType;
            SsColumn annotation;
            if (o instanceof Field) {
                Field field = (Field) o;
                propName = field.getName();
                propType = field.getType();
                annotation = field.getAnnotation(SsColumn.class);
            } else {
                Method method = (Method) o;
                Pair<String, Class<?>> propNameAndType = SsioReflectionUtils.extractPropertyNameAndType(method);
                propName = propNameAndType.getLeft();
                propType = propNameAndType.getRight();
                if (propName == null) {
                    errors.add(String.format("Method '%s' is not a getter/setter method and can't be mapped to a column", method.getName()));
                    continue;
                }
                annotation = method.getAnnotation(SsColumn.class);
            }

            //check property type
            Class<? extends ComplexTypeHandler> typeHandler = annotation.typeHandler();
            if (typeHandler == ComplexTypeHandler.NO_HANDLING.class) {
                typeHandler = null;
            }
            Class<?> propTypeForSheet = typeHandler == null ? propType : createInstance(typeHandler).getTargetSimpleType();
            SimpleTypeEnum ssioSimpleTypeEnum = SimpleTypeEnum.fromRealType(propTypeForSheet);
            if (ssioSimpleTypeEnum == null) {
                String suggestion = typeHandler == null ? "Please provide a typeHandler." : "Your typeHandler " + typeHandler.getName() + " should target a supported simple type.";
                errors.add(String.format("The final type of property '%s', which is %s, is not supported. The list of supported types are defined in %s . %s", propName, propTypeForSheet.getName(), SimpleTypeEnum.class.getName(), suggestion));
                continue;
            }


            //check property validity
            if (ssioMode == SsioMode.SAVE && !hasGetterMethodForProp(propertyDescriptors, propName)) {
                errors.add(String.format("'%s' is not a readable property. Does it have a public getter method? ", propName));
                continue;
            }

            if (ssioMode == SsioMode.PARSE && !hasSetterMethodForProp(propertyDescriptors, propName)) {
                errors.add(String.format("'%s' is not a writable property. Does it have a public setter method? ", propName));
                continue;
            }

            //check annotation
            if (ssioMode == SsioMode.SAVE) {
                boolean valid = validateAnnotationIndexNotNegative(errors, annotation);
                if (!valid) {
                    continue;
                }
            }
            if (ssioMode == SsioMode.PARSE) {
                switch (propFromColumnMappingModeIfParse) {
                    case BY_NAME: {
                        //nothing to do
                        break;
                    }
                    case BY_INDEX: {
                        boolean valid = validateAnnotationIndexNotNegative(errors, annotation);
                        if (!valid) {
                            continue;
                        }
                        break;
                    }
                    default:
                        throw new IllegalArgumentException("Unsupported propFromColumnMappingMode: " + propFromColumnMappingModeIfParse);
                }
            }


            ////check format in annotation
            String format = StringUtils.trimToNull(annotation.format());
            if (ssioSimpleTypeEnum.isDateRelated()) {
                if (format == null || format.equals(SsColumn.FORMAT_UNKNOWN)) {
                    format = ssioSimpleTypeEnum.getDefaultDateFormat();
                } else {
                    try {
                        new SimpleDateFormat(format);
                    } catch (IllegalArgumentException e) {
                        errors.add(String.format("Date format for property '%s', which is '%s', is an invalid date format. A date format must be accepted by java.text.SimpleDateFormat", propName, format));
                        continue;
                    }
                }
            } else {
                format = null;
            }


            //build a pac
            String columnName = StringUtils.trimToNull(annotation.name());
            if (columnName == null || columnName.equals(SsColumn.NAME_UNKNOWN)) { //falls back to "foobar => Foo Bar" in both save and parse modes
                columnName = SsioStringUtils.camelCaseToCapitalizedWords(propName);
            }

            PropAndColumn pac = new PropAndColumn();
            pac.setPropName(propName);
            pac.setColumnName(columnName);
            pac.setColumnIndex(annotation.index());
            pac.setFormat(format);
            pac.setTypeHandlerClass(typeHandler);
            pac.setSimpleTypeEnum(ssioSimpleTypeEnum);
            pac.setEnumClassIfEnum(propTypeForSheet.isEnum() ? ((Class<Enum<?>>) propTypeForSheet) : null);

            pacList.add(pac);

        }

        if (ssioMode == SsioMode.SAVE) {
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
        }


        //sort before return
        pacList = pacList.stream().sorted(Comparator.comparing(PropAndColumn::getColumnIndex)).collect(Collectors.toList());
        return pacList;
    }

    /**
     * @param errors
     * @param annotation
     * @return true = valid, false = invalid
     */
    private boolean validateAnnotationIndexNotNegative(List<String> errors, SsColumn annotation) {
        int index = annotation.index();
        if (index < 0) {
            errors.add("Invalid column annotation:  " + desc(annotation) + " .  The index should be provided and non-negative.");
            return false;
        }
        return true;
    }

    private boolean hasSetterMethodForProp(PropertyDescriptor[] propertyDescriptors, String propName) {
        return Arrays.stream(propertyDescriptors).filter(pd -> pd.getName().equals(propName) && pd.getWriteMethod() != null).findAny().isPresent();
    }


    private boolean hasGetterMethodForProp(PropertyDescriptor[] propertyDescriptors, String propName) {
        return Arrays.stream(propertyDescriptors).filter(pd -> pd.getName().equals(propName) && pd.getReadMethod() != null).findAny().isPresent();
    }


    private String desc(SsColumn annotation) {
        return String.format("[index = %s, name = %s]", annotation.index(), annotation.name());
    }

}
