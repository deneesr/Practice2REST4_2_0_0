package ua.com.as.service.conversion;

import org.junit.Test;
import ua.com.as.model.containers.ControllerContainer;
import ua.com.as.model.jaxb.Controller;
import ua.com.as.model.jaxb.Parameter;
import ua.com.as.model.jaxb.Query;
import ua.com.as.util.enums.HttpMethod;
import ua.com.as.util.exception.ConfigurationException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static ua.com.as.model.jaxb.Parameter.ParameterType.PATH_PARAMETER;
import static ua.com.as.model.jaxb.Parameter.ParameterType.QUERY_PARAMETER;

public class ConversionManagerTest {

    final String path = "/rest/user/{id}/{name}";
    final List<HttpMethod> httpMethods = Arrays.asList(HttpMethod.GET, HttpMethod.POST);
    final String methodName = "testMethod";
    final String fullClassName = "test.controllers.Controller";

    final String p1Name = "name";
    final String p2Name = "id";

    @Test
    public void getControllerContainers() throws Exception {


        Parameter p1 = new Parameter();
        p1.setName(p1Name);
        p1.setType(Integer.class);
        p1.setParameterType(PATH_PARAMETER);

        Parameter p2 = new Parameter();
        p2.setName(p2Name);
        p2.setType(String.class);
        p2.setParameterType(PATH_PARAMETER);

        Query q = new Query();
        q.setPath(path);
        q.setHttpMethods(httpMethods);
        q.setMethodName(methodName);
        q.setParameters(Arrays.asList(p1, p2));

        Controller c = new Controller();
        c.setFullClassName(fullClassName);
        c.setQueries(Collections.singletonList(q));

        List<ControllerContainer> controllerContainers =
                ConversionManager.getControllerContainers(Collections.singletonList(c));

        assertEquals(1, controllerContainers.size());
        assertEquals(1, controllerContainers.get(0)
                .getQueryContainers().get(0)
                .getParameters().get(0)
                .getPosition());
        assertEquals(0, controllerContainers.get(0)
                .getQueryContainers().get(0)
                .getParameters().get(1)
                .getPosition());
    }

    @Test
    public void getControllerContainersWithQueryParameters() throws Exception {

        final String p1Name = "name";
        final String p2Name = "id";
        final String p3Name = "age";

        Parameter p1 = new Parameter();
        p1.setName(p1Name);
        p1.setType(Integer.class);
        p1.setParameterType(PATH_PARAMETER);

        Parameter p2 = new Parameter();
        p2.setName(p2Name);
        p2.setType(String.class);
        p2.setParameterType(PATH_PARAMETER);

        Parameter p3 = new Parameter();
        p3.setName(p3Name);
        p3.setType(Byte.class);
        p3.setParameterType(QUERY_PARAMETER);

        Query q = new Query();
        q.setPath(path);
        q.setHttpMethods(httpMethods);
        q.setMethodName(methodName);
        q.setParameters(Arrays.asList(p1, p2, p3));

        Controller c = new Controller();
        c.setFullClassName(fullClassName);
        c.setQueries(Collections.singletonList(q));

        List<ControllerContainer> controllerContainers =
                ConversionManager.getControllerContainers(Collections.singletonList(c));

        assertEquals(1, controllerContainers.size());
        assertEquals(3, controllerContainers.get(0).getQueryContainers().get(0).getParameters().size());

        assertEquals(1, controllerContainers.get(0)
                .getQueryContainers().get(0)
                .getParameters().get(0)
                .getPosition());
        assertEquals(0, controllerContainers.get(0)
                .getQueryContainers().get(0)
                .getParameters().get(1)
                .getPosition());
        assertEquals(-1, controllerContainers.get(0)
                .getQueryContainers().get(0)
                .getParameters().get(2)
                .getPosition());
    }

    @Test
    public void getControllerContainersQueryWithoutParameters() throws Exception {

        Query q = new Query();
        q.setPath(path);
        q.setHttpMethods(httpMethods);
        q.setMethodName(methodName);

        Controller c = new Controller();
        c.setFullClassName(fullClassName);
        c.setQueries(Collections.singletonList(q));

        List<ControllerContainer> controllerContainers =
                ConversionManager.getControllerContainers(Collections.singletonList(c));

        assertEquals(1, controllerContainers.size());
        assertEquals(0, controllerContainers.get(0).getQueryContainers().get(0).getParameters().size());
    }

    @Test(expected = ConfigurationException.class)
    public void getControllerContainersQueryWithErrorConfiguration() throws Exception {

        final String p1Name = "name";
        final String p2Name = "errorName";

        Parameter p1 = new Parameter();
        p1.setName(p1Name);
        p1.setType(Integer.class);
        p1.setParameterType(PATH_PARAMETER);

        Parameter p2 = new Parameter();
        p2.setName(p2Name);
        p2.setType(String.class);
        p2.setParameterType(PATH_PARAMETER);

        Query q = new Query();
        q.setPath(path);
        q.setHttpMethods(httpMethods);
        q.setMethodName(methodName);
        q.setParameters(Arrays.asList(p1, p2));

        Controller c = new Controller();
        c.setFullClassName(fullClassName);
        c.setQueries(Collections.singletonList(q));

        ConversionManager.getControllerContainers(Collections.singletonList(c));
    }
}