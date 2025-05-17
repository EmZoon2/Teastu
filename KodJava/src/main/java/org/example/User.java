package org.example;


import java.util.ArrayList;

public class User
{
    protected String login;
    protected String password;
    protected UserType userType;
    protected ArrayList<Course> courses = new ArrayList<Course>();

    public User(String login, String password, UserType userType) {
        this.login = login;
        this.password = password;
        this.userType = userType;
    }

    public boolean tryLogin(String login, String password)
    {
        return this.login.equals(login) && this.password.equals(password);
    }

    public void joinCourse(Course course)
    {
        //todo: avoid duplicates
        courses.add(course);
    }

    public Department getDepartment() {
        return null;
    }
}
