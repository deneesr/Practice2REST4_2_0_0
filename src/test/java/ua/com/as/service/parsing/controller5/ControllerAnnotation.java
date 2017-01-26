package ua.com.as.service.parsing.controller5;

import ua.com.as.util.annotation.Controller;
import ua.com.as.util.annotation.Mapping;
import ua.com.as.util.annotation.PathParam;
import ua.com.as.util.annotation.QueryParam;
import ua.com.as.util.enums.HttpMethod;

/**
 *
 */
@Controller
public class ControllerAnnotation {

    @Mapping(path = "/getTest/{idGet}", methods = HttpMethod.GET)
    public String getTest(@PathParam(name = "idGet") int idName, @QueryParam(name = "nameGet") String userName) {
        return "Hello GET, {idGet}, idName: " + idName + ", (Query - nameGet) userName: " + userName;
    }

    @Mapping(path = "/getTest/{idPost}/block", methods = HttpMethod.GET)
    public String getPost(@PathParam(name = "idPost") int id, @QueryParam(name = "namePost") String name) {
        return "Hello POST, {idPost}, id: " + id + ", (Query - namePost) name: " + name;
    }

    @Mapping(path = "/getTestString/{idGet}", methods = HttpMethod.POST)
    public String getTestString(@PathParam(name = "idGet") String idName, @QueryParam(name = "nameGet") String userName) {
        return "Hello GET, {idGet}, idName: " + idName + ", (Query - nameGet) userName: " + userName;
    }

    @Mapping(path = "/getTestString/{idPost}/block", methods = HttpMethod.POST)
    public String getPostString(@PathParam(name = "idPost") String id, @QueryParam(name = "namePost") String name) {
        return "Hello POST, {idPost}, id: " + id + ", (Query - namePost) name: " + name;
    }

}
