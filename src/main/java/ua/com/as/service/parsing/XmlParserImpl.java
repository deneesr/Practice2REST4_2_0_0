package ua.com.as.service.parsing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.as.model.jaxb.Configuration;
import ua.com.as.model.jaxb.Controller;
import ua.com.as.util.exception.ParsingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Class present method for parsing xml file.
 */
public class XmlParserImpl implements XmlParser {

    private static final Logger LOG = LoggerFactory.getLogger(XmlParserImpl.class);

    private Configuration configuration;

    /**
     * <p>Class constructor.
     *
     * @param configFile xml file to parse
     */
    public XmlParserImpl(File configFile) {
        LOG.debug("File configFile: {}", configFile);
        parseXml(configFile);
    }

    /**
     * <p>Prepare and parse xml file.
     *
     * @param configFile - path to xml-file with configuration.
     */
    private void parseXml(File configFile) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Configuration.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            LOG.debug("Parsing started");
            Configuration conf = (Configuration) jaxbUnmarshaller.unmarshal(configFile);
            if (conf != null) {
                this.configuration = conf;
                LOG.debug("Parsing finished successfully! AutoScan and Controllers is not empty\n");
            } else {
                LOG.error("Parsing finished with error: wrong format xml-file.");
            }

        } catch (JAXBException e) {
            LOG.error("Parsing finished with error!");
            throw new ParsingException("parsing error", e);
        }
    }

    /**
     * <p>Get controllers from xml file.
     *
     * @return list of controllers
     */
    @Override
    public List<Controller> getControllers() {
        if (configuration.getControllers() != null) {
            return configuration.getControllers();
        }
        LOG.error("List Controllers is empty!");
        return new ArrayList<>();
    }

    /**
     * <p>Get packages to scan from xml file.
     *
     * @return list of package names
     */
    @Override
    public List<String> getPackagesToScan() {
        List<String> result;
        if (configuration.getAutoScan() != null) {
            String[] packages = configuration.getAutoScan().getPackages().split(",");
            result = Arrays.stream(packages)
                    .map(String::trim)
                    .filter(packagee -> !packagee.isEmpty())
                    .collect(Collectors.toList());
        } else {
            LOG.error("List PackagesToScan is empty!");
            result = Collections.emptyList();
        }
        return result;
    }
}
