package Day01.ex05;

public class UserIdsGenerator {

    private static UserIdsGenerator obj;
    private static Integer identifier = 0;

    public static UserIdsGenerator getInstance() {
        if (obj == null) {
            obj = new UserIdsGenerator();
        }
        return obj;
    }

    public int generateId() {
        return ++identifier;
    }

    public static Integer getIdentifier() {
        return identifier;
    }
}
