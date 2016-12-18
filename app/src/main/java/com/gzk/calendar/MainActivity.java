package com.gzk.calendar;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    SwipeRefreshLayout mSwipeRefreshLayout;
    private MyCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView = (MyCalendarView) findViewById(R.id.calendar_view);
        calendarView.setIDayItemClickListener(new CalendarItemView.IDayItemClickListener() {
            @Override
            public void dayClick(int year, int month, int day, int week) {
                Log.e("tag", year + "-" + (month + 1) + "-" + day);
                calendarView.addSelectedTip();
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
                // calendarView.invalidate();
                Log.e("test","======mSwipeRefreshLayout======");
                calendarView.addBeforOneData();
            }
        });
    }


}
