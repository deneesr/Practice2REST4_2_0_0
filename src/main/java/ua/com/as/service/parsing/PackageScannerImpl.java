package ua.com.as.service.parsing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.as.model.jaxb.Controller;
import ua.com.as.model.jaxb.Parameter;
import ua.com.as.model.jaxb.Query;
import ua.com.as.util.CustomClassLoader;
import ua.com.as.util.annotation.Mapping;
import ua.com.as.util.annotation.PathParam;
import ua.com.as.util.annotation.QueryParam;
import ua.com.as.util.exception.ScanningException;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ua.com.as.util.ReflectionHelper.createClass;
import static ua.com.as.util.ReflectionHelper.getAnnotatedMethods;
import static ua.com.as.util.ReflectionHelper.isController;

/**
 * <p>Scans packages for <code>classes</code>
 * which have {@link ua.com.as.util.annotation.Controller} annotation,
 * find there <code>methods</code> which have {@link Mapping}
 * annotation and find there annotation <code>PathParam</code> or <code>QueryParam</code>
 * in the method parameter and group them into {@link Controller} entities.
 *
 * @see Controller
 */
class PackageScannerImpl implements PackageScanner {
    private static final Logger LOG = LoggerFactory.getLogger(PackageScannerImpl.class);
    private static final String CLASS_FILE_SUFFIX = ".class";
    private static final String DOT_DELIMITER = ".";
    private static final String SLASH_DELIMITER = "/";

    private CustomClassLoader customClassLoader;

    /**
     * <p>Base overridden method for scanning packages.
     *
     * @param packagesToScan packages to scan.
     * @return list of <code>controllers</code>, in which each <code>controller</code>
     * stores specified <code>url path, class name, httpMethods and parameter</code> for
     * successfully invocation.
     * <p>
     * Method scan can return exception <code>ScanningException</code> if parameter of the method have not annotation
     * <code>PathParam</code> or <code>QueryParam</code>.
     */
    @Override
    public List<Controller> scan(List<String> packagesToScan) {
        LOG.debug("Scanning started");
        customClassLoader = new CustomClassLoader();

        List<Controller> scannedControllers = new ArrayList<>();
        for (String packagee : packagesToScan) {
            assert packagee != null;
            scannedControllers.addAll(processPackage(packagee));
        }

        customClassLoader = null;

        LOG.debug("Scanning finished successfully\n");
        return scannedControllers;
    }

    /**
     * <p>First phase of scanning package.
     * <p>Checking package and preparing directory for further work.
     *
     * @param packageToScan package to scan.
     * @return list of <code>controllers</code>, in which each <code>controller</code>
     * stores specified <code>url path, class name, httpMethods and parameter</code> for
     * successfully invocation.
     */
    @SuppressWarnings("ConstantConditions")
    private List<Controller> processPackage(String packageToScan) {

        LOG.debug("Scan package {}", packageToScan);

        String directoryPath = packageToScan.replace(DOT_DELIMITER, SLASH_DELIMITER);
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(directoryPath);
        assert scannedUrl != null;

        File directoryToScan = getDirectoryToScan(scannedUrl);
        List<Controller> controllers = getListControllersFromPackage(directoryToScan, packageToScan);
        LOG.debug("Package {} scanned successfully", packageToScan);
        return controllers;
    }

    /**
     * @param scannedUrl path to package for scanning
     * @return File path to package
     */
    private File getDirectoryToScan(URL scannedUrl) {
        try {
            return new File(scannedUrl.toURI());
        } catch (URISyntaxException e) {
            throw new ScanningException("creating file error");
        }
    }

    /**
     * @param directoryToScan File path to package.
     * @param currentPackage  String path to package.
     * @return List of Controllers from package.
     */
    private List<Controller> getListControllersFromPackage(File directoryToScan, String currentPackage) {
        return Arrays.stream(directoryToScan.listFiles())
                .flatMap(f -> processDirectory(f, currentPackage).stream())
                .collect(Collectors.toList());

    }

    /**
     * <p>Second phase of scanning package.
     * <p>Checks if current file is directory or not, if it is -
     * scan all sub-packages recursive, otherwise call method {@link #createClassIfController}
     * to update path storage.
     *
     * @param directory      file to check if it is directory or not.
     * @param currentPackage package to scan.
     * @return list of <code>controllers</code>, in which each <code>controller</code>
     * stores specified <code>url path, class name, httpMethods and parameter</code> for
     * successfully invocation.
     */
    @SuppressWarnings("ConstantConditions")
    private List<Controller> processDirectory(File directory, String currentPackage) {
        List<Controller> controllers = new ArrayList<>();
        String pathToClass = currentPackage + DOT_DELIMITER + directory.getName();

        if (directory.listFiles() != null && directory.isDirectory()) {
            controllers = Arrays.stream(directory.listFiles())
                    .flatMap(fileChild -> processDirectory(fileChild, pathToClass).stream())
                    .collect(Collectors.toList());
            return controllers;

        } else if (pathToClass.endsWith(CLASS_FILE_SUFFIX)) {
            Controller controller = createClassIfController(pathToClass);
            if (controller != null) {
                controllers.add(controller);
            }
        }

        return controllers;
    }

    /**
     * <p>Create object of class, if class is instance of <code>Controller class</code>.
     * <p>Checks for methods with annotation {@link Mapping}
     * and creating {@link Controller} entities.
     * <p>If object of class is not instance of <code>Controller class</code> returns null.
     *
     * @param pathToClass path to current class.
     * @return Object of class, if class is instance of <code>Controller class</code>.
     * If object of class is not instance of <code>Controller class</code> returns null.
     */
    private Controller createClassIfController(String pathToClass) {
        int endIndex = pathToClass.length() - CLASS_FILE_SUFFIX.length();
        String fullClassName = pathToClass.substring(0, endIndex);

        return isController(createClass(fullClassName, customClassLoader))
                ? createClassIfHasPathAnnotatedMethods(fullClassName) : null;
    }

    /**
     * <p>Create object of class Controller, if that controller has methods marked with <code>Path</code> annotation.
     * If instance of class Controller has not methods marked with <code>Path</code> annotation returns null.
     *
     * @param fullClassName full class name of controller.
     * @return Object of class Controller, if that controller has methods marked with <code>Path</code> annotation.
     * If instance of class Controller has not methods marked with <code>Path</code> annotation returns null.
     */
    private Controller createClassIfHasPathAnnotatedMethods(String fullClassName) {
        Class<?> clazz = createClass(fullClassName, getClass().getClassLoader());
        List<Method> pathAnnotatedMethods = getAnnotatedMethods(clazz, Mapping.class);

        return !pathAnnotatedMethods.isEmpty() ? getController(fullClassName, pathAnnotatedMethods) : null;
    }

    /**
     * <p>Returns object of class controller.
     *
     * @param fullClassName        full class name of controller.
     * @param pathAnnotatedMethods list of methods marked with <code>Path</code> annotation.
     * @return object of class Controller.
     */
    private Controller getController(String fullClassName, List<Method> pathAnnotatedMethods) {

        return new Controller(fullClassName,
                pathAnnotatedMethods.stream()
                        .map(this::getQueryByMethod)
                        .collect(Collectors.toList())
        );
    }

    /**
     * <p>Create object of Query class by specified method which is marked with <code>Path</code> annotation.
     *
     * @param pathAnnotatedMethod method marked with <code>Path</code> annotation.
     * @return object of Query class by specified method which is marked with <code>Path</code> annotation.
     */
    private Query getQueryByMethod(Method pathAnnotatedMethod) {
        return new Query(pathAnnotatedMethod, getParameters(pathAnnotatedMethod));
    }

    /**
     * <p>Create list of object of Parameter class by specified method which is marked with <code>PathParam</code>
     * or <code>QueryParam</code> annotation.
     *
     * @param pathAnnotatedMethod method marked with <code>Path</code> annotation.
     * @return list of object of Parameter class by specified method which is marked with <code>PathParam</code>
     * or <code>QueryParam</code> annotation.
     */
    private List<Parameter> getParameters(Method pathAnnotatedMethod) {
        List<Parameter> parameters = new ArrayList<>();

        for (int i = 0; i < pathAnnotatedMethod.getParameterCount(); i++) {
            Parameter parameter = getParameter(pathAnnotatedMethod, i);
            if (parameter != null) {
                parameters.add(parameter);
            } else {
                throw new ScanningException("Parameter of the method " + pathAnnotatedMethod.getName()
                        + "with number " + i + " have not annotation 'PathParam' or 'QueryParam'");
            }
        }
        return parameters;
    }

    /**
     * <p>Create object of Parameter class by specified method which is marked with <code>PathParam</code>
     * or <code>QueryParam</code> annotation.
     *
     * @param pathAnnotatedMethod method marked with <code>Path</code> annotation.
     * @param paramNumber         - number of parameter in method.
     * @return object of Parameter class by specified method which is marked with <code>PathParam</code>
     * or <code>QueryParam</code> annotation.
     */
    private Parameter getParameter(Method pathAnnotatedMethod, int paramNumber) {
        Parameter parameter = null;
        for (Annotation annotation : pathAnnotatedMethod.getParameterAnnotations()[paramNumber]) {
            if (annotation instanceof PathParam) {
                parameter = createPathParam(pathAnnotatedMethod, paramNumber, ((PathParam) annotation).name());
                break;
            } else if (annotation instanceof QueryParam) {
                parameter = createQueryParam(pathAnnotatedMethod, paramNumber, ((QueryParam) annotation).name());
                break;
            }
        }
        return parameter;
    }

    /**
     * <p>Create object of PathParam class for object Parameter.
     *
     * @param pathAnnotatedMethod method marked with <code>Path</code> annotation.
     * @param paramNumber         number of parameter in method.
     * @param paramName           name of value in the annotation <code>PathParam</code> or <code>QueryParam</code>.
     * @return Object of Parameter class by specified method which is marked with <code>PathParam</code> annotation.
     */
    private Parameter createPathParam(Method pathAnnotatedMethod, int paramNumber, String paramName) {
        Parameter parameter = createParam(pathAnnotatedMethod, paramNumber, paramName);
        parameter.setParameterType(Parameter.ParameterType.PATH_PARAMETER);
        return parameter;
    }

    /**
     * <p>Create object of QueryParam class for object Parameter.
     *
     * @param pathAnnotatedMethod method marked with <code>Path</code> annotation.
     * @param paramNumber         number of parameter in method.
     * @param paramName           name of value in the annotation <code>PathParam</code> or <code>QueryParam</code>.
     * @return Object of Parameter class by specified method which is marked with <code>QueryParam</code> annotation.
     */
    private Parameter createQueryParam(Method pathAnnotatedMethod, int paramNumber, String paramName) {
        Parameter parameter = createParam(pathAnnotatedMethod, paramNumber, paramName);
        parameter.setParameterType(Parameter.ParameterType.QUERY_PARAMETER);
        return parameter;
    }

    /**
     * <p>Create object of Parameter class for object Query.
     *
     * @param pathAnnotatedMethod method marked with <code>Path</code> annotation.
     * @param paramNumber         number of parameter in method.
     * @param paramName           name of value in the annotation <code>PathParam</code> or <code>QueryParam</code>.
     * @return object of Parameter class by specified method which is marked with <code>PathParam</code>
     * or <code>QueryParam</code> annotation.
     */
    private Parameter createParam(Method pathAnnotatedMethod, int paramNumber, String paramName) {
        Parameter parameter = new Parameter();
        parameter.setName(paramName);
        parameter.setType(pathAnnotatedMethod.getParameterTypes()[paramNumber]);
        return parameter;
    }
}
