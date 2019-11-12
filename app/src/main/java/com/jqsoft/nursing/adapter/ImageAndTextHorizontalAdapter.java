package com.jqsoft.nursing.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.bean.ImageAndTextBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/13.
 */

public class ImageAndTextHorizontalAdapter extends BaseQuickAdapterEx<ImageAndTextBean, BaseViewHolder> {
    public ImageAndTextHorizontalAdapter(List data) {
        super(R.layout.layout_image_and_text_item_horizontal, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, ImageAndTextBean item) {
        ImageAndTextBean bean = (ImageAndTextBean) item;
        helper.setImageResource(R.id.iv_image, bean.getImageId());
        helper.setText(R.id.tv_title, bean.getTitle());

    }
}
