package io.beanmapper.config;

import io.beanmapper.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Conventions;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class WebAppInitializer implements WebApplicationInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(WebAppInitializer.class);

    private static final class SessionListener implements HttpSessionListener {
        private static final int SESSION_TIMEOUT = 24 * 60 * 60; // in seconds

        @Override
        public void sessionCreated(HttpSessionEvent se) {
            se.getSession().setMaxInactiveInterval(SESSION_TIMEOUT);
            LOG.info("Session {} created", se.getSession().getId());
        }

        @Override
        public void sessionDestroyed(HttpSessionEvent se) {
            LOG.info("Session {} destroyed", se.getSession().getId());
        }
    }

    private FilterRegistration.Dynamic addFilter(ServletContext servletContext, Filter filter) {
        return servletContext.addFilter(Conventions.getVariableName(filter), filter);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(ApplicationConfig.class);

        FilterRegistration.Dynamic filter = addFilter(servletContext, new OpenEntityManagerInViewFilter());
        filter.addMappingForUrlPatterns(null, false, "/*");

//        filter.setInitParameter("singleSession", "true");
//        filter.addMappingForServletNames(null, true, "dispatcher");

//        addFilter(servletContext, new OpenSessionInViewFilter())
//                .addMappingForUrlPatterns(null, false, "/*");

        // Manage the lifecycle of the root application context
        servletContext.addListener(new ContextLoaderListener(rootContext));
        servletContext.addListener(new SessionListener());

        // Set up the dispatcher for "/api"
        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(WebMvcConfig.class);
        ServletRegistration.Dynamic dispatcher =
                servletContext.addServlet("apiDispatcher", new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/*");

    }
}
