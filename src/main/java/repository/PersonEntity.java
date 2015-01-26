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
    private double salary = 0;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
