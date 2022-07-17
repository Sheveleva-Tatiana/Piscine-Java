package Day00.ex01;

import java.util.Scanner;

public class Program {

    public static boolean checkNegative(int number) {
        if (number <= 1) {
            System.err.println("Illegal Argument");
            System.exit(-1);
        }
        return false;
    }

    public static void checkPrime(int number) {
        int step = 1;
        boolean isPrime = true;
        if (number == 2) {
            System.out.println("true " + 1);
        } else {
            for (int i = 2; i <= Math.sqrt(number); i++) {
                if (number % i == 0) {
                    isPrime = false;
                    break;
                }
                step++;
                isPrime = true;
            }
            System.out.println(isPrime + " " + step);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number;
        if (scanner.hasNextInt()) {
            number = scanner.nextInt();

            if (!checkNegative(number)) {
                checkPrime(number);
            }
        }
        scanner.close();
    }
}
