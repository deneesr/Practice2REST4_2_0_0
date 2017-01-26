package ua.com.as.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * <p>A custom class loader for releasing memory after scanning packages.
 */
public class CustomClassLoader extends ClassLoader {
    private static final int BUFFER_SIZE = 4096;
    private static final String[] DEFAULT_EXCLUDED_PACKAGES = {"java.", "javax.", "sun.", "oracle."};
    private static final String CLASS_FILE_SUFFIX = ".class";
    private static final Set<String> EXCLUDED_PACKAGES = new HashSet<>(Arrays.asList(DEFAULT_EXCLUDED_PACKAGES));

    /**
     * <p>Public constructor for this class which calls parent constructor
     * with <code>SystemClassLoader</code> as parameter.
     */
    public CustomClassLoader() {
        super(ClassLoader.getSystemClassLoader());
    }

    /**
     * <p>Base method for loading class.
     *
     * @param fullClassName The <code>binary name</code> of the class.
     * @param resolve       If <tt>true</tt> then resolve the class.
     * @return Object class by this binary name.
     * @throws ClassNotFoundException If the class could not be found.
     */
    @Override
    protected Class<?> loadClass(String fullClassName, boolean resolve) throws ClassNotFoundException {
        Class<?> result = null;
        if (!isStandardJavaClass(fullClassName)) {
            result = loading(fullClassName);
        }
        if (result != null) {
            if (resolve) {
                resolveClass(result);
            }
            return result;
        } else {
            return super.loadClass(fullClassName, resolve);
        }
    }

    /**
     * <p>Checks if class with this full class name is standard java class or not.
     *
     * @param fullClassName The <code>binary name</code> of the class.
     * @return true if class with this full class name is started
     * with "java.", "javax.", "sun.", "oracle.", false if not.
     */
    private boolean isStandardJavaClass(String fullClassName) {
        return EXCLUDED_PACKAGES.stream().anyMatch(fullClassName::startsWith);
    }

    /**
     * <p>Loads class by specified binary name.
     *
     * @param fullClassName The <code>binary name</code> of the class.
     * @return Class object by full class name.
     * @throws ClassNotFoundException If the class could not be found.
     */
    private Class<?> loading(String fullClassName) throws ClassNotFoundException {
        Class<?> result = findLoadedClass(fullClassName);
        if (result == null) {
            byte[] bytes = loadBytesForClass(fullClassName);
            if (bytes != null) {
                result = defineClass(fullClassName, bytes, 0, bytes.length);
            }
        }
        return result;
    }

    /**
     * <p>Loads bytes for this class by specified binary name.
     *
     * @param fullClassName The <code>binary name</code> of the class.
     * @return byte array with class representation.
     * @throws ClassNotFoundException If the class could not be found.
     */
    private byte[] loadBytesForClass(String fullClassName) throws ClassNotFoundException {
        String internalName = fullClassName.replace('.', '/') + CLASS_FILE_SUFFIX;

        try (InputStream in = CustomClassLoader.class.getClassLoader().getResourceAsStream(internalName)) {
            return in == null ? null : copyStreamToArray(in);
        } catch (IOException e) {
            throw new ClassNotFoundException("Cannot load resource for class: '" + fullClassName + "'", e);
        }
    }

    /**
     * <p>Copies specified stream to byte array.
     *
     * @param in input stream to be copied.
     * @return byte array with class representation.
     * @throws IOException if any input/output exception occurs.
     */
    private static byte[] copyStreamToArray(InputStream in) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
            return out.toByteArray();
        }
    }
}
