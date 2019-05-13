package com.dmmax.vaadin.view;

import com.dmmax.vaadin.view.saml2.Saml2CarprizaView;
import com.dmmax.vaadin.view.saml2.Saml2OktaView;
import com.dmmax.vaadin.view.secured.LoginView;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.router.Route;
import io.buji.pac4j.subject.Pac4jPrincipal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

@Route
public class MainView extends Composite<VerticalLayout> {

    public MainView() {

        String urlLoginPage = UI.getCurrent().getRouter().getUrl(LoginView.class);
        Anchor loginPage = new Anchor(urlLoginPage, "Login page");

        getContent().add(loginPage);
        createSaml2Components();
    }

    private void createSaml2Components() {

        if (isSaml2Auth()) {
            createUserInfo();
        } else {
            createLoginLayout();
        }
    }

    private boolean isSaml2Auth() {

        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.getPrincipals() != null) {
            final Pac4jPrincipal principal = subject.getPrincipals().oneByType(Pac4jPrincipal.class);
            return principal != null;
        }
        return false;
    }

    private void createUserInfo() {

        Subject subject = SecurityUtils.getSubject();
        final Pac4jPrincipal principal = subject.getPrincipals().oneByType(Pac4jPrincipal.class);
        getContent().add(new Label("User: " + principal.getName()));
        getContent().add(new Button("Logout", event -> {
            SecurityUtils.getSubject().logout();
            UI.getCurrent().getPage().reload();
        }));
    }

    private void createLoginLayout() {

        HorizontalLayout layoutLoginSAML = new HorizontalLayout();

        String urlCarpriza = UI.getCurrent().getRouter().getUrl(Saml2CarprizaView.class);
        Anchor saml2Carpriza = new Anchor(urlCarpriza, "Login Carpriza");

        String urlOkta = UI.getCurrent().getRouter().getUrl(Saml2OktaView.class);
        Anchor saml2Okta = new Anchor(urlOkta, "Login Okta");

        layoutLoginSAML.add(saml2Carpriza, saml2Okta);

        getContent().add(layoutLoginSAML);
    }
}
