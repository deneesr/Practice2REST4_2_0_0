package ua.com.as.service.invocation;

import ua.com.as.model.containers.ParameterContainer;
import ua.com.as.model.containers.ResponseContainer;
import ua.com.as.util.ReflectionHelper;

import java.lang.reflect.Method;
import java.util.*;

import static ua.com.as.util.ReflectionHelper.getValueByType;

/**
 * <p>Class is responsible for all invocation process in clients' controllers.
 */
public class InvocationManager {

    private InvocationManager() {
    }

    /**
     * <p>Method with all logic of invocation.
     *
     * @param queryParameters     names and values of query parameters.
     * @param responseContainer   container to invoke method in.
     * @param pathParameterValues values of all non static parameters in url path.
     * @return filled responseContainer with result of invocation.
     */
    public static Object invoke(Map<String, String> queryParameters,
                                ResponseContainer responseContainer, List<String> pathParameterValues) {

        if (!isCached(responseContainer)) {
            cache(responseContainer);
        }

        List<Object> params = new ArrayList<>(pathParameterValues.size());

        Map<Integer, Object> paramPathMap = validatePathParameters(responseContainer, pathParameterValues);

        if (!queryParameters.isEmpty()) {
            paramPathMap.putAll(validateQueryParameters(responseContainer, queryParameters));
        }

        params.addAll(paramPathMap.values());

        return invoke(responseContainer, params);
    }

    /**
     * <p>Main method with all logic of invocation.
     *
     * @param responseContainer container to invoke method in.
     * @param parametersValue   values of all non static parameters in url path and query value.
     * @return filled responseContainer with result of invocation.
     */
    private static Object invoke(ResponseContainer responseContainer, List<Object> parametersValue) {

        Method methodToInvoke = responseContainer.getMethod();
        List<Object> allParametersValue = getAllParametersValueForMethod(methodToInvoke, parametersValue);

        return ReflectionHelper.invoke(
                responseContainer.getClazz(),
                methodToInvoke,
                allParametersValue);
    }

    /**
     * <p>Fill all query parameters with their values.
     *
     * @param responseContainer object <code>ResponseContainer</code>.
     * @param queryParameters   names and values of query parameters of <code>ResponseContainer</code>.
     * @return validate of parameter value in query parameter.
     */
    private static Map<Integer, Object> validateQueryParameters(ResponseContainer responseContainer,
                                                                Map<String, String> queryParameters) {

        List<ParameterContainer> parameters = responseContainer.getParameters();
        Map<Integer, Object> paramMap = new HashMap<>();

        for (Map.Entry<String, String> entry : queryParameters.entrySet()) {
            String paramName = entry.getKey();
            String paramValue = entry.getValue();

            parameters.stream()
                    .filter(p -> p.getPosition() == -1 && p.getName().equals(paramName))
                    .forEach(p -> {
                        Class<?> type = p.getType();
                        Object parameterValue = getValueByType(paramValue, type);
                        paramMap.put(p.getArgPosition(), parameterValue);
                    });
        }
        return paramMap;
    }

    /**
     * <p>Fill all path parameters with their values.
     *
     * @param responseContainer   object <code>ResponseContainer</code>.
     * @param pathParameterValues values of all non static parameters in url path.
     * @return validate of parameter value in path parameter.
     */
    private static Map<Integer, Object> validatePathParameters(ResponseContainer responseContainer,
                                                               List<String> pathParameterValues) {

        List<ParameterContainer> parametersContainer = responseContainer.getParameters();

        Map<Integer, Object> paramMap = new HashMap<>();

        for (int i = 0; i < pathParameterValues.size(); i++) {
            for (ParameterContainer p : parametersContainer) {
                if (p.getPosition() == -1) {
                    paramMap.put(p.getArgPosition(), null);
                }
                if (p.getPosition() == i) {
                    Class<?> type = p.getType();
                    String parameterString = pathParameterValues.get(i);
                    Object parameterValue = getValueByType(parameterString, type);
                    paramMap.put(p.getArgPosition(), parameterValue);
                }
            }
        }
        return paramMap;
    }

    /**
     * <p>Cache current <code>ResponseContainer</code>.
     *
     * @param responseContainer object <code>ResponseContainer</code> to cache.
     */
    private static void cache(ResponseContainer responseContainer) {
        List<Class<?>> paramClasses = new ArrayList<>();

        responseContainer.getParameters().forEach(p -> paramClasses.add(p.getType()));

        Class<?> clazz = ReflectionHelper.createClass(responseContainer.getFullClassName(),
                InvocationManager.class.getClassLoader());
        Method method = ReflectionHelper.createMethod(clazz, responseContainer.getMethodName(), paramClasses);

        responseContainer.setClazz(clazz);
        responseContainer.setMethod(method);
    }

    /**
     * <p>Checks if current <code>ResponseContainer</code> is cached or not.
     *
     * @param responseContainer <code>ResponseContainer</code> to check.
     * @return true if fields <code>clazz</code> and <code>method</code> are not null,
     * false otherwise.
     */
    private static boolean isCached(ResponseContainer responseContainer) {
        return responseContainer.getClazz() != null && responseContainer.getMethod() != null;
    }

    private static List<Object> getAllParametersValueForMethod(Method methodToInvoke, List<Object> parametersValue) {

        int qtyParametersInMethod = methodToInvoke.getParameters().length;
        List<Object> allParametersValue = new ArrayList<>(qtyParametersInMethod);

        for (int i = 0; i < qtyParametersInMethod; i++) {
            if (i < parametersValue.size()) {
                allParametersValue.add(parametersValue.get(i));
            } else {
                allParametersValue.add(null);
            }
        }

        return allParametersValue;
    }
}
