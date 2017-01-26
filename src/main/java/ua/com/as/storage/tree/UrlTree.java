package ua.com.as.storage.tree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.as.model.containers.ParameterContainer;
import ua.com.as.model.containers.RequestContainer;
import ua.com.as.model.containers.ResponseContainer;
import ua.com.as.util.enums.EnumPatternRegex;
import ua.com.as.util.exception.ConfigurationException;
import ua.com.as.util.exception.RequestException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>Class for storing <code>ResponseContainers</code>.
 */
public class UrlTree {
    private static final Logger LOG = LoggerFactory.getLogger(UrlTree.class);
    private Node root = new StringNode("");

    /**
     * <p>Main method for adding <code>ResponseContainer</code> to this <code>UrlTree</code>
     * by specified <code>pathParts</code>.
     *
     * @param pathParts         parts of url pattern which client specified in xml file of with annotations.
     * @param responseContainer <code>ResponseContainer</code> object to add in <code>UrlTree</code>.
     */
    public void add(String[] pathParts, ResponseContainer responseContainer) {
        int level = 0;
        int patternLevel = 0;
        addTo(root, pathParts, level, patternLevel, responseContainer);
    }

    /**
     * <p>Main method for finding <code>ResponseContainer</code> in this <code>UrlTree</code>.
     *
     * @param requestContainer    all information about client's request.
     * @param pathParameterValues values of all non static parameters in url path.
     * @return required <code>ResponseContainer</code> object.
     */
    public ResponseContainer find(RequestContainer requestContainer, List<String> pathParameterValues) {
        AtomicInteger level = new AtomicInteger(0);

        return findIn(root, requestContainer, level, pathParameterValues);
    }

    /**
     * <p>Adds required <code>ResponseContainer</code> to next level node.
     *
     * @param currentNode       node where <code>ResponseContainer</code> should be added.
     * @param pathPatternParts  url pattern parts to add in node.
     * @param level             level of immersion in a tree.
     * @param patternLevel      level of current pattern parameter position.
     * @param responseContainer <code>ResponseContainer</code> to add.
     */
    private void addTo(Node currentNode,
                       String[] pathPatternParts,
                       int level,
                       int patternLevel,
                       ResponseContainer responseContainer) {

        if (pathPatternParts.length == level) {
            currentNode.setResponseContainer(responseContainer);
            return;
        }

        Node nextNode = getNextNode(currentNode, pathPatternParts[level], patternLevel,
                level, responseContainer);
        if (isPattern(pathPatternParts[level])) {
            ++patternLevel;
        }
        ++level;

        addTo(nextNode, pathPatternParts, level, patternLevel, responseContainer);
    }

    /**
     * @param parentNode        node where <code>ResponseContainer</code> should be added.
     * @param pathPatternPart   url pattern part to add in parent node.
     * @param patternLevel      level of current pattern parameter position.
     * @param level             current level of the path.
     * @param responseContainer <code>ResponseContainer</code> to add.
     * @return next level node.
     */
    private Node getNextNode(Node parentNode,
                             String pathPatternPart,
                             int patternLevel,
                             int level,
                             ResponseContainer responseContainer) {

        if (isPattern(pathPatternPart)) {
            return getNextPatternNode(parentNode, pathPatternPart, patternLevel, level, responseContainer);
        } else {
            return getNextStringNode(parentNode, pathPatternPart);
        }
    }

    /**
     * <p>Shows whether current url pattern part is pattern or not.
     *
     * @param pathPatternPart part of url pattern to check.
     * @return true if current url pattern part is regex pattern and false otherwise.
     */
    private boolean isPattern(String pathPatternPart) {
        return pathPatternPart.startsWith("{") && pathPatternPart.endsWith("}");
    }

    /**
     * <p>Find next level string node.
     *
     * @param parentNode      node where <code>ResponseContainer</code> should be added.
     * @param pathPatternPart part of url pattern to check.
     * @return next level string node.
     */
    private Node getNextStringNode(Node parentNode, String pathPatternPart) {
        List<StringNode> stringChildren = parentNode.getStringChildren();
        if (stringChildren == null) {
            stringChildren = new ArrayList<>();
            parentNode.setStringChildren(stringChildren);
        }

        for (StringNode stringNode : stringChildren) {
            if (stringNode.getPathPart().equals(pathPatternPart)) {
                return stringNode;
            }
        }

        StringNode nextStringNode = new StringNode(pathPatternPart);
        stringChildren.add(nextStringNode);
        return nextStringNode;
    }

    /**
     * <p>Find next level pattern node.
     *
     * @param parentNode        node where <code>ResponseContainer</code> should be added.
     * @param pathPatternPart   part of url pattern to check.
     * @param patternLevel      level of current pattern parameter position.
     * @param level             current level of the path.
     * @param responseContainer <code>ResponseContainer</code> to add.
     * @return next level pattern node.
     */
    private Node getNextPatternNode(Node parentNode,
                                    String pathPatternPart,
                                    int patternLevel,
                                    int level,
                                    ResponseContainer responseContainer) {

        for (ParameterContainer parameterContainer : responseContainer.getParameters()) {
            if (parameterContainer.getPosition() == patternLevel) {
                if (parameterContainer.getType().equals(String.class)) {
                    return getNextStringPatternChild(parentNode, parameterContainer, pathPatternPart,
                            level, responseContainer);
                } else {
                    return getNextNumberPatternChild(parentNode, parameterContainer, pathPatternPart,
                            level, responseContainer);
                }
            }
        }

        throw new ConfigurationException("Unexpected type of parameter or error in url pattern!");
    }

    /**
     * <p>Find next level pattern String node.
     *
     * @param parentNode         node where <code>ResponseContainer</code> should be added.
     * @param parameterContainer parameter to check which node has to be added to <code>ParentNode</code>.
     * @param pathPatternPart    part of url pattern to check.
     * @param level              current level of the path.
     * @param responseContainer  <code>ResponseContainer</code> to add.
     * @return next level pattern node with <code>String</code> pattern.
     */
    private Node getNextStringPatternChild(Node parentNode,
                                           ParameterContainer parameterContainer,
                                           String pathPatternPart, int level, ResponseContainer responseContainer) {

        if (parentNode.getStringChild() != null
                && level == responseContainer.getQtyElementsInPath()
                && parentNode.getStringChild().getResponseContainer() != null) {
            LOG.error("You try to use two similar url! (with string parameter)");
            throw new ConfigurationException("You try to use two similar url, check @Mapping path in Controller: " +
                    parentNode.getStringChild().getResponseContainer().getFullClassName() + " Method: " +
                    parentNode.getStringChild().getResponseContainer().getMethodName()
                    + " and second @Mapping path in Controller: " + responseContainer.getFullClassName()
                    + " Method: " + responseContainer.getMethodName());
        }

        if (parameterContainer.getName().equals(pathPatternPart.substring(1, pathPatternPart.length() - 1))) {
            if (parentNode.getStringChild() != null) {
                return parentNode.getStringChild();
            }
            PatternNode stringPatternNode = new PatternNode(EnumPatternRegex.STRING_REGEX.getPattern());
            parentNode.setStringChild(stringPatternNode);
            return stringPatternNode;
        }

        throw new ConfigurationException("\n No such parameter in Path of Controller!");
    }

    /**
     * <p>Find next level pattern Number node.
     *
     * @param parentNode         node where <code>ResponseContainer</code> should be added.
     * @param parameterContainer parameter to check which node has to be added to <code>ParentNode</code>.
     * @param pathPatternPart    part of url pattern to check.
     * @param level              current level of the path.
     * @param responseContainer  <code>ResponseContainer</code> to add.
     * @return next level pattern node with <code>Number</code> pattern.
     */
    private Node getNextNumberPatternChild(Node parentNode,
                                           ParameterContainer parameterContainer,
                                           String pathPatternPart, int level, ResponseContainer responseContainer) {

        if (parentNode.getNumberChild() != null
                && level == responseContainer.getQtyElementsInPath()
                && parentNode.getNumberChild().getResponseContainer() != null) {
            LOG.error("You try to use two similar url! (with integer parameter)");
            throw new ConfigurationException("You try to use two similar url, check @Mapping path in Controller: " +
                    parentNode.getNumberChild().getResponseContainer().getFullClassName() + " Method: " +
                    parentNode.getNumberChild().getResponseContainer().getMethodName()
                    + " and second @Mapping path in Controller: " + responseContainer.getFullClassName()
                    + " Method: " + responseContainer.getMethodName());
        }

        if (parameterContainer.getName().equals(pathPatternPart.substring(1, pathPatternPart.length() - 1))) {
            if (parentNode.getNumberChild() != null) {
                return parentNode.getNumberChild();
            }
            PatternNode numberPatternNode = new PatternNode(EnumPatternRegex.NUMBER_REGEX.getPattern());
            parentNode.setNumberChild(numberPatternNode);
            return numberPatternNode;
        }

        throw new ConfigurationException("\n No such parameter in Path of Controller!");
    }

    /**
     * <p>Find <code>ResponseContainer</code> in current <code>targetNode</code> by specified
     * pathParts and level.
     *
     * @param targetNode          node where <code>ResponseContainer</code> will be found.
     * @param requestContainer    all information about client's request.
     * @param level               level of immersion in a tree.
     * @param pathParameterValues values of all non static parameters in url path.
     * @return required <code>ResponseContainer</code>.
     */
    private ResponseContainer findIn(Node targetNode,
                                     RequestContainer requestContainer,
                                     AtomicInteger level,
                                     List<String> pathParameterValues) {

        if (requestContainer.getPathParts().length == level.intValue()) {
            ResponseContainer responseContainer = targetNode.getResponseContainer();
            if (responseContainer == null && !pathParameterValues.isEmpty()) {
                LOG.error("Error, cannot find response container");
                throw new RequestException("Cannot find method with integer parameter for path: "
                        + requestContainer.getFullPath());
            } else if (responseContainer == null) {
                LOG.error("Error, cannot find response container");
                throw new RequestException("Cannot find method for path: " + requestContainer.getFullPath());
            }
            return responseContainer;
        }

        Node nextNode = findNextNode(targetNode, requestContainer, level, pathParameterValues);
        level.incrementAndGet();

        return findIn(nextNode, requestContainer, level, pathParameterValues);
    }

    /**
     * <p>Find next level node.
     *
     * @param targetNode       node where <code>ResponseContainer</code> will be found.
     * @param requestContainer all information about client's request.
     * @param level            level of immersion in a tree.
     * @param parameterValues  values of all non static parameters in url path.
     * @return next level node in current url tree with required value.
     */
    private Node findNextNode(Node targetNode,
                              RequestContainer requestContainer,
                              AtomicInteger level,
                              List<String> parameterValues) {

        String currentPathPart = requestContainer.getPathParts()[level.intValue()];

        Node resultNode = checkStringChildren(targetNode, currentPathPart);

        if (resultNode != null) {
            return resultNode;
        }

        resultNode = checkNumberPatternChild(targetNode, currentPathPart, parameterValues,
                requestContainer.getFullPath());

        if (resultNode != null) {
            return resultNode;
        }

        return checkStringPatternChild(targetNode, currentPathPart, parameterValues, requestContainer.getFullPath());
    }

    /**
     * <p>Checks if there are full equality of current path part with one of string child of
     * current node.
     *
     * @param targetNode      node where <code>ResponseContainer</code> will be found.
     * @param currentPathPart current level part of url specified in client's request.
     * @return required next level node or null if there are no full equality of current path part
     * with one of string child of current node.
     */
    private Node checkStringChildren(Node targetNode, String currentPathPart) {
        List<StringNode> stringChildren = targetNode.getStringChildren();
        if (stringChildren != null) {
            for (StringNode stringNode : stringChildren) {
                if (stringNode.getPathPart().equals(currentPathPart)) {
                    return stringNode;
                }
            }
        }
        return null;
    }

    /**
     * <p>Checks if <code>currentPathPart</code> is number and try to check pattern child
     * with number pattern of current <code>targetNode</code>.
     *
     * @param targetNode      node where <code>ResponseContainer</code> will be found.
     * @param currentPathPart current level part of url specified in client's request.
     * @param parameterValues values of all non static parameters in url path.
     * @param fullPath        url path of client's request.
     * @return next level node with number pattern or null.
     */
    private Node checkNumberPatternChild(Node targetNode,
                                         String currentPathPart,
                                         List<String> parameterValues,
                                         String fullPath) {
        if (isNumber(currentPathPart)) {
            PatternNode numberChild = targetNode.getNumberChild();
            if (numberChild == null) {
                throw new RequestException("Cannot find method with integer parameter, for path: " + fullPath);
            }

            parameterValues.add(currentPathPart);
            return numberChild;
        }
        return null;
    }

    /**
     * <p>Returns string pattern child of current target node.
     *
     * @param targetNode      node where <code>ResponseContainer</code> will be found.
     * @param currentPathPart current path part.
     * @param parameterValues values of all non static parameters in url path.
     * @param fullPath        url path of client's request.
     * @return next level node with string pattern or null.
     */
    private Node checkStringPatternChild(Node targetNode,
                                         String currentPathPart,
                                         List<String> parameterValues,
                                         String fullPath) {
        PatternNode stringChild = targetNode.getStringChild();
        if (stringChild == null) {
            LOG.error("No child with string pattern! For path: {}", fullPath);
            throw new RequestException("Cannot find method with String parameter, for path: " + fullPath);
        }

        parameterValues.add(currentPathPart);
        return stringChild;
    }

    /**
     * <p>Checks if current path part is number or not.
     *
     * @param currentPathPart part of url path.
     * @return true is current path part is number, false otherwise.
     */
    @SuppressWarnings("all")
    private boolean isNumber(String currentPathPart) {
        try {
            Long.parseLong(currentPathPart);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
