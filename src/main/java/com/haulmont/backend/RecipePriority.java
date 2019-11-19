package com.haulmont.backend;

public enum RecipePriority {
    NORMAL("Нормальный"),
    CITO("Срочный"),
    STATIM("Немедленный");

    private String title;

    RecipePriority(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
