package ua.com.as.util.enums;

import java.util.regex.Pattern;

/**
 * EnumPatternRegex is list of pattern regex.
 */
public enum EnumPatternRegex {
    /**
     * NUMBER_REGEX is pattern for integer.
     */
    NUMBER_REGEX("\\d*"),
    /**
     * FLOAT_DOUBLE_REGEX is pattern for float and double.
     */
    FLOAT_DOUBLE_REGEX("[+-]?(\\\\d+\\\\.)?\\\\d+"),
    /**
     * STRING_REGEX is pattern for string.
     */
    STRING_REGEX("\\w*"),
    /**
     * CHAR_REGEX is pattern for character.
     */
    CHAR_REGEX(".");

    private Pattern pattern;

    EnumPatternRegex(String patternRegexText) {
        this.pattern = Pattern.compile(patternRegexText);
    }

    /**
     * @return pattern.
     */
    public Pattern getPattern() {
        return pattern;
    }
}
