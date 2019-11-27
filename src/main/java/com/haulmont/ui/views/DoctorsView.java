package com.haulmont.ui.views;

import com.haulmont.backend.Doctor;
import com.haulmont.backend.dao.AbstractEntityDAO;
import com.haulmont.backend.dao.DoctorDao;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;

public class DoctorsView extends VerticalLayout implements View {
    public DoctorsView() {
        setSizeFull();
        Label label = new Label("ВРАЧИ");
        Grid<Doctor> grid = new Grid<>();
        AbstractEntityDAO doctorsDao = new DoctorDao();
        List doctors = doctorsDao.getAll();

        grid.setSizeFull();
        grid.setItems(doctors);
        grid.addColumn(Doctor::getName).setId("DOCTOR_NAME").setCaption("ИМЯ");
        grid.addColumn(Doctor::getLastName).setId("DOCTOR_LASTNAME").setCaption("ФАМИЛИЯ");
        grid.addColumn(Doctor::getPatronymic).setId("DOCTOR_PATRONYMIC").setCaption("ОТЧЕСТВО");
        grid.addColumn(Doctor::getSpecialization).setId("DOCTOR_SPECIALIZATION").setCaption("СПЕЦИАЛИЗАЦИЯ");
        grid.addComponentColumn(l -> getEditLayout());

        addComponents(label, grid);
        setExpandRatio(grid, 1);
    }

    private Layout getEditLayout() {
        HorizontalLayout hl = new HorizontalLayout();
        return hl;
    }
}
