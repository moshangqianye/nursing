package com.jqsoft.nursing.popup_window;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.MonthQuarterYearListTextAdapter;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.feature.IDateRange;
import com.jqsoft.nursing.helper.FullyLinearLayoutManager;
import com.jqsoft.nursing.listener.NoDoubleItemClickListener;

import java.util.List;

/**
 * Created by Administrator on 2018-01-01.
 */

public class MonthQuarterYearRangePopupWindow extends PopupWindow {

    private Activity activity;
    private View anchorView;
    private RecyclerView recyclerView;
    private int type;
    private List<IDateRange> list;
    private MonthQuarterYearListTextAdapter adapter;
    private DateRangeItemClickListener listener;

    public MonthQuarterYearRangePopupWindow(Activity activity, int width, int height, View anchorView, List<IDateRange> list) {
        super(activity.getLayoutInflater().inflate(R.layout.layout_wrap_content_recyclerview_without_srl, null), width, height, true);
//        super(activity.getLayoutInflater().inflate(R.layout.layout_recyclerview_without_srl, null), width, height, true);
        this.activity = activity;
        this.anchorView = anchorView;
        this.list = list;

        initView();
    }

    private void initView(){
        recyclerView = (RecyclerView) getContentView().findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new FullyLinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
        adapter = new MonthQuarterYearListTextAdapter(list);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new NoDoubleItemClickListener() {
            @Override
            public void onNoDoubleItemClick(BaseQuickAdapter adapter, View view, int position) {
                super.onNoDoubleItemClick(adapter, view, position);
                IDateRange iDateRange = (IDateRange) adapter.getItem(position);
                if (listener != null){
                    listener.dateRangeItemDidClick(iDateRange);
                }
                hide();
            }
        });

        setAnimationStyle(R.style.popup_window_animation);
        setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        setOutsideTouchable(true);


//        if (type == MQY_TYPE_MONTH){
//        }
    }

    public void show(){
        showAsDropDown(anchorView, 20, Constants.POPUP_WINDOW_Y_OFFSET);

    }

    public void hide(){
        dismiss();
    }


    public void setDateRangeItemClickListener(DateRangeItemClickListener listener){
        this.listener = listener;
    }

    public interface DateRangeItemClickListener {
        void dateRangeItemDidClick(IDateRange iDateRange);
    }
}
