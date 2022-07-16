package Day00.ex02;

import java.util.Scanner;

public class Program {

    public static int getSumOfDigit(int number) {
        int result = 0;

        result += number % 10;
        while (number > 10){
            number = number / 10;
            result += number % 10;
        }
        return result;
    }

    public static boolean checkNegative(int number) {
        if (number < 0) {
            System.err.println("Illegal Argument");
            return true;
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

    public static boolean isPrime(int number) {
        int temp;

        if (number <= 1) {
            return false;
        } else if (number == 2) {
            return true;
        } else {
            for (int i = 2; i <= Math.sqrt(number); i++) {
                temp = number % i;
                if (temp == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        int amountCoffee = 0;
        int sumOfDigit = 0;

        while (number != 42) {
            sumOfDigit = getSumOfDigit(number);
            if (isPrime(sumOfDigit)) {
                amountCoffee++;
            }
            number = scanner.nextInt();
        }
        System.out.println("Count of coffee-request - " + amountCoffee);
        scanner.close();
    }
}
