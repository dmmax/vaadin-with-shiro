package com.dmmax.vaadin.view.saml2;

import com.dmmax.vaadin.view.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.buji.pac4j.subject.Pac4jPrincipal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

@Route(value = "user", layout = Saml2CommonView.class)
@PageTitle("SAML 2 User page")
public class Saml2UserView extends VerticalLayout {

    public Saml2UserView() {
        super();

        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.getPrincipals() != null) {
            final Pac4jPrincipal principal = subject.getPrincipals().oneByType(Pac4jPrincipal.class);
            if (principal != null) {
                add(new Label("Successfully auth!"));
                add(new Label("User: " + principal.getName()));
                add(new Button("Logout", event -> {
                    SecurityUtils.getSubject().logout();
                    getUI().ifPresent(ui -> ui.navigate(MainView.class));
                }));
            }
        } else {
            add(new Label("Can't auth using SAML"));
        }
        Button btnBack = new Button("Back", event -> getUI().ifPresent(ui -> ui.navigate(MainView.class)));
        add(btnBack);
    }
}
