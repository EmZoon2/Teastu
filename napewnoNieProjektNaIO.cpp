#include <iostream>
#include <vector>

enum UserType
{
    Teacher,
    Student
};

class Attachment
{
    std::string name;
    std::string content;
};

class Course
{
    std::vector<Attachment> resources;
};

class User
{
    UserType userType;
    std::string login;
    std::string password;
    std::vector<Course> courses;


 public:

    std::string getLogin()
    {
        return this->login;
    }

    std::string getPassword()
    {
        return this->password;
    }

    void setLogin(std::string login)
    {
        this->login = login;
    }

    void setPassword(std::string password)
    {
        this->password = password;
    }
};

class Campus
{
    std::vector<User> users;
    User activeUser;

public:

    void addUser();
    void login();
};

void Campus::addUser()
{
    User user;
    std::string login, password;
    int flag;
    std::cin>>login>>password;
    user.setLogin(login);
    user.setPassword(password);
    this->users.push_back(user);
};

void Campus::login()
{
    bool flag = true;
    std::string login, password;
    std::cin>>login>>password;
    for(int i = 0; i < this->users.size(); i++) 
    {
        if(users[i].getLogin() == login && users[i].getPassword() == password)
        {
            this->activeUser = users[i];
            std::cout<<"Zalogowano\n";
            flag = false;
            break;
        }
    }    
    std::cout<<"Nieprawidlowy Login lub Haslo\n";
}

int main()
{
    Campus campus;
    while(true)
    {
        campus.addUser();
        campus.login();
    }
    return 0;
}