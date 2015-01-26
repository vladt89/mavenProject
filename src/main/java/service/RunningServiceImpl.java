package service;

import application.Gui;
import service.parser.CsvParser;
import service.wage.WageService;

/**
 * @author vladimir.tikhomirov
 */
public class RunningServiceImpl implements RunningService {

    private CsvParser parser;
    private WageService wageService;

    @Override
    public void run() {
        new Gui();
    }

    public void setParser(CsvParser parser) {
        this.parser = parser;
    }

    public void setWageService(WageService wageService) {
        this.wageService = wageService;
    }
}
