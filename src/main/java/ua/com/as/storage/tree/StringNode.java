package ua.com.as.storage.tree;

/**
 * <p>Describes single <code>StringNode</code>.
 */
class StringNode extends Node {
    private String pathPart;

    /**
     * <p>Constructor.
     *
     * @param pathPart argument path part.
     */
    StringNode(String pathPart) {
        this.pathPart = pathPart;
    }

    /**
     * <p>Getter.
     *
     * @return path part of current string node.
     */
    String getPathPart() {
        return pathPart;
    }
}
