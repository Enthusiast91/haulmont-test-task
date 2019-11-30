package com.haulmont.backend;

import com.haulmont.backend.dao.Entity;

public enum RecipePriority implements Entity {
    NORMAL("НОРМАЛЬНЫЙ"),
    CITO("СРОЧНЫЙ"),
    STATIM("НЕМЕДЛЕННЫЙ");

    private long id;
    private String title;

    RecipePriority(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public long getId() {
        return ordinal();
    }
}

