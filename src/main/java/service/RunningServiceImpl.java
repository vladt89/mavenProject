package service;

import application.Gui;
import service.parser.CsvParser;
import service.wage.WageServiceImpl;

/**
 * Service to start the application and make service methods get ready to work with UI.
 *
 * @author vladimir.tikhomirov
 */
public class RunningServiceImpl implements RunningService {

    private CsvParser parser;
    private WageServiceImpl wageService;

    public RunningServiceImpl() {
    }

    @Override
    public void run() {
        new Gui(this);
    }

    public void setParser(CsvParser parser) {
        this.parser = parser;
    }

    public void setWageService(WageServiceImpl wageService) {
        this.wageService = wageService;
    }

    public CsvParser getParser() {
        return parser;
    }

    public WageServiceImpl getWageService() {
        return wageService;
    }
}
