package ua.com.as.service.parsing;

import org.junit.Assert;
import org.junit.Test;
import ua.com.as.model.jaxb.Controller;
import ua.com.as.model.jaxb.Query;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

public class ParsingManagerTest {

    private static final String TEST_CONFIGURATION_XML = "/configurationTest.xml";
    private static final String TEST_CONTROLLER_NAME = "ua.com.as.service.ControllerClass";
    private static final String TEST_METHOD_NAME = "testGet";
    private static final int EXPECTED_LIST_SIZE = 4;
    private static final String TEST_ANNOTATED_CONTROLLER_NAME =
            "ua.com.as.service.parsing.controller2.ControllerAnnotationClass";

    private File getFileFromResources(String path) {
        try {
            return new File(this.getClass().getResource(path).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void testGetControllers() throws Exception {
        List<Controller> controllers = ParsingManager.getControllers(getFileFromResources(TEST_CONFIGURATION_XML));
        Assert.assertEquals(controllers.size(), EXPECTED_LIST_SIZE);
        Controller controllerTest = controllers.get(0);
        Assert.assertEquals(controllerTest.getFullClassName(), TEST_CONTROLLER_NAME);
        Controller controllerAnnotated = controllers.get(2);
        Assert.assertEquals(controllerAnnotated.getFullClassName(), TEST_ANNOTATED_CONTROLLER_NAME);
        Query queryTest = controllerTest.getQueries().get(0);
        Assert.assertEquals(queryTest.getMethodName(), TEST_METHOD_NAME);
    }
}