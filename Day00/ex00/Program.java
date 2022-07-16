package Day00.ex00;

public class Program {
    public static void main(String[] args) {
        int number = 123456;
        int result = 0;

        result += number % 10;
        number = number / 10;
        result += number % 10;
        number = number / 10;
        result += number % 10;
        number = number / 10;
        result += number % 10;
        number = number / 10;
        result += number % 10;
        number = number / 10;
        result += number % 10;

        System.out.println(result);
    }
}
