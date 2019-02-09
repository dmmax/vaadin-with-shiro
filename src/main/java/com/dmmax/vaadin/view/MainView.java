package com.dmmax.vaadin.view;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends Composite<VerticalLayout> {

    public MainView() {
        getContent().add(
                new Button("Go to Login page?",
                        e -> getUI().ifPresent(ui -> ui.navigate(LoginView.class)))
        );
    }
}
