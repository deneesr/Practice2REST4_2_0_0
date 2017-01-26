package ua.com.as.model.containers;

import ua.com.as.util.enums.HttpMethod;
import ua.com.as.util.enums.ResponseType;

import java.util.List;

/**
 * <p>Class for storing all information about query which is part of <code>ControllerContainer</code>.
 */
public class QueryContainer {
    private String[] pathParts;
    private List<HttpMethod> httpMethods;
    private ResponseType responseType;
    private String methodName;
    private List<ParameterContainer> parameters;

    /**
     * <p>Constructor which specifies fields <code>pathParts</code>, <code>httpMethods</code>,
     * <code>methodName</code> and <code>parameters</code> in <code>QueryContainer</code>.
     *
     * @param pathParts   field <code>pathParts</code> in <code>QueryContainer</code>.
     * @param httpMethods field <code>httpMethods</code>, in <code>QueryContainer</code>.
     * @param responseType field <code>responseType</code> in <code>QueryContainer</code>.
     * @param methodName  field <code>methodName</code> in <code>QueryContainer</code>.
     * @param parameters  field <code>parameters</code> in <code>QueryContainer</code>.
     */
    public QueryContainer(String[] pathParts,
                          List<HttpMethod> httpMethods,
                          ResponseType responseType,
                          String methodName,
                          List<ParameterContainer> parameters) {
        this.pathParts = pathParts;
        this.httpMethods = httpMethods;
        this.methodName = methodName;
        this.parameters = parameters;
        this.responseType = responseType;
    }

    /**
     * <p>Getter.
     *
     * @return value of field <code>pathParts</code> in <code>QueryContainer</code>.
     */
    public String[] getPathParts() {
        return pathParts;
    }

    /**
     * <p>Getter.
     *
     * @return value of field <code>httpMethods</code>, in <code>QueryContainer</code>.
     */
    public List<HttpMethod> getHttpMethods() {
        return httpMethods;
    }

    /**
     * <p>Getter.
     *
     * @return value of field <code>methodName</code> in <code>QueryContainer</code>.
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * <p>Getter.
     *
     * @return value of field <code>parameters</code> in <code>QueryContainer</code>.
     */
    public List<ParameterContainer> getParameters() {
        return parameters;
    }

    /**
     * <p>Getter.
     *
     * @return quantity elements in url path minus one, because begin start count from zero index.
     */
    public int getQtyElementsInPath() {
        return pathParts.length - 1;
    }

    /**
     * <p>Getter.
     * @return responseType.
     */
    public ResponseType getResponseType() {
        return responseType;
    }

}
