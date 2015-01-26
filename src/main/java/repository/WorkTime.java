package repository;

import java.util.Date;

/**
 * Class which holds the information about the working time for the specific day.
 *
 * @author vladimir.tikhomirov
 */
public class WorkTime {
    private Date startTime;
    private Date endTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
