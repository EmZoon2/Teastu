package org.example;

import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        //CAMPUS INIT
        Campus campus = new Campus();
        campus.addUser(new User("Admin", "Admin", UserType.admin));
        campus.addUser(new Teacher("sKotyra", "Bieszczady51", UserType.teacher, Department.it));
        campus.addUser(new Student("nieuk1", "Hasl0", UserType.student));

        //SESSION INIT
        Scanner in = new Scanner(System.in); //console reader
        User activeUser = null;
        boolean keepRunning = true; //co to kurwa jest

        System.out.println("Welcome to Teastu! Please log in to begin your learning journey :D :D ");

        while(keepRunning)
        {
            activeUser = handleLogin(campus, in);
            if (activeUser == null) {
                break;
            }
            switch (activeUser.userType) {
                case admin -> adminSession(campus, activeUser, in);
                case student -> studentSession(campus, activeUser, in);
                case teacher -> teacherSession(campus, activeUser, in);
                default -> System.out.println("Unknown user type!");
            }

        }

        in.close();
        System.out.println("Thank you for using Teastu! Goodbye!");
    }


    // prywatne metody do trywialnej logiki (takie które można zastąpić inline-owym blokiem kodu) nie muszą być w UML
    private static User handleLogin(Campus campus, Scanner in) {
        System.out.print("Type -login (empty to exit): ");
        String typedLogin = in.nextLine();
        if (typedLogin.isEmpty()) {
            return null;
        }

        System.out.print("Type password: ");
        String typedPassword = in.nextLine();

        User user = campus.userLogin(typedLogin, typedPassword);
        if (user == null) {
            System.out.println("Invalid login or password!");
        }
        return user;
    }

    private static void studentSession(Campus campus, User activeUser, Scanner in) {
        boolean sessionActive = true;
        while (sessionActive) {
            System.out.println("\n=== Student Menu ===");
            System.out.println("1:  View already joined courses");
            System.out.println("2:  View courses available for joining");
            System.out.println("Empty: Logout"); //todo confirm with Y to logout

            String choice = in.nextLine();

            switch (choice) {
                case "1" -> System.out.println("Viewing joined courses...");
                case "2" -> System.out.println("Viewing available courses...");
                case "" -> sessionActive = false;
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private static void teacherSession(Campus campus, User activeUser, Scanner in) {
        boolean sessionActive = true;
        while (sessionActive) {
            System.out.println("\n=== Teacher Menu ===");
            System.out.println("1: View my courses");
            System.out.println("2: Create new course");
            System.out.println("Empty: Logout"); //todo confirm Y to logout


            String choice = in.nextLine();

            switch (choice) {
                case "1" -> {}
                case "2" -> {
                    boolean courseCreated = false;
                    while (!courseCreated) {
                        System.out.print("Enter course name: ");
                        courseCreated = campus.createCourse(in.nextLine(), activeUser.getDepartment(), activeUser);
                        ; //todo change to getter
                        if (!courseCreated) System.out.println("You already created a course with that name!");
                    }
                }
                case "" -> sessionActive = false;
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private static void adminSession(Campus campus, User activeUser, Scanner in) {
        boolean sessionActive = true;
        while (sessionActive) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1: Manage users");
            System.out.println("2: Manage courses");
            //System.out.println("3. View system logs");
            System.out.println("Empty: Logout");


            String choice = in.nextLine();

            switch (choice) {
                case "1" -> System.out.println("Managing users..."); // Implement this
                case "2" -> System.out.println("Managing courses..."); // Implement this
                //case "3" -> System.out.println("Viewing logs..."); // Implement this
                case "" -> sessionActive = false;
                default -> System.out.println("Invalid option!");
            }
        }
    }

}

