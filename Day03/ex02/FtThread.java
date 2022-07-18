package Day03.ex02;

public class FtThread extends Thread {
    private int num;
    private int first;
    private int last;
    private Printer printer;

    public FtThread(int num, int first, int last, Printer printer) {
        this.num = num;
        this.first = first;
        this.last = last;
        this.printer = printer;
    }

    @Override
    public void run() {
        printer.print(first, last, num);
    }
}
