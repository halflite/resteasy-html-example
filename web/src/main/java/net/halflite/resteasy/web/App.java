package net.halflite.resteasy.web;

import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * App Main
 *
 */
public class App {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    
    public static void main(String[] args) {
        try {
            Server server = new Server(8080);
            
            server.start();
            server.join();
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
        }
    }
}
