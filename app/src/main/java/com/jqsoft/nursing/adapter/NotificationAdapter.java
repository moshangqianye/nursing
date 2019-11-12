package com.jqsoft.nursing.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.grassroots_civil_administration.NotificationBean;
import com.jqsoft.nursing.util.Util;

import java.util.List;


//通知公告适配器
public class NotificationAdapter extends BaseQuickAdapterEx<NotificationBean, BaseViewHolder> {
    public static final int TYPE_SINGLE_LINE=1;
    public static final int TYPE_MULTIPLE_LINE=2;

    private int type=TYPE_MULTIPLE_LINE;
    private Context context;

    public NotificationAdapter(List<NotificationBean> data, int type) {
        super(type==TYPE_MULTIPLE_LINE?R.layout.item_notification_multiple_line:R.layout.item_notification_single_line, data);
        this.type = type;
//        this.context=context;
    }

//    private int getLayoutId(){
//        int result = R.layout.item_notification_single_line;
//        if (type == TYPE_SINGLE_LINE){
//            result=R.layout.item_notification_single_line;
//        } else if (type == TYPE_MULTIPLE_LINE){
//            result=R.layout.item_notification_multiple_line;
//        }
//        return result;
//    }

    @Override
    protected void convert(final BaseViewHolder helper, final NotificationBean item) {
        helper.setText(R.id.tv_content, Util.trimString(item.getTitle()));
        String createDate = Util.trimString(item.getReleaseTime());
        String processedCreateDate = Util.getProcessedDateTimeString(createDate);
//        String processedCreateDate = Util.getYearMonthDayFromFullString(createDate);
//        if (type==TYPE_MULTIPLE_LINE){
        if(TextUtils.isEmpty(processedCreateDate) || processedCreateDate==null || processedCreateDate.equals("null") || (processedCreateDate.length()<11)){

        }else {
            processedCreateDate=processedCreateDate.substring(0,10);
        }

            processedCreateDate= Constants.PUBLISH_TIME +processedCreateDate;
//        }
        helper.setText(R.id.tv_date,  processedCreateDate);

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
