package com.gzk.calendar;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gzk.calendar.util.CalendarUtils;

/**
 * Created by guozhk on 2016/12/17.
 */

public class CalendarItemView extends FrameLayout {
    private TextView mTvDateLable;
    private LinearLayout mWeekLayout;
    private LinearLayout mContentLayout;
    //日期年月日字体大小
    private int mDateTvSize;
    //年月日字体颜色
    private int mDateTvColor;
    //年月日布局高度
    private int mDateLayoutH;

    //星期字体大小
    private int mWeekTvSize;
    //星期字体颜色
    private int mWeekTvColor;
    //星期布局高度
    private int mWeekLayoutH;

    //日期字体大小
    private int mDayTvSize;
    //日期字体颜色
    private int mDayTvColor;
    //日期布局高度
    private int mDayTvLayoutH;

    private int year;
    private int month;
    private int day;
    private TypedArray mTypedArray;

    public CalendarItemView(Context context, TypedArray typedArray) {
        super(context);
        Resources resources = context.getResources();
        this.mTypedArray = typedArray;
        mDateTvSize = typedArray.getDimensionPixelSize(R.styleable.customCalendar_calendarDateTvSize,
                resources.getDimensionPixelSize(R.dimen.calendarDateTvSize));
        mDateTvColor = typedArray.getColor(R.styleable.customCalendar_calendarDateTvColor,
                resources.getColor(R.color.calendarDateTvColor));
        mDateLayoutH = typedArray.getDimensionPixelSize(R.styleable.customCalendar_calendarDateLayH,
                resources.getDimensionPixelSize(R.dimen.calendarDateLayH));

        mWeekTvSize = typedArray.getDimensionPixelSize(R.styleable.customCalendar_calendarWeekTvSize,
                resources.getDimensionPixelSize(R.dimen.calendarWeekTvSize));
        mWeekTvColor = typedArray.getColor(R.styleable.customCalendar_calendarWeekTvColor,
                resources.getColor(R.color.calendarWeekTvColor));
        mWeekLayoutH = typedArray.getDimensionPixelSize(R.styleable.customCalendar_calendarDateLayH,
                resources.getDimensionPixelSize(R.dimen.calendarWeekLayH));

        mDayTvSize = typedArray.getDimensionPixelSize(R.styleable.customCalendar_calendarDayTvSize,
                resources.getDimensionPixelSize(R.dimen.calendarDateTvSize));
        mDayTvColor = typedArray.getColor(R.styleable.customCalendar_calendarDayTvColor,
                resources.getColor(R.color.calendarDayTvColor));
        mDayTvLayoutH = typedArray.getDimensionPixelSize(R.styleable.customCalendar_calendarDayLayH,
                resources.getDimensionPixelSize(R.dimen.calendarDayLayH));

        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.calendar_item, this, true);
        RecyclerView.LayoutParams ls = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(ls);
        mTvDateLable = (TextView) view.findViewById(R.id.calendar_date_lable);
        mWeekLayout = (LinearLayout) view.findViewById(R.id.calendar_week_layout);
        mContentLayout = (LinearLayout) view.findViewById(R.id.calendar_layout);

        initDate(context);
        //星期lable
        initWeek(context);
        //日期
        initDay(context);
    }

    private void initDate(Context context) {
        LinearLayout.LayoutParams dateItemLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                mDateLayoutH);
        mTvDateLable.setTextSize(TypedValue.COMPLEX_UNIT_PX, mDateTvSize);
        mTvDateLable.setTextColor(mDateTvColor);
        mTvDateLable.setLayoutParams(dateItemLp);
    }

    private void initWeek(Context context) {
        LinearLayout.LayoutParams weekItemLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        weekItemLp.height = mWeekLayoutH;
        mWeekLayout.setLayoutParams(weekItemLp);

        for (int i = 0; i < 7; i++) {
            TextView tvWeek = new TextView(context);
            tvWeek.setGravity(Gravity.CENTER);
            tvWeek.setText(CalendarUtils.getWeek(i));
            tvWeek.setTextSize(TypedValue.COMPLEX_UNIT_PX, mWeekTvSize);
            tvWeek.setTextColor(mWeekTvColor);
            LinearLayout.LayoutParams weekLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            weekLp.weight = 1;
            weekLp.gravity = Gravity.CENTER;
            tvWeek.setLayoutParams(weekLp);
            mWeekLayout.addView(tvWeek);
        }
    }

    private void initDay(Context context) {
        int startPos = CalendarUtils.getWeekByDate("2016-2-1");
        int numDay = CalendarUtils.getDaysInMonth(1, 2016);
        double d = ((numDay + startPos) * 1d) / 7;
        int row = (int) Math.ceil(d);
        //日期具体数字
        for (int i = 0; i < row; i++) {
            LinearLayout.LayoutParams dayItemLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mDayTvLayoutH);
            LinearLayout dayHRItemLayout = new LinearLayout(context);
            dayHRItemLayout.setLayoutParams(dayItemLp);
            dayHRItemLayout.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < 7; j++) {
                LinearLayout.LayoutParams dayLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView tvDay = new TextView(context);
                tvDay.setGravity(Gravity.CENTER);
                tvDay.setTextColor(mDayTvColor);
                dayLp.weight = 1;
                dayLp.gravity = Gravity.CENTER;
                tvDay.setLayoutParams(dayLp);
                if ((i == 0 && startPos != 0 && j < startPos) || ((i * 7 + j - startPos + 1) > numDay)) {
                    dayHRItemLayout.addView(tvDay);
                } else {
                    tvDay.setText((i * 7 + j - startPos + 1) + "");
                    tvDay.setTextSize(TypedValue.COMPLEX_UNIT_PX, mDayTvSize);
                    dayHRItemLayout.addView(tvDay);
                }
            }
            mContentLayout.addView(dayHRItemLayout);
        }
    }


}
