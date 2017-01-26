package ua.com.as.storage.tree;

import org.junit.Before;
import org.junit.Test;
import ua.com.as.model.containers.ParameterContainer;
import ua.com.as.model.containers.RequestContainer;
import ua.com.as.model.containers.ResponseContainer;
import ua.com.as.util.enums.HttpMethod;
import ua.com.as.util.enums.ResponseType;
import ua.com.as.util.exception.ConfigurationException;
import ua.com.as.util.exception.RequestException;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class UrlTreeTest {

    private UrlTree tree;

    @Before
    public void init() {
        tree = new UrlTree();
    }

    @Test
    public void testAdd() throws Exception {

        ParameterContainer p1 = new ParameterContainer("id", Integer.class, 0);
        ParameterContainer p2 = new ParameterContainer("name", String.class, 1);

        ResponseContainer responseContainer = new ResponseContainer("com.test.Test", "test",
                ResponseType.JSON, Arrays.asList(p1, p2));

        String[] patternParts = {"user", "{id}", "{name}"};

        tree.add(patternParts, responseContainer);

        RequestContainer requestContainer = new RequestContainer("/user/56666/Ivan", HttpMethod.GET, new HashMap<>());

        List<String> parameterValues = new ArrayList<>();

        assertEquals(tree.find(requestContainer, parameterValues), responseContainer);
    }

    @Test(expected = RequestException.class)
    public void testFindWithError() throws Exception {

        ParameterContainer p1 = new ParameterContainer("id", char.class, 0);
        ParameterContainer p2 = new ParameterContainer("key", char.class, 1);

        ResponseContainer responseContainer = new ResponseContainer("com.test.Test", "test",
                ResponseType.JSON, Arrays.asList(p1, p2));

        String[] patternParts = {"user", "{id}", "{key}"};

        tree.add(patternParts, responseContainer);

        RequestContainer requestContainer = new RequestContainer("/user/5/i", HttpMethod.GET, new HashMap<>());

        List<String> parameterValues = new ArrayList<>();

        assertEquals(tree.find(requestContainer, parameterValues), responseContainer);
    }

    @Test(expected = RequestException.class)
    public void testFindWithErrorStringInsteadInt() throws Exception {

        ParameterContainer p1 = new ParameterContainer("id", String.class, 0);

        ResponseContainer responseContainer = new ResponseContainer("com.test.Test", "test",
                ResponseType.JSON, Arrays.asList(p1));

        String[] patternParts = {"user", "{id}"};

        tree.add(patternParts, responseContainer);

        RequestContainer requestContainer = new RequestContainer("/user/5", HttpMethod.GET, new HashMap<>());

        List<String> parameterValues = new ArrayList<>();

        assertEquals(tree.find(requestContainer, parameterValues), responseContainer);
    }

    @Test(expected = ConfigurationException.class)
    public void testAddBadUrlPattern() throws Exception {
        ParameterContainer p1 = new ParameterContainer("id", String.class, 2);
        ResponseContainer responseContainer =
                new ResponseContainer("com.test.Test", "test", ResponseType.JSON, Arrays.asList(p1));
        String[] patternParts = {"user", "{idd}"};
        tree.add(patternParts, responseContainer);
    }

    @Test(expected = ConfigurationException.class)
    public void testSimilarUrlsString() throws Exception {
        ParameterContainer parameterContainer1 = new ParameterContainer("id", String.class, 0);
        ResponseContainer responseContainer =
                new ResponseContainer("com.test.Test1", "test", ResponseType.JSON, Collections.singletonList(parameterContainer1));
        String[] patternParts = {"user", "{id}"};
        responseContainer.setQtyElementsInPath(1);
        tree.add(patternParts, responseContainer);
        tree.add(patternParts, responseContainer);
    }

    @Test(expected = ConfigurationException.class)
    public void testBadUrlPatternString() throws Exception {
        ParameterContainer parameterContainer1 = new ParameterContainer("id1", String.class, 0);
        ResponseContainer responseContainer =
                new ResponseContainer("com.test.Test1", "test", ResponseType.JSON, Collections.singletonList(parameterContainer1));
        String[] patternParts = {"user", "{id}"};
        responseContainer.setQtyElementsInPath(1);
        tree.add(patternParts, responseContainer);
    }

    @Test(expected = ConfigurationException.class)
    public void testSimilarUrlsNumber() throws Exception {
        ParameterContainer parameterContainer1 = new ParameterContainer("id", Integer.class, 0);
        ResponseContainer responseContainer =
                new ResponseContainer("com.test.Test", "test", ResponseType.JSON, Collections.singletonList(parameterContainer1));
        String[] patternParts = {"user", "{id}"};
        responseContainer.setQtyElementsInPath(1);
        tree.add(patternParts, responseContainer);
        tree.add(patternParts, responseContainer);
    }

    @Test(expected = ConfigurationException.class)
    public void testBadUrlPatternNumber() throws Exception {
        ParameterContainer parameterContainer1 = new ParameterContainer("id1", Integer.class, 0);
        ResponseContainer responseContainer =
                new ResponseContainer("com.test.Test", "test", ResponseType.JSON, Collections.singletonList(parameterContainer1));
        String[] patternParts = {"user", "{id}"};
        responseContainer.setQtyElementsInPath(1);
        tree.add(patternParts, responseContainer);
    }
}