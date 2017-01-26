package ua.com.as.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p><code>Controller</code> annotation indicates that an annotated class is a
 * client's controller.
 * <p>Classes with <code>Controller</code> annotation may be found via <code>PackageScanner</code>.
 *
 * @see Mapping
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Controller {
}
