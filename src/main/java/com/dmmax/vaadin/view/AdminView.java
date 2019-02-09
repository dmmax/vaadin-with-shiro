package com.dmmax.vaadin.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.Route;

@Route("admin")
public class AdminView extends SimpleSecuredView {

    public AdminView() {
        super("admin");
        add(new Button("Exit", event -> exit()));
        add(new Label("You are on admin page"));
    }
}
