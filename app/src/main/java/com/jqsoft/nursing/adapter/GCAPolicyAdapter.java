package com.jqsoft.nursing.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.base.Version;
import com.jqsoft.nursing.bean.grassroots_civil_administration.PolicyBean;
import com.jqsoft.nursing.util.Util;
import com.jqsoft.nursing.utils.GlideUtils;

import java.util.List;

/**
 * Created by Administrator on 2017-12-27.
 */

public class GCAPolicyAdapter extends BaseQuickAdapterEx<PolicyBean, BaseViewHolder> {
    public GCAPolicyAdapter(List data) {
        super(R.layout.item_gca_policy_multiple_line, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PolicyBean item) {
//        String imageUrl = item.getMessageFirstImgSrc();
        String pictureUrl = item.getPicture();
        pictureUrl= Util.getUrlPathWithoutLastSlashCharacter(Version.HTTP_URL)+pictureUrl;
        ImageView imageView = helper.getView(R.id.iv_image);
        imageView.setTag(R.id.imageId, pictureUrl);
        String url = (String) imageView.getTag(R.id.imageId);
        if (url!=null && url.equals(pictureUrl)) {
            GlideUtils.loadImageWithPlaceholderAndError(imageView, pictureUrl, R.mipmap.icon_wutu, R.mipmap.icon_wutu);
        }
        helper.setText(R.id.tv_content, item.getTitle());
        String processedDateTime = Util.getProcessedDateTimeString(item.getReleaseTime());

        if(TextUtils.isEmpty(processedDateTime) || processedDateTime==null || processedDateTime.equals("null") || (processedDateTime.length()<11)){

        }else {
            processedDateTime=processedDateTime.substring(0,10);
        }

        processedDateTime= Constants.PUBLISH_TIME +processedDateTime;
        helper.setText(R.id.tv_date, processedDateTime);
    }
}
