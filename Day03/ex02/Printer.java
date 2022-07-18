package Day03.ex02;

public class Printer {
    private int[] array;
    private int sumAll;

    public Printer(int[] array) {
        this.array = array;
    }

    public synchronized void print(int first, int last, int num) {
        int sum = 0;
        for (int i = first; i <= last; i++) {
            sum += array[i];
        }
        System.out.println("Thread " + num + ": from " + first + " to " + last
                        + " sum is " + sum);
        sumAll += sum;
    }

    public int getSumAll() {
        return sumAll;
    }
}
