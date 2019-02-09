package com.dmmax.vaadin.config;

import com.dmmax.vaadin.security.WebConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.DispatcherType;
import java.net.URI;
import java.net.URL;
import java.util.EnumSet;

public class VaadinShiroServer extends Server {

    public VaadinShiroServer(int port) {
        super(port);

        createHandler();
    }

    private void createHandler() {
        try {
            URL webRootLocation = this.getClass().getResource("/META-INF/resources/");
            URI webRootUri = webRootLocation.toURI();

            WebAppContext handler = new WebAppContext();

            handler.setInitParameter("org.atmosphere.websocket.suppressJSR356", "true");

            handler.setServer(this);
            handler.setContextPath("/");
            AppServlet servlet = new AppServlet();

            handler.addServlet(new ServletHolder(servlet), "/*");
            handler.setClassLoader(Thread.currentThread().getContextClassLoader());
            handler.setInitParameter("ui", AppServlet.MyUI.class.getCanonicalName());
            handler.setBaseResource(Resource.newResource(webRootUri));
            handler.addFilter(WebConfig.ShiroFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
            handler.addEventListener(new WebConfig.ShiroListener());


            setHandler(handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
