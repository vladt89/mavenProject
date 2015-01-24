package service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import repository.PersonEntity;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class which is responsible for parsing csv files.
 *
 * @author vladimir.tikhomirov
 */
public class CsvParser {

    public CsvParser() {
    }

    public CsvParser(String pathToFile) {
        Map<Integer, PersonEntity> integerPersonEntityMap = parseCsvFile(pathToFile);

        //check the results //TODO remove later
        for (Integer integer : integerPersonEntityMap.keySet()) {
            PersonEntity personEntity = integerPersonEntityMap.get(integer);
            System.out.println("Person: " + personEntity.getName());
            List<WorkTime> workingDays = personEntity.getWorkingDays();
            for (WorkTime workingDay : workingDays) {
                System.out.println("Date: " + workingDay.getDate() + " Start: " + workingDay.getStartTime() + ". End: " + workingDay.getEndTime());
            }
            System.out.println(workingDays.size());
        }
    }

    /**
     * Parses csv file.
     *
     * @param pathToFile file to parse
     * @return map of person ids to person entity
     */
    protected Map<Integer, PersonEntity> parseCsvFile(String pathToFile) {
        CSVFormat format = CSVFormat.RFC4180.withHeader().withDelimiter(',');
        CSVParser parser = null;
        try {
            parser = new CSVParser(new FileReader(pathToFile), format);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<Integer, PersonEntity> idToPersonEntityMap = new HashMap<>();
        if (parser != null) {
            for (CSVRecord record : parser) {
                int personId = Integer.parseInt(record.get("Person ID"));
                if (!idToPersonEntityMap.containsKey(personId)) {
                    PersonEntity person = new PersonEntity();
                    person.setName(record.get("Person Name"));
                    person.setId(personId);
                    person.getWorkingDays().add(parseDate(record));
                    idToPersonEntityMap.put(personId, person);
                } else {
                    PersonEntity person = idToPersonEntityMap.get(personId);
                    person.getWorkingDays().add(parseDate(record));
                }
            }
        }
        return idToPersonEntityMap;
    }

    private WorkTime parseDate(CSVRecord record) {
        WorkTime workTime = new WorkTime();
        try {
            workTime.setDate(new SimpleDateFormat("dd.MM.yyyy").parse(record.get("Date")));
            workTime.setStartTime(record.get("Start"));
            workTime.setEndTime(record.get("End"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return workTime;
    }
}
