package com.dmmax.vaadin.view.secured;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;

import com.vaadin.flow.router.Route;

@Route("user")
public class UserView extends SimpleSecuredView {

    public UserView() {
        super("user");
        add(new Button("Exit", event -> exit()));
        add(new Label("User page"));
    }


}
