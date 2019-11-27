package com.haulmont.ui.views;

import com.haulmont.backend.Doctor;
import com.haulmont.backend.dao.AbstractEntityDAO;
import com.haulmont.backend.dao.DoctorDao;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;

public class DoctorsView extends AbstractView<Doctor> {

    public DoctorsView() {
        super("ВРАЧИ", new DoctorDao());
    }

    @Override
    protected Grid<Doctor> createGrid() {
        Grid<Doctor> grid = new Grid<>();
        grid.addColumn(Doctor::getName).setCaption("ИМЯ");
        grid.addColumn(Doctor::getLastName).setCaption("ФАМИЛИЯ");
        grid.addColumn(Doctor::getPatronymic).setCaption("ОТЧЕСТВО");
        grid.addColumn(Doctor::getSpecialization).setCaption("СПЕЦИАЛИЗАЦИЯ");
        return grid;
    }

    @Override
    protected void addOtherComponents() {}

    @Override
    protected FormLayout createFormLayout(Action action) {
        FormLayout formLayout = new FormLayout();
        TextField nameField = new TextField("ИМЯ");
        TextField lastNameField = new TextField("ФАМИЛИЯ");
        TextField patronymicField = new TextField("ОТЧЕСТВО");
        TextField specializationField = new TextField("СПЕЦИАЛИЗАЦИЯ");

        if (action == Action.UPDATE) {
            nameField.setValue(currentEntity.getName());
            lastNameField.setValue(currentEntity.getLastName());
            patronymicField.setValue(currentEntity.getPatronymic());
            specializationField.setValue(currentEntity.getSpecialization());
        }

        formLayout.addComponents(nameField, lastNameField, patronymicField, specializationField);
        return formLayout;
    }

    @Override
    protected void doUpdate(Doctor doctor) {
        if (currentEntity.equals(doctor)) {
            return;
        }
        currentEntity.setName(doctor.getName());
        currentEntity.setLastName(doctor.getLastName());
        currentEntity.setPatronymic(doctor.getPatronymic());
        currentEntity.setSpecialization(doctor.getSpecialization());

        entityDao.update(currentEntity);
        grid.getDataProvider().refreshAll();
    }

    @Override
    protected Doctor getEntityFromFormLayout(FormLayout formLayout) {
        String name = ((TextField)formLayout.getComponent(0)).getValue().trim();
        String lastName = ((TextField)formLayout.getComponent(1)).getValue().trim();
        String patronymic = ((TextField)formLayout.getComponent(2)).getValue().trim();
        String specialization = ((TextField)formLayout.getComponent(3)).getValue().trim();

        return new Doctor(0, name, lastName, patronymic, specialization);
    }
}
