package org.example;

public class Teacher extends User
{
    private Department department;
    public Teacher(String login, String password, UserType userType, Department department)
    {
        super(login, password, userType);
        this.department = department;
    }
}
