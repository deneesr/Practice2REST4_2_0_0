package ua.com.as.web;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DispatcherServletTest {

    private static final String METHOD_GET = "GET";
    private static final String PATH_GET = "/testGet/5";
    private static final String PATH_GET_WITH_QUERY = "/testGetWithQueryParam/5";
    private static final String WRONG_PATH_GET = "/wrong_path_get";
    private static final String SERVLET_NAME = "mainservlet";
    private static final String WEB_INF = "WEB-INF";
    private static final String PATH_TO_WEB_INF = "src/test/resources/WEB-INF";
    private static final String RESPONSE_GET_METHOD = "\"ControllerClass, method: testGet, return PathParam: 5\"";
    private static final String RESPONSE_WITH_QUERY = "\"ControllerClass, method: testGetWithQueryParam, " +
            "PathParam: 5 QueryParam: Vova\"";

    private ServletConfig servletConfig;
    private ServletContext servletContext;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private DispatcherServlet dispatcherServlet;

    @Before
    public void init() {
        servletConfig = mock(ServletConfig.class);
        servletContext = mock(ServletContext.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcherServlet = new DispatcherServlet();
    }


    @Test(expected = ServletException.class)
    public void testInitParsingError() throws ServletException {

        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletConfig.getServletContext().getRealPath(WEB_INF)).thenReturn("test");

        dispatcherServlet.init(servletConfig);
        assertNull(dispatcherServlet.getServletName());
    }

    @Test
    public void testService() throws Exception {
        when(servletConfig.getServletName()).thenReturn(SERVLET_NAME);
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRealPath(WEB_INF)).thenReturn(PATH_TO_WEB_INF);
        dispatcherServlet.init(servletConfig);
        when(request.getPathInfo()).thenReturn(PATH_GET);
        when(request.getMethod()).thenReturn(METHOD_GET);

        ServletOutputStreamMock servletOutputStream = new ServletOutputStreamMock();
        when(response.getOutputStream()).thenReturn(servletOutputStream);

        dispatcherServlet.service(request, response);

        byte[] data = servletOutputStream.baos.toByteArray();
        StringBuilder responseResult = new StringBuilder();
        for (byte b : data) {
            responseResult.append((char) b);
        }
        Assert.assertEquals(RESPONSE_GET_METHOD, responseResult.toString().trim());
    }

    @Test
    public void testServiceWithQueryParam() throws Exception {
        when(servletConfig.getServletName()).thenReturn(SERVLET_NAME);
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRealPath(WEB_INF)).thenReturn(PATH_TO_WEB_INF);
        dispatcherServlet.init(servletConfig);
        when(request.getPathInfo()).thenReturn(PATH_GET_WITH_QUERY);
        when(request.getMethod()).thenReturn(METHOD_GET);

        String name = "name";
        String[] values = {"Vova"};
        Map<String, String[]> queryParams = new HashMap<>();
        queryParams.put(name, values);
        when(request.getParameterMap()).thenReturn(queryParams);

        ServletOutputStreamMock servletOutputStream = new ServletOutputStreamMock();
        when(response.getOutputStream()).thenReturn(servletOutputStream);

        dispatcherServlet.service(request, response);

        byte[] data = servletOutputStream.baos.toByteArray();
        StringBuilder responseResult = new StringBuilder();
        for (byte b : data) {
            responseResult.append((char) b);
        }
        Assert.assertEquals(RESPONSE_WITH_QUERY, responseResult.toString().trim());
    }

    @Test
    public void testServiceWrong() throws Exception {
        when(request.getPathInfo()).thenReturn(WRONG_PATH_GET);
        when(request.getMethod()).thenReturn(METHOD_GET);
        ServletOutputStream servletOutputStream = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(servletOutputStream);

        dispatcherServlet.service(request, response);
    }
}

class ServletOutputStreamMock extends ServletOutputStream {

    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    @Override
    public void write(int i) throws IOException {
        baos.write(i);
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
    }
}
