package com.haulmont.backend.dao;

import com.haulmont.backend.Doctor;
import com.haulmont.backend.Patient;
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
        Doctor doctor = new DoctorDao().getById(0);
        Patient patient = new PatientDao().getById(0);
        String description = "New Desription for new recipe";
        Date creationDate = Date.valueOf(LocalDate.now());
        short validity = 438;
        RecipePriority recipePriority = RecipePriority.STATIM;

        return new Recipe(id, description, creationDate, validity, doctor, patient, recipePriority);
    }

    @Test
    public void getFilteredTest() {
        List<Recipe> recipes = ((RecipeDao) entityDAO).getAllFiltered(-1, RecipePriority.STATIM.getId(), "");
        for (Recipe recipe : recipes) {
            System.out.println(recipe);
        }
    }
}