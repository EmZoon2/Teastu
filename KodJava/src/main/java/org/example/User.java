package org.example;


import java.util.ArrayList;

public class User
{
    protected String login;
    protected String password;
    protected ArrayList<Course> courses = new ArrayList<>();
    protected UserType userType;

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
        courses.add(course);
    }

    public ArrayList<Course> getCourses() {return courses;}

    public String getName() {return login;}

    public boolean courseExists(String name) {
        for(Course c : courses)
            if(c.getName().equals(name))
                return true;
        return false;
    }

    public UserType getUserType() {return userType;}

}
