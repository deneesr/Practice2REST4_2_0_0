package ua.com.as.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.as.model.containers.ControllerContainer;
import ua.com.as.model.containers.RequestContainer;
import ua.com.as.model.containers.ResponseContainer;
import ua.com.as.model.jaxb.Controller;
import ua.com.as.service.conversion.ConversionManager;
import ua.com.as.service.invocation.InvocationManager;
import ua.com.as.service.parsing.ParsingManager;
import ua.com.as.storage.QueriesStorage;
import ua.com.as.storage.Storage;
import ua.com.as.util.enums.ResponseType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Class represents 'server-controller-mapper'
 * which manages processes of parsing, scanning and getting required result.
 *
 * @see ParsingManager
 * @see ConversionManager
 */
public class ContextManager {
    private static final Logger LOG = LoggerFactory.getLogger(ContextManager.class);
    private Storage storage;

    /**
     * <p>Creates QueryStorage and ParsingManager. Gets controllers which client
     * has configured through xml-file or with annotations from the ParsingManager and
     * gives them to the QueryStorage.
     *
     * @param configFile file to parse in xml parser
     */
    public void configure(File configFile) {
        storage = new QueriesStorage();

        List<Controller> controllers = ParsingManager.getControllers(configFile);
        List<ControllerContainer> controllerContainers = ConversionManager.getControllerContainers(controllers);

        controllerContainers.forEach(storage::save);
    }

    /**
     * <p>Gets String response from invoked method with specified parameters.
     *
     * @param requestContainer container which has all info about client request.
     * @return String result of invocation
     */
    public String getRestResponse(RequestContainer requestContainer) {
        LOG.debug("Query response is requested");
        List<String> pathParameterValues = new ArrayList<>();
        ResponseContainer responseContainer = storage.find(requestContainer, pathParameterValues);

        Object result =
                InvocationManager.invoke(requestContainer.getQueryParameters(), responseContainer, pathParameterValues);
        LOG.debug("Query response is received");
        return convertResponse(result, responseContainer);
    }

    /**
     * <P>Convert response from <code>Object</code> type into JSON, XML or String according to <code>ResponseType</code>
     * specified in annotation or configuration xml file.
     * @param response           response from controller;
     * @param responseContainer  container that contains info about response;
     * @return response converted into specified <code>ResponseType</code>.
     */
    private String convertResponse(Object response, ResponseContainer responseContainer) {
        if (responseContainer.getResponseType().equals(ResponseType.JSON)) {
            LOG.debug("Query response is converted into JSON");
            return ConversionManager.getJson(response);
        } else if (responseContainer.getResponseType().equals(ResponseType.XML)) {
            LOG.debug("Query response is converted into XML");
            return ConversionManager.getXml(response);
        } else {
            return response.toString();
        }
    }
}
