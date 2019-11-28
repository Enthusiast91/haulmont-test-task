package com.haulmont.ui.views;

import com.haulmont.backend.Patient;
import com.haulmont.backend.dao.PatientDao;
import com.haulmont.ui.components.Message;
import com.haulmont.ui.components.Validation;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.Setter;
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
    protected FormLayout createFormLayout(Action action) {
        FormLayout formLayout = new FormLayout();
        TextField nameField = new TextField("ИМЯ");
        TextField lastNameField = new TextField("ФАМИЛИЯ");
        TextField patronymicField = new TextField("ОТЧЕСТВО");
        TextField phoneField = new TextField("ТЕЛЕФОН");
        Patient patient;

        if (action == Action.ADD) {
            patient = Patient.getEmpty();
            binder.setBean(patient);
        }

        binderForFieldName(nameField, Patient::getName, Patient::setName);
        binderForFieldName(lastNameField, Patient::getLastName, Patient::setLastName);
        binderForFieldName(patronymicField, Patient::getPatronymic, Patient::setPatronymic);
        binder.forField(phoneField)
                .withValidator(Validation::phoneIsValid, Message.phoneValidationError())
                .bind(Patient::getPhone, Patient::setPhone);

        formLayout.addComponents(nameField, lastNameField, patronymicField, phoneField);
        return formLayout;
    }

    private void binderForFieldName(TextField field, ValueProvider<Patient, String> getter, Setter<Patient, String> setter) {
        binder.forField(field)
                .withValidator(Validation::namedIsValid, Message.nameValidationError())
                .bind(getter, setter);
    }

    @Override
    protected void addOtherComponents() {
    }
}
