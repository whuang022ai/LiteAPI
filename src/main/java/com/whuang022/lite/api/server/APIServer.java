
package com.whuang022.lite.api.server;

import com.whuang022.lite.api.service.API;
import com.whuang022.lite.api.service.APITest;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * API Server Start
 * http://localhost:8082/api/v1/test?user=test&echo=echo
 * @author whuang022
 */
public class APIServer {
    public static String serverName = "/api";
    public static String apiVersion = "/v1";
    private static Server server = null;

    public static void main(String[] args) throws Exception {

        if (args[0].equals("Test")) {
            int port = Integer.parseInt(args[1]);
            server = new Server(port);
            server = serverInitTest(server);//<- change this
            server.start();

        } else if (args[0].equals("Run")) {
            int port = Integer.parseInt(args[1]);
            server = new Server(port);
            server = serverInitRun(server);//<- change this
            server.start();
        } else if (args[0].equals("Mock")) {
            int port = Integer.parseInt(args[1]);
            server = new Server(port);
            server = serverInitMock(server);//<- change this
            server.start();
        }
    }


    private static Server serverInitTest(Server server) {

        boolean timeStamp=false;
        boolean mockAPI=true;
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(serverName + apiVersion);
        //Setting Router
        //API-Test
        API apiTest = new APITest();
        apiTest.setTimeStamp(timeStamp);
        apiTest.setMockAPI(mockAPI);
        context.addServlet(new ServletHolder(apiTest), apiTest.getRoutePath());
        server.setHandler(context);
        return server;
    }

    private static Server serverInitRun(Server server) {
        boolean timeStamp=true;
        boolean mockAPI=false;
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(serverName + apiVersion);
        //Setting Router
        //API-Test
        API apiTest = new APITest();
        apiTest.setTimeStamp(timeStamp);
        apiTest.setMockAPI(mockAPI);
        context.addServlet(new ServletHolder(apiTest), apiTest.getRoutePath());
        return server;
    }

    private static Server serverInitMock(Server server) {
        boolean timeStamp=true;
        boolean mockAPI=true;
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(serverName + apiVersion);
        //Setting Router
        //API-Test
        API apiTest = new APITest();
        apiTest.setTimeStamp(timeStamp);
        apiTest.setMockAPI(mockAPI);
        context.addServlet(new ServletHolder(apiTest), apiTest.getRoutePath());
        server.setHandler(context);
        return server;
    }
}
