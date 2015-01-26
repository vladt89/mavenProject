package application;

import repository.PersonEntity;
import repository.WorkTime;
import service.parser.CsvParser;
import service.wage.WageService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;

/**
 * User interface for the application.
 *
 * @author vladimir.tikhomirov
 */
public class Gui {

    private CsvParser parser;
    private WageService wageService;

    public Gui() {
        JFrame frame = new JFrame("Wage system");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(300, 300));
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.add(createLoadButton());
        frame.add(panel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private JButton createLoadButton() {
        final JButton button = new JButton("Load csv file");
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionHappens(button);
            }
        });
        return button;
    }

    private void actionHappens(JButton button) {
        final JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(button);

        Map<Integer, PersonEntity> idToPersonEntityMap = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            idToPersonEntityMap = parser.parseCsvFile(fileChooser.getSelectedFile().getPath());
        }
        //check the results //TODO remove later
        for (Integer personId : idToPersonEntityMap.keySet()) {
            PersonEntity personEntity = idToPersonEntityMap.get(personId);
            System.out.println("Person: " + personEntity.getName());
            java.util.List<WorkTime> workingDays = personEntity.getWorkingDays();

            double totalSalary = 0;
            for (WorkTime workingDay : workingDays) {
                double dailyPay = wageService.calculateDailyPay(workingDay);
                totalSalary += dailyPay;
                System.out.println("StartTime: " + workingDay.getStartTime()
                        + " EndTime: " + workingDay.getEndTime()
                        + " DailyPay: " + dailyPay);
            }
            System.out.println("Salary: " + totalSalary);
            System.out.println(workingDays.size());
        }
    }

    public void setParser(CsvParser parser) {
        this.parser = parser;
    }

    public void setWageService(WageService wageService) {
        this.wageService = wageService;
    }
}




