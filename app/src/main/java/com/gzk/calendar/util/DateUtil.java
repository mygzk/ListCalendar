package com.gzk.calendar.util;

import android.util.Log;

import com.gzk.calendar.bean.DayBean;
import com.gzk.calendar.bean.MonthBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guozhk on 2016/12/18.
 */

public class DateUtil {
    public static List<MonthBean> getBefore(int currentYear, int currenMonth, int monthNums) {
        List<MonthBean> monthBeanList = new ArrayList<>();
        //   for (int i = 0; i < monthNums; i++) {
        for (int i = monthNums; i > 0; i--) {
            MonthBean bean = new MonthBean();
            int nextMonth = 0;
            int nextYear = 0;
            if (currenMonth == 0) {
                nextMonth = 11;
                nextYear = currentYear - 1;
            } else {
                int addMonth = currenMonth - i;
                nextMonth = addMonth % 12;
                int yearD = addMonth / 12;
                nextYear = currentYear - yearD;
            }
            bean.setMonth(nextMonth);
            bean.setYear(nextYear);
            bean.setDayNum(CalendarUtils.getDaysInMonth(nextMonth, nextYear));
            bean.setDays(getDays(CalendarUtils.getDaysInMonth(nextMonth, nextYear)));
            monthBeanList.add(bean);
        }
        return monthBeanList;
    }

    public static List<MonthBean> getAfter(int currentYear, int currenMonth, int monthNums) {
      /*  Log.e("Test", "currenMonth:" + currenMonth);
        Log.e("Test", "monthNums:" + monthNums);*/
        List<MonthBean> monthBeanList = new ArrayList<>();
        for (int i = 0; i < monthNums; i++) {
            MonthBean bean = new MonthBean();
            int addMonth = currenMonth + i;
            int nextMonth = addMonth % 12;
            bean.setMonth(nextMonth);
            int yearD = addMonth / 12;
            int nextYear = yearD + currentYear;
            bean.setYear(nextYear);
            Log.e("Test", "yearD:" + yearD);
            Log.e("Test", "11currenMonth:" + currenMonth);
            Log.e("Test", "11currentYear:" + currentYear);
            Log.e("Test", "nextMonth:" + nextMonth);
            Log.e("Test", "nextYear:" + nextYear);
            bean.setDayNum(CalendarUtils.getDaysInMonth(nextMonth, nextYear));
            bean.setDays(getDays(CalendarUtils.getDaysInMonth(nextMonth, nextYear)));
            monthBeanList.add(bean);
        }

        return monthBeanList;
    }

    public static List<DayBean> getDays(int dayNums) {
        List<DayBean> dayBeanList = new ArrayList<>();
        for (int i = 1; i <= dayNums; i++) {
            DayBean bean = new DayBean();
            bean.setDay(i);
            dayBeanList.add(bean);
        }
        return dayBeanList;

    }


    public static void main(String[] args) {
        System.out.println((0 % 12));
        System.out.println((1 % 12));
        System.out.println((2 % 12));
        System.out.println((12 % 12));
        System.out.println((13 % 12));
        System.out.println("===");
        System.out.println((0 / 12));
        System.out.println((1 / 12));
        System.out.println((12 / 12));
        System.out.println((13 / 12));
    }
}
