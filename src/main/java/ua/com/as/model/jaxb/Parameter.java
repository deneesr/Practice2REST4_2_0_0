package ua.com.as.model.jaxb;

import ua.com.as.util.adapters.ClassTypeAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Query object that has to be converted from xml file.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Parameter {

    @XmlAttribute(name = "paramtype")
    private ParameterType parameterType;

    @XmlElement
    private String name;

    @XmlElement
    @XmlJavaTypeAdapter(ClassTypeAdapter.class)
    private Class<?> type;

    /**
     * Enum for specifying parameter type.
     */
    @XmlType
    @XmlEnum
    public enum ParameterType {
        /**
         * If this parameter is query parameter.
         */
        @XmlEnumValue("query")
        QUERY_PARAMETER,
        /**
         * If this parameter is path parameter.
         */
        @XmlEnumValue("path")
        PATH_PARAMETER
    }

    /**
     * Getter.
     *
     * @return name of this parameter in client's controller method to invoke.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter.
     *
     * @param name name of this parameter in client's controller method to invoke.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter.
     *
     * @return type of this parameter in client's controller method to invoke.
     */
    public Class<?> getType() {
        return type;
    }

    /**
     * Setter.
     *
     * @param type type of this parameter in client's controller method to invoke.
     */
    public void setType(Class<?> type) {
        this.type = type;
    }

    /**
     * Getter.
     *
     * @return parameter type (query ot path) of this parameter in client's controller method to invoke.
     */
    public ParameterType getParameterType() {
        return parameterType;
    }

    /**
     * Setter.
     *
     * @param parameterType parameter type (query ot path) of this parameter in client's controller method to invoke.
     */
    public void setParameterType(ParameterType parameterType) {
        this.parameterType = parameterType;
    }
}
