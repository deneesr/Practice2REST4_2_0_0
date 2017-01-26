package ua.com.as.model.containers;

import ua.com.as.util.enums.HttpMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>Class for storing all information about client's request.
 */
public class RequestContainer {
    private String fullPath;
    private String[] pathParts;
    private HttpMethod httpMethodName;
    private Map<String, String> queryParameters;

    /**
     * <p>RequestContainer constructor with all required parameters.
     *
     * @param path            url path of client's request.
     * @param httpMethodName  http method name of client's request.
     * @param queryParameters query parameters of client's request.
     */
    public RequestContainer(String path, HttpMethod httpMethodName, Map<String, String> queryParameters) {
        this.fullPath = path;
        this.pathParts = getParts(path);
        this.httpMethodName = httpMethodName;
        this.queryParameters = queryParameters;
    }

    /**
     * <p>Delete all empty parts of url path.
     *
     * @param path url of client's request.
     * @return parts of url parts without empty parts.
     */
    private String[] getParts(String path) {
        String[] split = path.split("/");

        List<String> parts = new ArrayList<>();

        for (String s : split) {
            if (!s.isEmpty()) {
                parts.add(s);
            }
        }

        return parts.toArray(new String[parts.size()]);
    }

    /**
     * <p>Getter.
     *
     * @return url full path of client's request.
     */
    public String getFullPath() {
        return fullPath;
    }

    /**
     * <p>Getter.
     *
     * @return url path of client's request.
     */
    public String[] getPathParts() {
        return pathParts;
    }

    /**
     * <p>Getter.
     *
     * @return http method name of client's request.
     */
    public HttpMethod getHttpMethodName() {
        return httpMethodName;
    }

    /**
     * <p>Getter.
     *
     * @return query parameters of client's request.
     */
    public Map<String, String> getQueryParameters() {
        return queryParameters;
    }
}
