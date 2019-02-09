package com.dmmax.vaadin.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

@Route("login")
public class LoginView extends VerticalLayout {

    private Subject currentUser;

    public LoginView() {
        currentUser = SecurityUtils.getSubject();

        TextField tfUsername = new TextField("username");
        tfUsername.setValue("user1");
        tfUsername.setRequired(true);

        TextField tfPassword = new TextField("password");
        tfPassword.setValue("user1");
        tfPassword.setRequired(true);

        Button btnLogin = new Button("Login", event -> auth(tfUsername.getValue(), tfPassword.getValue()));
        btnLogin.focus();

        add(tfUsername, tfPassword, btnLogin);
    }

    private void auth(String username, String password) {
        currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            try {
                currentUser.login(token);
                currentUser.getSession().getAttributeKeys().forEach(action -> System.out.println(action + ":" + currentUser.getSession().getAttribute(action)));

                for (String realmName : currentUser.getPrincipals().getRealmNames()) {
                    System.out.println(currentUser.getPrincipals().fromRealm(realmName));
                }

                if (currentUser.hasRole("admin")) {
                    getUI().ifPresent(ui -> ui.navigate(AdminView.class));
                } else if (currentUser.hasRole("user")) {
                    getUI().ifPresent(ui -> ui.navigate(UserView.class));
                }
                Notification.show("Current user: " + currentUser.getPrincipal());
            } catch (UnknownAccountException | IncorrectCredentialsException e) {
                Notification.show("Incorrect username or password");
            } catch (LockedAccountException lae) {
                Notification.show("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
            } catch (AuthenticationException e) {
                e.printStackTrace();
            }
        } else {
            Notification.show("Already authed");
        }
    }
}
