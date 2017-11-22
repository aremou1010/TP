/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.tp;

import com.bootcamp.tp.rest.v1.LivrableRestService;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import java.net.URISyntaxException;
import javax.xml.transform.OutputKeys;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import java.lang.InterruptedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ARESKO
 */
public class MyServer {

//    System.out.print("Hello World ;-)...");
    private static final int SERVER_PORT = 8080;
    private static final Logger LOG = LoggerFactory.getLogger(MyServer.class);

    public static void main(String[] args) {

        Server server = null;

        try {
            // Workaround for resources from JAR files
            Resource.setDefaultUseCaches(false);

            // Build the Swagger Bean.
            buildSwagger();

            // Holds handlers
            final HandlerList handlers = new HandlerList();

            // Handler for Swagger UI, static handler.
            handlers.addHandler(buildSwaggerUI());

            // Handler for Entity Browser and Swagger
            handlers.addHandler(buildContext());

            // Start server
            server = new Server(SERVER_PORT);
            server.setHandler(handlers);
            server.start();
            server.join();

            //System.out.println(server.isRunning());
        } catch (Exception e) {
            LOG.error( "There are an error starting up the service ", e );
        }

    }

    private static void buildSwagger() {
        // This configures Swagger
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setResourcePackage(LivrableRestService.class.getPackage().getName());        //"com.bootcamp.rest.controllers"
        beanConfig.setScan(true);
        beanConfig.setBasePath("/");
//        beanConfig.setBasePath("/TPSERVICE");
        beanConfig.setDescription("Livrable Rest API to access all the livrable ressources");
        beanConfig.setTitle("Projet BOOTCAMP");
        System.out.println(LivrableRestService.class.getPackage().getName());
    }

    // This starts the Swagger UI at http://localhost:8080/docs
    private static ContextHandler buildSwaggerUI() throws URISyntaxException {
        //to configure swagger UI

        final ResourceHandler swaggerUIResourceHandler = new ResourceHandler();
        swaggerUIResourceHandler.setResourceBase(MyServer.class.getClassLoader().getResource("webapp").toURI().toString());
        System.out.println(MyServer.class.getClassLoader().getResource("webapp").toURI().toString());
        final ContextHandler swaggerUIContext = new ContextHandler();
        swaggerUIContext.setContextPath("/docs/");
//        swaggerUIContext.setContextPath("/TPSERVICE/docs/");
        swaggerUIContext.setHandler(swaggerUIResourceHandler);
        return swaggerUIContext;

    }

    private static ContextHandler buildContext() {

        ResourceConfig resourceConfig = new ResourceConfig();
        // io.swagger.jaxrs.listing loads up Swagger resources
        resourceConfig.packages(LivrableRestService.class.getPackage().getName(), ApiListingResource.class.getPackage().getName());
        ServletContainer servletContainer = new ServletContainer(resourceConfig);
        ServletHolder holder = new ServletHolder(servletContainer);
        ServletContextHandler handl = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handl.setContextPath("/");
//        handl.setContextPath("/TPSERVICE");
        handl.addServlet(holder, "/*");
        return handl;
    }

//        String string = "";
//        try {
//
//            // Step1: Let's 1st read file from fileSystem
//            // Change CrunchifyJSON.txt path here
//            InputStream livrableInputStream = new FileInputStream("C:\\Users\\ARESKO\\Documents\\NetBeansProjects\\BOOTCAMP PROJECT\\web-service\\tplivrable\\livrable.json");
//            InputStreamReader livrableReader = new InputStreamReader(livrableInputStream);
//            BufferedReader br = new BufferedReader(livrableReader);
//            String line;
//            while ((line = br.readLine()) != null) {
//                string += line + "\n";
//            }
//
//            JSONObject jsonObject = new JSONObject(string);
//            System.out.println(jsonObject);
//
//            // Step2: Now pass JSON File Data to REST Service
//            try {
//                URL url = new URL("http://localhost:8080/service/livrables/create");
//                URLConnection connection = url.openConnection();
//                connection.setDoOutput(true);
//                connection.setRequestProperty("Content-Type", "application/json");
//                connection.setConnectTimeout(5000);
//                connection.setReadTimeout(5000);
//                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
//                out.write(jsonObject.toString());
//                out.close();
//
//                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//
//                while (in.readLine() != null) {
//                }
//                System.out.println("\n Livrable REST Service Invoked Successfully..");
//                in.close();
//            } catch (Exception e) {
//                System.out.println("\n Error while calling Livrable REST Service");
//                System.out.println(e);
//            }
//
//            br.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    //}
}
