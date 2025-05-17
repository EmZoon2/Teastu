package org.example;

import java.util.ArrayList;

public class Teacher extends User
{
    private Department department;
    private ArrayList<Course> createdCourses = new ArrayList<Course>();
    public Teacher(String login, String password, UserType userType, Department department)
    {
        super(login, password, userType);
        this.department = department;
    }

    public boolean courseExists(String name)
    {
        for(Course c : createdCourses)
        {
            if(c.getName().equals(name))
            {
                return true;
            }
        }
        return false;
    }


}
