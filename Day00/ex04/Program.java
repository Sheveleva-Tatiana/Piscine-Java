package Day00.ex04;

import java.util.Scanner;

public class Program {
    public static void main(String[] argv) {
        Scanner scanner = new Scanner(System.in); // запускаем сканер
        String inputData = scanner.nextLine(); // считываем всю строку
        int[] charCount = new int[65536]; // создаем массив интов чтобы записать туда коды символов
        char[] dataArray = inputData.toCharArray(); // переводим строку в массив символов
        for (int i = 0; i < inputData.length(); i++) {
            charCount[dataArray[i]]++; // в массиве интов увеличиваем на 1 по аски коду- наприме, символ 'A' - 65 код будет увеличивать значние в 65 ячейке интовского массива на 1
        }
        char [] resultList = new char[10];
        int [] countCharList = new int[10];
        char maxChar = ' ';
        int maxCount = 0;
        int index = 0;
        for (int j = 0; j < 10; j++) { // ищем максимальное повторение заполняя 10 позиций
            for (int i = 0; i < 65536; i++) { // проходимся по всему интовскому массиву
                if (charCount[i] > maxCount) { // сравниваем сколько повторений было
                    maxCount = charCount[i]; // записываем сколько максимум повторений
                    maxChar = (char) i; // достаем чар
                    index = i; // сохраняем где достали чар
                }
            }
            countCharList[j] = charCount[index]; // сохранияем максимальное повторение
            resultList[j] = maxChar; // сохраняем символ
            charCount[index] = 0; // обнуляем значение в интовском массиве, чтобы повторно не встречать его
            maxChar = ' '; // обнуляем чар
            maxCount = 0; // обнуляем каунт для сравнения
        }
        if (charCount[0] > 999) { // проверка на 999 повторений
            System.err.println("Illegal Argument");
            System.exit(-1);
        }
        paintTable(resultList, countCharList); // печатаем
        scanner.close(); // закрываем сканер
    }

    public static void paintTable(char[] chars, int[] counts) {
        int d = counts[0]; // сохранияем максимальное количество повторения в переменную Д
        System.out.println(); // отступ
        System.out.println(); // отступ
        for(int i = 0; i < 10; i++) { // печатаем количество повторений (самые максимальные)
            if(counts[i] == d ) // если на одной позиции то печатаем
                System.out.print(counts[i] + "\t");
        }
        System.out.println(); // пустая строка
        for (int i = 10; i > 0; i--) { //проходимся по массиву
            for (int j = 0; j < 10; j++) {
                if (counts[j] * 10 / d >= i) // если нужно разобраться
                    System.out.print("#\t");
                if (counts[j] * 10 / d == i - 1) {
                    if (counts[j] != 0) {
                        System.out.print(counts[j] + "\t");
                    }
                }
            }
            System.out.println();
        }
        for (int i = 0; i < 10; i++){
            System.out.print(chars[i] + "\t");
        }
    }
}
