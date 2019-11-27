package com.haulmont.ui.views;

import com.haulmont.backend.Recipe;
import com.haulmont.backend.dao.AbstractEntityDAO;
import com.haulmont.backend.dao.RecipeDao;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class RecipesView extends VerticalLayout implements View {
    public RecipesView() {
        setSizeFull();
        Label label = new Label("РЕЦЕПТЫ");
        Grid<Recipe> grid = new Grid<>();
        AbstractEntityDAO recipeDao = new RecipeDao();
        List recipes = recipeDao.getAll();

        grid.setSizeFull();
        grid.setItems(recipes);
        grid.addColumn(Recipe::getDescription).setId("RECIPE_DESCRIPTION").setCaption("Описание");
        grid.addColumn(r -> r.getDoctor().getFullName()).setId("RECIPE_DOCTOR").setCaption("ДОКТОР");
        grid.addColumn(r -> r.getPatient().getFullName()).setId("RECIPE_PATIENT").setCaption("ПАЦИЕНТ");
        grid.addColumn(Recipe::getCreationDate).setId("RECIPE_DATE").setCaption("ДАТА СОЗДАНИЯ");
        grid.addColumn(r -> validityConvert(r.getCreationDate(), r.getValidity())).setId("RECIPE_VALIDITY").setCaption("ДАТА ОКОНЧАНИЯ");
        grid.addColumn(Recipe::getPriority).setId("RECIPE_PRIORITY").setCaption("ПРИОРИТЕТ");
        grid.addComponentColumn(l -> getEditLayout());

        addComponents(label, grid);
        setExpandRatio(grid, 1);
    }

    private String validityConvert(Date creationDate, int validity) {
        if (validity == 0) {
            return "Бессрочно";
        }
        return creationDate.toLocalDate()
                .plusDays(validity)
                .toString();
    }

    private Layout getEditLayout() {
        HorizontalLayout hl = new HorizontalLayout();
        return hl;
    }
}
