package service.wage;

import service.parser.WorkTime;

import java.util.Calendar;
import java.util.Date;

/**
 * Class that defines service methods to handle wage calculation process.
 *
 * @author vladimir.tikhomirov
 */
public class WageServiceImpl implements WageService {

    protected static final double HOURLY_WAGE = 3.75; //amount in dollars
    protected static final double EVENING_COMPENSATION = 1.15; //amount in dollars
    private static final int END_OF_WORK_DAY_HOUR = 18;

    @Override
    public double calculateDailyPay(WorkTime workTime) {

        //calculate normal working hours
        Date endDate = workTime.getEndTime();
        Date startDate = workTime.getStartTime();

        double regularDailyWage = calculateRegularDailyWage(endDate, startDate);

        //calculate overtime
        double overtimeCompensation = 0;

        return regularDailyWage + calculateEveningCompensation(endDate) + overtimeCompensation;
    }

    @Override
    public double calculateRegularDailyWage(Date endDate, Date startDate) {
        long diffInMilliSeconds = endDate.getTime() - startDate.getTime();
        double workingHours = (double) diffInMilliSeconds/1000/60/60;
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
            long diffInMilliSeconds = endTime.getTime() - endOfWorkTime.getTime();
            double eveningHours = (double) diffInMilliSeconds/1000/60/60;
            eveningCompensation = eveningHours * EVENING_COMPENSATION;
        }
        return eveningCompensation;
    }
}
