package com.haulmont.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class DoctorsView extends VerticalLayout implements View {
    public DoctorsView() {
        Label label = new Label("DoctorsView");
        addComponent(label);
    }
}
