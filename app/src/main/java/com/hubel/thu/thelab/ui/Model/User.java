package com.hubel.thu.thelab.ui.model;

/**
 * Created by thu on 12.04.16.
 */
public class User {

    private int birthYear;
    private String fullName;

    public User() {}
    public User(String fullName, int birthYear) {
        this.fullName = fullName;
        this.birthYear = birthYear;
    }
    public long getBirthYear() {
        return birthYear;
    }
    public String getFullName() {
        return fullName;
    }
}
