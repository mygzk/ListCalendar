package com.gzk.calendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.gzk.calendar.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyCalendarView calendarView = (MyCalendarView) findViewById(R.id.calendar_view);
        calendarView.setIDayItemClickListener(new CalendarItemView.IDayItemClickListener() {
            @Override
            public void dayClick(int year, int month, int day, int week) {
                Log.e("tag", year + "-" + (month + 1) +"-"+ day);
            }
        });
    }


}
