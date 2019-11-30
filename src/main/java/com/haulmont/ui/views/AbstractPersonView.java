package com.haulmont.ui.views;

import com.haulmont.backend.AbstractPerson;
import com.haulmont.backend.dao.AbstractEntityDAO;
import com.haulmont.ui.components.Message;
import com.haulmont.ui.components.Validation;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.Setter;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

public abstract class AbstractPersonView<E extends AbstractPerson<E>> extends AbstractView<E> {

    protected abstract void addGridColumn(Grid<E> grid);

    protected abstract E getEmptyPerson();

    protected abstract TextField getPersonField();

    protected abstract void bindPersonField(TextField personField);

    public AbstractPersonView(String labelText, AbstractEntityDAO<E> entityDao) {
        super(labelText, entityDao);
    }

    @Override
    protected Grid<E> createGrid() {
        Grid<E> grid = new Grid<>();
        grid.addColumn(E::getName).setCaption("ИМЯ");
        grid.addColumn(E::getLastName).setCaption("ФАМИЛИЯ");
        grid.addColumn(E::getPatronymic).setCaption("ОТЧЕСТВО");
        addGridColumn(grid);
        return grid;
    }

    @Override
    protected FormLayout createInputFormLayout(Action action) {
        FormLayout formLayout = new FormLayout();
        TextField nameField = new TextField("ИМЯ");
        TextField lastNameField = new TextField("ФАМИЛИЯ");
        TextField patronymicField = new TextField("ОТЧЕСТВО");

        TextField personField = getPersonField();

        E entity;
        if (action == Action.ADD) {
            entity = getEmptyPerson();
            binder.setBean(entity);
        }

        bindFieldOfNaming(nameField, E::getName, E::setName);
        bindFieldOfNaming(lastNameField, E::getLastName, E::setLastName);
        binder.forField(patronymicField)
                .withValidator(Validation::patronymicIsValid, Message.patronymicValidationError())
                .bind(E::getPatronymic, E::setPatronymic);

        bindPersonField(personField);

        formLayout.addComponents(nameField, lastNameField, patronymicField, personField);
        return formLayout;
    }

    protected void bindFieldOfNaming(TextField field, ValueProvider<E, String> getter, Setter<E, String> setter) {
        binder.forField(field)
                .withValidator(Validation::namedIsValid, Message.nameValidationError())
                .bind(getter, setter);
    }
}
    
