package service.wage;

import org.apache.log4j.Logger;
import repository.PersonEntity;
import repository.WorkTime;

import java.util.Calendar;
import java.util.Date;

/**
 * Class that defines service methods to handle wage calculation process.
 *
 * @author vladimir.tikhomirov
 */
public class WageServiceImpl implements WageService {

    private static final Logger LOG = Logger.getLogger(WageServiceImpl.class);

    protected static final double HOURLY_WAGE = 3.75; //amount in dollars
    protected static final double EVENING_COMPENSATION = 1.15; //amount in dollars
    private static final int END_OF_WORK_DAY_HOUR = 18;
    protected static final int NORMAL_WORK_DAY_IN_HOURS = 8;
    protected static final double OVERTIME_PERCENT_FOR_FIRST_2_HOURS = HOURLY_WAGE * 1.25;
    protected static final double OVERTIME_PERCENT_FOR_SECOND_2_HOURS = HOURLY_WAGE * 1.5;
    protected static final double OVERTIME_PERCENT_AFTER_4_HOURS =  HOURLY_WAGE * 2;

    @Override
    public double calculateDailyPay(WorkTime workTime) {
        Date endDate = workTime.getEndTime();
        Date startDate = workTime.getStartTime();
        LOG.info("Calculate daily pay for the day: " + startDate);

        double dailyPay;
        double overtimeHours = calculateWorkingHours(endDate, startDate) - NORMAL_WORK_DAY_IN_HOURS;
        double regularDailyWage = calculateRegularDailyWage(endDate, startDate);
        if (overtimeHours > 0) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("During this day was done overtime with " + overtimeHours + " hours, overtime compensation is coming");
            }
            dailyPay = regularDailyWage + calculateOvertimeCompensation(overtimeHours);
        } else {
            dailyPay = regularDailyWage + calculateEveningCompensation(endDate);
        }
        LOG.info("Daily pay for the day is " + dailyPay);
        return dailyPay;
    }

    @Override
    public double calculateOvertimeCompensation(double overtimeHours) {
        double overtimeCompensation = 0;
        if (overtimeHours > 0) {
            if (overtimeHours <= 2) {
                overtimeCompensation = overtimeHours * OVERTIME_PERCENT_FOR_FIRST_2_HOURS;
            } else if (overtimeHours <= 4) {
                //overtime compensation for first 2 hours
                overtimeCompensation = 2 * OVERTIME_PERCENT_FOR_FIRST_2_HOURS;
                //overtime compensation for the time after 2 hours
                overtimeCompensation += (overtimeHours - 2) * OVERTIME_PERCENT_FOR_SECOND_2_HOURS;
            } else {
                //overtime compensation for first 2 hours
                overtimeCompensation = 2 * OVERTIME_PERCENT_FOR_FIRST_2_HOURS;
                //overtime compensation for second 2 hours
                overtimeCompensation += 2 * OVERTIME_PERCENT_FOR_SECOND_2_HOURS;
                //overtime compensation for the rest hours
                overtimeCompensation += (overtimeHours - 4) * OVERTIME_PERCENT_AFTER_4_HOURS;
            }
        }
        return overtimeCompensation;
    }

    @Override
    public double calculateRegularDailyWage(Date endDate, Date startDate) {
        double workingHours = calculateWorkingHours(endDate, startDate);
        return workingHours * HOURLY_WAGE;
    }

    @Override
    public double calculateEveningCompensation(Date endTime) {
        Date endOfWorkTime = (Date) endTime.clone();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endOfWorkTime);
        calendar.set(Calendar.HOUR_OF_DAY, END_OF_WORK_DAY_HOUR);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        endOfWorkTime = calendar.getTime();

        double eveningCompensation = 0;
        if (endOfWorkTime.before(endTime)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("During this day the person as working in the evening, evening compensation is coming");
            }
            double eveningHours = calculateWorkingHours(endTime, endOfWorkTime);
            eveningCompensation = eveningHours * EVENING_COMPENSATION;
        }
        return eveningCompensation;
    }

    @Override
    public double calculateTotalSalary(PersonEntity personEntity) {
        LOG.info("Calculate total salary for person " + personEntity.getName() + " [" + personEntity.getId() + "]");

        java.util.List<WorkTime> workingDays = personEntity.getWorkingDays();
        double totalSalary = 0;
        for (WorkTime workingDay : workingDays) {
            double dailyPay = calculateDailyPay(workingDay);
            totalSalary += dailyPay;
        }
        LOG.info("Salary for " + workingDays.size() + " working days was calculated successfully" +
                ", amount: " + totalSalary);
        return totalSalary;
    }

    private double calculateWorkingHours(Date endDate, Date startDate) {
        long diffInMilliSeconds = endDate.getTime() - startDate.getTime();
        return (double) diffInMilliSeconds/1000/60/60;
    }
}
