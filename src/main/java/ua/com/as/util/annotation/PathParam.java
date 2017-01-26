package ua.com.as.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>PathParameter annotation.
 * <p>This annotation applies to the method argument in class which is marked
 * with the Controller annotation. It means that this argument is a path parameter,
 * and specifies a name of this parameter in path.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface PathParam {
    /**
     * @return name of this parameter
     */
    String name();
}
