package com.haulmont.backend.dao;

import com.haulmont.backend.Entity;
import org.junit.*;

import static org.junit.Assert.*;
import java.util.List;

public abstract class AbstractEntityDAOTest<E extends Entity> {
    protected AbstractEntityDAO<E> entityDAO;

    AbstractEntityDAOTest(AbstractEntityDAO<E> entityDAO) {
        this.entityDAO = entityDAO;
    }

    protected abstract E getUpdateEntity(E entity);

    protected abstract E getNewEntity();

    @Test
    public void getAllTest() {
        System.out.println("Get all: ");
        List<E> entities = entityDAO.getAll();
        for (E entity : entities) {
            System.out.println(entity);
        }
    }

    @Test
    public void getEntityByIdTest() {
        System.out.println("Get entity by id 0, 1, 2:");
        for (int i = 0; i < 3; i++) {
            E entity = entityDAO.getById(i);
            System.out.println(entity);
        }
    }

    @Test
    public void updateTest() {
        long id = 0;
        E entityOld = entityDAO.getById(id);
        entityOld = getUpdateEntity(entityOld);
        entityDAO.update(entityOld);
        E entityUpd = entityDAO.getById(id);

        assertEquals(entityOld, entityUpd);
    }

    @Test
    public void addTest() {
        E entityAdded = getNewEntity();
        boolean added = entityDAO.add(entityAdded);
        List<E> entities = entityDAO.getAll();
        boolean contains = entities.contains(entityAdded);

        assertTrue(added);
        assertTrue(contains);
    }

    @Test
    public void deleteTest() {
        E entityAdded = getNewEntity();
        entityDAO.add(entityAdded);

        List<E> entities = entityDAO.getAll();
        int entitiesSize = entities.size();
        long idAdded = entities.indexOf(entityAdded);
        entityDAO.delete(idAdded);
        int entitiesSizeAfterDelete = entityDAO.getAll().size();

        assertEquals(entitiesSize - 1, entitiesSizeAfterDelete);
    }
}