package ua.com.as.service.conversion;

import ua.com.as.model.containers.ParameterContainer;
import ua.com.as.model.jaxb.Parameter;
import ua.com.as.util.exception.ConfigurationException;

import static ua.com.as.model.jaxb.Parameter.ParameterType.PATH_PARAMETER;
import static ua.com.as.model.jaxb.Parameter.ParameterType.QUERY_PARAMETER;

/**
 * <p>Converts objects from <code>Parameter</code> type to <code>ParameterContainer</code> type.
 */
class ParameterConverter implements Converter<Parameter, ParameterContainer> {

    /**
     * <p>Converts objects from <code>Parameter</code> type to <code>ParameterContainer</code> type.
     *
     * @param from             initial object with type <code>Parameter</code>.
     * @param infoForException info for Exception.
     * @return object with type <code>ParameterContainer</code>.
     */
    @Override
    public ParameterContainer convert(Parameter from, String infoForException) {
        String name = from.getName();
        Class<?> type = from.getType();

        checkPathParam(type, from, infoForException);

        int position = 0;
        if (from.getParameterType() == QUERY_PARAMETER) {
            position = -1;
        }


        return new ParameterContainer(name, type, position);
    }

    private void checkPathParam(Class<?> type, Parameter from, String infoForException) {
        if (from.getParameterType() == PATH_PARAMETER) {
            if (type.equals(Float.class)
                    || type.equals(float.class)
                    || type.equals(Double.class)
                    || type.equals(double.class)
                    || type.equals(Character.class)
                    || type.equals(char.class)) {
                throw new ConfigurationException("You have: (" + type.getName() +
                        ") in the Path parameter. Controller: " + infoForException +
                        ", please, point string or integer");
            }
        }
    }
}
