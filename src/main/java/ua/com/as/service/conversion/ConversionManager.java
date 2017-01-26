package ua.com.as.service.conversion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.as.model.containers.ControllerContainer;
import ua.com.as.model.jaxb.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Class is responsible for conversion jaxb objects into inner mutable objects of rest4j framework.
 */
public class ConversionManager {
    private static final Logger LOG = LoggerFactory.getLogger(ConversionManager.class);

    private ConversionManager() {
    }

    /**
     * <p>Manages process of converting <code>Controllers</code> to <code>ControllerContainers</code>.
     *
     * @param controllers controllers to be converted into containers.
     * @return list of <code>ControllerContainers</code>.
     */
    public static List<ControllerContainer> getControllerContainers(List<Controller> controllers) {
        Converter<Controller, ControllerContainer> converter = new ControllerConverter();

        String infoForException = "";

        List<ControllerContainer> controllerContainers = new ArrayList<>();
        controllers.forEach(controller -> controllerContainers.add(converter.convert(controller, infoForException)));

        return controllerContainers;
    }


    /**
     * <p>Manage process of converting <code>objects</code> to <code>JSON strings</code>.
     *
     * @param from object to be converted to JSON string.
     * @return JSON string.
     */
    public static String getJson(Object from) {
        Converter<Object, String> converter = new JsonConverter();
        String infoForException = "";
        String convertedResult = converter.convert(from, infoForException);
        LOG.debug("Object {} converted to JSON", from);
        return convertedResult;
    }

    /**
     * <p>Manage process of converting <code>objects</code> to <code>XML strings</code>.
     *
     * @param from object to be converted to XML string.
     * @return XML string.
     */
    public static String getXml(Object from) {
        Converter<Object, String> converter = new XmlConverter();
        String infoForException = "";
        String convertedResult = converter.convert(from, infoForException);
        LOG.debug("Object {} converted to XML", from);
        return convertedResult;
    }
}
