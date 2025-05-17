package org.example;

import java.util.ArrayList;

public class Campus
{
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<Course> courses = new ArrayList<Course>();

    public User userLogin(String login, String password)
    {
        for(User u : users)
        {
            if(u.tryLogin(login, password))
            {
                return u;
            }
        }
        return null;
    }
    public void addUser(User user)
    {
        users.add(user);
    }
    public void createCourse(String name, Department department)
    {
        courses.add(new Course(name, department));
    }
}
