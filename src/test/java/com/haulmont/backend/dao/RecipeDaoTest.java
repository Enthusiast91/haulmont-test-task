package com.haulmont.backend.dao;

import com.haulmont.backend.Recipe;
import com.haulmont.backend.RecipePriority;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class RecipeDaoTest extends AbstractEntityDAOTest<Recipe> {
    public RecipeDaoTest() {
        super(new RecipeDao());
    }

    @Override
    protected Recipe getUpdateEntity(Recipe recipe) {
        recipe.setValidity((short) 777);
        recipe.setDescription("New description after update test");
        for (RecipePriority priority : RecipePriority.values()) {
            if (recipe.getPriority() != priority) {
                recipe.setPriority(priority);
                return recipe;
            }
        }
        return recipe;
    }

    @Override
    protected Recipe getNewEntity() {
        long id = 0;
        long doctorId = 0;
        long patientID = 0;
        String description = "New Desription for new recipe";
        Date creationDate = Date.valueOf(LocalDate.now());
        short validity = 438;
        RecipePriority recipePriority = RecipePriority.STATIM;

        return new Recipe(id, doctorId, patientID, description, creationDate, validity, recipePriority);
    }

    @Test
    public void getFilteredTest() {
        List<Recipe> recipes = ((RecipeDao) entityDAO).getFiltered(-1, RecipePriority.STATIM.getId(), "");
        for (Recipe recipe : recipes) {
            System.out.println(recipe);
        }
    }
}