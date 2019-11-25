package com.haulmont.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class PatientsView extends VerticalLayout implements View {
    public PatientsView() {
        Label label = new Label("PatientsView");
        addComponent(label);
    }
}
