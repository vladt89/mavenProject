package service.parser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import repository.PersonEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Test class for {@link CsvParser}.
 *
 * @author vladimir.tikhomirov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class CsvParserTest {

    @Autowired
    private CsvParser csvParser;

    /**
     * Tests {@link CsvParser#parseCsvFile(String)}. Happy path.
     */
    @Test
    public void testParseCsvFile() throws Exception {
        //EXERCISE
        Map<Integer, PersonEntity> idToPersonEntity = csvParser.parseCsvFile("src/test/testData/HourList201403.csv");

        //PREPARE DATA FOR VERIFICATION
        PersonEntity firstPerson = idToPersonEntity.get(1);
        List<WorkTime> firstPersonWorkingDays = firstPerson.getWorkingDays();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date startDate = sdf.parse("03/03/2014 9:30");
        Date endDate = sdf.parse("03/03/2014 17:00");
        WorkTime firstWorkDayOfFirstPerson = firstPersonWorkingDays.get(0);

        //VERIFY
        Assert.assertEquals(3, idToPersonEntity.size());
        Assert.assertEquals("Janet Java", firstPerson.getName());
        Assert.assertEquals(23, firstPersonWorkingDays.size());
        Assert.assertTrue(firstWorkDayOfFirstPerson.getStartTime().equals(startDate));
        Assert.assertTrue(firstWorkDayOfFirstPerson.getEndTime().equals(endDate));
    }
}
