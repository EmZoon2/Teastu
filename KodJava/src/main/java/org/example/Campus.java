package org.example;

import java.util.ArrayList;

public class Campus {
    private static Campus instance = null;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Course> courses = new ArrayList<>();

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

    public void createStudent(String login, String password, Department dept) {
        Student createdStudent = new Student(login, password, UserType.student);
        users.add(createdStudent);
        createdStudent.addDepartment(dept);
    }

    public void createAdmin(String login, String password) {
        Admin createdAdmin = new Admin(login, password, UserType.admin);
        users.add(createdAdmin);
    }

    public void createTeacher(String login, String password, Department dept) {
        Teacher createdTeacher = new Teacher(login, password, UserType.teacher, dept);
        users.add(createdTeacher);
    }

    public void createCourse(Teacher creator, String name, Department dept) {
        Course createdCourse = new Course(name, dept, creator);
        courses.add(createdCourse);
        creator.joinCourse(createdCourse);
    }



    public ArrayList<Course> getDepartmentCourses(Department dept) {
        ArrayList<Course> departmentCourses = new ArrayList<>();
        for (Course c : courses) {
            if (c.getDepartment().equals(dept)) {
                departmentCourses.add(c);
            }
        }
        return departmentCourses;
    }

    public boolean userExists(String name) {
        for(User u : users)
            if(u.getName().equals(name))
                return true;
        return false;
    }

    boolean addStudentDepartment(String login, Department dept) {
        Student student = (Student) fetchUser(login);
        return student.addDepartment(dept);
    }

    private User fetchUser(String login) {
        for( User u : users )
            if( u.getName().equals( login ) )
                return u;
        return null;
    }

}