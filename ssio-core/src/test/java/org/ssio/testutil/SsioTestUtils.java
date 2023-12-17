package org.ssio.testutil;

import java.util.Arrays;
import java.util.List;

public class SsioTestUtils {
    public static boolean havingErrorContains(List<String> errors, String... snippets) {
        for (String error : errors) {
            boolean containsAllSnippets = Arrays.stream(snippets).map(s -> error.contains(s)).reduce(Boolean::logicalAnd).get();
            if (containsAllSnippets) {
                return true;
            }
        }
        return false;
    }
}
