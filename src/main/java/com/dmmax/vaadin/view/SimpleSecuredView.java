package com.dmmax.vaadin.view;

import com.dmmax.vaadin.exception.AccessDeniedException;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.ParentLayout;
import org.apache.shiro.SecurityUtils;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletResponse;

public class SimpleSecuredView extends VerticalLayout {

    SimpleSecuredView(@Nonnull String role) {
        if (!SecurityUtils.getSubject().isAuthenticated() ||
                !SecurityUtils.getSubject().hasRole(role)) {
            Notification.show("You doesn't have permission for this page");
            throw new AccessDeniedException("You doesn't have permission for this page");
        }
    }

    void exit() {
        SecurityUtils.getSubject().logout();
        getUI().ifPresent(ui -> ui.navigate(LoginView.class));
    }
}
