package com.haulmont.ui.views;

import com.haulmont.backend.Doctor;
import com.haulmont.backend.Recipe;
import com.haulmont.backend.dao.DoctorDao;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.renderers.Renderer;

import java.util.Locale;
import java.util.Map;

public class DoctorsView extends AbstractPersonView<Doctor> {
    private Button buttonStatictics;

    public DoctorsView() {
        super("ВРАЧИ", DoctorDao.getInstance());
    }

    @Override
    protected void localEnter() {
        if (buttonStatictics.getData() != null) {
            Map<Long, Integer> mapQuantityRecipes = ((DoctorDao) entityDao).getQuantityRecipes();
            grid.removeColumn("STATISTICS");
            grid.addColumn(doctor -> mapQuantityRecipes.get(doctor.getId())).setId("STATISTICS").setCaption("КОЛИЧЕСТВО РЕЦЕПТОВ");
        }
    }

    @Override
    protected void addOtherComponents(VerticalLayout layout) {
        buttonStatictics = new Button("Показать статистику");
        buttonStatictics.setWidth("200px");
        buttonStatictics.setIcon(VaadinIcons.EYE);

        buttonStatictics.addClickListener(event -> {
            if (buttonStatictics.getData() == null) {
                Map<Long, Integer> mapQuantityRecipes = ((DoctorDao) entityDao).getQuantityRecipes();
                grid.addColumn(doctor -> mapQuantityRecipes.get(doctor.getId())).setId("STATISTICS").setCaption("КОЛИЧЕСТВО РЕЦЕПТОВ");
                buttonStatictics.setData(true);
                buttonStatictics.setCaption("Убрать статистику");
                buttonStatictics.setIcon(VaadinIcons.EYE_SLASH);
                return;
            }
            grid.removeColumn("STATISTICS");
            buttonStatictics.setData(null);
            buttonStatictics.setCaption("Показать статистику");
            buttonStatictics.setIcon(VaadinIcons.EYE);
        });
        layout.addComponent(buttonStatictics);
    }

    @Override
    protected void addGridColumn(Grid<Doctor> grid) {
        grid.addColumn(Doctor::getSpecialization).setCaption("СПЕЦИАЛИЗАЦИЯ");
    }

    @Override
    protected Doctor getEmptyPerson() {
        return Doctor.getEmpty();
    }

    @Override
    protected TextField getPersonField() {
        return new TextField("СПЕЦИАЛИЗАЦИЯ");
    }

    @Override
    protected void bindPersonField(TextField specializationField) {
        bindFieldOfNaming(specializationField, Doctor::getSpecialization, Doctor::setSpecialization);
    }
}
