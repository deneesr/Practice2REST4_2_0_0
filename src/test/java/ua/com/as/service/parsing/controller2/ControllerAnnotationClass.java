package ua.com.as.service.parsing.controller2;

import ua.com.as.util.annotation.Controller;
import ua.com.as.util.annotation.Mapping;
import ua.com.as.util.annotation.PathParam;
import ua.com.as.util.enums.HttpMethod;

/**
 *
 */
@Controller
public class ControllerAnnotationClass {

    @Mapping(path = "/postTest/{id}", methods = HttpMethod.POST)
    public String postTest(@PathParam(name = "id") long idName) {
        return "Hello /postTest/{id} method = POST";
    }
}
