package Day03.ex01;

public class Hen implements Runnable {
    private int count;
    private Printer printer;

    public Hen(int count, Printer printer) {
        this.count = count;
        this.printer = printer;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            try {
                printer.print("Hen", "EGG");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
