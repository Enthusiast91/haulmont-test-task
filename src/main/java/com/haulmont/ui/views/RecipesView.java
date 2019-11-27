package com.haulmont.ui.views;

import com.haulmont.backend.Doctor;
import com.haulmont.backend.Patient;
import com.haulmont.backend.Recipe;
import com.haulmont.backend.RecipePriority;
import com.haulmont.backend.dao.*;
import com.vaadin.data.*;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.server.ComponentSizeValidator;
import com.vaadin.ui.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class RecipesView extends AbstractView<Recipe> {
    public RecipesView() {
        super("РЕЦЕПТЫ", new RecipeDao());
    }

    @Override
    protected Grid<Recipe> createGrid() {
        Grid<Recipe> grid = new Grid<>();
        grid.addColumn(Recipe::getDescription).setCaption("ОПИСАНИЕ");
        grid.addColumn(r -> r.getPatient().getFullName()).setCaption("ПАЦИЕНТ");
        grid.addColumn(r -> r.getDoctor().getFullName()).setCaption("ВРАЧ");
        grid.addColumn(Recipe::getCreationDate).setCaption("ДАТА СОЗДАНИЯ");
        grid.addColumn(r -> {
            if (r.getValidity() == 0) {
                return "НЕОГРАНИЧЕННЫЙ";
            }
            return r.getValidity();
        }).setCaption("СРОК ДЕЙСТВИЯ");
        grid.addColumn(r -> r.getPriority().getTitle()).setCaption("ПРИОРИТЕТ");
        return grid;
    }

    @Override
    protected void addOtherComponents() {

    }
//    private ComboBox<? extends Entity> createComboBox (List<? extends Entity> entities, String caption){
//        ComboBox<? extends Entity> entityComboBox = new ComboBox<>(caption);
//        entityComboBox.setWidth(100.0f, Unit.PERCENTAGE);
//        entityComboBox.setItems(entities);
//        entityComboBox.setEmptySelectionAllowed(false);
//        return entityComboBox;
//    }

    @Override
    protected FormLayout createFormLayout(Action action) {
        List<Patient> patients = new PatientDao().getAll();
        List<RecipePriority> recipePriorities = new RecipePriorityDao().getAll();
        List<Doctor> doctors = new DoctorDao().getAll();

        FormLayout formLayout = new FormLayout();
        formLayout.setWidth("500px");

        ComboBox<Patient> patientComboBox = new ComboBox<>("ПАЦИЕНТ");
        patientComboBox.setWidth(100.0f, Unit.PERCENTAGE);
        patientComboBox.setItems(patients);
        patientComboBox.setItemCaptionGenerator(Patient::getFullName);
        patientComboBox.setEmptySelectionAllowed(false);


        ComboBox<Doctor> doctorComboBox = new ComboBox<>("ВРАЧ");
        doctorComboBox.setWidth(100.0f, Unit.PERCENTAGE);
        doctorComboBox.setItems(doctors);
        doctorComboBox.setItemCaptionGenerator(Doctor::getFullName);
        doctorComboBox.setEmptySelectionAllowed(false);

        DateField creationDateField = new DateField("ДАТА СОЗДАНИЯ");
        creationDateField.setWidth(100.0f, Unit.PERCENTAGE);
        creationDateField.setValue(LocalDate.now());
        creationDateField.setReadOnly(true);

        TextField validityField = new TextField("СРОК ДЕЙСТВИЯ");
        validityField.setWidth(100.0f, Unit.PERCENTAGE);

        CheckBox unlimitedCheckBox = new CheckBox("неограниченный");
        unlimitedCheckBox.setValue(false);
        unlimitedCheckBox.addValueChangeListener(event -> {
            if (event.getValue()) {
                validityField.setValue("НЕОГРАНИЧЕННЫЙ");
                validityField.setReadOnly(true);
            } else {
                validityField.clear();
                validityField.setReadOnly(false);
            }
        });

        ComboBox<RecipePriority> priorityComboBox = new ComboBox<>("ПРИОРИТЕТ");
        priorityComboBox.setWidth(100.0f, Unit.PERCENTAGE);
        priorityComboBox.setItems(recipePriorities);
        priorityComboBox.setItemCaptionGenerator(RecipePriority::getTitle);
        priorityComboBox.setSelectedItem(recipePriorities.get(0));
        priorityComboBox.setTextInputAllowed(false);
        patientComboBox.setEmptySelectionAllowed(false);

        TextArea descriptionField = new TextArea("ОПИСАНИЕ");
        descriptionField.setWidth(100.0f, Unit.PERCENTAGE);

//        Validator<Patient> patientValidator = new Validator<Patient>() {
//            @Override
//            public ValidationResult apply(Patient value, ValueContext context) {
//                if (value == null) {
//                    return ValidationResult.error("Поле пациент не может быть пустым");
//                }
//                return ValidationResult.ok();
//            }
//        };

        if (action == Action.UPDATE) {
            patientComboBox.setValue(currentEntity.getPatient());
            doctorComboBox.setValue(currentEntity.getDoctor());
            doctorComboBox.setReadOnly(true);
            creationDateField.setValue(currentEntity.getCreationDate().toLocalDate());
            if (currentEntity.getValidity() == 0) {
                unlimitedCheckBox.setValue(true);
            } else {
                unlimitedCheckBox.setValue(false);
                validityField.setValue(String.valueOf(currentEntity.getValidity()));
            }
            priorityComboBox.setValue(currentEntity.getPriority());
            descriptionField.setValue(currentEntity.getDescription());
        }

        formLayout.addComponents(patientComboBox,
                doctorComboBox,
                creationDateField,
                validityField,
                unlimitedCheckBox,
                priorityComboBox,
                descriptionField);
        return formLayout;
    }

    @Override
    protected void doUpdate(Recipe recipe) {
        if (currentEntity.equals(recipe)) {
            return;
        }
        currentEntity.setPatient(recipe.getPatient());
        currentEntity.setDescription(recipe.getDescription());
        currentEntity.setValidity(recipe.getValidity());
        currentEntity.setPriority(recipe.getPriority());

        entityDao.update(currentEntity);
        grid.getDataProvider().refreshAll();
    }

    @Override
    protected Recipe getEntityFromFormLayout(FormLayout formLayout) {
        Patient patient = ((ComboBox<Patient>) formLayout.getComponent(0)).getValue();
        Doctor doctor = ((ComboBox<Doctor>) formLayout.getComponent(1)).getValue();
        Date creationDate = Date.valueOf(((DateField) formLayout.getComponent(2)).getValue());
        boolean unlimited = ((CheckBox) formLayout.getComponent(4)).getValue();
        short validity;
        if (unlimited) {
            validity = 0;
        } else {
         validity = Short.valueOf(((TextField) formLayout.getComponent(3)).getValue());
        }
        RecipePriority priority = ((ComboBox<RecipePriority>)formLayout.getComponent(5)).getValue();
        String description = ((TextArea)formLayout.getComponent(6)).getValue();

        Notification.show("Patient= " + patient +
                "\nDoctor= " + doctor +
                "\nCreationDate= " + creationDate +
                "\nUnlimited= " + unlimited +
                "\nValidity= " + validity +
                "\nPriority= " + priority +
                "\nDescription=" + description).setDelayMsec(10000);

        return new Recipe(0, description, creationDate, validity, doctor, patient, priority);
    }
}
