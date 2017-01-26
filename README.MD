Implement a servlet that will analyze the incoming requests in REST format.

The request can contain parameters ("/query/users?id=1") and payload data for HTTP METHOD (POST, PUT, DELETE).
After analyzing the request, depending on the PATH ("/query/users") and HTTP METHOD (GET, POST, PUT, DELETE) call the necessary method in the controller, the result should be sent as a response to the request.
Practices > REST4J Framework > Untitled Diagram.png

Controller.
Simple POJO class.
public String anyMethodName (HttpServletRequest request) {
...
}

In the current version REST4J Framework, PATH does't include dynamic data ("/query/user/1")
