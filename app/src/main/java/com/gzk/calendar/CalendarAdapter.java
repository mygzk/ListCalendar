package com.gzk.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guozhk on 2016/12/17.
 */

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarHolder> {
    private int mCount;
    private Context mContext;
    private TypedArray mTypedArray;
    private List<Integer> years = new ArrayList<>();


    public CalendarAdapter(Context mContext, TypedArray typedArray) {
        this.mContext = mContext;
        this.mTypedArray = typedArray;
    }

    public void updateCount(int count) {
        this.mCount = count;
        years.clear();
        
        notifyDataSetChanged();
    }

    @Override
    public CalendarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CalendarItemView itemView = new CalendarItemView(parent.getContext(), mTypedArray);
        return new CalendarHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CalendarHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        if (mCount != 0) {
            return mCount;
        }
        return 5;
    }

    class CalendarHolder extends RecyclerView.ViewHolder {

        public CalendarHolder(View view) {
            super(view);
        }
    }


}
