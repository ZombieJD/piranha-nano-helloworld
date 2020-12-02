package helloworld;

import cloud.piranha.http.impl.DefaultHttpServer;
import cloud.piranha.http.nano.NanoHttpServerProcessor;
import cloud.piranha.nano.NanoPiranha;
import cloud.piranha.nano.NanoPiranhaBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorldServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, 
            HttpServletResponse response) throws IOException, ServletException {
        try (PrintWriter writer = response.getWriter()) {
            writer.println("Hello World");
            writer.flush();
        }
    }

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
