package com.gzk.calendar;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gzk.calendar.bean.MonthBean;
import com.gzk.calendar.util.CalendarContanst;
import com.gzk.calendar.util.CalendarUtils;

import java.util.Map;

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
    //今天字体颜色
    private int mDayTodayTvColor;
    //选中时候
    private int mDaySelectTvColor;

    //日期布局高度
    private int mDayTvLayoutH;

    //提示字体大小
    private int mTipTvSize;
    //提示字体颜色
    private int mTipTvColor;

    private int year;
    private int month;
    private int day;
    private TypedArray mTypedArray;


    private Context mContext;
    private int mCurrentYear;
    private int mCurrentMonth;
    private int mCurrentDay;
    private MonthBean mMonthBean;
    private IDayItemClickListener mItemClick;

    private Bitmap mTodayBitmap;
    private Bitmap mSelectedBitmap;

    public CalendarItemView(Context context, TypedArray typedArray) {
        super(context);
        mContext = context;
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

        mTipTvSize = typedArray.getDimensionPixelSize(R.styleable.customCalendar_calendarTipTvSize,
                resources.getDimensionPixelSize(R.dimen.calendarTipTvSize));
        mTipTvColor = typedArray.getColor(R.styleable.customCalendar_calendarTipTvColor,
                resources.getColor(R.color.calendarTipTvColor));
        mDayTodayTvColor = typedArray.getColor(R.styleable.customCalendar_calendarTipTvColor,
                resources.getColor(R.color.calendarTodayTvColor));
        mDaySelectTvColor = typedArray.getColor(R.styleable.customCalendar_calendarTipTvColor,
                resources.getColor(R.color.calendarSelectTvColor));
        mSelectedBitmap = BitmapFactory.decodeResource(resources,
                typedArray.getResourceId(R.styleable.customCalendar_calendarSelectedBg,
                        R.drawable.bag_select_circle));
        /*mSelectedBitmap = BitmapFactory.decodeResource(typedArray.getInt(R.styleable.customCalendar_calendarSelectedBg,
                R.drawable.bag_select_circle));*/
       /* typedArray.getIndex()*/

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

        initDate();
        //星期lable
        initWeek(context);
        //日期
        //initDay(context);
    }

    private void initDate() {
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

    private void initDay(final MonthBean bean) {
        mContentLayout.removeAllViews();
        final String date = bean.getYear() + "-" + (bean.getMonth() + 1) + "-1";
        final int startPos = CalendarUtils.getWeekByDate(date);
        int numDay = bean.getDayNum();
        double d = ((numDay + startPos) * 1d) / 7;
        int row = (int) Math.ceil(d);
        //日期具体数字
        for (int i = 0; i < row; i++) {
            LinearLayout.LayoutParams dayItemLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    mDayTvLayoutH);
            LinearLayout dayHRItemLayout = new LinearLayout(mContext);
            dayHRItemLayout.setLayoutParams(dayItemLp);
            dayHRItemLayout.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < 7; j++) {
                final int day = i * 7 + j - startPos + 1;
                FrameLayout tbFrameLayout = new FrameLayout(mContext);
                LinearLayout.LayoutParams tvFrameLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                tvFrameLp.weight = 1;
                tvFrameLp.gravity = Gravity.CENTER;
                tbFrameLayout.setLayoutParams(tvFrameLp);

                FrameLayout.LayoutParams tvLp = new FrameLayout.LayoutParams(mDayTvSize * 2,
                        mDayTvSize * 2);
                tvLp.gravity = Gravity.CENTER;
                tvLp.setMargins(0, 0, 0, (mDayTvLayoutH / 6));
                TextView tvDay = new TextView(mContext);

                tvDay.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                tvDay.setTextColor(mDayTvColor);
                tvDay.setLayoutParams(tvLp);

                FrameLayout.LayoutParams imgLp = new FrameLayout.LayoutParams(mDayTvSize * 2,
                        mDayTvSize * 2);
                ImageView bgImg = new ImageView(mContext);
                imgLp.gravity = Gravity.CENTER;
                imgLp.setMargins(0, 0, 0, (mDayTvLayoutH / 6));
                bgImg.setScaleType(ImageView.ScaleType.CENTER);
                bgImg.setLayoutParams(imgLp);

                FrameLayout.LayoutParams tvTipLp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView tvTip = new TextView(mContext);
                tvTip.setGravity(Gravity.CENTER);
                tvTipLp.setMargins(0, mDayTvLayoutH * 2 / 3, 0, 0);
                tvTip.setLayoutParams(tvTipLp);
                tvTip.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTipTvSize);
                tvTip.setTextColor(mTipTvColor);

                if ((i == 0 && startPos != 0 && j < startPos) || ((i * 7 + j - startPos + 1) > numDay)) {
                    tvDay.setTextColor(mDayTvColor);
                    tbFrameLayout.addView(tvDay);
                } else {
                    tbFrameLayout.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mItemClick != null) {
                                mItemClick.dayClick(bean.getYear(), bean.getMonth(), day, startPos);
                            }
                        }
                    });
                    tvDay.setText(day + "");
                    tvDay.setTextColor(mDayTvColor);
                    tvDay.setTextSize(TypedValue.COMPLEX_UNIT_PX, mDayTvSize);
                    if (bean.getYear() == mCurrentYear && bean.getMonth() == mCurrentMonth && day == mCurrentDay) {
                        tvDay.setTextColor(mDayTodayTvColor);
                        bgImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.bag_current_circle));
                        // tvDay.setBackground(mContext.getResources().getDrawable(R.drawable.bag_current_circle));
                    } else {
                        if (bean.getDays().get(day - 1).isChecked()) {
                            tvDay.setTextColor(mDaySelectTvColor);
                            // tvDay.setBackground(mContext.getResources().getDrawable(R.drawable.bag_select_circle));
                            bgImg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.bag_select_circle));
                           /* if (mSelectedBitmap != null) {
                                bgImg.setImageBitmap(mSelectedBitmap);
                            }*/

                        }
                    }

                    if (bean.getDays().get(day - 1).getTip() != null && !"".equals(bean.getDays().get(day - 1).getTip())) {
                        tvTip.setText(bean.getDays().get(day - 1).getTip());
                    }

                    tbFrameLayout.addView(bgImg);
                    tbFrameLayout.addView(tvTip);
                    tbFrameLayout.addView(tvDay);
                }
                dayHRItemLayout.addView(tbFrameLayout);
            }
            mContentLayout.addView(dayHRItemLayout);
        }
    }


    public void reuse() {
        requestLayout();
    }

    public void setParams(Map<String, Object> params) {
        if (params.isEmpty()) {
            System.out.println("no params...");
            return;
        }
        setTag(params);

        mCurrentYear = (int) params.get(CalendarContanst.PARAMS_CURRENT_YEAR);
        mCurrentMonth = (int) params.get(CalendarContanst.PARAMS_CURRENT_MONTH);
        mCurrentDay = (int) params.get(CalendarContanst.PARAMS_CURRENT_DAY);
        mMonthBean = (MonthBean) params.get(CalendarContanst.PARAMS_MONTH_DATA);
        if (mMonthBean == null) {
            return;
        }
        updateLable();

        initDay(mMonthBean);
    }

    private void updateLable() {
        if (mMonthBean != null) {
            mTvDateLable.setText(mMonthBean.getYear() + "年" + (mMonthBean.getMonth() + 1) + "月");
        }
    }

    public void setItemClick(IDayItemClickListener mItemClick) {
        this.mItemClick = mItemClick;
    }

    public interface IDayItemClickListener {
        void dayClick(int year, int month, int day, int week);
    }


}
