package ua.com.as.util.adapters;

import ua.com.as.util.enums.HttpMethod;
import ua.com.as.util.exception.ConfigurationException;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Converter type from string to list of HttpMethod.
 */
public class HttpMethodAdapter extends XmlAdapter<String, List<HttpMethod>> {

    /**
     * Converts a value type to a bound type.
     *
     * @param v The value to be converted. Can be null.
     * @return instance of class.
     * @throws Exception if there's an error during the conversion.
     */

    @Override
    public List<HttpMethod> unmarshal(String v) throws Exception {
        String[] s = v.split(", ");
        List<HttpMethod> httpMethods = new ArrayList<>();
        for (String value : s) {
            String trim = value.trim().toUpperCase();
            switch (trim) {
                case "GET":
                    httpMethods.add(HttpMethod.GET);
                    break;
                case "POST":
                    httpMethods.add(HttpMethod.POST);
                    break;
                case "PUT":
                    httpMethods.add(HttpMethod.PUT);
                    break;
                case "DELETE":
                    httpMethods.add(HttpMethod.DELETE);
                    break;
                default:
                    throw new ConfigurationException("No such HTTP method!");
            }

        }
        return httpMethods;
    }

    @Override
    public String marshal(List<HttpMethod> v) throws Exception {
        return null;
    }
}
