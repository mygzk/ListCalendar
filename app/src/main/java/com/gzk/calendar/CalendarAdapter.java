package com.gzk.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.gzk.calendar.bean.MonthBean;
import com.gzk.calendar.util.CalendarContanst;
import com.gzk.calendar.util.CalendarUtils;
import com.gzk.calendar.util.DateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guozhk on 2016/12/17.
 */

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarHolder> implements CalendarItemView.IDayItemClickListener {
    private int mCount;
    private Context mContext;
    private TypedArray mTypedArray;
    private List<MonthBean> mDatas = new ArrayList<>();
    private int mCurrentYear;
    private int mCurrentMonth;
    private int mCurrentDay;

    private CalendarItemView.IDayItemClickListener mIDayItemClickListener;


    public CalendarAdapter(Context mContext, TypedArray typedArray) {
        this.mContext = mContext;
        this.mTypedArray = typedArray;
        // updateCount(5);
    }

    public void updateCount(int count) {
        this.mCount = count;
        mDatas.clear();
        mCurrentYear = CalendarUtils.getCurrentYear();
        mCurrentMonth = CalendarUtils.getCurrentMonth();
        mCurrentDay = CalendarUtils.getCurrentDay();
        mDatas.addAll(DateUtil.getAfter(mCurrentYear, mCurrentMonth - 1, mCount));
        notifyDataSetChanged();

    }

    public void addSelectedTip() {
        if (mDatas.isEmpty()) {
            return;
        }
        for (int i = 0; i < mDatas.size(); i++) {
            mDatas.get(0).addSelectedDay(10, "值班");
            mDatas.get(2).addSelectedDay(12, "值班");
        }
        notifyDataSetChanged();
    }

    public List<MonthBean> getmDatas() {
        return mDatas;
    }

    public void addBefor(List<MonthBean> befor){
        mDatas.addAll(0,befor);
        notifyDataSetChanged();
    }

    @Override
    public CalendarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CalendarItemView itemView = new CalendarItemView(parent.getContext(), mTypedArray);
        itemView.setItemClick(this);
        return new CalendarHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CalendarHolder holder, int position) {
        Map<String, Object> params = new HashMap<>();
        MonthBean bean = mDatas.get(position);
        params.put(CalendarContanst.PARAMS_CURRENT_YEAR, mCurrentYear);
        params.put(CalendarContanst.PARAMS_CURRENT_MONTH, mCurrentMonth - 1);
        params.put(CalendarContanst.PARAMS_CURRENT_DAY, mCurrentDay);
        params.put(CalendarContanst.PARAMS_MONTH_DATA, bean);
        holder.calendarItemView.reuse();
        holder.calendarItemView.setParams(params);
        holder.calendarItemView.invalidate();

    }

    @Override
    public int getItemCount() {
        if (mCount != 0) {
            return mCount;
        }
        return 5;
    }

    class CalendarHolder extends RecyclerView.ViewHolder {
        final CalendarItemView calendarItemView;

        public CalendarHolder(View view) {
            super(view);
            calendarItemView = (CalendarItemView) view;
        }
    }

    @Override
    public void dayClick(int year, int month, int day, int week) {
        if (mDatas != null) {
            for (int i = 0; i < mDatas.size(); i++) {
                MonthBean monthBean = mDatas.get(i);
                if (monthBean.getYear() == year && monthBean.getMonth() == month) {
                    mDatas.get(i).updateSelectedDay(day - 1);
                } else {
                    mDatas.get(i).clearSelectedDay();
                }
            }
            notifyDataSetChanged();
        }
        if (mIDayItemClickListener != null) {
            mIDayItemClickListener.dayClick(year, month, day, week);
        }

    }

    public void setIDayItemClickListener(CalendarItemView.IDayItemClickListener mIDayItemClickListener) {
        this.mIDayItemClickListener = mIDayItemClickListener;
    }
}
