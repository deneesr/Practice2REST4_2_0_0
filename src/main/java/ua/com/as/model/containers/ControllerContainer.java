package ua.com.as.model.containers;

import java.util.List;

/**
 * <p>Class for storing all information about <code>ControllerContainer</code>.
 */
public class ControllerContainer {
    private String fullClassName;
    private List<QueryContainer> queryContainers;

    /**
     * <p>Constructor which specifies fields <code>fullClassName</code> and <code>queryContainers</code>.
     *
     * @param fullClassName   field <code>fullClassName</code> in <code>ControllerContainer</code>.
     * @param queryContainers field <code>queryContainers</code> in <code>ControllerContainer</code>.
     */
    public ControllerContainer(String fullClassName, List<QueryContainer> queryContainers) {
        this.fullClassName = fullClassName;
        this.queryContainers = queryContainers;
    }

    /**
     * <p>Getter.
     *
     * @return value of field <code>fullClassName</code> in <code>ControllerContainer</code>.
     */
    public String getFullClassName() {
        return fullClassName;
    }

    /**
     * <p>Getter.
     *
     * @return value of field <code>queryContainers</code> in <code>ControllerContainer</code>.
     */
    public List<QueryContainer> getQueryContainers() {
        return queryContainers;
    }
}
