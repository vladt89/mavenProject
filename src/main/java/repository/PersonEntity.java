package repository;

import java.util.Date;
import java.util.List;

/**
 * Class which contains all working information about specific person.
 *
 * @author vladimir.tikhomirov
 */
public class PersonEntity {
    private String name;
    private int id;
    private List<Date> workingDays;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<Date> getWorkingDays() {
        return workingDays;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWorkingDays(List<Date> workingDays) {
        this.workingDays = workingDays;
    }
}
