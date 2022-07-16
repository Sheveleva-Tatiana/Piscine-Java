package Day00.ex03;

import java.util.Scanner;

public class Program {


    public static long getGrades(long grades, int week, Scanner scanner) {
        int min;
        long result;
        long pow;

        pow = 1;

        for (int i = 1; i < week; i++) {
            pow = pow * 10;
        }
        min = getMinGrade(scanner);
        result = grades + (min * pow);
        return result;
    }

    public static int getMinGrade(Scanner scanner) {
        int min;
        int count;
        int inputNumber;

        if (scanner.hasNextInt()) {
            min = scanner.nextInt();
            if (min < 1 || min > 9) {
                System.err.println("IllegalArgument");
                System.exit(-1);
            }
            count = 0;
            while (count < 4) {
                if (scanner.hasNextInt()) {
                    inputNumber = scanner.nextInt();
                    if (inputNumber < 1 || inputNumber > 9) {
                        System.err.println("IllegalArgument");
                        System.exit(-1);
                    }
                    if (inputNumber < min) {
                        min = inputNumber;
                    }
                } else {
                    System.err.println("IllegalArgument");
                    System.exit(-1);
                }
                count++;
            }
            if (min > 9 || min < 1) {
                System.err.println("IllegalArgument");
                System.exit(-1);
            }
            return (min);
        } else {
            System.err.println("IllegalArgument");
            System.exit(-1);
        }
        return 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputData = scanner.next();
        int week = 1;
        long grade;
        long grades = 0;

        while (!inputData.equals("42") && week <= 18){
            int inputWeek = scanner.nextInt();
            if (!inputData.equals("Week") || inputWeek != week){
                System.err.println("IllegalArgument");
                System.exit(-1);
            }
            grades = getGrades(grades, week, scanner);
            scanner.nextLine();
            inputData = scanner.next();
            week++;
        }
        for (int i = 1; i < week; i++){
            System.out.print("Week " + i + ' ');
            grade = grades % 10;
            grades = grades / 10;
            for (int j = 0; j < grade; j++){
                System.out.print("=");
            }
            System.out.println(">");
        }
        scanner.close();
    }
}
