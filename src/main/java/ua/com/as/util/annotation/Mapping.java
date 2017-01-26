package ua.com.as.util.annotation;

import ua.com.as.util.enums.HttpMethod;
import ua.com.as.util.enums.ResponseType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code Path} annotation to mark the methods of class of controller.
 */


/**
 * <p><code>Path</code> annotation indicates that an annotated method is a
 * method which will be invoked with from the outside.
 * <p>Methods with <code>Path</code> annotation may be found via PackageScanner.
 *
 * @see Controller
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Mapping {
    /**
     * <p>Url path value.
     * <p> Not required. Default value is:
     * <code>"/"</code>
     * @return url path
     */
    String path();

    /**
     * <p>Type of return response.
     * @return type of response
     */
    ResponseType responseType() default ResponseType.STRING;

    /**
     * <p>Array of <code>Http methods</code> names. You may specify
     * these methods: <code>GET, POST, PUT, DELETE</code>.
     * <p>Not required. Default value is:
     * <code>{"GET"}</code>
     *
     * @return HTTP method list
     */
    HttpMethod[] methods() default HttpMethod.GET;


}
