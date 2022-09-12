package school21.spring.app;

import school21.spring.preprocessor.PreProcessor;
import school21.spring.preprocessor.PreProcessorToUpperImpl;
import school21.spring.printer.Printer;
import school21.spring.printer.PrinterWithPrefixImpl;
import school21.spring.renderer.Renderer;
import school21.spring.renderer.RendererErrImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Program {
    public static void main(String[] args) {
        System.out.println("Демонстрация результата создания класса Printer обычным способом:");
        PreProcessor preProcessor = new PreProcessorToUpperImpl();
        Renderer renderer = new RendererErrImpl(preProcessor);
        PrinterWithPrefixImpl printer = new PrinterWithPrefixImpl(renderer);
        printer.setPrefix ("Prefix ");
        printer.print ("Hello!");

        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        System.out.println("Демонстрация создания класса Printer с Бинами:");
        Printer prefixPrinter = context.getBean("printerWithPrefix", Printer.class);
        prefixPrinter.print("Hello!");

        prefixPrinter = context.getBean("printerWithPrefixStd" ,Printer.class);
        prefixPrinter.print("Hello!");

        Printer datePrinter = context.getBean("printerWithDate", Printer.class);
        datePrinter.print("Hello!");

        datePrinter = context.getBean("printerWithDateErr", Printer.class);
        datePrinter.print("Hello!");
        
        //contex.registerShutdownHook(); Этот метод регистрирует перехватчик выключения в среде выполнения JVM . Когда JVM завершает работу, она также закрывает контейнер Spring
    }
}
