package Day01.ex01;

public class UserIdsGenerator {

    private static final UserIdsGenerator obj;
    private Integer identifier = 0;

    public static UserIdsGenerator getInstance() {
        if (obj == null) {
            obj = new UserIdsGenerator();
        }
        return obj;
    }
    
    private UserIdsGenerator(){}

    public int generateId() {
        return ++identifier;
    }

    public Integer getIdentifier() {
        return identifier;
    }
}
