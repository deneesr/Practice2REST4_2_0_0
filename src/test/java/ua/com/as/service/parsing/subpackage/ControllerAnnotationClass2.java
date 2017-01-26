package ua.com.as.service.parsing.subpackage;

import ua.com.as.util.annotation.Controller;
import ua.com.as.util.annotation.Mapping;
import ua.com.as.util.annotation.PathParam;
import ua.com.as.util.enums.HttpMethod;

/**
 *
 */
@Controller
public class ControllerAnnotationClass2 {

    @Mapping(path = "/getTestSubpackage/{id}", methods = HttpMethod.GET)
    public String getTestSubpackage(@PathParam(name = "id") long idName) {
        return "Hello ControllerAnnotationClass2 /getTestSubpackage/{id} method = GET";
    }
}
