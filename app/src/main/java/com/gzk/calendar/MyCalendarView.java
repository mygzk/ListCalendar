package com.gzk.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.gzk.calendar.bean.MonthBean;
import com.gzk.calendar.util.CalendarUtils;
import com.gzk.calendar.util.DateUtil;
import com.gzk.calendar.view.LoadMoreRecyclerView;

import java.util.List;

/**
 * Created by guozhk on 2016/12/17.
 */

public class MyCalendarView extends RecyclerView {
    private TypedArray mTypedArray;
    private CalendarAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private int mCount = 0;
    //最大年份
    private int mMaxYear = 0;
    //最小年份
    private int mMinYear = 0;


    public MyCalendarView(Context context) {
        this(context, null);
    }

    public MyCalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCalendarView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.customCalendar);
        initView(context);
    }

    private void initView(Context context) {
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        setLayoutManager(mLayoutManager);
        setVerticalScrollBarEnabled(false);
        setFadingEdgeLength(0);
        mAdapter = new CalendarAdapter(context, mTypedArray);
        setAdapter(mAdapter);
        updateItemCount();
    }

    public void addSelectedTip() {
        mAdapter.addSelectedTip();
    }
    public void addBeforOneData() {
        List<MonthBean> datas= mAdapter.getmDatas();
        if(datas!=null&&datas.size()>0){
            mAdapter.addBefor(DateUtil.getBefore(datas.get(0).getYear(), datas.get(0).getMonth(), 1));
        }
      //  mAdapter.addBefor(DateUtil.getBefore(2016, 11, 12));
    }


    public void updateItemCount() {
        mAdapter.updateCount(50);
    }

    public void setIDayItemClickListener(CalendarItemView.IDayItemClickListener mIDayItemClickListener) {
        mAdapter.setIDayItemClickListener(mIDayItemClickListener);
    }
}
