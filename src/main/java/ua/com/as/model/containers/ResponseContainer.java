package ua.com.as.model.containers;

import ua.com.as.util.enums.ResponseType;

import java.lang.reflect.Method;
import java.util.List;

/**
 * <p>Class for storing all information about rest response.
 */
public class ResponseContainer {
    private String fullClassName;
    private String methodName;
    private ResponseType responseType;
    private int qtyElementsInPath;
    private List<ParameterContainer> parameters;
    private Class<?> clazz;
    private Method method;

    /**
     * <p>Constructor for <code>ResponseContainer</code> which specifies fullClassName, methodName
     * and parameters arguments.
     *
     * @param fullClassName fullClassName of current controller.
     * @param methodName    name of method which will be invoked in controller.
     * @param responseType  type of response: JSON or XML.
     * @param parameters    parameters to add in this response container.
     */
    public ResponseContainer(String fullClassName, String methodName, ResponseType responseType,
                             List<ParameterContainer> parameters) {
        this.fullClassName = fullClassName;
        this.methodName = methodName;
        this.parameters = parameters;
        this.responseType = responseType;
    }

    /**
     * <p>Getter.
     *
     * @return value of field <code>fullClassName</code> in <code>ResponseContainer</code>.
     */
    public String getFullClassName() {
        return fullClassName;
    }

    /**
     * <p>Getter.
     *
     * @return value of field <code>methodName</code> in <code>ResponseContainer</code>.
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * <p>Getter.
     *
     * @return value of field <code>parameters</code> in <code>ResponseContainer</code>.
     */
    public List<ParameterContainer> getParameters() {
        return parameters;
    }

    /**
     * <p>Getter.
     *
     * @return value of field <code>method</code> in <code>ResponseContainer</code>.
     */
    public Method getMethod() {
        return method;
    }

    /**
     * <p>Setter.
     *
     * @param method field <code>method</code> in <code>ResponseContainer</code>.
     */
    public void setMethod(Method method) {
        this.method = method;
    }

    /**
     * <p>Getter.
     *
     * @return value of field <code>clazz</code> in <code>ResponseContainer</code>.
     */
    public Class<?> getClazz() {
        return clazz;
    }

    /**
     * <p>Setter.
     *
     * @param clazz field <code>clazz</code> in <code>ResponseContainer</code>.
     */
    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    /**
     * <p>Getter.
     *
     * @return value of field <code>getQtyElementsInPath</code> in <code>ResponseContainer</code>.
     */
    public int getQtyElementsInPath() {
        return qtyElementsInPath;
    }

    /**
     * <p>Setter.
     *
     * @param qtyElementsInPath field <code>qtyElementsInPath</code> in <code>ResponseContainer</code>.
     */
    public void setQtyElementsInPath(int qtyElementsInPath) {
        this.qtyElementsInPath = qtyElementsInPath;
    }

    /**
     * <p>Getter.
     *
     * @return responseType.
     */
    public ResponseType getResponseType() {
        return responseType;
    }

    /**
     * Setter.
     *
     * @param responseType JSON or XML of response.
     */
    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }
}
