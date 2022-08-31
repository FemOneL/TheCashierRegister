package com.epam.cashierregister.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service for validation by regex
 * @see Pattern
 * @see Matcher
 */
public class ValidateService {
    private Pattern pattern;
    private Matcher matcher;

    /**
     * @param email
     * @return true if email correct and != null
     */
    public boolean validateEmail(String email) {
        if (email == null) {
            return false;
        }
        pattern = Pattern.compile("\\w{2,80}@\\w{2,15}\\.\\w{2,5}");
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * @param password
     * @return true if password correct and != null
     */
    public boolean validatePassword(String password) {
        if (password == null) {
            return false;
        }
        pattern = Pattern.compile("^\\w{6,15}$", Pattern.UNICODE_CHARACTER_CLASS);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /**
     * @param select selected item
     * @param newElement new item
     */
    public boolean validateSelect(String select, String newElement) {
        if (select == null || newElement == null) {
            return false;
        }

        return !((select.equals("none") && newElement.equals("")) || (!select.equals("none") && !newElement.equals("")));
    }

    /**
     * @param firstName
     * @param secondName
     * @return true if name correct and != null
     */
    public boolean validateName(String firstName, String secondName) {
        if (firstName == null || secondName == null) {
            return false;
        }
        pattern = Pattern.compile("^\\w{2,50}$", Pattern.UNICODE_CHARACTER_CLASS);
        return pattern.matcher(firstName).matches() && pattern.matcher(secondName).matches();
    }

    /**
     * @param input
     * @return true if cost field correct and != null
     */
    public boolean validateCostField(String input) {
        if (input == null) {
            return false;
        }
        pattern = Pattern.compile("^\\d+(\\.\\d+)?");
        matcher = pattern.matcher(input);
        return matcher.matches();
    }

    /**
     * @param model
     * @return true if model correct and != null
     */
    public boolean validateModel(String model) {
        return model != null && !model.trim().equals("") && model.length() < 50;
    }


}
