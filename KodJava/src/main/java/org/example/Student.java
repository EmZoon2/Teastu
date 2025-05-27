package org.example;

import java.util.HashSet;

public class Student extends User
{
    private HashSet<Department> departments = new HashSet<>();

    public Student(String login, String password, UserType userType)
    {
        super(login, password, userType);
    }

    public boolean addDepartment(Department department) {
        return departments.add(department);
    }


    public HashSet<Department> getDepartments() {return departments;}

}
