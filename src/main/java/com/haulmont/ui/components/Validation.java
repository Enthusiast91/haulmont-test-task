package com.haulmont.ui.components;

public abstract class Validation {

    public static boolean namedIsValid(String text) {
        String regEx = "^[а-яА-ЯёЁa-zA-Z]{2,30}$";
        return text.matches(regEx);
    }

    /**
     * Россия интернационалная страна и её гражданином
     * могут стать люди у кого нет отчества
     */
    public static boolean patronymicIsValid(String text) {
        String regEx = "^[а-яА-ЯёЁa-zA-Z]{0,30}$";
        return text.matches(regEx);
    }

    public static boolean phoneIsValid(String text) {
        String regEX = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";
        return text.matches(regEX);
    }

    public static boolean validityIsValid(String text) {
        if (!notEmpty(text)) {
            return false;
        }

        int validity;
        try {
            validity = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return false;
        }
        return validity >= 0;
    }

    public static boolean descriptionIsValid(String text) {
        return text.length() > 10;
    }

    public static boolean notNull(Object object) {
        return object != null;
    }

    public static boolean notEmpty(String text) {
        return text != null && !text.trim().equals("");
    }
}