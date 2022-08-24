package com.epam.cashierregister.services.entities.employee;

public class AuthorizeInfo {
    private int id;
    private String email;
    private String password;

    public AuthorizeInfo(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AuthorizeInfo(int id, String email) {
        this.email = email;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
