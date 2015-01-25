package service;

import repository.PersonEntity;
import service.parser.CsvParser;
import service.parser.WorkTime;
import service.wage.WageService;
import service.wage.WageServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * @author vladimir.tikhomirov
 */
public class RunningServiceImpl implements RunningService {

    private CsvParser parser = new CsvParser();
    WageService wageService = new WageServiceImpl();

    @Override
    public void run() {
        Map<Integer, PersonEntity> idToPersonEntityMap = parser.getIdToPersonEntityMap();

        //check the results //TODO remove later
        for (Integer personId : idToPersonEntityMap.keySet()) {
            PersonEntity personEntity = idToPersonEntityMap.get(personId);
            System.out.println("Person: " + personEntity.getName());
            List<WorkTime> workingDays = personEntity.getWorkingDays();

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
}
