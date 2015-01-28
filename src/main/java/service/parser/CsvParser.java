package service.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import repository.PersonEntity;
import repository.WorkTime;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Class which is responsible for parsing csv files.
 *
 * @author vladimir.tikhomirov
 */
public class CsvParser {

    private static final Logger LOG = Logger.getLogger(CsvParser.class);

    public CsvParser() {

    }

    /**
     * Parses csv file.
     *
     * @param pathToFile file to parse
     * @return map of person ids to person entity
     */
    public Map<Integer, PersonEntity> parseCsvFile(String pathToFile) throws CsvParsingException {
        LOG.info("Start parsing csv file");
        CSVFormat format = CSVFormat.RFC4180.withHeader().withDelimiter(',');
        CSVParser parser;
        try {
            parser = new CSVParser(new FileReader(pathToFile), format);
        } catch (IOException e) {
            String message = "Cannot parse the file: " + pathToFile + " " + e.toString();
            LOG.error(message);
            throw new CsvParsingException(message);
        }

        Map<Integer, PersonEntity> idToPersonEntityMap = new HashMap<>();
        for (CSVRecord record : parser) {
            int personId = Integer.parseInt(record.get("Person ID"));
            if (!idToPersonEntityMap.containsKey(personId)) {
                PersonEntity person = new PersonEntity();
                person.setName(record.get("Person Name"));
                person.setId(personId);
                person.getWorkingDays().add(parseDate(record));
                idToPersonEntityMap.put(personId, person);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("New person found: " + person.getName() + " [" + personId + "]");
                }
            } else {
                PersonEntity person = idToPersonEntityMap.get(personId);
                person.getWorkingDays().add(parseDate(record));
            }
        }
        LOG.info("Parsing csv file finished successfully");
        return idToPersonEntityMap;
    }

    private WorkTime parseDate(CSVRecord record) throws CsvParsingException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyyHH:mm");
        Date startTime;
        Date endTime;
        try {
            String date = record.get("Date");
            startTime = dateFormat.parse(date + record.get("Start"));
            endTime = dateFormat.parse(date + record.get("End"));
        } catch (ParseException e) {
            String message = "Cannot parse the record: " + record + ", " + e;
            LOG.error(message);
            throw new CsvParsingException(message);
        }

        WorkTime workTime = new WorkTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);
        workTime.setMonth(cal.get(Calendar.MONTH));
        workTime.setStartTime(startTime);
        if (endTime != null) {
            if (endTime.before(startTime)) {
                endTime = DateUtils.addDays(endTime, 1);
            }
        }
        workTime.setEndTime(endTime);
        return workTime;
    }
}
