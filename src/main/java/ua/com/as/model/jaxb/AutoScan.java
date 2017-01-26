package ua.com.as.model.jaxb;

import javax.xml.bind.annotation.*;

/**
 * <p>Configuration object that has to be converted from xml file
 * to get list of packages to scan.
 */
@XmlRootElement(name = "auto-scan")
@XmlAccessorType(XmlAccessType.FIELD)
public class AutoScan {

    @XmlAttribute
    private String packages;

    /**
     * Getter.
     *
     * @return packages to scan.
     */
    public String getPackages() {
        return packages;
    }

    /**
     * Setter.
     *
     * @param packages set packages to scan.
     */
    public void setPackages(String packages) {
        this.packages = packages;
    }
}
