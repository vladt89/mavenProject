package repository;

import java.util.LinkedList;
import java.util.List;

/**
 * Class which contains all working information about specific person.
 *
 * @author vladimir.tikhomirov
 */
public class PersonEntity {
    private String name;
    private int id;
    private List<WorkTime> workingDays = new LinkedList<>();
    private double totalSalary = 0;
    private Double[] salaryPerMonth;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setWorkingDays(List<WorkTime> workingDays) {
        this.workingDays = workingDays;
    }

    public List<WorkTime> getWorkingDays() {
        return workingDays;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(double totalSalary) {
        this.totalSalary = totalSalary;
    }

    public Double[] getSalaryPerMonth() {
        return salaryPerMonth;
    }

    public void setSalaryPerMonth(Double[] salaryPerMonth) {
        this.salaryPerMonth = salaryPerMonth;
    }
}
