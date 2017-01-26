package ua.com.as.model.containers;

/**
 * <p>Class for storing all information about parameter which is part of <code>QueryContainer</code>.
 */
public class ParameterContainer {
    private String name;
    private int position;
    private int argPosition;
    private Class<?> type;

    /**
     * <p>Constructor which specifies fields <code>name</code>, <code>type</code>
     * and <code>position</code> in <code>ParameterContainer</code>.
     *
     * @param name     field <code>name</code> in <code>ParameterContainer</code>.
     * @param type     field <code>type</code> in <code>ParameterContainer</code>.
     * @param position field <code>position</code> in <code>ParameterContainer</code>.
     */
    public ParameterContainer(String name, Class<?> type, int position) {
        this.name = name;
        this.type = type;
        this.position = position;
    }

    /**
     * <p>Setter.
     *
     * @param position set value of field <code>position</code> in <code>ParameterContainer</code>.
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * <p>Setter.
     *
     * @param argPosition set value of field <code>position</code> in <code>ParameterContainer</code>.
     */
    public void setArgPosition(int argPosition) {
        this.argPosition = argPosition;
    }

    /**
     * <p>Getter.
     *
     * @return value of field <code>argPosition</code> in <code>ParameterContainer</code>.
     */
    public int getArgPosition() {
        return argPosition;
    }

    /**
     * <p>Getter.
     *
     * @return value of field <code>name</code> in <code>ParameterContainer</code>.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Getter.
     *
     * @return value of field <code>position</code> in <code>ParameterContainer</code>.
     */
    public int getPosition() {
        return position;
    }

    /**
     * <p>Getter.
     *
     * @return value of field <code>type</code> in <code>ParameterContainer</code>.
     */
    public Class<?> getType() {
        return type;
    }

}
