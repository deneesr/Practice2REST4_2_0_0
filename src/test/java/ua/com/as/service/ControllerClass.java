package ua.com.as.service;

public class ControllerClass {

    public String testGet(byte id) {
        return "ControllerClass, method: testGet, return PathParam: " + id;
    }

    public String testGetWithQueryParam(int id, String name) {
        return "ControllerClass, method: testGetWithQueryParam, PathParam: " + id + " QueryParam: " + name;
    }

    public String testPostWithQueryParam(short index, char alphabet) {
        return "ControllerClass, method: testGetWithQueryParam, PathParam: " + index + " QueryParam: " + alphabet;
    }

    public String postTest(short index, String alphabet) {
        return "ControllerClass, method: postTest, PathParam1: " + index + " PathParam2: " + alphabet;
    }

    public String putTest(int index, String alphabet, char key) {
        return "ControllerClass, method: postTest, PathParam1: " + index + " PathParam2: "
                + alphabet + " QueryParam: " + key;
    }

    public String deleteTest() {
        return "ControllerClass, method: deleteTest";
    }
}
