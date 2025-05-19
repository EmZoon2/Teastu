package org.example;

import java.util.ArrayList;

public class Course
{
    private final Teacher creator;
    private final String name;
    private final Department department;
    private final ArrayList<Attachment> attachments = new ArrayList<>();


    public Course(String name, Department department, Teacher creator)
    {
        this.name = name;
        this.department = department;
        this.creator = creator;
    }
    public String getName() {return name;}
    public Department getDepartment() {return department;}
    public Teacher getCreator() {return creator;}

}
