package com.jqsoft.nursing.adapter.nursing;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.base.IdentityManager;
import com.jqsoft.nursing.bean.nursing.ElderDetailBean;
import com.jqsoft.nursing.bean.nursing.SaveRoundDataBean;
import com.jqsoft.nursing.di.ui.activity.nursing.NursingDetailActivity;
import com.jqsoft.nursing.di.ui.activity.nursing.RoundRoomDetailActivity;
import com.jqsoft.nursing.util.Util;
import com.jqsoft.nursing.utils.GlideUtils;
import com.jqsoft.nursing.view.ZQImageViewRoundOval;

import net.qiujuer.genius.ui.widget.TextView;


import org.litepal.LitePal;

import java.util.List;

/**
 * Created by Administrator on 2017-12-27.
 */

public class ElderListAdapter extends BaseQuickAdapterEx<ElderDetailBean, BaseViewHolder> {

    private RoundRoomDetailActivity roundRoomDetailActivity;

    public ElderListAdapter(List data,RoundRoomDetailActivity  roundRoomDetailActivity) {
        super(R.layout.item_round_room, data);
        this.roundRoomDetailActivity =roundRoomDetailActivity;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ElderDetailBean item) {

        ZQImageViewRoundOval imageView = helper.getView(R.id.civ_head);
        String nurseHeadImg= item.getElderPic();
        GlideUtils.loadImageWithPlaceholderAndError(imageView, nurseHeadImg, R.mipmap.icon_touxiang, R.mipmap.icon_touxiang);

        helper.setText(R.id.tv_name, item.getElderName());
        helper.setText(R.id.tv_nurse_level, item.getNurseLevelName());
        helper.setText(R.id.tv_gender_and_age, item.getElderSex());

        final AppCompatTextView tv_select1 =  helper.getView(R.id.tv_select1);

        tv_select1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roundRoomDetailActivity.myElderItem(item.getElderInfoID());
            }
        });

        List<SaveRoundDataBean>  qusId1 = LitePal.where("fKElderInfoID=?",item.getElderInfoID()).find(SaveRoundDataBean.class);

        if(qusId1.size()==0){
            tv_select1.setText("检查项");
            tv_select1.setTextColor(Color.parseColor("#3688ff"));
            tv_select1.setBackgroundDrawable(roundRoomDetailActivity.getResources().getDrawable(R.drawable.positive_background_positive_round_corner_border));
        }else {
            tv_select1.setText("已检查");
            tv_select1.setTextColor(Color.parseColor("#019782"));
            tv_select1.setBackgroundDrawable(roundRoomDetailActivity.getResources().getDrawable(R.drawable.positive_background_positive_round_corner_border_had));
        }


//         String pictureUrl = item.getServicePic();
//        ZQImageViewRoundOval imageView = helper.getView(R.id.civ_head);
//        imageView.setType(ZQImageViewRoundOval.TYPE_CIRCLE);
//
//        imageView.setTag(R.id.imageId, pictureUrl);
//        String url = (String) imageView.getTag(R.id.imageId);
//        if (url!=null && url.equals(pictureUrl)) {
//            GlideUtils.loadImageWithPlaceholderAndError(imageView, pictureUrl, R.mipmap.icon_touxiang, R.mipmap.icon_touxiang);
//        }
//
//        helper.setText(R.id.tv_name, item.getElderName());
//        String genderAndAge = item.getElderSex()+ Constants.COLON_STRING+item.getElderAge();
//        helper.setText(R.id.tv_gender_and_age, genderAndAge);
//        final android.view.View selectView = helper.getView(R.id.tv_select);
//        Util.setViewClickListener(selectView, new Runnable() {
//            @Override
//            public void run() {
//                Bundle bundle = new Bundle();
//                bundle.putString(Constants.NURSING_ELDER_ID_KEY, item.getElderInfoID());
//                Util.gotoActivityWithBundle(selectView.getContext(), NursingDetailActivity.class, bundle);
//            }
//        });
//        String numberString = Util.getNumberStringFromString(item.getIsDo());
//        helper.setText(R.id.tv_service_amount, "服务"+numberString+"次");
//        helper.setText(R.id.tv_nursing_level, item.getNurseLevelName());


    }
}
