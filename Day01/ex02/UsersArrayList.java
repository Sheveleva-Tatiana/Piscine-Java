package Day01.ex02;

public class UsersArrayList implements UsersList {

    private int arraySize = 10;
    private User[] users = new User[arraySize];
    private int count = 0;

    @Override
    public void addUser(User newUser) {
        if (this.count == this.arraySize) {
            User[] newUsers = new User[arraySize * 2];
            for (int i = 0; i < this.arraySize; i++){
                newUsers[i] = this.users[i];
            }
            this.arraySize = this.arraySize * 2;
            this.users = newUsers;
            this.users[count++] = newUser;
        } else {
            this.users[count++] = newUser;
        }
    }


    @Override
    public User getUserById(int id) {
        for (int i = 0; i < this.count; i++) {
            if (users[i].getIdentifier() == id) {
                return users[i];
            }
        }
        throw new UserNotFoundException("User with id " + id + " not found");
    }

    @Override
    public User getUserByIndex(int index) {
        if (index < this.count && index >= 0) {
            return users[index];
        }
        throw new UserNotFoundException("User with index " + index + " not found");
    }

    @Override
    public int getUserCount() {
         return this.count;
    }


    public void printInfo(){
        for (int i = 0; i < this.count; i++){
            System.out.print(i + "\tName: " + users[i].getName() + "  balance: " + users[i].getBalance());
            System.out.println("\tid: " + users[i].getIdentifier());
        }
    }
}
