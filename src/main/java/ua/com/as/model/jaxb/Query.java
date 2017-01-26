package ua.com.as.model.jaxb;


import ua.com.as.util.adapters.HttpMethodAdapter;
import ua.com.as.util.adapters.ResponseTypeAdapter;
import ua.com.as.util.annotation.Mapping;
import ua.com.as.util.enums.HttpMethod;
import ua.com.as.util.enums.ResponseType;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Query object that need to convert from xml file.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Query {

    @XmlAttribute
    private String path;

    @XmlAttribute(name = "response_type")
    @XmlJavaTypeAdapter(ResponseTypeAdapter.class)
    private ResponseType responseType;


    @XmlAttribute(name = "http_methods")
    @XmlJavaTypeAdapter(HttpMethodAdapter.class)
    private List<HttpMethod> httpMethods;

    @XmlElement(name = "method_name")
    private String methodName;

    @XmlElement(name = "parameter")
    private List<Parameter> parameters = new ArrayList<>();


    /**
     * default constructor for jaxb.
     */
    public Query() {
    }

    /**
     * @param pathAnnotatedMethod Method with annotation Mapping.class.
     * @param parameters          List of parameters.
     */
    public Query(Method pathAnnotatedMethod, List<Parameter> parameters) {
        path = pathAnnotatedMethod.getAnnotation(Mapping.class).path();
        responseType = pathAnnotatedMethod.getAnnotation(Mapping.class).responseType();
        httpMethods = Arrays.asList(pathAnnotatedMethod.getAnnotation(Mapping.class).methods());
        methodName = pathAnnotatedMethod.getName();
        this.parameters = parameters;
    }

    /**
     * Getter.
     *
     * @return list of parameters in client's controller method to invoke.
     */
    public List<Parameter> getParameters() {
        return parameters;
    }

    /**
     * Setter.
     *
     * @param parameters list of parameters in client's controller method to invoke.
     */
    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    /**
     * Getter.
     *
     * @return path of path.
     */
    public String getPath() {
        return path;
    }

    /**
     * Setter.
     *
     * @param path set new parameter.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Getter.
     *
     * @return httpMethods of path.
     */
    public List<HttpMethod> getHttpMethods() {
        return httpMethods;
    }

    /**
     * Setter.
     *
     * @param httpMethods set new parameter.
     */
    public void setHttpMethods(List<HttpMethod> httpMethods) {
        this.httpMethods = httpMethods;
    }

    /**
     * Getter.
     *
     * @return methodName to invoke.
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Setter.
     *
     * @param methodName name of method to invoke in client's controller.
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * Getter.
     *
     * @return responseType.
     */
    public ResponseType getResponseType() {
        return responseType;
    }

    /**
     * Setter.
     *
     * @param responseType type of response JSON or XML;
     */
    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

}
