package com.haulmont.ui.views;

import com.haulmont.backend.dao.AbstractEntityDAO;
import com.haulmont.ui.components.Action;
import com.haulmont.ui.components.InputFormWindow;
import com.haulmont.ui.components.Viewable;
import com.vaadin.data.Binder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractView<E extends Viewable<E>> extends VerticalLayout implements View {
    protected final AbstractEntityDAO<E> entityDao;
    protected final List<E> entityList;
    protected final Binder<E> binder;
    protected final Grid<E> grid;

    protected AbstractView(String labelText, AbstractEntityDAO<E> entityDao) {
        this.entityDao = entityDao;
        Label label = new Label(labelText);
        entityList = new ArrayList<>();
        binder = new Binder<>();
        grid = createGrid();
        grid.setSizeFull();
        grid.setItems(entityList);
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                binder.setBean(null);
                return;
            }
            binder.setBean(event.getValue().getCopy());
        });

        setSizeFull();
        addComponents(label, getToolsLayout());
    }

    protected abstract void localEnter();

    protected abstract Grid<E> createGrid();

    protected abstract void addLocalComponents(VerticalLayout layout);

    protected abstract FormLayout createInputFormLayout(Action action);

    public abstract boolean fieldNotValid();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        updateGrid();
        localEnter();
    }

    private Layout getToolsLayout() {
        HorizontalLayout layout = new HorizontalLayout();

        Button buttonAdd = createToolButton(VaadinIcons.PLUS, "ДОБАВИТЬ");
        buttonAdd.addClickListener(event -> {
            grid.deselectAll();
            getUI().addWindow(createInputFormWindow(Action.ADD));
        });

        Button buttonEdit = createToolButton(VaadinIcons.EDIT, "РЕДАКТИРОВАТЬ");
        buttonEdit.addClickListener(event -> {
            if (grid.asSingleSelect().getValue() == null) {
                Notification.show("Выберите строку");
                return;
            }
            getUI().addWindow(createInputFormWindow(Action.UPDATE));
        });

        Button buttonRemove = createToolButton(VaadinIcons.CLOSE, "УДАЛИТЬ");
        buttonRemove.addClickListener(event -> {
            E entity = binder.getBean();
            if (entity == null) {
                Notification.show("Выберите строку");
                return;
            }
            if (!entityDao.delete(entity.getId())) {
                Notification.show("Не может быть удален! Данный субъект связан с записью в базе данных.");
                return;
            }
            entityList.remove(entity);
            grid.getDataProvider().refreshAll();
        });

        layout.addComponents(buttonAdd, buttonEdit, buttonRemove);
        layout.setComponentAlignment(buttonAdd, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(buttonEdit, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(buttonRemove, Alignment.MIDDLE_CENTER);

        return layout;
    }

    private Button createToolButton(VaadinIcons icon, String description) {
        Button button = new Button(description);
        button.setIcon(icon, description);
        button.setDescription(description);
        button.setWidth("200px");
        return button;
    }

    private Window createInputFormWindow(Action action) {
        FormLayout formLayout = createInputFormLayout(action);
        return new InputFormWindow<>(this, formLayout, binder.getBean(), action);
    }

    private void updateGrid() {
        entityList.clear();
        entityList.addAll(entityDao.getAll());
        grid.getDataProvider().refreshAll();
    }

    public boolean doAdd(E entity) {
        if (!entityDao.add(entity)) {
            Notification.show("Запись не добавлена");
            return false;
        }
        updateGrid();
        return true;
    }

    public boolean doUpdate(E oldEntity) {
        if (fieldNotValid()) {
            Notification.show("Поля не прошли валидацию");
            return false;
        }
        E entity = binder.getBean();
        if (entity.equals(oldEntity)) {
            Notification.show("Данные идентичны");
            return false;
        }
        if (!entityDao.update(entity)) {
            Notification.show("Запись не обнавлена");
            return false;
        }
        updateGrid();
        grid.setItems(entityList);
        return true;
    }
}
