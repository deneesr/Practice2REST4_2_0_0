package ua.com.as.service.parsing;

import org.junit.Test;
import ua.com.as.model.jaxb.Controller;
import ua.com.as.util.exception.ParsingException;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class XmlParserImplTest {

    private static final String TEST_CONFIGURATION_XML = "/configurationTest.xml";
    private static final String TEST_WRONG_XML = "/wrongTest.xml";
    private static final String TEST_EMPTY_XML = "/emptyFile.xml";
    private static final String TEST_PATH = "/testGet/{id}";
    private static final String TEST_PACKAGE = "ua.com.as.service.parsing.controller1";
    private static final int TEST_SIZE_CONTROLLERS = 1;
    private static final int TEST_QTY_PACKAGES = 2;

    private File getFileFromResources(String path) {
        try {
            return new File(this.getClass().getResource(path).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void testParseXml() {
        File file = getFileFromResources(TEST_CONFIGURATION_XML);
        XmlParser parser = new XmlParserImpl(file);

        List<Controller> controllers = parser.getControllers();
        List<String> packagesToScan = parser.getPackagesToScan();

        assertEquals(TEST_SIZE_CONTROLLERS, controllers.size());
        assertEquals(TEST_QTY_PACKAGES, packagesToScan.size());

        Controller controller = controllers.get(0);
        assertEquals(4, controller.getQueries().size());
        assertEquals( controller.getQueries().get(0).getPath(), TEST_PATH);

        assertEquals(packagesToScan.get(0), TEST_PACKAGE);
    }

    @Test
    public void testParseWithWrongXml() {
        File file = getFileFromResources(TEST_WRONG_XML);
        XmlParser parserWithWrongXml = new XmlParserImpl(file);
        assertEquals(Collections.EMPTY_LIST, parserWithWrongXml.getPackagesToScan());
        assertEquals(Collections.EMPTY_LIST, parserWithWrongXml.getControllers());
    }

    @Test (expected = ParsingException.class)
    public void testParseXmlWithEmptyFile() {
        File file = getFileFromResources(TEST_EMPTY_XML);
        new XmlParserImpl(file);
    }
}