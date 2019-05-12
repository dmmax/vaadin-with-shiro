package com.dmmax.vaadin.view.saml2;

import com.dmmax.vaadin.exception.AccessDeniedException;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RoutePrefix;
import com.vaadin.flow.router.RouterLayout;
import org.apache.shiro.SecurityUtils;

@RoutePrefix("saml2")
public class Saml2CommonView extends VerticalLayout implements RouterLayout {

    public Saml2CommonView() {

        if (!SecurityUtils.getSubject().isAuthenticated()) {
            Notification.show("You doesn't have permission for this page");
            throw new AccessDeniedException("You doesn't have permission for this page");
        }
    }
}