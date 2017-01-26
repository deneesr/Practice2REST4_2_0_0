package ua.com.as.service;

/**
 *
 */
public class ControllerClassForContextManager {

    public String testGetXml(byte id) {
        return "ControllerClassForContextManager, method: testGetXml, return PathParam: " + id;
    }

    public String testGetWithQueryParamXml(int id, String name) {
        return "ControllerClassForContextManager, method: testGetWithQueryParamXml, PathParam: "
                + id + " QueryParam: " + name;
    }

    public String testPostWithQueryParamXml(short index, char alphabet) {
        return "ControllerClassForContextManager, method: testGetWithQueryParamXml, PathParam: "
                + index + " QueryParam: " + alphabet;
    }

    public String postTestXml(short index, String alphabet) {
        return "ControllerClassForContextManager, method: postTestXml, PathParam1: "
                + index + " PathParam2: " + alphabet;
    }

    public String putTestXml(int index, String alphabet, char key) {
        return "ControllerClassForContextManager, method: postTestXml, PathParam1: "
                + index + " PathParam2: " + alphabet + " QueryParam: " + key;
    }

    public String deleteTestXml() {
        return "ControllerClassForContextManager, method: deleteTestXml";
    }

}
