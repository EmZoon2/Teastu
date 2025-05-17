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
        Scanner in = new Scanner(System.in);
        User activeUser = null;
        boolean keepRunning = true;

        //LOOP
        while(keepRunning)
        {
            switch(activeUser.userType)
            {
                    case admin ->
                    {
                        //admins operations menu
                    }
                    case student ->
                    {
                        //students operations menu
                    }
                    case teacher ->
                    {
                        //teacher operations menu
                    }
                    case null, default -> {activeUser = campus.userLogin(in.nextLine(), in.nextLine());}
            }
        }
    }
}