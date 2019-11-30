package com.haulmont.backend.dao;

import com.haulmont.backend.RecipePriority;
import org.junit.Test;

public class RecipePriorityDaoTest extends AbstractEntityDAOTest<RecipePriority> {
    public RecipePriorityDaoTest() {
        super(RecipePriorityDao.getInstance());
    }

    @Override
    protected RecipePriority getUpdateEntity(RecipePriority entity) {
        return RecipePriority.STATIM;
    }

    @Override
    protected RecipePriority getNewEntity() {
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