package com.jqsoft.nursing.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.bean.grassroots_civil_administration.NewsListBean;
import com.jqsoft.nursing.util.Util;

import java.util.List;


//受理中心新闻适配器
public class ReceptionNewListAdapter extends BaseQuickAdapterEx<NewsListBean, BaseViewHolder> {
    public static final int TYPE_SINGLE_LINE=1;
    public static final int TYPE_MULTIPLE_LINE=2;
    private String filePath;
    private int type=TYPE_MULTIPLE_LINE;

    private Context context;
    public ReceptionNewListAdapter(List<NewsListBean> data, int type, Context context) {
        super(R.layout.item_reception_single_line, data);
        this.type = type;
        this.context=context;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final NewsListBean item) {
        helper.setText(R.id.tv_content, Util.trimString(item.getTitle()));//getname



    }



}
