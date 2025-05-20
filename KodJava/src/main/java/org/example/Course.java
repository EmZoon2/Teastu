package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Course
{
    private final Teacher creator;
    private final String name;
    private final Department department;
    private final ArrayList<Attachment> attachments = new ArrayList<>();
    private final ArrayList<String> homeworks = new ArrayList<>();
    public final Map<String, Map<Student, String>> homeworkSubmissions = new HashMap<>();
    private final Map<String, Map<Student, String>> homeworkGrades = new HashMap<>();


    public Course(String name, Department department, Teacher creator)
    {
        this.name = name;
        this.department = department;
        this.creator = creator;
    }
    public String getName() {return name;}
    public Department getDepartment() {return department;}
    public Teacher getCreator() {return creator;}
    public void addHomework(String homework){
        homeworks.add(homework);
        homeworkSubmissions.put(homework, new HashMap<>());
    }
    public ArrayList<String> getHomeworks(){
        return homeworks;
    }
    public void submitHomework(String homework, Student student, String answer){
        if (homeworkSubmissions.containsKey(homework)){
            homeworkSubmissions.get(homework).put(student, answer);
        }
    }
    public  String getSubmissions(String homework, Student student){
        return homeworkSubmissions.getOrDefault(homework, new HashMap<>()).get(student);
    }
    public void gradeHomework(String homework, Student student , String grade){
        homeworkGrades.computeIfAbsent(homework, k -> new HashMap<>()).put(student, grade);
    }
    public String getGrade(String homework, Student student) {
        return homeworkGrades.getOrDefault(homework, new HashMap<>()).get(student);
    }


}
