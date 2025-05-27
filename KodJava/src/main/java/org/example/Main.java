package org.example;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        //CAMPUS INIT - DEBUG
        Campus.getInstance().createAdmin("Admin", "Admin");
        Campus.getInstance().createTeacher("teach1", "teach1", Department.it);
        Campus.getInstance().createStudent("stud1", "stud1", Department.it);  //czwarty argument to "wydział startowy"

        //SESSION INIT
        Scanner in = new Scanner(System.in); //console reader


        System.out.println("Welcome to Teastu! Please log in to begin your learning journey :D :D ");

        while(true)
        {
            User activeUser;
            System.out.print("Type your login (empty to exit): ");
            String username = in.nextLine();
            if (Objects.equals(username, "")) {
                System.out.println("Are you sure you want to quit? y/N");
                if (in.nextLine().equalsIgnoreCase("y"))
                    break;
                else
                    continue;
            }
            activeUser = handleLoginAttempt(username, in);
            if (activeUser == null)
                continue;
            switch (activeUser.getUserType()) {
                case admin -> adminSession(activeUser, in);
                case student -> studentSession(activeUser, in);
                case teacher -> teacherSession(activeUser, in);
                default -> System.out.println("Unknown user type!");
            }
        }

        in.close();
        System.out.println("Thank you for using Teastu! Goodbye!");
    }


    private static User handleLoginAttempt(String username, Scanner in)
    {
        System.out.print("Type password: ");
        String typedPassword = in.nextLine();

        User user = Campus.getInstance().userLogin(username, typedPassword);
        if (user == null)
            System.out.println("Invalid login or password!");
        return user;
    }

    private static void studentSession(User activeUser, Scanner in) {
        boolean sessionActive = true;
        while (sessionActive) {
            System.out.println("\n=== Student Menu ===");
            System.out.println("1:  View already joined courses");
            System.out.println("2:  View courses available for joining");
            System.out.println("Empty: Logout");

            String choice = in.nextLine();

            switch (choice) {
                case "1" -> availableCoursesMenu(activeUser, false, in);
                case "2" -> availableCoursesMenu(activeUser, true, in);
                case "" ->
                {
                    System.out.println("Are you sure you want to quit? y/N\n");
                    if (in.nextLine().equalsIgnoreCase("y"))
                        sessionActive = false;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private static void teacherSession(User activeUser, Scanner in) {
        Teacher teacher = (Teacher) activeUser;
        boolean sessionActive = true;
        while (sessionActive) {
            System.out.println("\n=== Teacher Menu ===");
            System.out.println("1: View my courses");
            System.out.println("2: Create new course");

            System.out.println("Empty: Logout");

            String choice = in.nextLine();
            switch (choice) {
                case "1" -> availableCoursesMenu(activeUser, false, in);
                case "2" -> createCourseMenu(activeUser, in);

                case "" -> {
                    System.out.println("Are you sure you want to quit? y/N");
                    if (in.nextLine().equalsIgnoreCase("y"))
                        sessionActive = false;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private static void createCourseMenu(User activeUser, Scanner in) {
        Teacher teacher = (Teacher) activeUser;
        System.out.print("Enter course name: ");
        String name = in.nextLine();
        if (name.isEmpty()) return;
        if (activeUser.courseExists(name))
        {
            System.out.println("You've already created a course with that name!");
            return;
        }
        Department department;
        System.out.println(
                "Do you want to make the course with no assigned department," +
                        " available to all students? y/N");
        if (in.nextLine().equalsIgnoreCase("y")) department = Department.none;
        else department = teacher.getDepartment();
        Campus.getInstance().createCourse(teacher, name, department);
    }

    private static void adminSession(User activeUser, Scanner in) {
        boolean sessionActive = true;
        while (sessionActive) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1: Add users");
            System.out.println("2: Modify Users");
            System.out.println("Empty: Logout");

            String choice = in.nextLine();

            switch (choice) {
                case "1" -> addUsersMenu(activeUser, in);
                case "2" -> addStudentDepartmentMenu(activeUser, in);
                case "" -> sessionActive = false;
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private static void addStudentDepartmentMenu(User activeUser, Scanner in) {
        System.out.println("=== Adding new Student Department ===");
        //adds a student to a department, so that the student can attend that department's courses
        String modifiedStudentLogin;
        while(true) {
            System.out.print("\nEnter modified student's login: ");
            modifiedStudentLogin = in.nextLine();
            if (modifiedStudentLogin.isEmpty()) return;
            if (Campus.getInstance().userExists(modifiedStudentLogin)) {
                break;
            }
            else System.out.println("User with this login does not exist.");
        }
        Department selectedDepartment;
        System.out.println("Assigning a student to another department...");
        selectedDepartment = chooseDepartment(in, false);
        if (selectedDepartment == null) return;
        if ( Campus.getInstance().addStudentDepartment(modifiedStudentLogin, selectedDepartment) )
            System.out.println("Student added to department successfully.");
        else System.out.println("Student could not be added to department. Most likely they already belong to it.");
    }

    private static Department chooseDepartment(Scanner in, boolean noneAllowed) {
        Department selectedDepartment;
        while (true) {
            System.out.println("Select department by number:");
            Department[] departments = Department.values();
            int validIndex = 1;
            if (noneAllowed) {
                System.out.println("0: none");
            }
            for (Department dept : departments) {
                if (dept != Department.none) {
                    System.out.println(validIndex + ": " + dept);
                    validIndex++;
                }
            }
            String input = in.nextLine();
            if (input.isEmpty()) return null;
            try {
                int deptIndex = Integer.parseInt(input);
                if (noneAllowed && deptIndex == 0) {
                    selectedDepartment = Department.none;
                    break;
                }
                if (deptIndex > 0 && deptIndex <= validIndex - 1) {
                    selectedDepartment = departments[deptIndex];
                    break;
                }
                System.out.println("Invalid department number.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
        return selectedDepartment;
    }

    private static void displayCourses(boolean includeCreator, ArrayList<Course> courses) {
        StringBuilder header = new StringBuilder("INDEX\tName\t");
        if (includeCreator) header.append("Creator\t");
        header.append("Department\t");
        System.out.println(header);

        for (int i = 0; i < courses.size(); i++) {
            Course c = courses.get(i);
            StringBuilder row = new StringBuilder();
            row.append(i).append("\t").append(c.getName()).append("\t");
            if (includeCreator) row.append(c.getCreator().getName()).append("\t");
            row.append(c.getDepartment()).append("\t");
            System.out.println(row);
        }
    }

    private static void availableCoursesMenu(User activeUser, boolean global, Scanner in) {
        UserType userType = activeUser.getUserType();
        ArrayList<Course> courses = new ArrayList<>();
        switch (userType) {
            case student -> {
                if (global) {
                    Student student = (Student) activeUser;
                    for (Department department : student.getDepartments()) {
                        courses.addAll(Campus.getInstance().getDepartmentCourses(department));
                    }
                    courses.addAll(Campus.getInstance().getDepartmentCourses(Department.none));
                    courses.removeAll(activeUser.getCourses());
                }
                else courses = activeUser.getCourses();
            }
            case teacher -> courses = activeUser.getCourses();
            case admin -> {}
            default -> System.out.println("Unknown user type!");
        }
        while (true) {
            switch (userType) {
                case student -> {
                    if (global) System.out.println("\n=== Courses You Can Join ===");
                    else System.out.println("\n=== Your Joined Courses ===");
                    displayCourses(true, courses);
                }
                case teacher -> {
                    System.out.println("\n=== Your Created Courses ===");
                    displayCourses(false, courses);
                }
                case admin -> {
                    System.out.println("\n=== Admins can't browse courses! ===");
                    return;
                }
                default -> {
                    System.out.println("Unknown user type!");
                    return;
                }
            }
            if (!global) System.out.println("Type the index of the course whose menu you want to open (Empty to go back)");
            else System.out.println("Type the index of the course which you want to join (Empty to go back)");
            String courseIndex = in.nextLine();
            if (courseIndex.isEmpty()) {
                break;
            }
            try {
                int index = Integer.parseInt(courseIndex);
                Course course = courses.get(index);
                switch (userType) {
                    case student -> {
                        if (global) {
                                System.out.println("You've successfully joined the course!");
                                activeUser.joinCourse(course);
                                courses.remove(course);
                        }
                        else {
                            courseMenu(activeUser, course, in);
                        }
                    }
                    case teacher -> {
                        courseMenu(activeUser, course, in);
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid index. Please select a number from the list.");
            }
        }
    }

    private static void courseMenu(User activeUser, Course course, Scanner in) {
        if (activeUser.getUserType() != UserType.student && activeUser.getUserType() != UserType.teacher ) {
            System.out.println("User type not allowed here.");
            return;
        }
        while (true) {
            System.out.println("\n=== Course Menu: " + course.getName() + " ===");
            System.out.println("1: Homework session");
            System.out.println("2: Attachments session");
            System.out.println("Empty: Go back");

            String choice = in.nextLine();
            switch (choice) {
                case "1" -> homeworkSession(activeUser, course, in);
                case "2" -> attachmentSession(activeUser, course, in);
                case "" -> {
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void homeworkSession(User activeUser, Course course, Scanner in) {
        while (true) {
            System.out.println("\n=== Homework Session ===");
            switch (activeUser.getUserType()) {
                case teacher -> {
                    System.out.println("1: View homeworks");
                    System.out.println("2: Add homework");
                    System.out.println("3: Grade homeworks");
                    System.out.println("Empty: Go back");

                    String choice = in.nextLine();
                    switch (choice) {
                        case "1" -> {
                            System.out.println("Homework assignments:");
                            for (int i = 0; i < course.getHomeworks().size(); i++) {
                                System.out.println((i + 1) + ". " + course.getHomeworks().get(i));
                            }
                        }
                        case "2" -> {
                            System.out.print("Enter homework description: ");
                            String hw = in.nextLine();
                            if (course.addHomework(hw))
                                System.out.println("Homework added successfully.");
                            else
                                System.out.println("Homework already exists.");
                        }
                        case "3" -> gradeHomeworks(course, in);
                        case "" -> { return; }
                        default -> System.out.println("Invalid option.");
                    }
                }
                case student -> {
                    Student student = (Student) activeUser;
                    System.out.println("Homework List:");
                    ArrayList<String> hws = course.getHomeworks();
                    for (int i = 0; i < hws.size(); i++) {
                        String hw = hws.get(i);
                        String submission = course.getSubmissions(hw, student);
                        System.out.println((i + 1) + ". " + hw + " [" +
                                (submission != null ? "Submitted" : "Not submitted") + "]");
                    }
                    System.out.println("Type homework number to submit or ENTER to go back: ");
                    String input = in.nextLine();
                    if (input.isEmpty())
                        return;
                    try {
                        int index = Integer.parseInt(input) - 1;
                        if (index >= 0 && index < hws.size()) {
                            String selectedHw = hws.get(index);
                            System.out.println("Enter your answer:");
                            String answer = in.nextLine();
                            course.submitHomework(selectedHw, student, answer);
                            System.out.println("Submission received!");
                        } else {
                            System.out.println("Invalid number.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input.");
                    }
                }
                default -> { return; }
            }
        }
    }

    private static void gradeHomeworks(Course course, Scanner in) {
        ArrayList<String> hws = course.getHomeworks();
        if (hws.isEmpty()) {
            System.out.println("No homeworks to grade.");
            return;
        }
        System.out.println("Choose the homework you want to grade:");
        for (int i = 0; i < hws.size(); i++) {
            System.out.println((i + 1) + ". " + hws.get(i));
        }
        String inputHw = in.nextLine();
        try {
            int hwIndex = Integer.parseInt(inputHw) - 1;
            if (hwIndex < 0 || hwIndex >= hws.size()) {
                System.out.println("Invalid homework number.");
                return;
            }
            String selectedHw = hws.get(hwIndex);
            Map<Student, String> submissions = course.getHomeworkSubmissions().get(selectedHw);
            if (submissions == null || submissions.isEmpty()) {
                System.out.println("No assignments for this task.");
                return;
            }
            System.out.println("List of submitted homeworks:");
            ArrayList<Student> students = new ArrayList<>(submissions.keySet());
            for (int i = 0; i < students.size(); i++) {
                Student s = students.get(i);
                String submission = submissions.get(s);
                String grade = course.getGrade(selectedHw, s);
                System.out.println((i + 1) + ". " + s.getName() + " - Answer: " + submission + " - Grade: " + (grade != null ? grade : "No grade."));
            }
            System.out.print("Pick the student number to grade the homework: ");
            String inputStudent = in.nextLine();
            int studentIndex = Integer.parseInt(inputStudent) - 1;
            if (studentIndex < 0 || studentIndex >= students.size()) {
                System.out.println("Invalid student number.");
                return;
            }
            Student selectedStudent = students.get(studentIndex);
            System.out.print("Grade: ");
            String grade = in.nextLine();
            course.gradeHomework(selectedHw, selectedStudent, grade);
            System.out.println("Grade saved!");
        } catch (NumberFormatException e) {
            System.out.println("Wrong number format");
        }
    }

    private static void attachmentSession(User activeUser, Course course, Scanner in) {
        while (true) {
            System.out.println("\n=== Attachments Session ===");
            System.out.println("1: View attachments");
            if (activeUser.getUserType() == UserType.teacher)
                System.out.println("2: Add attachment");
            System.out.println("Empty: Go back");

            String choice = in.nextLine();
            switch (choice) {
                case "1" -> viewAttachments(course, in);
                case "2" -> {
                    if (activeUser.getUserType() == UserType.teacher)
                        addAttachment(course, in);
                    else
                        System.out.println("Invalid option.");
                }
                case "" -> {return;}
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void viewAttachments(Course course, Scanner in) {
        ArrayList<Map<String, String>> attachments = course.getAttachments();
        if (attachments.isEmpty()) {
            System.out.println("No attachments available.");
            return;
        }
        System.out.println("Attachments:");
        for (int i = 0; i < attachments.size(); i++) {
            Map<String, String> attachment = attachments.get(i);
            System.out.printf("%d. %s - Content: %s\n",
                    i + 1,
                    attachment.get("name"),
                    attachment.get("content"));
        }
        System.out.println("Press ENTER to go back");
        in.nextLine();
    }

    private static void addAttachment(Course course, Scanner in) {
        System.out.print("Enter attachment name: ");
        String name = in.nextLine();
        if (name.isEmpty()) {
            System.out.println("Attachment creation cancelled.");
            return;
        }

        System.out.print("Enter attachment content (can be empty): ");
        String content = in.nextLine();

        course.addAttachment(name, content);
        System.out.println("Attachment added successfully.");
    }
    private static void addUsersMenu(User activeUser, Scanner in) {
        System.out.println("=== Adding new User ===");
        String newUserLogin;
        while(true) {
            System.out.print("\nEnter new user's login.\nPunctuation symbols disallowed, press ENTER to go back: ");
            newUserLogin = in.nextLine();
            if (newUserLogin.isEmpty()) return;
            if (!inputIsValid(newUserLogin, false, false)) {
                System.out.println("Forbidden characters. Type new input.");
                continue;
            }
            if (Campus.getInstance().userExists(newUserLogin)) {
                System.out.println("User with this login already exists.");
                continue;
            }
            break;
        }
        String newPassword;
        while(true) {
            System.out.print("\nEnter new user's password.\nSpaces disallowed, symbols allowed are ! # $ % & * + - , . ? @ | ,\npress ENTER to go back: ");
            newPassword = in.nextLine();
            if (newPassword.isEmpty()) return;
            if (!inputIsValid(newUserLogin, false, true)) {
                System.out.println("Forbidden characters. Type new input.");
                continue;
            }
            break;
        }
        UserType newUserType;
        while(true) {
            System.out.print("\nChoose new user's type - A for admin, S for student, T for teacher: ");
            String input;
            input = in.nextLine();
            if (input.isEmpty()) return;
            switch (input) {
                case "A" -> newUserType = UserType.admin;
                case "S" -> newUserType = UserType.student;
                case "T" -> newUserType = UserType.teacher;
                default -> {
                    System.out.println("Invalid input.");
                    continue;
                }
            }
            break;
        }
        if (newUserType.equals(UserType.admin)) {
            Campus.getInstance().createAdmin(newUserLogin, newPassword);
        }
        else {
            Department selectedDepartment;
            System.out.println("\nChoose new user's department - none for no department.");
            selectedDepartment = chooseDepartment(in, false);
            if (selectedDepartment == null) return;
            if (newUserType.equals(UserType.student))
                Campus.getInstance().createStudent(newUserLogin, newPassword, selectedDepartment);
            else
                Campus.getInstance().createTeacher(newUserLogin, newPassword, selectedDepartment);
            System.out.println("User created successfully.");
        }
    }

    private static boolean inputIsValid(String input, boolean allowSpaces, boolean allowPunct) {
        for (char c : input.toCharArray()) {
            if (Character.isLetterOrDigit(c))
                continue;
            if (allowSpaces && (c == ' ' || c == '\t' || c == '\n'))
                continue;
            if (allowPunct && (c == '!' || c == '#' || c == '$' || c == '%' || c == '&' ||  // "!#$%&*+-,.?@|"
                    c == '*' || c == '+' || c == '-' || c == ',' || c == '.' || c == '?' || c == '@' ||
                    c == '|'))
                continue;
            return false;
        }
        return true;
    }

}
