package org.example;

import java.util.ArrayList;

public class Course
{
    private Teacher creator;
    private String name;
    private Department department;
    private ArrayList<Attachment> attachments = new ArrayList<Attachment>();
    public Course(String name, Department department)
    {
        this.name = name;
        this.department = department;
    }
    public String getName()
    {
        return name;
    }
}
