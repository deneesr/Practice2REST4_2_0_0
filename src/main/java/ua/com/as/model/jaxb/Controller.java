package ua.com.as.model.jaxb;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller object that has to be converted from xml file.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Controller {

    @XmlAttribute
    private String fullClassName;

    @XmlElement(name = "query")
    private List<Query> queries = new ArrayList<>();

    /**
     * default constructor for jaxb.
     */
    public Controller() {
    }

    /**
     * @param fullClassName Full Class Name.
     * @param queries       List of queries.
     */
    public Controller(String fullClassName, List<Query> queries) {
        this.fullClassName = fullClassName;
        this.queries = queries;
    }

    /**
     * Getter.
     *
     * @return fullClassName.
     */
    public String getFullClassName() {
        return fullClassName;
    }

    /**
     * Setter.
     *
     * @param fullClassName set new fullClassName.
     */
    public void setFullClassName(String fullClassName) {
        this.fullClassName = fullClassName;
    }

    /**
     * Getter.
     *
     * @return list of rest methods.
     */
    public List<Query> getQueries() {
        return queries;
    }

    /**
     * Setter.
     *
     * @param queries set new list.
     */
    public void setQueries(List<Query> queries) {
        this.queries = queries;
    }

    @Override
    public String toString() {
        return fullClassName;
    }
}
