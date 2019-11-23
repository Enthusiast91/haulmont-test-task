package com.haulmont.backend.dao;

import com.haulmont.backend.RecipePriority;
import org.junit.Test;

public class RecipePriorityDaoTest extends AbstractEntityDAOTest {
    public RecipePriorityDaoTest() {
        super(new RecipePriorityDao());
    }

    @Override
    protected Entity getUpdateEntity(Entity entity) {
        return RecipePriority.STATIM;
    }

    @Override
    protected Entity getNewEntity() {
        return RecipePriority.STATIM;
    }

    @Override
    @Test (expected = UnsupportedOperationException.class)
    public void updateTest() {
        super.updateTest();
    }

    @Override
    @Test (expected = UnsupportedOperationException.class)
    public void addTest() {
        super.addTest();
    }

    @Override
    @Test (expected = UnsupportedOperationException.class)
    public void deleteTest() {
        super.deleteTest();
    }
}