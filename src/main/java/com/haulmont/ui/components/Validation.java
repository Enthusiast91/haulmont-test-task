package com.haulmont.ui.components;

public abstract class Validation {

    public static boolean namedIsValid(String text) {
        String regEx = "^[а-яА-ЯёЁa-zA-Z]{2,30}$";
        return text.matches(regEx);
    }

    public static boolean patronymicIsValid(String text) {
        String regEx = "^[а-яА-ЯёЁa-zA-Z]{0,30}$";
        return text.matches(regEx);
    }

    public static boolean phoneIsValid(String text) {
        //String regEX = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";
        String regEX = "[0-9]{5,15}$";
        return text.matches(regEX);
    }

    public static boolean descriptionIsValid(String text) {
        return text.length() > 10;
    }

    public static boolean notNull(Object object) {
        return object != null;
    }
}