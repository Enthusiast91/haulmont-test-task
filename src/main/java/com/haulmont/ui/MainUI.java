package com.haulmont.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {
    @Override
    protected void init(VaadinRequest request) {
        MainLayout layout = new MainLayout();
        layout.setSizeFull();
        setContent(layout);
    }
}