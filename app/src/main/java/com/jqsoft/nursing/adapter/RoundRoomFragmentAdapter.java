package com.jqsoft.nursing.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.bean.BuildingRoomFloorBean;
import com.jqsoft.nursing.bean.RoomList;
import com.jqsoft.nursing.bean.nursing.RoundsRecordCount;
import com.jqsoft.nursing.util.Util;

import java.util.ArrayList;
import java.util.List;


public class RoundRoomFragmentAdapter extends BaseQuickAdapterEx<RoomList, BaseViewHolder> {
    private Context context;
    private List<RoundsRecordCount> roundsRecordCounts = new ArrayList<>();

    public RoundRoomFragmentAdapter(List<RoomList> data, Context context, List<RoundsRecordCount> roundsRecordCounts) {
        super(R.layout.item_round_room_fragment, data);

        this.context = context;
//        this.roundsRecordCounts=roundsRecordCounts;
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
       //   helper.setText(R.id.objectname, Util.trimString(item.getBuilding().getBuildingName()));
           helper.setText(R.id.tv_name, Util.trimString(item.getRoom().getRoomNO()+"房"));

        helper.setText(R.id.tv_nursing_level, Util.trimString(item.getRoom().getRoomTypeName()));

        roundsRecordCounts= getRoundsRecordCounts();
        if(roundsRecordCounts==null){

        }else {
            for(int i=0;i<roundsRecordCounts.size();i++){
                if(item.getRoom().getRoomID().equals(roundsRecordCounts.get(i).getRoomID())){
                    helper.setText(R.id.tv_service_amount, "查房"+""+roundsRecordCounts.get(i).getRoomRecordCount()+"次");
                    break;
                }
            }
        }


    }

    public List<RoundsRecordCount> getRoundsRecordCounts() {
        return roundsRecordCounts;
    }

    public void setRoundsRecordCounts(List<RoundsRecordCount> roundsRecordCounts) {
        this.roundsRecordCounts = roundsRecordCounts;
    }




}
