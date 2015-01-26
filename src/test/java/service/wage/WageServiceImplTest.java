package service.wage;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.Date;

/**
 * Test class for {@link service.wage.WageServiceImpl}.
 *
 * @author vladimir.tikhomirov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class WageServiceImplTest {

    private static final double DELTA = 0.00001;

    @Autowired
    WageServiceImpl wageService;

    /**
     * Tests {@link WageService#calculateDailyPay(repository.WorkTime)} when the person worked several hours.
     */
    @Test
    public void testCalculateDailyPay() throws Exception {
        //SETUP SUT
        Date startTime = new Date();
        int workingHours = 5;
        Date endTime = DateUtils.addHours(startTime, workingHours);

        //EXERCISE
        double result = wageService.calculateRegularDailyWage(endTime, startTime);

        //VERIFY
        Assert.assertEquals(workingHours * WageServiceImpl.HOURLY_WAGE, result, workingHours);
    }

    /**
     * Tests {@link WageService#calculateDailyPay(repository.WorkTime)} when the person worked several hours.
     */
    @Test
    public void testCalculateDailyPayWithSomeMinutes() throws Exception {
        //SETUP SUT
        Date startTime = new Date();
        int workingMinutes = 15;
        Date endTime = DateUtils.addMinutes(startTime, workingMinutes);

        //EXERCISE
        double result = wageService.calculateRegularDailyWage(endTime, startTime);

        //VERIFY
        Assert.assertEquals(0.25 * WageServiceImpl.HOURLY_WAGE, result, DELTA);
    }

    /**
     * Tests {@link WageServiceImpl#calculateEveningCompensation(java.util.Date)} when the the passed time is later
     * than 18:00 of the current date.
     */
    @Test
    public void testCalculateEveningCompensation() {
        //SETUP SUT
        Date endOfWork = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endOfWork);
        calendar.set(Calendar.HOUR_OF_DAY, 19);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        endOfWork = calendar.getTime();

        //EXERCISE
        double result = wageService.calculateEveningCompensation(endOfWork);

        //VERIFY
        Assert.assertEquals(WageServiceImpl.EVENING_COMPENSATION, result, DELTA);
    }

    /**
     * Tests {@link WageService#calculateOvertimeCompensation(double)}
     * when the person worked more than 8 hours, but his overtime was not more than 2 hours.
     */
    @Test
    public void testCalculateOvertimeCompensationWithinFirstTwoHours() throws Exception {
        //SETUP SUT
        int workingHours = 9;
        double overtime = workingHours - WageServiceImpl.NORMAL_WORK_DAY_IN_HOURS;

        //EXERCISE
        double result = wageService.calculateOvertimeCompensation(overtime);

        //VERIFY
        Assert.assertEquals(WageServiceImpl.OVERTIME_PERCENT_FOR_FIRST_2_HOURS * overtime, result, DELTA);
    }

    /**
     * Tests {@link WageService#calculateOvertimeCompensation(double)}
     * when the person worked more than 8 hours, but his overtime was not more than 4 hours and not less than 2.
     */
    @Test
    public void testCalculateOvertimeCompensationWithinFirstFourHours() throws Exception {
        //SETUP SUT
        int workingHours = 11;
        double overtime = workingHours - WageServiceImpl.NORMAL_WORK_DAY_IN_HOURS;

        //EXERCISE
        double result = wageService.calculateOvertimeCompensation(overtime);

        //VERIFY
        double expected = WageServiceImpl.OVERTIME_PERCENT_FOR_FIRST_2_HOURS * 2
                + WageServiceImpl.OVERTIME_PERCENT_FOR_SECOND_2_HOURS;
        Assert.assertEquals(expected, result, DELTA);
    }

    /**
     * Tests {@link WageService#calculateOvertimeCompensation(double)}
     * when the person worked more than 8 hours and his overtime was more than 4 hours.
     */
    @Test
    public void testCalculateOvertimeCompensationWithMoreThanFourHours() throws Exception {
        //SETUP SUT
        int workingHours = 13;
        double overtime = workingHours - WageServiceImpl.NORMAL_WORK_DAY_IN_HOURS;

        //EXERCISE
        double result = wageService.calculateOvertimeCompensation(overtime);

        //VERIFY
        double expected = WageServiceImpl.OVERTIME_PERCENT_FOR_FIRST_2_HOURS * 2
                + WageServiceImpl.OVERTIME_PERCENT_FOR_SECOND_2_HOURS * 2
                + WageServiceImpl.OVERTIME_PERCENT_AFTER_4_HOURS;
        Assert.assertEquals(expected, result, DELTA);
    }
}