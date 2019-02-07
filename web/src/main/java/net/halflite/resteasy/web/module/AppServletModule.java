package net.halflite.resteasy.web.module;

import java.util.EnumSet;

import javax.inject.Singleton;
import javax.servlet.DispatcherType;

import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

public class AppServletModule extends ServletModule {

    @Override
    protected void configureServlets() {
        install(new ResourceModule());

        bind(GuiceResteasyBootstrapServletContextListener.class).in(Singleton.class);
        bind(HttpServletDispatcher.class).in(Singleton.class);

        bind(GuiceFilter.class).in(Singleton.class);

        serve("/*").with(HttpServletDispatcher.class);
    }

    
    @Provides
    @Singleton
    public ServletContextHandler provideContextHandler(GuiceFilter guiceFilter, GuiceResteasyBootstrapServletContextListener resteasyListener,
            GuiceServletContextListener guiceServletContextListener) {
        final FilterHolder guiceFilterHolder = new FilterHolder(guiceFilter);
        final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addFilter(guiceFilterHolder, "/*", EnumSet.allOf(DispatcherType.class));
        context.addEventListener(resteasyListener);
        context.addEventListener(guiceServletContextListener);
        return context;
    }

    @Provides
    @Singleton
    public GuiceServletContextListener provideContextListener(final Injector injector) {
        return new GuiceServletContextListener() {
            @Override
            protected Injector getInjector() {
                return injector;
            }
        };
    }
}
