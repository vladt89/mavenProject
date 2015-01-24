package service.wage;

import service.parser.WorkTime;

/**
 * @author vladimir.tikhomirov
 */
public interface WageService {

    /**
     * Calculates the salary for the specific person for the specific date.
     *
     * @param personId id of the person which salary will be calculated
     * @param workTime working time information
     * @return salary amount
     */
    double calculateDailyPay(int personId, WorkTime workTime);
}
