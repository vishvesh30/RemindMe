package com.example.admin.remindme;

/**
 * Created by admin on 28-01-2017.
 */

public class totalDays_Id_Model {
    int Id;
    long total_days;

    public totalDays_Id_Model(int id,long days)
    {
        this.setId(id);
        this.setTotal_days(days);
    }

    public long getTotal_days() {
        return total_days;
    }

    public void setTotal_days(long total_days) {
        this.total_days = total_days;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

}
