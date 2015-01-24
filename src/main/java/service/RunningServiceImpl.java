package service;

import repository.PersonEntity;
import service.parser.CsvParser;
import service.parser.WorkTime;

import java.util.List;
import java.util.Map;

/**
 * @author vladimir.tikhomirov
 */
public class RunningServiceImpl implements RunningService {

    private CsvParser parser = new CsvParser();

    @Override
    public void run() {
        Map<Integer, PersonEntity> integerPersonEntityMap = parser.getIntegerPersonEntityMap();

        //check the results //TODO remove later
        for (Integer integer : integerPersonEntityMap.keySet()) {
            PersonEntity personEntity = integerPersonEntityMap.get(integer);
            System.out.println("Person: " + personEntity.getName());
            List<WorkTime> workingDays = personEntity.getWorkingDays();
            for (WorkTime workingDay : workingDays) {
                System.out.println("StartTime: " + workingDay.getStartTime() + " EndTime: " + workingDay.getEndTime());
            }
            System.out.println(workingDays.size());
        }
    }

    public void setParser(CsvParser parser) {
        this.parser = parser;
    }
}
