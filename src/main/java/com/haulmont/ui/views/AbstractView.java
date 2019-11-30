package com.haulmont.ui.views;

import com.haulmont.backend.dao.AbstractEntityDAO;
import com.haulmont.ui.components.Viewable;
import com.vaadin.data.Binder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;

import java.util.List;

public abstract class AbstractView<E extends Viewable<E>> extends VerticalLayout implements View {
    protected final AbstractEntityDAO<E> entityDao;
    protected List<E> entityList;
    protected Binder<E> binder;
    protected Grid<E> grid;

    protected abstract void localEnter();

    protected abstract Grid<E> createGrid();

    protected abstract void addOtherComponents(VerticalLayout layout);

    protected abstract FormLayout createInputFormLayout(Action action);

    protected AbstractView(String labelText, AbstractEntityDAO<E> entityDao) {
        this.entityDao = entityDao;
        entityList = entityDao.getAll();
        binder = new Binder<>();
        grid = createGrid();
        Label label = new Label(labelText);
        grid.setSizeFull();
        grid.addItemClickListener(event -> binder.setBean(event.getItem()));

        setSizeFull();
        addComponents(label, getToolsLayout());
        addOtherComponents(this);
        addComponent(grid);
        setExpandRatio(grid, 1);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        entityList = entityDao.getAll();
        grid.setItems(entityList);
        grid.deselectAll();
        binder.removeBean();
        grid.getDataProvider().refreshAll();
        localEnter();
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
                Notification.show("Выберите строку");
                return;
            }
            getUI().addWindow(createWindowForm(Action.UPDATE));
        });

        buttonRemove.addClickListener(event -> {
            E entity = binder.getBean();
            if (entity == null) {
                Notification.show("Выберите строку");
                return;
            }
            if (!entityDao.delete(entity.getId())) {
                Notification.show("Не может быть удален! Данный субъект связан с записью в базе данных.").setDelayMsec(2500);
                return;
            }
            entityList.remove(entity);
            binder.removeBean();
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

    private Window createWindowForm(Action action) {
        Window window = new Window();
        HorizontalLayout layout = new HorizontalLayout();
        HorizontalLayout buttonLayout = new HorizontalLayout();
        Button acceptButton = new Button("ОК");
        Button cancelButton = new Button("ОТМЕНИТЬ");
        FormLayout formLayout = createInputFormLayout(action);

        window.setResizable(false);
        window.setModal(true);
        window.setClosable(false);
        formLayout.setMargin(true);
        buttonLayout.setMargin(new MarginInfo(true, false, false, false));

        E selectedEntity = binder.getBean().getCopy();

        acceptButton.addClickListener(event -> {
            if (binder.isValid()) {
                switch (action) {
                    case ADD:
                        doAdd(binder.getBean());
                        binder.removeBean();
                        break;
                    case UPDATE:
                        if (!doUpdate(selectedEntity)) {
                            Notification.show("Данные идентичны");
                            return;
                        }
                        break;
                }
                window.close();
            } else {
                Notification.show("Введены некорректные данные").setDelayMsec(100);
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

    private boolean doAdd(E entity) {
        if (!entityDao.add(entity)) {
            return false;
        }
        entityList.clear();
        entityList.addAll(entityDao.getAll());
        grid.getDataProvider().refreshAll();
        return true;
    }

    private boolean doUpdate(E oldEntity) {
        E entity = binder.getBean();
        if (entity.equals(oldEntity)) {
            return false;
        }
        if (!entityDao.update(entity)) {
            return false;
        }

        entityList = entityDao.getAll();     //
        grid.setItems(entityList);           // Костыль

        grid.deselect(entity);
        binder.removeBean();
        grid.getDataProvider().refreshAll();
        return true;
    }

    protected enum Action {
        ADD,
        UPDATE
    }
}
