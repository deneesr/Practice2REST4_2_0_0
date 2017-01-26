package ua.com.as.storage.tree;

import ua.com.as.model.containers.ResponseContainer;

import java.util.List;

/**
 * <p>Describes single Node of <code>UrlTree</code>.
 */
abstract class Node {
    private ResponseContainer responseContainer;

    private List<StringNode> stringChildren;
    private PatternNode numberChild;
    private PatternNode stringChild;

    /**
     * <p>Getter.
     *
     * @return response container of current node.
     */
    ResponseContainer getResponseContainer() {
        return responseContainer;
    }

    /**
     * <p>Setter.
     *
     * @param responseContainer set response container to current node.
     */
    void setResponseContainer(ResponseContainer responseContainer) {
        this.responseContainer = responseContainer;
    }

    /**
     * <p>Getter.
     *
     * @return string children of current node.
     */
    List<StringNode> getStringChildren() {
        return stringChildren;
    }

    /**
     * <p>Setter.
     *
     * @param stringChildren set string children to current node.
     */
    void setStringChildren(List<StringNode> stringChildren) {
        this.stringChildren = stringChildren;
    }

    /**
     * <p>Getter.
     *
     * @return number child of current node.
     */
    PatternNode getNumberChild() {
        return numberChild;
    }

    /**
     * <p>Setter.
     *
     * @param numberChild set number child to current node.
     */
    void setNumberChild(PatternNode numberChild) {
        this.numberChild = numberChild;
    }

    /**
     * <p>Getter.
     *
     * @return string child of current node.
     */
    PatternNode getStringChild() {
        return stringChild;
    }

    /**
     * <p>Setter.
     *
     * @param stringChild set string child to current node.
     */
    void setStringChild(PatternNode stringChild) {
        this.stringChild = stringChild;
    }
}
