package Day03.ex01;


public class Printer {
    private Category category = Category.EGG;

    private enum Category {
        EGG,
        HEN
    }

    public synchronized void print(String str, String str2) throws InterruptedException {
        Category cameCategory = Category.valueOf(str.toUpperCase());
        if (category != cameCategory) {
            wait();
        }
        System.out.println(str);
        category = Category.valueOf(str2);
        notify();
    }
}
