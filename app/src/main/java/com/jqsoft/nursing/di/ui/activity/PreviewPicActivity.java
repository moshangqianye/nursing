package com.jqsoft.nursing.di.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.utils.GlideUtils;

/**
 * @author yedong
 * @date 2019/1/18
 * 图片预览页面
 */
public class PreviewPicActivity extends AbstractActivity implements View.OnClickListener {

    private PhotoView iv_show;    // 图片控件

    private String mPicUrl;  // 展示的图片地址

    public static void start(Context context, String picUrl) {
        Intent intent = new Intent(context, PreviewPicActivity.class);
        intent.putExtra("PicUrl", picUrl);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_preview_pic;
    }

    @Override
    protected void initData() {
        mPicUrl = getIntent().getStringExtra("PicUrl");
    }

    @Override
    protected void initView() {
        iv_show = (PhotoView) findViewById(R.id.iv_show);
        TextView tv_title = (TextView) findViewById(R.id.nursing_title);
        tv_title.setText("病例详情");
        findViewById(R.id.tv_scan).setVisibility(View.GONE);
        View ll_back = findViewById(R.id.ll_back);
        ll_back.setOnClickListener(this);
        ll_back.setVisibility(View.VISIBLE);

    }

    @Override
    protected void loadData() {
        GlideUtils.loadImageWithPlaceholderAndError(iv_show, mPicUrl, R.mipmap.icon_touxiang, R.mipmap.icon_touxiang);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
        }
    }
}
