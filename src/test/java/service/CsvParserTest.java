package service;

import org.junit.Assert;
import org.junit.Test;
import repository.PersonEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
    public void testParseCsvFile() throws Exception {
        //EXERCISE
        Map<Integer, PersonEntity> idToPersonEntity = csvParser.parseCsvFile("src/test/testData/HourList201403.csv");

        PersonEntity firstPerson = idToPersonEntity.get(1);
        List<WorkTime> firstPersonWorkingDays = firstPerson.getWorkingDays();

        //VERIFY
        Assert.assertEquals(3, idToPersonEntity.size());
        Assert.assertEquals("Janet Java", firstPerson.getName());

        Assert.assertEquals("9:30", firstPersonWorkingDays.get(0).getStartTime());
        Assert.assertEquals("17:00", firstPersonWorkingDays.get(0).getEndTime());
        Assert.assertEquals(23, firstPersonWorkingDays.size());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = sdf.parse("03/03/2014");
        Assert.assertEquals(0, firstPerson.getWorkingDays().get(0).getDate().compareTo(date));
    }
}
