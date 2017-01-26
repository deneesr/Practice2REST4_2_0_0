package ua.com.as.service.conversion;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class XmlConverterTest {

    @Test
    public void convert() throws Exception {
        Customer customer = new Customer("Ivan", 20);
        String result = ConversionManager.getXml(customer);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<customer>" +
                    "<age>20</age>" +
                    "<name>Ivan</name>" +
                "</customer>",
                result);
    }

    @Test
    public void convertWithList() throws Exception {
        CustomerList customerList = new CustomerList("Ivan", "Anna");
        String result = ConversionManager.getXml(customerList);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<customerList>" +
                "<customers>Ivan</customers>" +
                "<customers>Anna</customers>" +
                "</customerList>", result);
    }
}
