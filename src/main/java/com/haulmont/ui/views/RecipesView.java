package com.haulmont.ui.views;

import com.haulmont.backend.Doctor;
import com.haulmont.backend.Patient;
import com.haulmont.backend.Recipe;
import com.haulmont.backend.RecipePriority;
import com.haulmont.backend.dao.*;
import com.haulmont.ui.components.Message;
import com.haulmont.ui.components.Validation;
import com.haulmont.ui.components.Viewable;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.ShortRangeValidator;
import com.vaadin.ui.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

    private ComboBox createComboBox (List entities, String caption){
        ComboBox entityComboBox = new ComboBox<>(caption);
        entityComboBox.setWidth(100.0f, Unit.PERCENTAGE);
        entityComboBox.setItems(entities);
        entityComboBox.setEmptySelectionAllowed(false);
        return entityComboBox;
    }

    @Override
    protected FormLayout createFormLayout(Action action) {
        List<Patient> patients = new PatientDao().getAll();
        List<RecipePriority> recipePriorities = new RecipePriorityDao().getAll();
        List<Doctor> doctors = new DoctorDao().getAll();

        FormLayout formLayout = new FormLayout();
        formLayout.setWidth("500px");

        ComboBox<Patient> patientComboBox = createComboBox(patients, "ПАЦИЕНТ");
        patientComboBox.setItemCaptionGenerator(Patient::getFullName);

        ComboBox<Doctor> doctorComboBox = createComboBox(doctors, "ВРАЧ");
        doctorComboBox.setItemCaptionGenerator(Doctor::getFullName);

        DateField creationDateField = new DateField("ДАТА СОЗДАНИЯ");
        creationDateField.setWidth(100.0f, Unit.PERCENTAGE);
        creationDateField.setValue(LocalDate.now());

        TextField validityField = new TextField("СРОК ДЕЙСТВИЯ");
        validityField.setWidth(100.0f, Unit.PERCENTAGE);

        Label unlimitedLabel = new Label("0 = неограниченный срок");
        unlimitedLabel.setEnabled(false);

        ComboBox<RecipePriority> priorityComboBox = createComboBox(recipePriorities, "ПРИОРИТЕТ");
        priorityComboBox.setItemCaptionGenerator(RecipePriority::getTitle);
        priorityComboBox.setSelectedItem(recipePriorities.get(0));
        priorityComboBox.setTextInputAllowed(false);

        TextArea descriptionField = new TextArea("ОПИСАНИЕ");
        descriptionField.setWidth(100.0f, Unit.PERCENTAGE);

        Recipe recipe;

        if (action == Action.ADD) {
            recipe = Recipe.getEmpty();
            binder.setBean(recipe);
        }

        binder.forField(patientComboBox)
                .withValidator(Validation::notNull, Message.notNullError("\"Пациент\""))
                .bind(Recipe::getPatient, Recipe::setPatient);
        binder.forField(doctorComboBox)
                .withValidator(Validation::notNull, Message.notNullError("\"Доктор\""))
                .bind(Recipe::getDoctor, Recipe::setDoctor);
        binder.forField(creationDateField)
                .withValidator(Validation::notNull, Message.notNullError("\"Дата создания\""))
                .bind(r -> r.getCreationDate().toLocalDate(), Recipe::setCreationDate);
        binder.forField(validityField)///////////////////////////////////////////////////////////////////////////////////////// при удалениии nullpointer
                .withValidator(Validation::notNull, Message.notNullError("\"Срок\""))
                .withConverter(new StringToIntegerConverter("Невозможно преобразовать в целое число"))
                .withValidator(new IntegerRangeValidator("Максимально 10 лет, либо неограниченный", 0, 3650))
                .bind(Recipe::getValidity, Recipe::setValidity);
        binder.forField(priorityComboBox).bind(Recipe::getPriority, Recipe::setPriority);
        binder.forField(descriptionField)
                .withValidator(Validation::descriptionIsValid, Message.descriptionValidationError())
                .bind(Recipe::getDescription, Recipe::setDescription);

        formLayout.addComponents(patientComboBox,
                doctorComboBox,
                creationDateField,
                validityField,
                unlimitedLabel,
                priorityComboBox,
                descriptionField);
        return formLayout;
    }
}
