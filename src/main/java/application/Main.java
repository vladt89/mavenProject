package application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repository.PersonEntity;
import service.RunningServiceImpl;
import service.parser.CsvParser;

import java.util.Map;

/**
 * Main entry point for the application.
 *
 * @author vladimir.tikhomirov
 */
public class Main {
    public static void main(String[] args) {
//        Gui gui = new Gui();

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        CsvParser pa = (CsvParser) context.getBean("csvParser");
        Map<Integer, PersonEntity> integerPersonEntityMap = pa.getIntegerPersonEntityMap();

        RunningServiceImpl runningService = new RunningServiceImpl();
        runningService.run();


    }
}
