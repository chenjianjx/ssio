package org.ssio.util.lang;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenjianjx@gmail.com
 */
public class SsioStringUtils {

    /**
     * firstName => First Name
     *
     * @param camelStr
     * @return
     */
    public static String camelCaseToCapitalizedWords(String camelStr) {
        if (StringUtils.isBlank(camelStr)) {
            return camelStr;
        }

        String[] wordArray = StringUtils.splitByCharacterTypeCamelCase(camelStr);
        if (wordArray == null || wordArray.length == 0) {
            return null;
        }

        List<String> capitalizedList = Arrays.stream(wordArray).map(w -> StringUtils.capitalize(w))
                .collect(Collectors.toList());
        return StringUtils.join(capitalizedList, " ");
    }

}
