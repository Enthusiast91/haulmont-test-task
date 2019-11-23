package com.haulmont.backend;

import com.haulmont.backend.dao.SQLEntity;
import org.omg.CORBA.UNKNOWN;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public enum RecipePriority implements SQLEntity {
    NORMAL("Нормальный"),
    CITO("Срочный"),
    STATIM("Немедленный");

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
