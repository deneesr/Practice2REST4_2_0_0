package ua.com.as.util.adapters;

import org.junit.Assert;
import org.junit.Test;
import ua.com.as.util.enums.ResponseType;
import ua.com.as.util.exception.ConfigurationException;

public class ResponseTypeAdapterTest {
    ResponseTypeAdapter adapter = new ResponseTypeAdapter();

    @Test
    public void testUnmarshal() throws Exception {
        Assert.assertEquals(ResponseType.JSON, adapter.unmarshal("JSON"));
        Assert.assertEquals(ResponseType.XML, adapter.unmarshal("XML"));
        Assert.assertEquals(ResponseType.STRING, adapter.unmarshal("String"));
    }

    @Test(expected = ConfigurationException.class)
    public void wrongResponseType() throws Exception {
        adapter.unmarshal("object");

    }
}