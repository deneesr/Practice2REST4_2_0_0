package ua.com.as.service.conversion;

import ua.com.as.model.containers.ParameterContainer;
import ua.com.as.model.containers.QueryContainer;
import ua.com.as.model.jaxb.Parameter;
import ua.com.as.model.jaxb.Query;
import ua.com.as.util.enums.HttpMethod;
import ua.com.as.util.enums.ResponseType;
import ua.com.as.util.exception.ConfigurationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Converts objects from <code>Query</code> type to <code>QueryContainer</code> type.
 */
class QueryConverter implements Converter<Query, QueryContainer> {

    /**
     * <p>Converts objects from <code>Query</code> type to <code>QueryContainer</code> type.
     *
     * @param from         initial object with type <code>Query</code>.
     * @param infoForExcep info about controller for Exception.
     * @return object with type <code>QueryContainer</code>.
     */
    @Override
    public QueryContainer convert(Query from, String infoForExcep) {
        Converter<Parameter, ParameterContainer> converter = new ParameterConverter();

        String[] pathParts = getParts(from.getPath());
        List<HttpMethod> httpMethods = from.getHttpMethods();

        ResponseType responseType = from.getResponseType();
        String methodName = from.getMethodName();

        String infoForException = infoForExcep + ", Method name: " + methodName;

        List<Parameter> parameters = from.getParameters();
        List<ParameterContainer> parameterContainers = new ArrayList<>();
        parameters.forEach(param -> parameterContainers.add(converter.convert(param, infoForException)));
        parameterContainers.forEach(param -> setPositionToParameterContainer(pathParts, param));

        for (int i = 0; i < parameterContainers.size(); i++) {
            parameterContainers.get(i).setArgPosition(i);
        }
        return new QueryContainer(pathParts, httpMethods,
                responseType, methodName, parameterContainers);
    }

    private String[] getParts(String path) {
        String[] split = path.split("/");

        List<String> parts = new ArrayList<>();

        for (String s : split) {
            if (!s.isEmpty()) {
                parts.add(s);
            }
        }

        return parts.toArray(new String[parts.size()]);
    }

    /**
     * <p>Sets position of parameter to required value specified in <code>url path</code> or if parameter has
     * type <code>QueryParameter</code> then it's position is -1.
     *
     * @param pathParts          parts of url splitted by slash.
     * @param parameterContainer parameter to set position.
     */
    private void setPositionToParameterContainer(String[] pathParts, ParameterContainer parameterContainer) {
        if (parameterContainer.getPosition() == -1) {
            return;
        }

        List<String> pathPartsList = Arrays.stream(pathParts)
                .filter(pathPart -> pathPart.startsWith("{") && pathPart.endsWith("}"))
                .map(pathPart -> pathPart = pathPart.substring(1, pathPart.length() - 1))
                .collect(Collectors.toList());

        for (int i = 0; i < pathPartsList.size(); i++) {
            if (parameterContainer.getName().equals(pathPartsList.get(i))) {
                parameterContainer.setPosition(i);
                return;
            }
        }

        throw new ConfigurationException("Error with configuration of parameter " + parameterContainer.getName());
    }
}
