package Day03.ex01;

public class Egg implements Runnable {
    private int count;
    private Printer printer;

    public Egg(int count, Printer printer) {
        this.count = count;
        this.printer = printer;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            try {
                printer.print("Egg", "HEN");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
