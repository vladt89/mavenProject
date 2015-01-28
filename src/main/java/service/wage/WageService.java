package service.wage;

import repository.PersonEntity;
import repository.WorkTime;

import java.util.Date;

/**
 * Interface which contains the declarations of the methods for salary calculation.
 *
 * @author vladimir.tikhomirov
 */
public interface WageService {

    /**
     * Calculates the salary for the specific date.
     *
     * @param workTime working time information
     * @return salary amount
     */
    double calculateDailyPay(WorkTime workTime);

    /**
     * Calculates regular salary for the specific working day according to the agreement of normal working hours.
     *
     * @param endDate time when person finished the work
     * @param startDate time when person started the work
     * @return salary amount
     */
    double calculateRegularDailyWage(Date endDate, Date startDate);

    /**
     * Calculates the additional wage for the time after 18:00 of the day and before 6:00 of the next day.
     *
     * @param endTime time when person finished the work
     * @return salary amount
     */
    double calculateEveningCompensation(Date endTime);

    /**
     * Calculates compensation for the overtime (the person was working more than 8 hours during specific day).
     *
     * @param overtimeHours amount of overtime hours
     * @return salary amount
     */
    double calculateOvertimeCompensation(double overtimeHours);

    /**
     * Calculates the salary amounts for the specific person.
     *
     * @param personEntity person for whom salaries are going to be calculated
     * @return arrays of salaries per month
     */
    Double[] calculateSalariesPerMonth(PersonEntity personEntity);
}
