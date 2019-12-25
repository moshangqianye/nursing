package com.jqsoft.nursing.adapter.nursing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.bean.nursing.HealthListBean;
import com.jqsoft.nursing.di.ui.activity.ArcFaceListActivity;
import com.jqsoft.nursing.di.ui.activity.ElderLogOutActivity;
import com.jqsoft.nursing.util.Util;


import java.io.Serializable;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author yedong
 * @date 2019/1/17
 * 老人列表适配器
 */

public class HealthListAdapter extends BaseQuickAdapterEx<HealthListBean.RowsBean, BaseViewHolder> {
    private Context context;
    private ArcFaceListActivity arcFaceListActivity;


    public HealthListAdapter(ArcFaceListActivity arcFaceListActivity,List data) {
        super(R.layout.item_health_list, data);
        this.context=context;
        this.arcFaceListActivity =arcFaceListActivity;
    }

    @Override
    protected void convert(BaseViewHolder helper, final HealthListBean.RowsBean item) {
        String pictureUrl = item.getSElderPhoto();
//        pictureUrl= Util.getUrlPathWithoutLastSlashCharacter(Version.HTTP_URL)+pictureUrl;
        CircleImageView imageView = helper.getView(R.id.civ_head);


        imageView.setTag(R.id.imageId, pictureUrl);
        String url = (String) imageView.getTag(R.id.imageId);
        if (url!=null && url.equals(pictureUrl)) {
//            GlideUtils.loadImageWithPlaceholderAndError(imageView, pictureUrl, R.mipmap.icon_touxiang, R.mipmap.icon_touxiang);

            Glide.with(arcFaceListActivity.getApplicationContext())
                    .load(pictureUrl)
                    .crossFade(1500)
                    .error(R.mipmap.person)
                    .into((ImageView) helper.getView(R.id.civ_head));
        }


        TextView tv_end = helper.getView(R.id.tv_end);







        helper.setText(R.id.tv_name,"姓名："+item.getSName());
        helper.setText(R.id.tv_sex,"性别："+item.getSSexName());
        helper.setText(R.id.tv_bad_no,"年龄："+item.getIAge());
        helper.setText(R.id.tv_id_card,"家庭地址："+item.getSAddress());
        helper.setText(R.id.tv_status,"身份证号："+item.getSIdCard());

        tv_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                arcFaceListActivity.face(item);

//                Intent intent = new Intent();
//                intent.setClass(arcFaceListActivity, ElderLogOutActivity.this);
//                arcFaceListActivity.startActivity(intent);

                Bundle bundle = new Bundle();
//                bundle.putString(Constants.NURSING_BED_ID_KEY, item.getBedID());
                bundle.putSerializable("item", (Serializable) item);
                Util.gotoActivityWithBundle(arcFaceListActivity, ElderLogOutActivity.class, bundle);

            }
        });

    }
}
