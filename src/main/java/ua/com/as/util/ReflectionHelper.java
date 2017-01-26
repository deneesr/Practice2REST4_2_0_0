package ua.com.as.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.as.util.annotation.Controller;
import ua.com.as.util.exception.InvocationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Class provides api for working with reflection and some util methods.
 * <p>It also handles exceptions, which are thrown.
 */
public class ReflectionHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionHelper.class);

    /**
     * <p>Private constructor to forbid creating instances of this class.
     */
    private ReflectionHelper() {
    }

    /**
     * <p>Returns the <code>Class</code> object associated with the class or
     * interface with the given <code>String</code> name.
     *
     * @param fullClassName the fully qualified name of the desired class
     * @param classLoader   classloader which will load this class
     * @return the <code>Class</code> object for the class with the
     * specified name.
     */
    public static Class<?> createClass(String fullClassName, ClassLoader classLoader) {
        try {
            Class<?> clazz = classLoader.loadClass(fullClassName);
            LOGGER.trace(String.format("Controller class created successfully: %s", clazz));
            return clazz;
        } catch (ClassNotFoundException e) {
            throw new InvocationException("Class is not found", e);
        }
    }

    /**
     * <p>Returns a <code>Method</code> object that reflects the specified
     * declared method of the class or interface represented by this
     * <code>Class</code> object with this <code>String</code> name and
     * this array of <code>Class</code> objects of parameter classes.
     *
     * @param controllerClass   object of the controller class where you are creating method
     * @param methodName        the name of the method
     * @param parametersClasses the parameter classes array
     * @return the <code>Method</code> object for the method of this class
     * matching the specified name and parameters
     */
    static Method createMethod(Class<?> controllerClass, String methodName, Class<?>... parametersClasses) {
        try {
            Method method = controllerClass.getDeclaredMethod(methodName, parametersClasses);
            LOGGER.trace(String.format("Method create successfully: %s", method));
            return method;
        } catch (NoSuchMethodException e) {
            throw new InvocationException("Method is not found", e);
        }
    }

    /**
     * <p>Returns a <code>Method</code> object that reflects the specified
     * declared method of the class or interface represented by this
     * <code>Class</code> object with this <code>String</code> name and
     * this array of <code>Class</code> objects of parameter classes.
     *
     * @param controllerClass   object of the controller class where you are creating method
     * @param methodName        the name of the method
     * @param parametersClasses the parameter classes list
     * @return the <code>Method</code> object for the method of this class
     * matching the specified name and parameters
     */
    public static Method createMethod(Class<?> controllerClass, String methodName, List<Class<?>> parametersClasses) {
        Class<?>[] classes = parametersClasses.toArray(new Class<?>[parametersClasses.size()]);
        return createMethod(controllerClass, methodName, classes);
    }

    /**
     * <p>Invokes the underlying method represented by this <code>Method</code>
     * object, on the specified object with the specified parameters.
     *
     * @param controllerClass    object of the controller class where you are invocation method
     * @param methodToInvoke     method which you are invocation
     * @param allParametersValue the parameters array
     * @return the <code>Object</code> result of invocation the method
     */
    static Object invoke(Class<?> controllerClass, Method methodToInvoke, Object... allParametersValue) {
        try {
            assert controllerClass != null && methodToInvoke != null;
            methodToInvoke.setAccessible(true);
            Object invocationResult = methodToInvoke.invoke(controllerClass.newInstance(), allParametersValue);
            LOGGER.debug("Invocation successfully");
            return invocationResult;
        } catch (ReflectiveOperationException e) {
            throw new InvocationException("Invocation error", e);
        }
    }

    /**
     * <p>Invokes the underlying method represented by this <code>Method</code>
     * object, on the specified object with the specified parameters.
     *
     * @param controllerClass    object of the controller class where you are invocation method
     * @param methodToInvoke     method which you are invocation
     * @param allParametersValue the parameters list
     * @return the <code>Object</code> result of invocation the method
     */
    public static Object invoke(Class<?> controllerClass, Method methodToInvoke, List<?> allParametersValue) {
        return invoke(controllerClass, methodToInvoke, allParametersValue.toArray());
    }

    /**
     * <p>Determines whether specified class is client's controller or not.
     * <p>If class is annotated with <code>Controller</code> annotation
     * regardless of the classloader then returns <code>true</code>,
     * if not then returns <code>false</code>.
     *
     * @param clazz class which will be checked of being controller.
     * @return if class is annotated with <code>Controller</code> annotation
     * regardless of the classloader then returns <code>true</code>,
     * if not then returns <code>false</code>.
     */
    public static boolean isController(Class<?> clazz) {
        return Arrays.stream(clazz.getAnnotations())
                .map(classAnnotation -> classAnnotation.annotationType().getCanonicalName())
                .anyMatch(annotationCanonicalName -> annotationCanonicalName
                        .equals(Controller.class.getCanonicalName()));
    }

    /**
     * <p>Returns all methods from class which are presented with specified annotation.
     *
     * @param clazz           class to check.
     * @param annotationClass verifiable annotation.
     * @return <code>list</code> of methods which are presented with specified annotation.
     */
    public static List<Method> getAnnotatedMethods(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(annotationClass))
                .collect(Collectors.toList());
    }

    /**
     * <p>Returns value with primitive type.
     * <p>Works only for <code>primitives</code>,
     * their <code>wrappers</code> and <code>String</code>.
     *
     * @param stringValue string value of object.
     * @param type        type which will be used during wrapping value.
     * @return value with primitive type.
     */
    @SuppressWarnings("all")
    public static Object getValueByType(String stringValue, Class<?> type) {
        if (type.equals(Byte.class) || type.equals(byte.class)) {
            if (Long.valueOf(stringValue) <= Byte.MAX_VALUE && Long.valueOf(stringValue) >= Byte.MIN_VALUE) {
                return Byte.valueOf(stringValue);
            } else {
                throw new InvocationException("Variable type does not match the value");
            }
        } else if (type.equals(Short.class) || type.equals(short.class)) {
            if (Long.valueOf(stringValue) <= Short.MAX_VALUE && Long.valueOf(stringValue) >= Short.MIN_VALUE) {
                return Short.valueOf(stringValue);
            } else {
                throw new InvocationException("Variable type does not match the value");
            }
        } else if (type.equals(Integer.class) || type.equals(int.class)) {
            if (Long.valueOf(stringValue) <= Integer.MAX_VALUE && Long.valueOf(stringValue) >= Integer.MIN_VALUE) {
                return Integer.valueOf(stringValue);
            } else {
                throw new InvocationException("Variable type does not match the value");
            }
        } else if (type.equals(Long.class) || type.equals(long.class)) {
            try {
                return Long.valueOf(stringValue);
            } catch (NumberFormatException e) {
                throw new InvocationException("Variable type does not match the value");
            }
        } else if (type.equals(Float.class) || type.equals(float.class)) {
            if (Double.valueOf(stringValue) <= Float.MAX_VALUE && Double.valueOf(stringValue) >= Float.MIN_VALUE) {
                return Float.valueOf(stringValue);
            } else {
                throw new InvocationException("Variable type does not match the value");
            }
        } else if (type.equals(Double.class) || type.equals(double.class)) {
            try {
                return Double.valueOf(stringValue);
            } catch (NumberFormatException e) {
                throw new InvocationException("Variable type does not match the value");
            }
        } else if (type.equals(Character.class) || type.equals(char.class)) {

            if (Character.getNumericValue(stringValue.charAt(0)) <= Character.MAX_VALUE
                    && Character.getNumericValue(stringValue.charAt(0)) >= Character.MIN_VALUE) {
                return stringValue.charAt(0);
            } else {
                throw new InvocationException("Variable type does not match the value");
            }
        } else {
            return stringValue;
        }
    }
}
