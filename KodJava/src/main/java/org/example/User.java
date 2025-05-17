package org.example;

import java.util.ArrayList;

public class User
{
    protected String login;
    protected String password;
    protected UserType userType;
    protected ArrayList<Course> courses = new ArrayList<Course>();
    boolean tryLogin(String login, String password)
    {
        return this.login.equals(login) && this.password.equals(password);
    }
}
