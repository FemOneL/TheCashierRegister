package com.epam.cashierregister.services;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateService {

    public boolean validateEmail(String email) {
        if (email == null) {
            return false;
        }
        Pattern p = Pattern.compile("\\w+@\\w{2,}\\.\\w{2,}");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public boolean validatePassword(String password) {
        if (password == null) {
            return false;
        }
        Pattern p = Pattern.compile("\\w{6,}");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public boolean validateSelect(String select, String newElement) {
        return !((select.equals("none") && newElement.equals("")) || (!select.equals("none") && !newElement.equals("")));
    }

    public boolean validateNumber(int number){
        return number > 0;
    }

    public boolean validateModel(String model){
        return model != null && !model.trim().equals("");
    }


}
