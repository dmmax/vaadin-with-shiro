package com.dmmax.vaadin.view.error;

import com.dmmax.vaadin.exception.AccessDeniedException;
import com.dmmax.vaadin.view.MainView;
import com.dmmax.vaadin.view.secured.LoginView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.theme.NoTheme;

import javax.servlet.http.HttpServletResponse;

@Tag(Tag.DIV)
@NoTheme
public class AccessDeniedErrorView extends Component
    implements HasErrorParameter<AccessDeniedException> {

    @Override
    public int setErrorParameter(BeforeEnterEvent beforeEnterEvent, ErrorParameter<AccessDeniedException> parameter) {

        VerticalLayout contentError = new VerticalLayout();
        contentError.add(new Label("Tried to navigate to a view without correct access rights. Error message: " + parameter.getCustomMessage()));
        contentError.add(new Button("Go to MAIN page", event -> beforeEnterEvent.getUI().navigate(MainView.class)));

        getElement().appendChild(contentError.getElement());

        return HttpServletResponse.SC_FORBIDDEN;
    }
}
