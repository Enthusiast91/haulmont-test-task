package com.haulmont.ui.views;

import com.haulmont.backend.Patient;
import com.haulmont.backend.Recipe;
import com.haulmont.backend.dao.AbstractEntityDAO;
import com.haulmont.backend.dao.Entity;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

import javax.xml.bind.Binder;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractView<E extends Entity> extends VerticalLayout implements View {
    protected final AbstractEntityDAO<E> entityDao;
    protected Grid<E> grid;
    protected List<E> entityList;
    protected E currentEntity = null;

    protected abstract Grid<E> createGrid();

    protected abstract void addOtherComponents();

    protected abstract FormLayout createFormLayout(Action action);

    protected abstract void doUpdate(E entity);

    protected abstract E getEntityFromFormLayout(FormLayout formLayout);

    protected AbstractView(String labelText, AbstractEntityDAO<E> entityDao) {
        this.entityDao = entityDao;
        entityList = entityDao.getAll();
        grid = createGrid();
        Label label = new Label(labelText);

        grid.setItems(entityList);
        grid.setSizeFull();
        grid.addItemClickListener(event -> {
            currentEntity = event.getItem();
        });

        setSizeFull();
        addComponents(label, getToolsLayout());
        addOtherComponents();
        addComponent(grid);
        setExpandRatio(grid, 1);
    }

    private Layout getToolsLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        Button buttonAdd = createToolButton(VaadinIcons.PLUS, "ДОБАВИТЬ");
        Button buttonEdit = createToolButton(VaadinIcons.EDIT, "РЕДАКТИРОВАТЬ");
        Button buttonRemove = createToolButton(VaadinIcons.CLOSE, "УДАЛИТЬ");

        buttonAdd.addClickListener(event -> {
            getUI().addWindow(createWindowForm(Action.ADD));
        });

        buttonEdit.addClickListener(event -> {
            if (currentEntity == null) {
                Notification.show("Пожалуйста выберите строку");
                return;
            }
            getUI().addWindow(createWindowForm(Action.UPDATE));
        });

        buttonRemove.addClickListener(event -> {
            if (currentEntity == null) {
                Notification.show("Пожалуйста выберите строку");
                return;
            }
            if (!entityDao.delete(currentEntity.getId())) {
                Notification.show("Не может быть удален! Данный пациент связан с записью в списке рецептов.").setDelayMsec(2500);
                return;
            }
            entityList.remove(currentEntity);
            grid.getDataProvider().refreshAll();
        });

        layout.addComponents(buttonAdd, buttonEdit, buttonRemove);
        return layout;
    }

    private Button createToolButton(VaadinIcons icon, String description) {
        Button button = new Button(description);
        button.setIcon(icon, description);
        button.setDescription(description);
        button.setWidth("200px");
        return button;
    }

    private Window createWindowForm(Action action) {
        Window window = new Window("");
        HorizontalLayout layout = new HorizontalLayout();
        HorizontalLayout buttonLayout = new HorizontalLayout();
        Button acceptButton = new Button("ОК");
        Button cancelButton = new Button("ОТМЕНИТЬ");
        FormLayout formLayout = createFormLayout(action);

        window.setResizable(false);
        window.setModal(true);
        window.setClosable(false);
        formLayout.setMargin(true);
        buttonLayout.setMargin(new MarginInfo(true, false, false, false));

        acceptButton.addClickListener(event -> {
            Notification.show("AddClickListener");
            E entity = getEntityFromFormLayout(formLayout);
            switch (action) {
                case ADD:
                    doAdd(entity);
                    break;
                case UPDATE:
                    doUpdate(entity);
                    break;
            }
            window.close();
        });

        cancelButton.addClickListener(event -> window.close());

        buttonLayout.addComponents(acceptButton, cancelButton);
        formLayout.addComponent(buttonLayout);
        layout.addComponent(formLayout);
        window.setContent(layout);
        return window;
    }

    private void doAdd(E entity) {
        entityDao.add(entity);
        entityList.clear();
        entityList.addAll(entityDao.getAll());
        grid.getDataProvider().refreshAll();
    }

    protected enum Action {
        ADD,
        UPDATE
    }
}
