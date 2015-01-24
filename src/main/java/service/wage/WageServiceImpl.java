package service.wage;

import service.parser.WorkTime;

/**
 * @author vladimir.tikhomirov
 */
public class WageServiceImpl implements WageService {

    private static final double HOURLY_WAGE = 3.75; //amount in dollars

    @Override
    public double calculateDailyPay(int personId, WorkTime workTime) {

        long diffInSec = workTime.getEndTime().getTime() - workTime.getStartTime().getTime();
        long workingHours = diffInSec/60/60;
        double regularDailyWage = workingHours * HOURLY_WAGE;

//        long dailyPayment = regularDailyWage + eveningCompensation + overtimeCompensation;

//        return dailyPayment;
        return regularDailyWage;
    }
}
