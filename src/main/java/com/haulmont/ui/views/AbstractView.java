package com.haulmont.ui.views;

import com.haulmont.backend.dao.AbstractEntityDAO;
import com.haulmont.backend.dao.Entity;
import com.haulmont.ui.components.Viewable;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

import com.vaadin.data.Binder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractView<E extends Viewable<E>> extends VerticalLayout implements View {
    protected final AbstractEntityDAO<E> entityDao;
    protected List<E> entityMap;
    protected Binder<E> binder;
    protected Grid<E> grid;

    protected abstract Grid<E> createGrid();

    protected abstract void addOtherComponents();

    protected abstract FormLayout createFormLayout(Action action);

    protected AbstractView(String labelText, AbstractEntityDAO<E> entityDao) {
        this.entityDao = entityDao;
        entityMap = entityDao.getAll();
        binder = new Binder<>();
        grid = createGrid();
        Label label = new Label(labelText);

        grid.setItems(entityMap);
        grid.setSizeFull();
        grid.addItemClickListener(event -> binder.setBean(event.getItem()));

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
            binder.removeBean();
            grid.deselectAll();
            getUI().addWindow(createWindowForm(Action.ADD));
        });

        buttonEdit.addClickListener(event -> {
            if (binder.getBean() == null) {
                Notification.show("Пожалуйста выберите строку");
                return;
            }
            getUI().addWindow(createWindowForm(Action.UPDATE));
        });

        buttonRemove.addClickListener(event -> {
            E entity = binder.getBean();
            if (entity == null) {
                Notification.show("Пожалуйста выберите строку");
                return;
            }
            if (!entityDao.delete(entity.getId())) {
                Notification.show("Не может быть удален! Данный пациент связан с записью в базе данных.").setDelayMsec(2500);
                return;
            }
            entityMap.remove(entity);
            binder.removeBean();
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
        Window window = new Window();
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

        E selectedEntity = binder.getBean().getCopy();

        acceptButton.addClickListener(event -> {
//            Notification.show(binder.getBean() + "").setDelayMsec(3000);/////////////////////////////////////////////////////
            if (binder.isValid()) {
                switch (action) {
                    case ADD:
                        doAdd(binder.getBean());
                        binder.removeBean();
                        break;
                    case UPDATE:
                        if(!doUpdate(selectedEntity)) {
                            Notification.show("Данные идентичны");
                            return;
                        }
                        break;
                }
                window.close();
            } else {
                Notification.show("Введены некорректные данные").setDelayMsec(600);
            }
        });

        cancelButton.addClickListener(event -> {
            if (action == Action.ADD) {
                binder.removeBean();
            }
            window.close();
        });

        buttonLayout.addComponents(acceptButton, cancelButton);
        formLayout.addComponent(buttonLayout);
        layout.addComponent(formLayout);
        window.setContent(layout);
        return window;
    }

    private void doAdd(E entity) {
        entityDao.add(entity);
        entityMap.clear();
        entityMap.addAll(entityDao.getAll());
        grid.getDataProvider().refreshAll();
    }

    protected boolean doUpdate(E oldEntity) {
        E entity = binder.getBean();
        if (entity.equals(oldEntity)) {
            return false;
        }
        entityDao.update(binder.getBean());

        entityMap = entityDao.getAll();     // Костыль
        grid.setItems(entityMap);           // Костыль
        binder.removeBean();                // Костыль

        grid.getDataProvider().refreshAll();
        return true;
    }
    protected enum Action {
        ADD,
        UPDATE
    }
}
