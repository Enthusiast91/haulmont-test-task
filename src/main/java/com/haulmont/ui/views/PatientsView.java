package com.haulmont.ui.views;

import com.haulmont.backend.Doctor;
import com.haulmont.backend.Patient;
import com.haulmont.backend.dao.PatientDao;
import com.vaadin.shared.ui.MarginInfo;
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

        formLayout.addComponents(nameField, lastNameField, patronymicField, phoneField);
        return formLayout;
    }

    @Override
    protected void doAdd(Patient patient) {
        entityDao.add(patient);
        entityList.clear();
        entityList.addAll(entityDao.getAll());
        grid.getDataProvider().refreshAll();
    }

    @Override
    protected void doUpdate(Patient patient) {
        String name = patient.getName();
        String lastName = patient.getLastName();
        String patronymic =patient.getPatronymic();
        String phone = patient.getPhone();

        boolean isUpdate = false;
        if (!currentEntity.getName().equals(name)) {
            currentEntity.setName(name);
            isUpdate = true;
        }
        if (!currentEntity.getLastName().equals(lastName)) {
            currentEntity.setLastName(lastName);
            isUpdate = true;
        }
        if (!currentEntity.getPatronymic().equals(patronymic)) {
            currentEntity.setPatronymic(patronymic);
            isUpdate = true;
        }
        if (!currentEntity.getPhone().equals(phone)) {
            currentEntity.setPhone(phone);
            isUpdate = true;
        }
        if (isUpdate) {
            entityDao.update(currentEntity);
            grid.getDataProvider().refreshAll();
        }
    }

    @Override
    protected Patient getEntityFromFormLayout(FormLayout formLayout) {
        String name = ((TextField)formLayout.getComponent(0)).getValue().trim();
        String lastName = ((TextField)formLayout.getComponent(1)).getValue().trim();
        String patronymic = ((TextField)formLayout.getComponent(2)).getValue().trim();
        String phone = ((TextField)formLayout.getComponent(3)).getValue().trim();

        return new Patient(0, name, lastName, patronymic, phone);
    }
}
