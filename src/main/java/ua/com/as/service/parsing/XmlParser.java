package ua.com.as.service.parsing;

import ua.com.as.model.jaxb.Controller;

import java.util.List;

/**
 * <p><code>XmlParser</code> interface provides base api for parsing xml files.
 */
interface XmlParser {

    /**
     * <p>Get controllers from xml file.
     *
     * @return list of controllers
     */
    List<Controller> getControllers();

    /**
     * <p>Get packages to scan from xml file.
     *
     * @return list of package names
     */
    List<String> getPackagesToScan();
}
