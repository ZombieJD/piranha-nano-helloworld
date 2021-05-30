package helloworld;

import cloud.piranha.http.impl.DefaultHttpServer;
import cloud.piranha.http.nano.NanoHttpServerProcessor;
import cloud.piranha.nano.NanoPiranha;
import cloud.piranha.nano.NanoPiranhaBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * This Servlet illustrates how to create a 'Hello World' server using Piranha
 * Nano.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class HelloWorldServlet extends HttpServlet {

    /**
     * Handle the GET requests.
     *
     * @param request the request.
     * @param response the response.
     * @throws IOException when an I/O error occurs.
     * @throws ServletException when a Servlet error occurs.
     */
    @Override
    protected void doGet(HttpServletRequest request, 
            HttpServletResponse response) throws IOException, ServletException {
        try (PrintWriter writer = response.getWriter()) {
            writer.println("Hello World");
            writer.flush();
        }
    }

    /**
     * Main method.
     * 
     * <p>
     * The FUNCTIONS_CUSTOMHANDLER_PORT is the environment variable that defines
     * the port number the Azure Functions runtime wants this server to run on.
     * When the environment variable is not present the server will run on port
     * 8080.
     * </p>
     * 
     * @param arguments the command-line arguments.
     * @throws Exception when an error occurs.
     */
    public static void main(String[] arguments) throws Exception {
        NanoPiranha piranha = new NanoPiranhaBuilder()
                .servlet("HelloWorld", new HelloWorldServlet())
                .build();
        
        int port = System.getenv("FUNCTIONS_CUSTOMHANDLER_PORT") != null 
                ? Integer.parseInt(System.getenv("FUNCTIONS_CUSTOMHANDLER_PORT")) : 8080;
        
        DefaultHttpServer server = new DefaultHttpServer(port, 
                new NanoHttpServerProcessor(piranha), false);
        
        server.start();
    }
}
