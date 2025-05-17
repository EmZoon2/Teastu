package org.example;

import java.util.HashSet;

public class Student extends User
{
    private HashSet<Department> departments = new HashSet<>();
    public Student(String login, String password, UserType userType)
    {
        super(login, password, userType);
    }
    public void addDepartment(String department)
    {
        //todo
    }
}
