package com.haulmont.ui.views;

import com.haulmont.backend.*;
import com.haulmont.backend.dao.DoctorDao;
import com.haulmont.backend.dao.PatientDao;
import com.haulmont.backend.dao.RecipeDao;
import com.haulmont.backend.dao.RecipePriorityDao;
import com.haulmont.ui.components.Action;
import com.haulmont.ui.components.Message;
import com.haulmont.ui.components.Validation;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.ui.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RecipesView extends AbstractView<Recipe> {
    private final List<Patient> patients = new ArrayList<>();
    private final List<Doctor> doctors = new ArrayList<>();
    private final List<RecipePriority> priorities = new ArrayList<>();

    private ComboBox<RecipePriority> priorityComboBox;
    private ComboBox<Patient> patientComboBox;
    private ComboBox<Doctor> doctorComboBox;
    private DateField creationDateField;
    private TextArea descriptionField;
    private TextField validityField;

    public RecipesView() {
        super("РЕЦЕПТЫ", new RecipeDao());
        addLocalComponents(this);
        addComponent(grid);
        setExpandRatio(grid, 1);
    }

    @Override
    protected void localEnter() {
        patients.clear();
        patients.addAll(new PatientDao().getAll());

        doctors.clear();
        doctors.addAll(new DoctorDao().getAll());

        priorities.clear();
        priorities.addAll(new RecipePriorityDao().getAll());
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
    protected void addLocalComponents(VerticalLayout outerLayout) {
        HorizontalLayout filterLayout = new HorizontalLayout();
        Panel panel = new Panel("ФИЛЬТР", filterLayout);
        filterLayout.setMargin(true);
        panel.setHeightUndefined();

        ComboBox<Patient> patientFilterComboBox = new ComboBox<>("ПАЦИЕНТ");
        patientFilterComboBox.setItems(patients);
        patientFilterComboBox.setItemCaptionGenerator(Patient::getFullName);
        patientFilterComboBox.setWidth("220px");

        ComboBox<RecipePriority> priorityFilterComboBox = new ComboBox<>("ПРИОРИТЕТ");
        priorityFilterComboBox.setItems(priorities);
        priorityFilterComboBox.setItemCaptionGenerator(RecipePriority::getTitle);
        priorityFilterComboBox.setTextInputAllowed(false);

        TextField descriptionFilterField = new TextField("ОПИСАНИЕ");
        descriptionFilterField.setWidth("220px");

        Button buttonFilterAccept = new Button("ПРИМЕНИТЬ");
        buttonFilterAccept.addClickListener(event -> {
            Patient patient = patientFilterComboBox.getValue();
            RecipePriority priority = priorityFilterComboBox.getValue();
            String description = descriptionFilterField.getValue();

            long patientID = patient == null ? -1 : patient.getId();
            long priorityID = priority == null ? -1 : priority.getId();

            entityList.clear();
            entityList.addAll(((RecipeDao) entityDao).getAllFiltered(patientID, priorityID, description));
            grid.getDataProvider().refreshAll();
        });

        filterLayout.addComponents(patientFilterComboBox, priorityFilterComboBox, descriptionFilterField, buttonFilterAccept);
        filterLayout.setComponentAlignment(buttonFilterAccept, Alignment.BOTTOM_CENTER);
        outerLayout.addComponent(panel);
    }

    @Override
    protected FormLayout createInputFormLayout(Action action) {
        FormLayout formLayout = new FormLayout();
        formLayout.setWidth("500px");

        priorityComboBox = createComboBoxForInputForm(priorities, "ПРИОРИТЕТ");
        patientComboBox = createComboBoxForInputForm(patients, "ПАЦИЕНТ");
        doctorComboBox = createComboBoxForInputForm(doctors, "ВРАЧ");
        creationDateField = new DateField("ДАТА СОЗДАНИЯ");
        descriptionField = new TextArea("ОПИСАНИЕ");
        validityField = new TextField("СРОК ДЕЙСТВИЯ");

        patientComboBox.setItemCaptionGenerator(Patient::getFullName);
        doctorComboBox.setItemCaptionGenerator(Doctor::getFullName);
        priorityComboBox.setItemCaptionGenerator(RecipePriority::getTitle);
        priorityComboBox.setTextInputAllowed(false);

        creationDateField.setWidth(100.0f, Unit.PERCENTAGE);
        if (action == Action.ADD) {
            creationDateField.setValue(LocalDate.now());
            creationDateField.setRangeStart(LocalDate.now());
        } else {
            creationDateField.setReadOnly(true);
        }

        validityField.setWidth(100.0f, Unit.PERCENTAGE);
        Label unlimitedLabel = new Label("0 = неограниченный срок");
        unlimitedLabel.setEnabled(false);

        descriptionField.setWidth(100.0f, Unit.PERCENTAGE);

        bindingFields(action, patientComboBox, doctorComboBox, creationDateField, validityField, priorityComboBox, descriptionField);

        formLayout.addComponents(patientComboBox,
                doctorComboBox,
                creationDateField,
                validityField,
                unlimitedLabel,
                priorityComboBox,
                descriptionField);
        return formLayout;
    }

    @Override
    public boolean fieldNotValid() {
        return !Validation.notNull(patientComboBox.getValue())
                || !Validation.notNull(doctorComboBox.getValue())
                || !Validation.notNull(creationDateField.getValue())
                || !Validation.validityIsValid(validityField.getValue())
                || !Validation.descriptionIsValid(descriptionField.getValue());
    }

    private void bindingFields(Action action,
                               ComboBox<Patient> patientComboBox,
                               ComboBox<Doctor> doctorComboBox,
                               DateField creationDateField,
                               TextField validityField,
                               ComboBox<RecipePriority> priorityComboBox,
                               TextArea descriptionField) {
        Recipe recipe;

        if (action == Action.ADD) {
            recipe = Recipe.getEmpty();
            binder.setBean(recipe);
        }

        binder.forField(patientComboBox)
                .withValidator(Validation::notNull, Message.notEmptyError("Пациент"))
                .bind(Recipe::getPatient, Recipe::setPatient);
        binder.forField(doctorComboBox)
                .withValidator(Validation::notNull, Message.notEmptyError("Доктор"))
                .bind(Recipe::getDoctor, Recipe::setDoctor);
        binder.forField(creationDateField)
                .withValidator(Validation::notNull, Message.notEmptyError("Дата"))
                .bind(r -> r.getCreationDate().toLocalDate(), Recipe::setCreationDate);
        binder.forField(validityField)
                .withValidator(Validation::notEmpty, Message.notEmptyError("Срок"))
                .withConverter(new StringToIntegerConverter("Невозможно преобразовать в целое число"))
                .withValidator(new IntegerRangeValidator("Максимально 10 лет, либо неограниченный", 0, 3650))
                .bind(Recipe::getValidity, Recipe::setValidity);
        binder.forField(priorityComboBox).bind(Recipe::getPriority, Recipe::setPriority);
        binder.forField(descriptionField)
                .withValidator(Validation::descriptionIsValid, Message.descriptionValidationError())
                .bind(Recipe::getDescription, Recipe::setDescription);
    }

    private ComboBox createComboBoxForInputForm(List entities, String caption) {
        ComboBox<Entity> entityComboBox = new ComboBox<>(caption);
        entityComboBox.setItems(entities);
        entityComboBox.setWidth(100.0f, Unit.PERCENTAGE);
        entityComboBox.setEmptySelectionAllowed(false);
        return entityComboBox;
    }
}