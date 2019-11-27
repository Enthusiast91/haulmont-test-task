package com.haulmont.backend.dao;

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
        E entity = entityDAO.getById(id);
        System.out.println("Before update:\n" + entity);

        entity = getUpdateEntity(entity);
        entityDAO.update(entity);
        entity = entityDAO.getById(id);
        System.out.println("After update:\n" + entity);
    }

    @Test
    public void addTest() {
        E entity = getNewEntity();
        System.out.println("Added entity:\n" + entity);

        entityDAO.add(entity);
        List<E> entities = entityDAO.getAll();
        E lastEntity = entities.get(entities.size() - 1);
        System.out.println("Last entity from BD:\n" + lastEntity);
    }

    @Test
    public void deleteTest() {
        //entityDAO.add(getNewEntity());
        List<E> entities = entityDAO.getAll();
        int entitiesSize = entities.size();
        System.out.println("Delete:\nSize before delete = " + entitiesSize);

        entityDAO.delete((long) (entitiesSize - 1));
        List<E> entitiesAfterDelete = entityDAO.getAll();
        int entitiesSizeAfterDelete = entitiesAfterDelete.size();
        System.out.println("Size after delete = " + entitiesSizeAfterDelete);
        assertEquals(entitiesSize - 1, entitiesSizeAfterDelete);
    }
}