package Day03.ex02;

public class Program {

    public static void main(String[] args) {
        try {
            Utils.checkArgs(args);
            Utils.letsStart();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


}
