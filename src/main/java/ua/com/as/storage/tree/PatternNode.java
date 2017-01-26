package ua.com.as.storage.tree;

import java.util.regex.Pattern;

/**
 * <p>Describes single <code>PatternNode</code>.
 */
class PatternNode extends Node {
    private Pattern pathPattern;

    /**
     * <p>Constructor.
     *
     * @param pathPattern argument path pattern.
     */
    PatternNode(Pattern pathPattern) {
        this.pathPattern = pathPattern;
    }

    /**
     * <p>Getter.
     *
     * @return pattern part of current pattern node.
     */
    Pattern getPathPattern() {
        return pathPattern;
    }
}
