package ua.com.as.service.conversion;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

/**
 * <p>Converts objects from <code>Object</code> type to <code>XML string</code>.
 */
class XmlConverter implements Converter<Object, String> {

    private static final Logger LOG = LoggerFactory.getLogger(XmlConverter.class);

    /**
     * <p>Converts objects from <code>F</code> type to <code>T</code> type.
     *
     * @param from             initial object with type <code>F</code>.
     * @param infoForException info about controller for Exception.
     * @return object with type <code>T</code>.
     */
    @Override
    @SuppressWarnings("all")
    public String convert(Object from, String infoForException) {
        LOG.debug("Start convert to XML");
        StringWriter sw = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(from.getClass());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.marshal(from, sw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        LOG.debug("End convert to XML");
        return sw.toString();
    }
}
