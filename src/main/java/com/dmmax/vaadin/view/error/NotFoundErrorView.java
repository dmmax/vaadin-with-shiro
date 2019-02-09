package com.dmmax.vaadin.view.error;

import com.dmmax.vaadin.view.LoginView;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.RouteNotFoundError;

import javax.servlet.http.HttpServletResponse;

public class NotFoundErrorView extends RouteNotFoundError {

    @Override
    public int setErrorParameter(BeforeEnterEvent event,
                                 ErrorParameter<NotFoundException> parameter) {
        System.out.println("ERROR MESSAGE: " + parameter.getCustomMessage());
        getElement().setText("My custom not found class!");
        event.rerouteTo(LoginView.class);
        return HttpServletResponse.SC_NOT_FOUND;
    }
}
