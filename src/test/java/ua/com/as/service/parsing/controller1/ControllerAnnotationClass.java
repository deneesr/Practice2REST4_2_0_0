package ua.com.as.service.parsing.controller1;

import ua.com.as.util.annotation.Controller;
import ua.com.as.util.annotation.Mapping;
import ua.com.as.util.annotation.PathParam;
import ua.com.as.util.annotation.QueryParam;
import ua.com.as.util.enums.HttpMethod;

/**
 *
 */
@Controller
public class ControllerAnnotationClass {

    @Mapping(path = "/getTest/{idGet}", methods = {HttpMethod.GET})
    public String getTest(@PathParam(name = "idGet") long idName, @QueryParam(name = "sum") float sum) {
        return "/getTest/{idGet}. Method: getTest. PathParam : idName - " + idName + " QueryParam: sum " + sum;
    }

    @Mapping(path = "/getPost/{idPost}", methods = {HttpMethod.GET, HttpMethod.POST})
    public String getPost(@PathParam(name = "idPost") int id, @QueryParam(name = "sum") double sum) {
        return "/getPost/{idPost}. Method: getPost. PathParam : idPost - " + id + " QueryParam: sum " + sum;
    }

}