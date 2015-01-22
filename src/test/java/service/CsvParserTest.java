package service;

import org.junit.Assert;
import org.junit.Test;
import repository.PersonEntity;

import java.util.Map;

/**
 * @author vladimir.tikhomirov
 */
public class CsvParserTest {

    private CsvParser csvParser = new CsvParser();

    /**
     * Tests {@link service.CsvParser#parseCsvFile(String)}. Happy path.
     */
    @Test
    public void testParseCsvFile() {
        //EXERCISE
        Map<Integer, PersonEntity> idToPersonEntity = csvParser.parseCsvFile("src/test/testData/HourList201403.csv");

        //VERIFY
        Assert.assertEquals(3, idToPersonEntity.size());
        Assert.assertEquals("Janet Java", idToPersonEntity.get(1).getName());
    }
}
