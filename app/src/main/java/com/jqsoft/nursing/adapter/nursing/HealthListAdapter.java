package com.jqsoft.nursing.adapter.nursing;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.bean.nursing.HealthListBean;
import com.jqsoft.nursing.utils.GlideUtils;
import com.jqsoft.nursing.view.ZQImageViewRoundOval;

import java.util.List;

/**
 * @author yedong
 * @date 2019/1/17
 * 老人列表适配器
 */

public class HealthListAdapter extends BaseQuickAdapterEx<HealthListBean.RowsBean, BaseViewHolder> {

    public HealthListAdapter(List data) {
        super(R.layout.item_health_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final HealthListBean.RowsBean item) {
        String pictureUrl = item.getSElderPhoto();
//        pictureUrl= Util.getUrlPathWithoutLastSlashCharacter(Version.HTTP_URL)+pictureUrl;
        ZQImageViewRoundOval imageView = helper.getView(R.id.civ_head);
        imageView.setType(ZQImageViewRoundOval.TYPE_CIRCLE);

        imageView.setTag(R.id.imageId, pictureUrl);
        String url = (String) imageView.getTag(R.id.imageId);
        if (url!=null && url.equals(pictureUrl)) {
            GlideUtils.loadImageWithPlaceholderAndError(imageView, pictureUrl, R.mipmap.icon_touxiang, R.mipmap.icon_touxiang);
        }

        helper.setText(R.id.tv_name,"姓名："+item.getSName());
        helper.setText(R.id.tv_sex,"性别："+item.getSSexName());
        helper.setText(R.id.tv_bad_no,"年龄："+item.getIAge());
        helper.setText(R.id.tv_id_card,"家庭地址："+item.getSAddress());
        helper.setText(R.id.tv_status,"身份证号："+item.getSIdCard());


    }
}
