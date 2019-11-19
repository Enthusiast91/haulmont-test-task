package com.haulmont.backend.dao;

import com.haulmont.backend.Recipe;

import java.util.List;

public interface RecipeDao {
    Recipe create();

    Recipe read();

    void update(Recipe recipe);

    void delete(Recipe recipe);

    List<Recipe> getAll();

    List<Recipe> getAllWithDescriptionFilter();

    List<Recipe> getAllWithPatientFilter();

    List<Recipe> getAllWithPriorityFilter();
}
