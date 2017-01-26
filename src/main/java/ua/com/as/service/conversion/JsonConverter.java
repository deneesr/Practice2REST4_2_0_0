package ua.com.as.service.conversion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ua.com.as.util.exception.ConversionException;

/**
 * <p>Converts objects from <code>Object</code> type to <code>JSON string</code>.
 */
class JsonConverter implements Converter<Object, String> {


    /**
     * <p>Converts objects from <code>F</code> type to <code>T</code> type.
     *
     * @param from             initial object with type <code>F</code>.
     * @param infoForException info about controller for Exception.
     * @return object with type <code>T</code>.
     */
    @Override
    public String convert(Object from, String infoForException) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(from);
        } catch (JsonProcessingException e) {
            throw new ConversionException("Error during conversion to JSON", e);
        }
    }
}
