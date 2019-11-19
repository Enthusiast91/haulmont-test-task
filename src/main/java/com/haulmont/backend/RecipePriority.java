package com.haulmont.backend;

public enum  RecipePriority {
    STATIM("Немедленный"),
    CITO("Срочный"),
    NORMAL("Нормальный");

    private  String title;
    RecipePriority(String title) {
        this.title = title;
    }
}
