package service;

import java.util.Date;

/**
 * Class which holds the information about the working time for the specific date.
 *
 * @author vladimir.tikhomirov
 */
public class WorkTime {
    private Date date;
    private String startTime;
    private String endTime;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
