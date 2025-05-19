package org.example;

import java.util.ArrayList;

public class Teacher extends User
{
    private final Department department;
    public Teacher(String login, String password, Department department)
    {
        super(login, password);
        this.department = department;
    }

    public Department getDepartment() {
        return department;
    }

    @Override
    public void joinCourse(Course course) {
        courses.add(course);
    }

    @Override
    public UserType getUserType() {
        return UserType.teacher;
    }


}
