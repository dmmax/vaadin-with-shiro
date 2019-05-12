package com.dmmax.vaadin.security;

import io.buji.pac4j.filter.SecurityFilter;
import org.pac4j.saml.exceptions.SAMLException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AppSecurityFilter extends SecurityFilter {

    public AppSecurityFilter() {
        super();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            super.doFilter(servletRequest, servletResponse, filterChain);
        } catch (SAMLException e) {
            e.printStackTrace();
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            httpServletResponse.sendRedirect(servletRequest.getServletContext().getContextPath() + "/");
        }
    }
}