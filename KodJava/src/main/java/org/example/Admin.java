package org.example;


public class Admin extends User {
    public Admin(String login, String password)
    {
        super(login, password);
    }

    @Override
    public UserType getUserType() {
        return UserType.admin;
    }

}
