package com.jqsoft.nursing.adapter.nursing;

import android.os.Bundle;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.nursing.NursingBean;
import com.jqsoft.nursing.di.ui.activity.nursing.NursingDetailActivity;
import com.jqsoft.nursing.util.Util;
import com.jqsoft.nursing.utils.GlideUtils;
import com.jqsoft.nursing.utils3.util.StringUtils;
import com.jqsoft.nursing.view.ZQImageViewRoundOval;

import java.util.List;

/**
 * Created by Administrator on 2017-12-27.
 */

public class NursingListAdapter extends BaseQuickAdapterEx<NursingBean, BaseViewHolder> {
    public NursingListAdapter(List data) {
        super(R.layout.item_nursing, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final NursingBean item) {
         String pictureUrl = item.getServicePic();
//        pictureUrl= Util.getUrlPathWithoutLastSlashCharacter(Version.HTTP_URL)+pictureUrl;
        ZQImageViewRoundOval imageView = helper.getView(R.id.civ_head);
        imageView.setType(ZQImageViewRoundOval.TYPE_CIRCLE);

        imageView.setTag(R.id.imageId, pictureUrl);
        String url = (String) imageView.getTag(R.id.imageId);
        if (url!=null && url.equals(pictureUrl)) {
            GlideUtils.loadImageWithPlaceholderAndError(imageView, pictureUrl, R.mipmap.icon_touxiang, R.mipmap.icon_touxiang);
        }

        helper.setText(R.id.tv_name, Util.trimString(item.getElderName()));

        String elderGender = Util.trimString(item.getElderSex());
        String elderAge = Util.trimString(item.getElderAge());
        String genderAndAge = Constants.EMPTY_STRING;
        String genderAndAgeSeparator = Constants.EMPTY_STRING;
        if (!StringUtils.isBlank(elderGender) && !StringUtils.isBlank(elderAge)) {
            genderAndAgeSeparator = Constants.COLON_STRING;
        } else {
            genderAndAgeSeparator = Constants.SPACE_STRING;
        }
        genderAndAge = elderGender + genderAndAgeSeparator + elderAge;
        helper.setText(R.id.tv_gender_and_age, genderAndAge);
        final android.view.View selectView = helper.getView(R.id.tv_select);
        Util.setViewClickListener(selectView, new Runnable() {
            @Override
            public void run() {
//                Util.showToast(selectView.getContext(), "选择按钮被点击");
                Bundle bundle = new Bundle();
                bundle.putString(Constants.NURSING_BED_ID_KEY, item.getBedID());
                bundle.putParcelable(Constants.NURSING_BEAN_KEY, item);
                Util.gotoActivityWithBundle(selectView.getContext(), NursingDetailActivity.class, bundle);
            }
        });
        String numberString = Util.getNumberStringFromString(item.getIsDo());
        helper.setText(R.id.tv_service_amount, "服务"+numberString+"次");
        helper.setText(R.id.tv_nursing_level, Util.trimString(item.getNurseLevelName()));


//        String pictureUrl = item.getServicePic();
////        pictureUrl= Util.getUrlPathWithoutLastSlashCharacter(Version.HTTP_URL)+pictureUrl;
//        ZQImageViewRoundOval imageView = helper.getView(R.id.civ_head);
//        imageView.setType(ZQImageViewRoundOval.TYPE_CIRCLE);
//
//        imageView.setTag(R.id.imageId, pictureUrl);
//        String url = (String) imageView.getTag(R.id.imageId);
//        if (url!=null && url.equals(pictureUrl)) {
//            GlideUtils.loadImageWithPlaceholderAndError(imageView, pictureUrl, R.mipmap.icon_touxiang, R.mipmap.icon_touxiang);
//        }
//
//        helper.setText(R.id.tv_service_category, "服务类别:"+item.getFKServiceTypeName());
//        helper.setText(R.id.tv_service_status, item.getIsExcute());
//        helper.setText(R.id.tv_nurse, "医护人员:"+item.getParamedic());
////        helper.setText(R.id.tv_department, "("+item.get+")");
//        helper.setText(R.id.tv_service_item, "服务项目:"+item.getServiceItemName());
//        helper.setText(R.id.tv_money, "¥"+item.getServicePrice());
//
//        helper.setText(R.id.tv_patient, "长者姓名:"+item.getElderName());
//        helper.setText(R.id.tv_service_address, "服务地址:"+item.getElderHouseAddress()+item.getBuildingNO()+"栋"+
//        item.getRoomNumber()+"房"+item.getBedNO()+"床位");
//        helper.setText(R.id.tv_nursing_level, "护理级别:"+item.getNurseLevelName());
//        helper.setText(R.id.tv_service_time, "护理时间:"+item.getBeginTime()+ Constants.HYPHEN_STRING+item.getEndTime());
//
//        final View cancelButton = helper.getView(R.id.btn_cancel_order);
//        Util.setViewClickListener(cancelButton, new Runnable() {
//            @Override
//            public void run() {
//                Util.showToast(cancelButton.getContext(), "取消按钮被点击");
//            }
//        });
//        final View confirmButton = helper.getView(R.id.btn_confirm_purchase);
//        Util.setViewClickListener(confirmButton, new Runnable() {
//            @Override
//            public void run() {
//                Util.showToast(confirmButton.getContext(), "确定按钮被点击");
//            }
//        });
    }
}
