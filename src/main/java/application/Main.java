package application;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Main entry point for the application.
 *
 * @author vladimir.tikhomirov
 */
public class Main {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring-config.xml");
//        new RunningServiceImpl().run();
    }
}
