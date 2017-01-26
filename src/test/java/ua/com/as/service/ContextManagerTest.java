package ua.com.as.service;

import org.junit.Before;
import org.junit.Test;
import ua.com.as.model.containers.RequestContainer;
import ua.com.as.util.enums.HttpMethod;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class ContextManagerTest {

    private static final String TEST_CONFIGURATION_XML = "/configContextManagerTest.xml";
    private static final String TEST_CONFIG_XML = "/configTest.xml";

    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";

    private ContextManager contextManager;

    @Before
    public void init() {
        contextManager = new ContextManager();
    }

    private File getFileFromResources(String path) {
        try {
            return new File(this.getClass().getResource(path).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void getRestResponseTest() throws Exception {
        contextManager.configure(getFileFromResources(TEST_CONFIGURATION_XML));
        String result =
                contextManager.getRestResponse(new RequestContainer("/testGetXml/127", HttpMethod.GET, new HashMap<>()));
        assertEquals("\"ControllerClassForContextManager, method: testGetXml, " +
                "return PathParam: 127\"", result);
    }

    @Test
    public void getRestResponseOnlyAnnotationTest() throws Exception {
        contextManager.configure(getFileFromResources(TEST_CONFIG_XML));
        Map<String, String> query = new HashMap<>();
        query.put("nameGet", "Kolya");
        String result =
                contextManager.getRestResponse(new RequestContainer("/getTest/111", HttpMethod.GET, query));
        assertEquals("Hello GET, {idGet}, idName: 111, (Query - nameGet) userName: Kolya", result);
    }

    @Test
    public void getRestResponseOnlyAnnotation2Test() throws Exception {
        contextManager.configure(getFileFromResources(TEST_CONFIG_XML));
        Map<String, String> query = new HashMap<>();
        query.put("namePost", "Vika");
        String result =
                contextManager.getRestResponse(new RequestContainer("/getTest/222/block", HttpMethod.GET, query));
        assertEquals("Hello POST, {idPost}, id: 222, (Query - namePost) name: Vika", result);
    }

    @Test
    public void getRestResponseOnlyAnnotation3Test() throws Exception {
        contextManager.configure(getFileFromResources(TEST_CONFIG_XML));
        String result =
                contextManager.getRestResponse(new RequestContainer("/getTestString/Vova", HttpMethod.POST, new HashMap<>()));
        assertEquals("Hello GET, {idGet}, idName: Vova, (Query - nameGet) userName: null", result);
    }

    @Test
    public void getRestResponseOnlyAnnotation4Test() throws Exception {
        contextManager.configure(getFileFromResources(TEST_CONFIG_XML));
        String result =
                contextManager.getRestResponse(new RequestContainer("/getTestString/Nina/block", HttpMethod.POST, new HashMap<>()));
        assertEquals("Hello POST, {idPost}, id: Nina, (Query - namePost) name: null", result);
    }

}
