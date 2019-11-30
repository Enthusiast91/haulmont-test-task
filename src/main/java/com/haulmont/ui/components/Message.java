package com.haulmont.ui.components;

public abstract class Message {

    public static String nameValidationError() {
        return "Строка от 2 до 30 знаков. Без цифр";
    }

    public static String patronymicValidationError() {
        return "Строка от 0 до 30 знаков. Без цифр";
    }

    public static String phoneValidationError() {
        return "Введите корректный телефон";
    }

    public static String descriptionValidationError() {
        return "Описание должно содержать больше 10 символов";
    }

    public static String notNullError(String type) {
        return "Поле " + type + " не может быть пустым";
    }
}
