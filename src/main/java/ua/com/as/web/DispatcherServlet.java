package ua.com.as.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.as.model.containers.RequestContainer;
import ua.com.as.service.ContextManager;
import ua.com.as.util.enums.HttpMethod;
import ua.com.as.util.exception.RequestException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>DispatcherServlet is base and main part of <code>rest4j framework</code>
 * for the client.
 * <p>Client has to register this class in his configuration <code>web.xml</code> file
 * to use <code>rest4j framework</code> functionality.
 * <p>On the first use servlet tries to find client's configuration file
 * with the specified name (the name of the dispatcher servlet + mapping-cfg.xml) in the <code>WEB-INF folder</code>,
 * transmits it to the <code>ParsingManager</code>, which receives all the necessary information for the application
 * and stores it in the <code>QueryStorage</code>.
 * <p>Further, the class takes all the requests received from the outside by using <code>http protocol</code>,
 * receives the necessary information from them to call the appropriate method
 * in the client's project and returns the expected result.
 * <p><b>Note:</b>
 * <p>Client has to add configuration file to his project in the WEB-INF folder.
 * File should be named like: the name of the dispatcher servlet + mapping-cfg.xml.
 * Client may configure controller either in this xml file either add auto-scan tag
 * which will contains names of packages with classes marked with the Controller annotation.
 * For the more detailed information, please refer to the examples described in @README.
 *
 * @see ua.com.as.service.ContextManager
 */
public class DispatcherServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final ContextManager CONTEXT_MANAGER = new ContextManager();

    /**
     * <p>It is called when this servlet is used at first time.
     * <p>In this method configuration file is prepared for using and
     * is passed to ContextManager.
     *
     * @throws ServletException when servlet encounters difficulty {@link ServletException}.
     */
    @Override
    public void init() throws ServletException {
        try {
            File configFile = prepareConfigurationFile();
            LOG.debug("ConfigFile: {}", configFile);
            CONTEXT_MANAGER.configure(configFile);
        } catch (RuntimeException e) {
            LOG.error("Server Error: {}, {}", HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
            throw new ServletException(e.toString());
        }
    }

    /**
     * <p>It is called each time receiving a request from a client.
     * <p>Gets all required information from client's request, gives it to the ContextManager,
     * and sends a response to the client, which is received from QueryStorage.
     *
     * @param request  the HttpServletRequest object that contains the client's request
     * @param response the HttpServletResponse object that will contain the servlet's response
     * @throws ServletException when servlet encounters difficulty {@link ServletException}
     * @throws IOException      when an I/O exception has occurred {@link IOException}
     * @see ua.com.as.service.ContextManager
     */
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        LOG.debug("Request is received");

        String path = request.getPathInfo();
        LOG.debug("Path: {}", path);

        String httpMethod = request.getMethod();
        LOG.debug("HttpMethod: {}", httpMethod);
        HttpMethod httpMethodName = HttpMethod.valueOf(httpMethod);
        Map<String, String> queryParameters = getQueryParameters(request);
        if (queryParameters.size() > 0) {
            LOG.debug("Query parameters: {}", queryParameters);
        }

        try {
            String result =
                    CONTEXT_MANAGER.getRestResponse(new RequestContainer(path, httpMethodName, queryParameters));

            response.getOutputStream().println(result);
            LOG.debug("Response is sent\n");
        } catch (RequestException e) {
            LOG.error("Error, cannot find resource", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad Request!");
        } catch (RuntimeException e) {
            LOG.error("Error, cannot find resource", e);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Source not found!");
        }
    }

    /**
     * <p>In this method configuration file is prepared for using and
     * is passed to ContextManager.
     *
     * @return configuration file for parsing.
     */
    private File prepareConfigurationFile() {
        String webInfPath = this.getServletContext().getRealPath("WEB-INF");
        LOG.trace("WebInfPath: {}", webInfPath);
        String servletName = this.getServletName();
        LOG.trace("ServletName: {}", servletName);
        return new File(webInfPath + "/" + servletName + "-mapping-cfg.xml");
    }

    /**
     * <p>Returns query parameters from request.
     *
     * @param request client's request.
     * @return query parameters from request.
     */
    private Map<String, String> getQueryParameters(HttpServletRequest request) {
        Map<String, String> queryParameters = new HashMap<>();

        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            queryParameters.put(entry.getKey(), entry.getValue()[0]);
        }

        return queryParameters;
    }
}
