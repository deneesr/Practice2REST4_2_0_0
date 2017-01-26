package ua.com.as.util.adapters;

import org.junit.Test;
import ua.com.as.util.exception.ParsingException;

import static org.junit.Assert.assertEquals;

public class ClassTypeAdapterTest {
    private static ClassTypeAdapter adapter = new ClassTypeAdapter();

    @Test
    public void unmarshal() throws Exception {
        assertEquals(Byte.class, adapter.unmarshal("Byte"));
        assertEquals(Short.class, adapter.unmarshal("Short"));
        assertEquals(Integer.class, adapter.unmarshal("Integer"));
        assertEquals(Long.class, adapter.unmarshal("Long"));
        assertEquals(Float.class, adapter.unmarshal("Float"));
        assertEquals(Double.class, adapter.unmarshal("Double"));
        assertEquals(Character.class, adapter.unmarshal("Character"));
        assertEquals(String.class, adapter.unmarshal("String"));
        assertEquals(byte.class, adapter.unmarshal("byte"));
        assertEquals(short.class, adapter.unmarshal("short"));
        assertEquals(int.class, adapter.unmarshal("int"));
        assertEquals(long.class, adapter.unmarshal("long"));
        assertEquals(float.class, adapter.unmarshal("float"));
        assertEquals(double.class, adapter.unmarshal("double"));
        assertEquals(char.class, adapter.unmarshal("char"));
    }

    @Test(expected = ParsingException.class)
    public void unmarshalError() throws Exception {
        adapter.unmarshal("noSuchType");
    }

    @Test
    public void marshal() throws Exception {
        assertEquals("java.lang.Byte", adapter.marshal(Byte.class));
        assertEquals("java.lang.Short", adapter.marshal(Short.class));
        assertEquals("java.lang.Integer", adapter.marshal(Integer.class));
        assertEquals("java.lang.Long", adapter.marshal(Long.class));
        assertEquals("java.lang.Float", adapter.marshal(Float.class));
        assertEquals("java.lang.Double", adapter.marshal(Double.class));
        assertEquals("java.lang.Character", adapter.marshal(Character.class));
        assertEquals("java.lang.String", adapter.marshal(String.class));
        assertEquals("byte", adapter.marshal(byte.class));
        assertEquals("short", adapter.marshal(short.class));
        assertEquals("int", adapter.marshal(int.class));
        assertEquals("long", adapter.marshal(long.class));
        assertEquals("float", adapter.marshal(float.class));
        assertEquals("double", adapter.marshal(double.class));
        assertEquals("char", adapter.marshal(char.class));
        assertEquals("boolean", adapter.marshal(boolean.class));
        assertEquals("java.lang.Boolean", adapter.marshal(Boolean.class));
    }
}