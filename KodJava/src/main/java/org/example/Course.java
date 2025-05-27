package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Course
{
    private Teacher creator;
    private String name;
    private Department department;
    private ArrayList<String> homeworks = new ArrayList<>();
    private Map<String, Map<Student, String>> homeworkSubmissions = new HashMap<>();
    private Map<String, Map<Student, String>> homeworkGrades = new HashMap<>();
    private ArrayList<Map<String, String>> attachments = new ArrayList<>();

    public Course(String name, Department department, Teacher creator)
    {
        this.name = name;
        this.department = department;
        this.creator = creator;
    }

    public String getName() {return name;}

    public Department getDepartment() {return department;}

    public Teacher getCreator() {return creator;}

    public boolean addHomework(String homework){
        if (homeworks.contains(homework)) return false;
        homeworks.add(homework);
        homeworkSubmissions.put(homework, new HashMap<>());
        return true;
    }
    public ArrayList<String> getHomeworks(){
        return homeworks;
    }

    public void submitHomework(String homework, Student student, String answer) {
        if (homeworkSubmissions.containsKey(homework)) {  //can overwrite already graded homework, that's ok
            Map<Student, String> grades = homeworkGrades.get(homework);
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
    public ArrayList<Map<String, String>> getAttachments() {
        return attachments;
    }
    public void addAttachment(String name, String content) {
        //adding attachment with existing name simply overwrites the attachment
        Map<String, String> attachment = new HashMap<>();
        attachment.put("name", name);
        attachment.put("content", content);
        attachments.add(attachment);
    }

    public Map<String, Map<Student, String>> getHomeworkSubmissions() {
        return homeworkSubmissions;
    }
}
