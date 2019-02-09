package com.dmmax.vaadin.config;

import com.dmmax.vaadin.view.error.AccessDeniedErrorView;
import com.dmmax.vaadin.view.error.NotFoundErrorView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.InvalidRouteConfigurationException;
import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.server.VaadinServletConfiguration;
import com.vaadin.flow.server.startup.RouteRegistry;
import org.reflections.Reflections;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.util.HashSet;
import java.util.Set;

@WebServlet(urlPatterns = {"/*"}, name = "VaadinServlet", asyncSupported = true)
@VaadinServletConfiguration(ui = AppServlet.MyUI.class, productionMode = false)
public class AppServlet extends VaadinServlet {

    private Set<Class<? extends Component>> routeClasses;

    @Override
    protected void servletInitialized() throws ServletException {
        final ServletContext context = getServletContext();
        final Object routeRegistryObject = context.getAttribute(RouteRegistry.class.getName());

        if (routeRegistryObject == null)
            throw new ServletException("routeRegistryObject is null");

        if ((routeRegistryObject instanceof RouteRegistry) == false)
            throw new ServletException("routeRegistryObject is not of type RouteRegistry");

        final RouteRegistry routeRegistry = (RouteRegistry) routeRegistryObject;

        findRouteClasses();

        try {
            if (!routeRegistry.navigationTargetsInitialized()) {
                routeRegistry.setNavigationTargets(routeClasses);
            }
            if (!routeRegistry.errorNavigationTargetsInitialized()) {
                Set<Class<? extends Component>> errorPages = new HashSet<>();
                errorPages.add(NotFoundErrorView.class);
                errorPages.add(AccessDeniedErrorView.class);
                routeRegistry.setErrorNavigationTargets(errorPages);
            }
        } catch (final InvalidRouteConfigurationException e) {
            throw new ServletException(e);
        }

        super.servletInitialized();
    }

    public static class MyUI extends UI {

    }

    @SuppressWarnings("unchecked")
    private void findRouteClasses() {
        Reflections reflections = new Reflections("com.dmmax.vaadin.view");
        final Set<Class<?>> routeClasses = reflections.getTypesAnnotatedWith(Route.class);

        this.routeClasses = new HashSet<>();

        for (final Class<?> clazz : routeClasses)
            this.routeClasses.add((Class<? extends Component>) clazz);
    }
}