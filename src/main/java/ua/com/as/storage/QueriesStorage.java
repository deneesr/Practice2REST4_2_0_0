package ua.com.as.storage;

import ua.com.as.model.containers.*;
import ua.com.as.storage.tree.UrlTree;
import ua.com.as.util.enums.HttpMethod;
import ua.com.as.util.enums.ResponseType;
import ua.com.as.util.exception.ConfigurationException;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * <p>This class is used for keeping (save and find) user data wrapped in Containers
 * for better usability.
 */
public class QueriesStorage implements Storage {
    private Map<HttpMethod, UrlTree> storage = new EnumMap<>(HttpMethod.class);

    /**
     * <p>Saves <code>ControllerContainer</code> to storage.
     *
     * @param controllerContainer <code>ControllerContainer</code> to save.
     */
    @Override
    public void save(ControllerContainer controllerContainer) {
        String fullClassName = controllerContainer.getFullClassName();
        List<QueryContainer> queryContainers = controllerContainer.getQueryContainers();
        queryContainers.forEach(queryContainer -> saveQueryContainer(fullClassName, queryContainer));
    }

    /**
     * <p>Finds <code>ResponseContainer</code> in storage by specified <code>RequestContainer</code>.
     *
     * @param requestContainer    <code>RequestContainer</code> to find.
     * @param pathParameterValues values of all non static parameters in url path.
     * @return <code>ResponseContainer</code> object.
     */
    @Override
    public ResponseContainer find(RequestContainer requestContainer, List<String> pathParameterValues) {
        HttpMethod httpMethodName = requestContainer.getHttpMethodName();

        UrlTree urlTree = storage.get(httpMethodName);
        if (urlTree == null) {
            throw new ConfigurationException("No url urlTree with such http method!");
        }

        return urlTree.find(requestContainer, pathParameterValues);
    }

    /**
     * <p>Saves single <code>QueryContainer</code> to storage.
     *
     * @param fullClassName  fullClassName of controller.
     * @param queryContainer <code>QueryContainer</code> to save.
     */
    private void saveQueryContainer(String fullClassName, QueryContainer queryContainer) {
        String[] pathParts = queryContainer.getPathParts();
        List<HttpMethod> httpMethods = queryContainer.getHttpMethods();
        int qtyElementsInPath = queryContainer.getQtyElementsInPath();

        String methodName = queryContainer.getMethodName();
        ResponseType responseType = queryContainer.getResponseType();
        List<ParameterContainer> parameters = queryContainer.getParameters();
        ResponseContainer responseContainer = new ResponseContainer(fullClassName, methodName,
                                                                    responseType, parameters);
        responseContainer.setQtyElementsInPath(qtyElementsInPath);

        httpMethods.forEach(httpMethod -> saveResponseContainer(httpMethod, pathParts, responseContainer));
    }

    /**
     * <p>Saves <code>ResponseContainer</code> to storage.
     *
     * @param httpMethod        specified http method.
     * @param pathParts         url pattern splitted by slash.
     * @param responseContainer <code>ResponseContainer</code> to save.
     */
    private void saveResponseContainer(HttpMethod httpMethod,
                                       String[] pathParts,
                                       ResponseContainer responseContainer) {

        UrlTree urlTree = storage.get(httpMethod);
        if (urlTree == null) {
            urlTree = new UrlTree();
            storage.put(httpMethod, urlTree);
            urlTree.add(pathParts, responseContainer);
        } else {
            urlTree.add(pathParts, responseContainer);
        }
    }
}
