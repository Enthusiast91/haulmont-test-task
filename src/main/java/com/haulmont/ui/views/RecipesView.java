package com.haulmont.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class RecipesView extends VerticalLayout implements View {
    public RecipesView() {
        Label label = new Label("RecipesView");
        addComponent(label);
    }
}
