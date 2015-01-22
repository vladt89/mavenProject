package service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import repository.PersonEntity;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
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
        parseCsvFile(pathToFile);
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

        Map<Integer, PersonEntity> idToPersonEntity = new HashMap<>();
        if (parser != null) {
            for (CSVRecord record : parser) {
                int personId = Integer.parseInt(record.get("Person ID"));
                if (!idToPersonEntity.containsKey(personId)) {
                    PersonEntity person = new PersonEntity();
                    person.setName(record.get("Person Name"));
                    person.setId(personId);
                    idToPersonEntity.put(personId, person);
                }
            }
        }
        return idToPersonEntity;
    }
}
