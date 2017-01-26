package ua.com.as.util;

import org.junit.Test;
import ua.com.as.util.exception.InvocationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 */
@SuppressWarnings("all")
public class ReflectionHelperTest {
    @Test(expected = InvocationException.class)
    public void testCreateClassNotFound() {
        ReflectionHelper.createClass("", getClass().getClassLoader());
    }

    @Test(expected = InvocationException.class)
    public void testCreateMethodNotFound() {
        Class clazz = ReflectionHelperTest.class;
        ReflectionHelper.createMethod(clazz, "");
    }

    @Test(expected = InvocationException.class)
    public void testInvokeError() throws NoSuchMethodException {
        Class clazz = ReflectionHelperTest.class;
        Method method = clazz.getMethod("testInvokeError");
        ReflectionHelper.invoke(clazz, method);
    }

    @Test(expected = AssertionError.class)
    public void testInvokeNullClass() {
        ReflectionHelper.invoke(getClass(), null);
    }

    @Test(expected = AssertionError.class)
    public void testInvokeNullMethod() throws NoSuchMethodException {
        ReflectionHelper.invoke(null, getClass().getDeclaredMethod("testInvokeNullMethod"));
    }

    @Test
    public void testConstructor() throws Exception {
        Constructor constructor = ReflectionHelper.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void getValueByTypeTest() {

        assertEquals(ReflectionHelper.getValueByType("127", byte.class), (byte) 127);
        assertEquals(ReflectionHelper.getValueByType("-32768", Short.class), (short) -32768);
        assertEquals(ReflectionHelper.getValueByType("8", Integer.class), 8);
        assertEquals(ReflectionHelper.getValueByType("888", Long.class), (long) 888);
        assertEquals(ReflectionHelper.getValueByType("8.1", Float.class), 8.1f);
        assertEquals(ReflectionHelper.getValueByType("8.2", Double.class), 8.2);
        assertEquals(ReflectionHelper.getValueByType("a", Character.class), 'a');
        assertEquals(ReflectionHelper.getValueByType("abc", String.class), "abc");

    }

    @Test(expected = InvocationException.class)
    public void getValueByTypeByteExceptionTest() throws InvocationException {
        ReflectionHelper.getValueByType("129", byte.class);
    }

    @Test(expected = InvocationException.class)
    public void getValueByTypeShortExceptionTest() throws InvocationException {
        ReflectionHelper.getValueByType("40000", short.class);
    }

    @Test(expected = InvocationException.class)
    public void getValueByTypeIntegerExceptionTest() throws InvocationException {
        ReflectionHelper.getValueByType("2147483648", int.class);
    }

    @Test(expected = InvocationException.class)
    public void getValueByTypeLongExceptionTest() throws InvocationException {
        ReflectionHelper.getValueByType("92233720368547758078", long.class);
    }

    @Test(expected = InvocationException.class)
    public void getValueByTypeFloatExceptionTest() throws InvocationException {
        ReflectionHelper.getValueByType("3.4e+39f", float.class);
    }

    @Test(expected = InvocationException.class)
    public void getValueByTypeDoubleExceptionTest() throws InvocationException {
        ReflectionHelper.getValueByType("1.7е+309", double.class);
    }

    @Test(expected = InvocationException.class)
    public void getValueByTypeCharExceptionTest() throws InvocationException {
        ReflectionHelper.getValueByType("-1", char.class);
    }
}
