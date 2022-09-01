package com.epam.cashierregister.services.entities.employee;

import com.epam.cashierregister.services.entities.Entity;

public class Employee implements Entity {
    private int id;
    private String photo;
    private String firstname;
    private String secondname;
    private Role role;
    private AuthorizeInfo authorize;

    public Employee(int id, String photo, String firstname, String secondname, Role role, AuthorizeInfo authorize) {
        this.id = id;
        this.photo = photo;
        this.firstname = firstname;
        this.secondname = secondname;
        this.role = role;
        this.authorize = authorize;
    }

    public Employee() {

    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public AuthorizeInfo getAuthorize() {
        return authorize;
    }

    public void setAuthorize(AuthorizeInfo authorize) {
        this.authorize = authorize;
    }
}
