package com.dmmax.vaadin.view;

import com.dmmax.vaadin.view.saml2.Saml2UserView;
import com.dmmax.vaadin.view.secured.LoginView;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends Composite<VerticalLayout> {

    public MainView() {

        String urlLoginPage = UI.getCurrent().getRouter().getUrl(LoginView.class);
        Anchor loginPage = new Anchor(urlLoginPage, "Login page");

        String urlSaml2PUserPage = UI.getCurrent().getRouter().getUrl(Saml2UserView.class);
        Anchor saml2UserPage = new Anchor(urlSaml2PUserPage, "SAML 2 User page");

        getContent().add(loginPage, saml2UserPage);
    }
}
