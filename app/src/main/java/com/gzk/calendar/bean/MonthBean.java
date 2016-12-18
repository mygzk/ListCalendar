package com.gzk.calendar.bean;

import java.util.List;

/**
 * Created by guozhk on 2016/12/18.
 */

public class MonthBean {
    private int year;
    private int month;
    private int dayNum;
    private List<DayBean> days;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDayNum() {
        return dayNum;
    }

    public void setDayNum(int dayNum) {
        this.dayNum = dayNum;
    }

    public List<DayBean> getDays() {
        return days;
    }

    public void setDays(List<DayBean> days) {
        this.days = days;
    }
}
