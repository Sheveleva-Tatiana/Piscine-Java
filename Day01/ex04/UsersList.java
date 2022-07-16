package Day01.ex04;

public interface UsersList {

    void addUser(User newUser);
    User getUserById(int id);
    User getUserByIndex(int index);
    int getUserCount();

}
