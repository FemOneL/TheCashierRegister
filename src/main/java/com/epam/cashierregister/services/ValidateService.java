package com.epam.cashierregister.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateService {
    private Pattern pattern;
    private Matcher matcher;


    public boolean validateEmail(String email) {
        if (email == null) {
            return false;
        }
        pattern = Pattern.compile("\\w+@\\w{2,}\\.\\w{2,}");
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePassword(String password) {
        if (password == null) {
            return false;
        }
        pattern = Pattern.compile("^\\w{6,10}$");
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public boolean validateSelect(String select, String newElement) {
        return !((select.equals("none") && newElement.equals("")) || (!select.equals("none") && !newElement.equals("")));
    }

    public boolean validateName(String firstName, String secondName) {
        if (firstName == null || secondName == null) {
            return false;
        }
        pattern = Pattern.compile("^\\w{2,50}$");
        return pattern.matcher(firstName).matches() && pattern.matcher(secondName).matches();
    }

    public boolean validateCostField(String input) {
        if (input == null) {
            return false;
        }
        pattern = Pattern.compile("\\d+[0-9](\\.\\d+)?");
        matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public boolean validateModel(String model) {
        return model != null && !model.trim().equals("");
    }


}
