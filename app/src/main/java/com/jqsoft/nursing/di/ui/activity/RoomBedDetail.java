package com.jqsoft.nursing.di.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.RoomDetailAdapter;
import com.jqsoft.nursing.adapter.SocialAssistanceObjectAdapter;
import com.jqsoft.nursing.bean.BedList;
import com.jqsoft.nursing.bean.RoomList;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.helper.FullyLinearLayoutManager;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/4/8.
 */

public class RoomBedDetail extends AbstractActivity implements SwipeRefreshLayout.OnRefreshListener {
    private ArrayList<BedList> bedLists = new ArrayList<>();
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.nursing_title)
    TextView nursing_title;
    private RoomDetailAdapter mAdapter;
    private String SelectLogo,buildinfo,roomNO;

    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_roombed;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void loadData() {
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        SelectLogo = getIntent().getStringExtra("SelectLogo");
        buildinfo = getIntent().getStringExtra("buildinfo");
        roomNO = getIntent().getStringExtra("roomNO");
        nursing_title.setText(buildinfo+roomNO);
        bedLists = (ArrayList<BedList>) bundle.getSerializable("bedLists");
        // Log.i("", bedLists + "");


        final BaseQuickAdapter<BedList, BaseViewHolder> mAdapter = new RoomDetailAdapter(new ArrayList<BedList>(), getApplicationContext());
        this.mAdapter = (RoomDetailAdapter) mAdapter;
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        //  mAdapter.setOnLoadMoreListener(this, recyclerView);
        recyclerview.setLayoutManager(new FullyLinearLayoutManager(this));
        recyclerview.setAdapter(mAdapter);
        mAdapter.setNewData(bedLists);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(RoomBedDetail.this, WriteNFC.class);
                Bundle bundle = new Bundle();
                bundle.putString("SelectLogo",SelectLogo);
                bundle.putSerializable("bedinfo", mAdapter.getData().get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onRefresh() {

    }
}
