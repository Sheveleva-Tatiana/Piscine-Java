package Day01.ex05;

public interface UsersList {

    void addUser(User newUser);
    User getUserById(int id);
    User getUserByIndex(int index);
    int getUserCount();

}
