package com.jqsoft.nursing.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.TitleAndValueListTextAdapter;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.TitleAndValueBean;
import com.jqsoft.nursing.dialog.base.BaseDialog;
import com.jqsoft.nursing.helper.FullyLinearLayoutManager;
import com.jqsoft.nursing.listener.NoDoubleItemClickListener;

import java.util.List;

/**
 * 周边搜索对话框
 * Created by Administrator on 2018-03-06.
 */

public class AmbientRadiusSelectDialog extends BaseDialog {
    private String title;
    private List<TitleAndValueBean> list;

    private TextView tvTitle;
    private RecyclerView recyclerView;
    private TitleAndValueListTextAdapter adapter;
    private TitleAndCategoryItemClickListener listener;

    public AmbientRadiusSelectDialog(@NonNull Context context, String title, List<TitleAndValueBean> list) {
        super(context, R.style.white_background_dialog, R.layout.layout_title_and_recyclerview_without_srl);
        this.title = title;
        this.list = list;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(title);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new FullyLinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        adapter = new TitleAndValueListTextAdapter(list);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new NoDoubleItemClickListener() {
            @Override
            public void onNoDoubleItemClick(BaseQuickAdapter adapter, View view, int position) {
                super.onNoDoubleItemClick(adapter, view, position);
                TitleAndValueBean bean = (TitleAndValueBean) adapter.getItem(position);
                if (listener != null){
                    listener.titleAndCategoryItemDidClick(bean);
                }
                hide();
            }
        });


    }

    public void setListener(TitleAndCategoryItemClickListener listener) {
        this.listener = listener;
    }

    public interface TitleAndCategoryItemClickListener {
        public void titleAndCategoryItemDidClick(TitleAndValueBean bean);
    }

}
