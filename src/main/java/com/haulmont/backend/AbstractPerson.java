package com.haulmont.backend;

import com.haulmont.backend.dao.SQLEntity;

public abstract class AbstractPerson implements SQLEntity {
    protected final long id;
    protected String name;
    protected String lastName;
    protected String patronymic;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    protected AbstractPerson(long id, String name, String lastName, String patronymic) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.patronymic = patronymic;
    }

    @Override
    public long getId() {
        return id;
    }
}
