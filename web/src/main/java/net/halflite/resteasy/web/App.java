package net.halflite.resteasy.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

import net.halflite.resteasy.web.module.AppServletModule;

/**
 * App Main
 *
 */
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        Server server = new Server(8080);

        try {
            final Injector injector = Guice.createInjector(new AppServletModule());
            ServletContextHandler contextHandler = injector.getInstance(ServletContextHandler.class);
            server.setHandler(contextHandler);

            server.start();
            server.join();
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), e);
        } finally {
            server.destroy();
        }
    }
}
