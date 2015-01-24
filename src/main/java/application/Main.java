package application;

import service.CsvParser;

/**
 * Main entry point for the application.
 *
 * @author vladimir.tikhomirov
 */
public class Main {
    public static void main(String[] args) {
        Gui gui = new Gui();
        new CsvParser("inputData/HourList201403.csv");
    }
}
