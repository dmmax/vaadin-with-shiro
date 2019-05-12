package com.dmmax.vaadin.view.secured;

import com.dmmax.vaadin.exception.AccessDeniedException;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.apache.shiro.SecurityUtils;

import javax.annotation.Nonnull;

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
