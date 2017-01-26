package ua.com.as.service.conversion;

import org.junit.Test;
import ua.com.as.model.containers.ParameterContainer;
import ua.com.as.model.jaxb.Parameter;
import ua.com.as.util.exception.ConfigurationException;

public class ParameterConverterTest {

    @Test(expected = ConfigurationException.class)
    public void convertFloat1Path() throws Exception {
        Parameter p = new Parameter();
        p.setName("testParameter");
        p.setParameterType(Parameter.ParameterType.PATH_PARAMETER);
        p.setType(float.class);

        Converter<Parameter, ParameterContainer> c = new ParameterConverter();
        c.convert(p, "");
    }

    @Test(expected = ConfigurationException.class)
    public void convertDouble1Path() throws Exception {
        Parameter p = new Parameter();
        p.setName("testParameter");
        p.setParameterType(Parameter.ParameterType.PATH_PARAMETER);
        p.setType(double.class);

        Converter<Parameter, ParameterContainer> c = new ParameterConverter();
        c.convert(p, "");
    }

    @Test(expected = ConfigurationException.class)
    public void convertCharacter1Path() throws Exception {
        Parameter p = new Parameter();
        p.setName("testParameter");
        p.setParameterType(Parameter.ParameterType.PATH_PARAMETER);
        p.setType(char.class);

        Converter<Parameter, ParameterContainer> c = new ParameterConverter();
        c.convert(p, "");
    }

    @Test(expected = ConfigurationException.class)
    public void convertFloat2Path() throws Exception {
        Parameter p = new Parameter();
        p.setName("testParameter");
        p.setParameterType(Parameter.ParameterType.PATH_PARAMETER);
        p.setType(Float.class);

        Converter<Parameter, ParameterContainer> c = new ParameterConverter();
        c.convert(p, "");
    }

    @Test(expected = ConfigurationException.class)
    public void convertDouble2Path() throws Exception {
        Parameter p = new Parameter();
        p.setName("testParameter");
        p.setParameterType(Parameter.ParameterType.PATH_PARAMETER);
        p.setType(Double.class);

        Converter<Parameter, ParameterContainer> c = new ParameterConverter();
        c.convert(p, "");
    }

    @Test(expected = ConfigurationException.class)
    public void convertCharacter2Path() throws Exception {
        Parameter p = new Parameter();
        p.setName("testParameter");
        p.setParameterType(Parameter.ParameterType.PATH_PARAMETER);
        p.setType(Character.class);

        Converter<Parameter, ParameterContainer> c = new ParameterConverter();
        c.convert(p, "");
    }

}