package com.haulmont.ui.views;

import com.haulmont.backend.Patient;
import com.haulmont.backend.dao.PatientDao;
import com.vaadin.data.Binder;
import com.vaadin.ui.*;

public class PatientsView extends AbstractView<Patient> {

    public PatientsView() {
        super("ПАЦИЕНТЫ", new PatientDao());
    }

    @Override
    protected Grid<Patient> createGrid() {
        Grid<Patient> grid = new Grid<>();
        grid.addColumn(Patient::getName).setCaption("ИМЯ");
        grid.addColumn(Patient::getLastName).setCaption("ФАМИЛИЯ");
        grid.addColumn(Patient::getPatronymic).setCaption("ОТЧЕСТВО");
        grid.addColumn(Patient::getPhone).setCaption("ТЕЛЕФОН");
        return grid;
    }

    @Override
    protected void addOtherComponents() {
    }

    @Override
    protected FormLayout createFormLayout(Action action) {
        FormLayout formLayout = new FormLayout();
        TextField nameField = new TextField("ИМЯ");
        TextField lastNameField = new TextField("ФАМИЛИЯ");
        TextField patronymicField = new TextField("ОТЧЕСТВО");
        TextField phoneField = new TextField("ТЕЛЕФОН");

        if (action == Action.UPDATE) {
            nameField.setValue(currentEntity.getName());
            lastNameField.setValue(currentEntity.getLastName());
            patronymicField.setValue(currentEntity.getPatronymic());
            phoneField.setValue(currentEntity.getPhone());
        }

        Binder<Patient> binder = new Binder<>();
        binder.forField(nameField).withValidator(text -> text.length() >= 2, "Error eept").bind(Patient::getName, Patient::setName);

        formLayout.addComponents(nameField, lastNameField, patronymicField, phoneField);
        return formLayout;
    }

    @Override
    protected void doUpdate(Patient patient) {
        if (currentEntity.equals(patient)) {
            return;
        }
        currentEntity.setName(patient.getName());
        currentEntity.setLastName(patient.getLastName());
        currentEntity.setPatronymic(patient.getPatronymic());
        currentEntity.setPhone(patient.getPhone());

        entityDao.update(currentEntity);
        grid.getDataProvider().refreshAll();
    }

    @Override
    protected Patient getEntityFromFormLayout(FormLayout formLayout) {
        String name = ((TextField) formLayout.getComponent(0)).getValue().trim();
        String lastName = ((TextField) formLayout.getComponent(1)).getValue().trim();
        String patronymic = ((TextField) formLayout.getComponent(2)).getValue().trim();
        String phone = ((TextField) formLayout.getComponent(3)).getValue().trim();

        return new Patient(0, name, lastName, patronymic, phone);
    }
}
