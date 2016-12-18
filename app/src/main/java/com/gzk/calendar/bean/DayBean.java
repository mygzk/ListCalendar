package com.gzk.calendar.bean;

/**
 * Created by guozhk on 2016/12/18.
 */

public class DayBean {
    private int day;
    private boolean isTip = false;
    private String tip;


    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isTip() {
        return isTip;
    }

    public void setTip(boolean tip) {
        isTip = tip;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
