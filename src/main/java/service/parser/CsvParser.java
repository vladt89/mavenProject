package service.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.time.DateUtils;
import repository.PersonEntity;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Class which is responsible for parsing csv files.
 *
 * @author vladimir.tikhomirov
 */
public class CsvParser {

    private Map<Integer, PersonEntity> idToPersonEntityMap;
    private String pathToFile = "inputData/HourList201403.csv";

    public CsvParser() {
        idToPersonEntityMap = parseCsvFile(pathToFile);
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyyHH:mm");
        Date startTime = null;
        Date endTime = null;
        try {
            startTime = dateFormat.parse(record.get("Date") + record.get("Start"));
            endTime = dateFormat.parse(record.get("Date") + record.get("End"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        WorkTime workTime = new WorkTime();
        workTime.setStartTime(startTime);
        if (endTime != null) {
            if (endTime.before(startTime)) {
                endTime = DateUtils.addDays(endTime, 1);
            }
        }
        workTime.setEndTime(endTime);
        return workTime;
    }

    public Map<Integer, PersonEntity> getIdToPersonEntityMap() {
        return idToPersonEntityMap;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }
}
