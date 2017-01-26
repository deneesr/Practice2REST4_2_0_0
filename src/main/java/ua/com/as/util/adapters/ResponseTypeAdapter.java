package ua.com.as.util.adapters;

import ua.com.as.util.enums.ResponseType;
import ua.com.as.util.exception.ConfigurationException;

import javax.xml.bind.annotation.adapters.XmlAdapter;


/**
 * <p>Converter type from <code>String</code> to <code>ResponseType</code>.
 */
public class ResponseTypeAdapter extends XmlAdapter<String, ResponseType> {

    /**
     * Converts a value type to a bound type.
     *
     * @param v The value to be converted. Can be null.
     * @return instance of class.
     * @throws Exception if there's an error during the conversion.
     */

    @Override
    public ResponseType unmarshal(String v) throws Exception {
        switch (v) {
            case "JSON":
                return ResponseType.JSON;
            case "XML":
                return ResponseType.XML;
            case "String":
                return ResponseType.STRING;
            default:
                throw new ConfigurationException("Please select response type: JSON or XML or STRING");
        }

    }

    @Override
    public String marshal(ResponseType v) throws Exception {
        return null;
    }
}
