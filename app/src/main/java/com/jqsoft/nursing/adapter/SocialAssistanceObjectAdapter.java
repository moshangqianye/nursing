package com.jqsoft.nursing.adapter;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.base.Version;
import com.jqsoft.nursing.bean.BuildingRoomFloorBean;
import com.jqsoft.nursing.bean.RoomList;
import com.jqsoft.nursing.util.Util;

import java.util.ArrayList;
import java.util.List;


public class SocialAssistanceObjectAdapter extends BaseQuickAdapterEx<RoomList, BaseViewHolder> {
    private Context context;

    public SocialAssistanceObjectAdapter(ArrayList<RoomList> roomLists ,Context context) {
        super(R.layout.item_round_write_room_fragment, roomLists);

        this.context = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final RoomList item) {
//        item.setPhotoUrl("http://192.168.88.36:8080/fdss-api/photo/0123456.jpg");
        //  String photoUrl = Util.trimString(item.getPhotoUrl());
        //    String imageUrl = Version.FILE_URL_BASE+photoUrl;

        //   GlideUtils.loadImage(imageUrl, (ImageView) helper.getView(R.id.iv_head));

//        String photoUrl = Util.trimString(item.getFilePath());
//        String imageUrl = Version.FIND_FILE_URL_BASE + photoUrl;
////         GlideUtils.loadImageNew(imageUrl, (ImageView) helper.getView(R.id.iv_title));
////        GlideUtils.load(context,imageUrl,(ImageView) helper.getView(R.id.iv_title));
//        //http://192.168.44.134:8080/sri/JingQi_Sri_File/upload/sriattach/wechat/EKZ6H7c7YIGX.jpg
//        Uri uri = Uri.parse(imageUrl);
//        SimpleDraweeView draweeView = (SimpleDraweeView) helper.getView(R.id.iv_title);
//        draweeView.setImageURI(uri);
        helper.setText(R.id.tv_name, Util.trimString(item.getRoom().getRoomNO()+"房"));

        helper.setText(R.id.tv_nursing_level, Util.trimString(item.getRoom().getRoomTypeName()));
//          helper.setText(R.id.tv_name,Util.trimString(item.getRoom().getRoomNO()));
//           helper.setText(R.id.tv_service_amount, Util.trimString(item.getRoom().getRoomName()));
//        ImageView iv_sex = helper.getView(R.id.objectsex);
//        String sex = Util.trimString(item.getSex());
//        if (sex.equals("sex_1")) {
//            iv_sex.setImageResource(R.mipmap.icon_sex_man);
//        } else {
//            iv_sex.setImageResource(R.mipmap.icon_sex_woman);
//        }


//        helper.setText(R.id.tv_message, Util.trimString(item.getPostMessage()));
//        String time = Util.trimString(item.getSetTime());
//        String canonicalTime = Util.getYearMonthDayFromFullString(time);
//        helper.setText(R.id.tv_time, canonicalTime);
//        ImageView iv_tang =helper.getView(R.id.iv_tang);


//        helper.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClickListener.onItemClickListener(item.getDocid(), item.getImgsrc(),helper.getView(R.id.iv_item_top_news));
//            }
//        });

    }

//    OnItemClickListener onItemClickListener;
//
//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }
//
//    public interface OnItemClickListener {
//        void onItemClickListener(String id, String imgUrl, View view);}

}
