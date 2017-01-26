package ua.com.as.service.parsing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.as.model.jaxb.Controller;

import java.io.File;
import java.util.List;

/**
 * <p>Manages all operations related to parsing.
 *
 * @see ua.com.as.util.annotation.Controller
 */
public class ParsingManager {
    private static final Logger LOG = LoggerFactory.getLogger(ParsingManager.class);

    private ParsingManager() {
    }

    /**
     * <p>Receives the name of the configuration file, and sends
     * controllers obtained from xml file and classes which
     * marked the annotation <code>Controller</code> as an output.
     *
     * @param configFile - file that stores configuration of controllers.
     * @return list of <code>controller</code> that is received from configuration file.
     */
    public static List<Controller> getControllers(File configFile) {
        XmlParser parser = new XmlParserImpl(configFile);
        PackageScanner packageScanner = new PackageScannerImpl();

        LOG.info("Parsing of xml file to get controllers from xml");
        List<Controller> parserControllers = parser.getControllers();
        LOG.info("Parsing of packages to get annotated controllers");
        List<String> packagesToScan = parser.getPackagesToScan();

        List<Controller> scannedControllers = packageScanner.scan(packagesToScan);
        parserControllers.addAll(scannedControllers);

        return parserControllers;
    }
}
