package edu.school21.printer.app;

import edu.school21.printer.logic.Logic;
import edu.school21.printer.logic.Args;
import com.beust.jcommander.JCommander;

public class App {
    public static void main(String[] args) {
        try {
            Args jArgs = new Args();
            JCommander jCommander = new JCommander(jArgs);
            jCommander.parse(args);
            Logic logic = new Logic(jArgs, "/resources/image.bmp");
            logic.print();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}