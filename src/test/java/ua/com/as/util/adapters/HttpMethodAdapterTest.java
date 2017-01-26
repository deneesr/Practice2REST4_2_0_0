package ua.com.as.util.adapters;

import org.junit.Assert;
import org.junit.Test;
import ua.com.as.util.enums.HttpMethod;
import ua.com.as.util.exception.ConfigurationException;

import java.util.ArrayList;
import java.util.List;

public class HttpMethodAdapterTest {
    HttpMethodAdapter adapter = new HttpMethodAdapter();
    private List<HttpMethod> httpMethods = new ArrayList<>();


    @Test
    public void testUnmarshal() throws Exception {
        httpMethods.add(HttpMethod.GET);
        httpMethods.add(HttpMethod.POST);
        httpMethods.add(HttpMethod.PUT);
        httpMethods.add(HttpMethod.DELETE);
        Assert.assertEquals(httpMethods, adapter.unmarshal("get, post, put, delete"));
    }

    @Test(expected = ConfigurationException.class)
    public void wrongHttpMethod() throws Exception {
        adapter.unmarshal("clear");
    }
}