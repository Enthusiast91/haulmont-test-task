package com.haulmont.ui;

import com.haulmont.ui.views.DoctorsView;
import com.haulmont.ui.views.PatientsView;
import com.haulmont.ui.views.RecipesView;
import com.vaadin.navigator.Navigator;
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
        VerticalLayout layout = new VerticalLayout();

        Button buttonPatients = new Button("ПАЦИЕНТЫ");
        Button buttonDoctors = new Button("ВРАЧИ");
        Button buttonRecipes = new Button("РЕЦЕПТЫ");
        buttonPatients.setWidth("120px");
        buttonDoctors.setWidth("120px");
        buttonRecipes.setWidth("120px");
        buttonPatients.addClickListener(clickEvent -> navigateTo("PatientsView"));
        buttonDoctors.addClickListener(clickEvent -> navigateTo("DoctorsView"));
        buttonRecipes.addClickListener(clickEvent -> navigateTo("RecipesView"));

        layout.addComponent(buttonPatients);
        layout.addComponent(buttonDoctors);
        layout.addComponent(buttonRecipes);
        layout.setMargin(true);
        layout.setWidth("180px");
        layout.setComponentAlignment(buttonPatients, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(buttonDoctors, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(buttonRecipes, Alignment.MIDDLE_CENTER);


        return layout;
    }

    private void navigateTo(String viewId) {
        navigator.navigateTo(viewId);
    }
}
