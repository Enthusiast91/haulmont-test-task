package com.haulmont.ui;

import com.haulmont.ui.views.DoctorsView;
import com.haulmont.ui.views.PatientsView;
import com.haulmont.ui.views.RecipesView;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;

public class MainLayout extends HorizontalLayout {
    private final Panel contentWrapper;
    private final Navigator navigator;

    public MainLayout() {
        setMargin(false);
        addComponent(createNavigationLayout());

        contentWrapper = new Panel();
        contentWrapper.setSizeFull();
        addComponent(contentWrapper);
        setExpandRatio(contentWrapper, 1);

        navigator = new Navigator(UI.getCurrent(), contentWrapper);
        navigator.addView("PatientsView", new PatientsView());
        navigator.addView("DoctorsView", new DoctorsView());
        navigator.addView("RecipesView", new RecipesView());
        navigator.navigateTo("PatientsView");
    }

    private Layout createNavigationLayout() {
        HorizontalLayout outerLayout = new HorizontalLayout();
        VerticalLayout layout = new VerticalLayout();

        Button buttonPatients = new Button("Patients");
        Button buttonDoctors = new Button("Doctors");
        Button buttonRecipes = new Button("Recipes");
        layout.addComponent(buttonPatients);
        layout.addComponent(buttonDoctors);
        layout.addComponent(buttonRecipes);
        layout.setMargin(true);

        buttonPatients.addClickListener(clickEvent -> navigateTo("PatientsView"));
        buttonDoctors.addClickListener(clickEvent -> navigateTo("DoctorsView"));
        buttonRecipes.addClickListener(clickEvent -> navigateTo("RecipesView"));

        outerLayout.addComponent(layout);
        return outerLayout;
    }

    private void navigateTo(String viewId) {
        navigator.navigateTo(viewId);
    }
}
