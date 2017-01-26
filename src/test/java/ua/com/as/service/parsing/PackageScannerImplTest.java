package ua.com.as.service.parsing;

import org.junit.Before;
import org.junit.Test;
import ua.com.as.model.jaxb.Controller;
import ua.com.as.util.exception.ScanningException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class PackageScannerImplTest {

    private static final String PACKAGE_CONTROLLER_1 = "ua.com.as.service.parsing.controller1";
    private static final String PACKAGE_CONTROLLER_2 = "ua.com.as.service.parsing.controller2";
    private static final String PACKAGE_CONTROLLER_3 = "ua.com.as.service.parsing.controller3";
    private static final String PACKAGE_CONTROLLER_5 = "ua.com.as.service.parsing.controller77";
    private static final String PACKAGE_CONTROLLER_6 = "ua.com.as.service.parsing.controller6";

    private PackageScanner ps;
    private List<String> packagesToScan;

    @Before
    public void init() {
        ps = new PackageScannerImpl();
        packagesToScan = new ArrayList<>();
    }

    @Test
    public void testScan() {
        packagesToScan.add(PACKAGE_CONTROLLER_1);
        List<Controller> paths = ps.scan(packagesToScan);
        assertEquals(1, paths.size());
    }

    @Test
    public void testScanTwoPackages() {
        packagesToScan.add(PACKAGE_CONTROLLER_1);
        packagesToScan.add(PACKAGE_CONTROLLER_2);
        List<Controller> paths = ps.scan(packagesToScan);
        assertEquals(3, paths.size());
    }

    @Test
    public void testScanClassWithoutPathMethods() {
        packagesToScan.add(PACKAGE_CONTROLLER_3);
        List<Controller> paths = ps.scan(packagesToScan);
        assertEquals(0, paths.size());
    }

    @Test
    public void testScanWithEmptyClass() {
        packagesToScan.add(PACKAGE_CONTROLLER_2);
        List<Controller> paths = ps.scan(packagesToScan);
        assertEquals(2, paths.size());
    }

    @Test(expected = AssertionError.class)
    public void testScanWithNull() {
        packagesToScan.add(null);
        packagesToScan.add(null);
        packagesToScan.add(null);
        ps.scan(packagesToScan);
    }

    @Test(expected = AssertionError.class)
    public void testScanNonexistentPath() {
        packagesToScan.add(PACKAGE_CONTROLLER_5);
        ps.scan(packagesToScan);
    }

    @Test(expected = ScanningException.class)
    public void testWithScanningException() {
        packagesToScan.add(PACKAGE_CONTROLLER_6);
        ps.scan(packagesToScan);
    }
}
