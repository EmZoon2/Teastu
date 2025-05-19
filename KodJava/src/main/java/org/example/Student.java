package org.example;

import java.util.HashSet;

public class Student extends User
{
    private final HashSet<Department> departments = new HashSet<>();

    public Student(String login, String password)
    {
        super(login, password);
    }
    public boolean addDepartment(Department department) {
        return departments.add(department); //zwraca true jeśli udało się dodać do hasha
    }

    @Override
    public UserType getUserType() {
        return UserType.student;
    }

    public HashSet<Department> getDepartments() {return departments;}

}
