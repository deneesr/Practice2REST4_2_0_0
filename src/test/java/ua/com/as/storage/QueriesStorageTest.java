package ua.com.as.storage;

import org.junit.Test;
import ua.com.as.model.containers.*;
import ua.com.as.util.enums.HttpMethod;
import ua.com.as.util.enums.ResponseType;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class QueriesStorageTest {
    @Test
    public void storageTest() throws Exception {
        Storage storage = new QueriesStorage();

        ParameterContainer p2 = new ParameterContainer("name", String.class, 1);
        ParameterContainer p1 = new ParameterContainer("id", Integer.class, 0);

        String[] patternParts = {"user", "{id}", "{name}"};

        QueryContainer queryContainer = new QueryContainer(
                patternParts,
                Collections.singletonList(HttpMethod.GET),
                ResponseType.JSON,
                "test",
                Arrays.asList(p1, p2));

        ControllerContainer controllerContainer = new ControllerContainer("com.test.Controller",
                Collections.singletonList(queryContainer));

        ResponseContainer responseContainer = new ResponseContainer(
                "com.test.Controller",
                "test",
                ResponseType.JSON,
                Arrays.asList(p1, p2));

        storage.save(controllerContainer);

        RequestContainer requestContainer = new RequestContainer(
                "/user/5/Sergey",
                HttpMethod.GET,
                new HashMap<>()
        );

        List<String> parameterValues = new ArrayList<>();

        ResponseContainer response = storage.find(requestContainer, parameterValues);
        assertEquals(responseContainer.getFullClassName(), response.getFullClassName());
    }
}