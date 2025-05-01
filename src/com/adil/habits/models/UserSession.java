package com.adil.habits.models;

public class UserSession {
    private String name;
    private String surname;
    private String username;
    private String password;

    private static UserSession currentSession;

    public UserSession(String name, String surname, String username, String password) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
    }

    public static void setSession(String name, String surname, String username, String password) {
        currentSession = new UserSession(name, surname, username, password);
    }

    public static UserSession getSession() { 
        return currentSession;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
