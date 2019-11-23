package com.haulmont.backend.dao;

import com.haulmont.backend.Recipe;
import com.haulmont.backend.RecipePriority;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class RecipeDaoTest extends AbstractEntityDAOTest {
    public RecipeDaoTest() {
        super(new RecipeDao());
    }

    @Override
    protected Entity getUpdateEntity(Entity entity) {
        Recipe recipe = (Recipe) entity;
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
    protected Entity getNewEntity() {
        long id = 0;
        long doctorId = 0;
        long patientID = 0;
        String description = "New Desription for new recipe";
        Date creationDate = Date.valueOf(LocalDate.now());
        short validity = 438;
        RecipePriority recipePriority = RecipePriority.STATIM;

        return new Recipe(id, doctorId, patientID, description, creationDate, validity, recipePriority);
    }
}