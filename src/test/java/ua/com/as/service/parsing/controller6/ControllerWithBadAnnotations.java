package ua.com.as.service.parsing.controller6;

import ua.com.as.util.annotation.Controller;
import ua.com.as.util.annotation.Mapping;
import ua.com.as.util.enums.HttpMethod;

@Controller
public class ControllerWithBadAnnotations {
    @Mapping(path = "/", methods = HttpMethod.GET)
    public String test(String name){
        return name;
    }
}
