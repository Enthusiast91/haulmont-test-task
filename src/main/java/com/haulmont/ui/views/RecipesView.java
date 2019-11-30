package com.haulmont.ui.views;

import com.haulmont.backend.Doctor;
import com.haulmont.backend.Patient;
import com.haulmont.backend.Recipe;
import com.haulmont.backend.RecipePriority;
import com.haulmont.backend.dao.*;
import com.haulmont.ui.components.Message;
import com.haulmont.ui.components.Validation;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.server.Setter;
import com.vaadin.ui.*;

import java.time.LocalDate;
import java.util.List;

public class RecipesView extends AbstractView<Recipe> {
    private List<Patient> patients;
    private List<Doctor> doctors;
    private List<RecipePriority> recipePriorities;

    public RecipesView() {
        super("РЕЦЕПТЫ", RecipeDao.getInstance());
    }

    @Override
    protected void localEnter() {
        patients.clear();
        patients.addAll(PatientDao.getInstance().getAll());

        doctors.clear();
        doctors.addAll(DoctorDao.getInstance().getAll());

        recipePriorities.clear();
        recipePriorities.addAll(RecipePriorityDao.getInstance().getAll());
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
    protected FormLayout createInputFormLayout(Action action) {
        FormLayout formLayout = new FormLayout();
        formLayout.setWidth("500px");

        ComboBox<Patient> patientComboBox = createComboBoxForInputForm(patients, "ПАЦИЕНТ");
        patientComboBox.setItemCaptionGenerator(Patient::getFullName);

        ComboBox<Doctor> doctorComboBox = createComboBoxForInputForm(doctors, "ВРАЧ");
        doctorComboBox.setItemCaptionGenerator(Doctor::getFullName);

        DateField creationDateField = new DateField("ДАТА СОЗДАНИЯ");
        creationDateField.setWidth(100.0f, Unit.PERCENTAGE);
        creationDateField.setValue(LocalDate.now());

        TextField validityField = new TextField("СРОК ДЕЙСТВИЯ");
        validityField.setWidth(100.0f, Unit.PERCENTAGE);

        Label unlimitedLabel = new Label("0 = неограниченный срок");
        unlimitedLabel.setEnabled(false);

        ComboBox<RecipePriority> priorityComboBox = createComboBoxForInputForm(recipePriorities, "ПРИОРИТЕТ");
        priorityComboBox.setItemCaptionGenerator(RecipePriority::getTitle);
        priorityComboBox.setTextInputAllowed(false);

        TextArea descriptionField = new TextArea("ОПИСАНИЕ");
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
    protected void addOtherComponents(VerticalLayout outerLayout) {
        patients = PatientDao.getInstance().getAll();
        doctors = DoctorDao.getInstance().getAll();
        recipePriorities = RecipePriorityDao.getInstance().getAll();

        HorizontalLayout layout = new HorizontalLayout();
        Panel panel = new Panel("ФИЛЬТР", layout);
        layout.setMargin(true);
        panel.setHeightUndefined();

        ComboBox<Patient> patientComboBox = new ComboBox<>("ПАЦИЕНТ");
        patientComboBox.setItems(patients);
        patientComboBox.setItemCaptionGenerator(Patient::getFullName);
        patientComboBox.setWidth("220px");

        ComboBox<RecipePriority> priorityComboBox = new ComboBox<>("ПРИОРИТЕТ");
        priorityComboBox.setItems(recipePriorities);
        priorityComboBox.setItemCaptionGenerator(RecipePriority::getTitle);
        priorityComboBox.setTextInputAllowed(false);

        TextField descriptionField = new TextField("ОПИСАНИЕ");
        descriptionField.setWidth("220px");

        Button buttonAccept = new Button("ПРИМЕНИТЬ");
        buttonAccept.addClickListener(event -> {
            Patient patient = patientComboBox.getValue();
            RecipePriority priority = priorityComboBox.getValue();
            String description = descriptionField.getValue();

            long patientID = patient == null ? -1 : patient.getId();
            long priorityID = priority == null ? -1 : priority.getId();

            entityList.clear();
            entityList.addAll(((RecipeDao) entityDao).getFiltered(patientID, priorityID, description));
            grid.getDataProvider().refreshAll();
        });

        layout.addComponents(patientComboBox, priorityComboBox, descriptionField, buttonAccept);
        layout.setComponentAlignment(buttonAccept, Alignment.BOTTOM_CENTER);
        outerLayout.addComponent(panel);
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
                .withValidator(Validation::notNull, Message.notNullError("\"Пациент\""))
                .bind(Recipe::getPatient, Recipe::setPatient);
        binder.forField(doctorComboBox)
                .withValidator(Validation::notNull, Message.notNullError("\"Доктор\""))
                .bind(Recipe::getDoctor, Recipe::setDoctor);
        binder.forField(creationDateField)
                .withValidator(Validation::notNull, Message.notNullError("\"Дата создания\""))
                .bind(r -> r.getCreationDate().toLocalDate(), Recipe::setCreationDate);
        binder.forField(validityField)
                .withValidator(Validation::notNull, Message.notNullError("\"Срок\""))
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
