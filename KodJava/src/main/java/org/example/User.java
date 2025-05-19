package org.example;


import java.util.ArrayList;

public abstract class User
{
    protected final String login;
    protected String password;
    protected final ArrayList<Course> courses = new ArrayList<>();

    public User(String login, String password) {
        this.login = login;
        this.password = password;
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

    public abstract UserType getUserType();

    public boolean courseExists(String name)
    {
        for(Course c : courses)
        {
            if(c.getName().equals(name))
                return true;
        }
        return false;
    }
}
