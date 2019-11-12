package com.jqsoft.nursing.content.function;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.FunctionImageTextAdapter;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.FunctionImageBean;
import com.jqsoft.nursing.helper.FullyGridLayoutManagerSmoothScroll;
import com.jqsoft.nursing.listener.NoDoubleItemClickListener;
import com.jqsoft.nursing.rx.RxBus;
import com.jqsoft.nursing.utils.LogUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/5/13.
 */

public class FunctionImageTextGroupContent {
    private Context context;
    private View view;
    public FunctionImageTextGroupContent(Context context) {
        this.context=context;
    }

    public void initView(List<FunctionImageBean> list){
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
//        View   dialog = inflater.inflate(R.layout.layout_recyclerview_with_padding,(ViewGroup) getActivity().findViewById(R.id.root));
        View rootView = inflater.inflate(R.layout.layout_recyclerview_with_padding_without_srl, null);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
//        List<FunctionImageBean> areaList = SimulateData.getSimulatedFunctionImageGroup();
        List<FunctionImageBean> areaList = list;
//        GridLayoutManager gridLayoutManager = new FullyGridLayoutManager((Activity)context, Constants.FUNCTION_VIEW_COL_NUMBER);
        GridLayoutManager gridLayoutManager = new FullyGridLayoutManagerSmoothScroll((Activity)context, Constants.FUNCTION_VIEW_COL_NUMBER);
        FunctionImageTextAdapter adapter = new FunctionImageTextAdapter(areaList);
        adapter.setOnItemClickListener(new NoDoubleItemClickListener() {
            @Override
            public void onNoDoubleItemClick(BaseQuickAdapter adapter, View view, int position) {
                super.onNoDoubleItemClick(adapter, view, position);
                FunctionImageBean item = (FunctionImageBean) adapter.getItem(position);
                LogUtil.i("has selected item name:" + item.getTitle());
                RxBus.getDefault().post(Constants.EVENT_TYPE_DID_SELECT_FUNCTION_IMAGE, item);
            }
        });
//        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                FunctionImageBean item = (FunctionImageBean) adapter.getItem(position);
//                LogUtil.i("has selected item name:" + item.getTitle());
//                RxBus.getDefault().post(Constants.EVENT_TYPE_DID_SELECT_FUNCTION_IMAGE, item);
//
//            }
//        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        view=rootView;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
