package com.haulmont.ui.views;

import com.haulmont.backend.Doctor;
import com.haulmont.backend.Patient;
import com.haulmont.backend.dao.DoctorDao;
import com.haulmont.backend.dao.Entity;
import com.haulmont.ui.components.Message;
import com.haulmont.ui.components.Validation;
import com.vaadin.data.Binder;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.Setter;
import com.vaadin.ui.*;

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
    protected FormLayout createFormLayout(Action action) {
        FormLayout formLayout = new FormLayout();
        TextField nameField = new TextField("ИМЯ");
        TextField lastNameField = new TextField("ФАМИЛИЯ");
        TextField patronymicField = new TextField("ОТЧЕСТВО");
        TextField specializationField = new TextField("СПЕЦИАЛИЗАЦИЯ");
        Doctor doctor;

        if (action == Action.ADD) {
            doctor = Doctor.getEmpty();
            binder.setBean(doctor);
        }

        binderForFieldName(nameField, Doctor::getName, Doctor::setName);
        binderForFieldName(lastNameField, Doctor::getLastName, Doctor::setLastName);
        binderForFieldName(patronymicField, Doctor::getPatronymic, Doctor::setPatronymic);
        binder.forField(specializationField)
                .withValidator(Validation::phoneIsValid, Message.phoneValidationError())
                .bind(Doctor::getSpecialization, Doctor::setSpecialization);

        formLayout.addComponents(nameField, lastNameField, patronymicField, specializationField);
        return formLayout;
    }

    private void binderForFieldName(TextField field, ValueProvider<Doctor, String> getter, Setter<Doctor, String> setter) {
        binder.forField(field)
                .withValidator(Validation::namedIsValid, Message.nameValidationError())
                .bind(getter, setter);
    }

    @Override
    protected void addOtherComponents() {
        Button buttonStatictics = new Button("Показать статистику");
        buttonStatictics.addClickListener(event -> {

        });
    }

    private Window createStatisticsWindow() {
        Window window = new Window();

        return window;
    }

}
