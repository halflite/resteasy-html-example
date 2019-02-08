package net.halflite.resteasy.web.module;

import java.util.EnumSet;
import java.util.Map;

import javax.inject.Singleton;
import javax.servlet.DispatcherType;

import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.webjars.servlet.WebjarsServlet;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

import freemarker.ext.servlet.FreemarkerServlet;

public class AppServletModule extends ServletModule {

    @Override
    protected void configureServlets() {
        install(new ResourceModule());

        bind(GuiceResteasyBootstrapServletContextListener.class).in(Singleton.class);
        bind(HttpServletDispatcher.class).in(Singleton.class);

        bind(WebjarsServlet.class).in(Singleton.class);
        bind(FreemarkerServlet.class).in(Singleton.class);

        bind(GuiceFilter.class).in(Singleton.class);

        serve("/webjars/*").with(WebjarsServlet.class);
        Map<String, String> fmInitParam = ImmutableMap.<String, String> builder()
                .put("TemplatePath", "classpath:views")
                .put("NoCache", "true")
                .put("ResponseCharacterEncoding", "fromTemplate")
                .put("ExceptionOnMissingTemplate", "true")
                .put("incompatible_improvements", "2.3.28")
                .put("template_exception_handler", "rethrow")
                .put("template_update_delay", "0")
                .put("default_encoding", "UTF-8")
                .put("output_encoding", "UTF-8")
                .put("locale", "ja_JP")
                .put("number_format", "0.##########")
                .build();
        serve("*.ftl").with(FreemarkerServlet.class, fmInitParam);
        serve("/*").with(HttpServletDispatcher.class);
    }

    @Provides
    @Singleton
    public ServletContextHandler provideContextHandler(GuiceFilter guiceFilter,
            GuiceResteasyBootstrapServletContextListener resteasyListener, GuiceServletContextListener guiceServletContextListener) {
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
