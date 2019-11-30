package com.haulmont.ui.components;

import com.haulmont.backend.dao.Entity;

public interface Viewable<E> extends Entity {
    E getCopy();
}
