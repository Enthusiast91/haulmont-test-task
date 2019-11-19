package com.haulmont.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        //layout.setSizeFull();
        layout.setMargin(true);
        layout.addComponent(new Label("Main UI"));
        Button button = new Button("Click");
        button.addClickListener(event -> Notification.show("The button was clicked", Notification.Type.TRAY_NOTIFICATION));
        layout.addComponent(button);
        setContent(layout);
    }
}