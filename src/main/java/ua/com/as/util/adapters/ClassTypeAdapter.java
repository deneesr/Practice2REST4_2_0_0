package ua.com.as.util.adapters;

import ua.com.as.util.exception.ParsingException;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * <p>Converter type from <code>String</code> to <code>Class</code>.
 */
public class ClassTypeAdapter extends XmlAdapter<String, Class<?>> {

    /**
     * Converts a value type to a bound type.
     *
     * @param v The value to be converted. Can be null.
     * @return instance of class.
     * @throws Exception if there's an error during the conversion.
     */
    @Override
    public Class<?> unmarshal(String v) throws Exception {
        switch (v) {
            case "Byte":
                return Byte.class;
            case "Short":
                return Short.class;
            case "Integer":
                return Integer.class;
            case "Long":
                return Long.class;
            case "Float":
                return Float.class;
            case "Double":
                return Double.class;
            case "Character":
                return Character.class;
            case "String":
                return String.class;
            case "byte":
                return byte.class;
            case "short":
                return short.class;
            case "int":
                return int.class;
            case "long":
                return long.class;
            case "float":
                return float.class;
            case "double":
                return double.class;
            case "char":
                return char.class;
            default:
                throw new ParsingException("No such type!");
        }
    }

    /**
     * Converts a bound type to a value type.
     *
     * @param v The value to be converted. Can be null.
     * @return name of class.
     * @throws Exception if there's an error during the conversion.
     */
    @Override
    public String marshal(Class<?> v) throws Exception {
        return v.getCanonicalName();
    }
}
