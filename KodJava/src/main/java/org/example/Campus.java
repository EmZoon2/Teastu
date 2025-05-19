package org.example;

import java.util.ArrayList;

public class Campus {
    private static Campus instance = null;
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Course> courses = new ArrayList<>();

    private Campus() {
    }

    public static Campus getInstance() {
        if (instance == null)
            instance = new Campus();
        return instance;
    }

    public User userLogin(String login, String password) {
        for (User u : users) {
            if (u.tryLogin(login, password)) {
                return u;
            }
        }
        return null;
    }

    public void createStudent(String login, String password, Department startingDepartment) {
        Student createdStudent = new Student(login, password);
        users.add(createdStudent);
        createdStudent.addDepartment(startingDepartment);
    }

    public void createTeacher(String login, String password, Department department) {
        Teacher createdTeacher = new Teacher(login, password, department);
        users.add(createdTeacher);
    }

    public void createAdmin(String login, String password) {
        Admin createdAdmin = new Admin(login, password);
        users.add(createdAdmin);
    }

    public void createCourse(Teacher creator, String name, Department department) {
        Course createdCourse = new Course(name, department, creator);
        courses.add(createdCourse);
        creator.joinCourse(createdCourse);
    }



    public ArrayList<Course> getDepartmentCourses(Department department) {
        ArrayList<Course> departmentCourses = new ArrayList<>();
        for (Course c : courses) {
            if (c.getDepartment().equals(department)) {
                departmentCourses.add(c);
            }
        }
        return departmentCourses;
    }
}