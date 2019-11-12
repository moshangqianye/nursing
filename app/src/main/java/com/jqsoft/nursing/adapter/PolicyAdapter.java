package com.jqsoft.nursing.adapter;

import android.content.Context;
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


//政策新闻适配器
public class PolicyAdapter extends BaseQuickAdapterEx<PolicyBean, BaseViewHolder> {
    public static final int TYPE_SINGLE_LINE=1;
    public static final int TYPE_MULTIPLE_LINE=2;

    private int type=TYPE_MULTIPLE_LINE;
    private Context context;

    public PolicyAdapter(List<PolicyBean> data, int type) {
        super(type==TYPE_MULTIPLE_LINE?R.layout.item_policy_multiple_line:R.layout.item_policy_single_line, data);
        this.type = type;
//        this.context=context;
    }

//    private int getLayoutId(){
//        int result = R.layout.item_policy_single_line;
//        if (type == TYPE_SINGLE_LINE){
//            result=R.layout.item_policy_single_line;
//        } else if (type == TYPE_MULTIPLE_LINE){
//            result=R.layout.item_policy_multiple_line;
//        }
//        return result;
//    }

    @Override
    protected void convert(final BaseViewHolder helper, final PolicyBean item) {
//        String imageUrl = item.getMessageFirstImgSrc();
        String pictureUrl = item.getPicture();
        pictureUrl= Util.getUrlPathWithoutLastSlashCharacter(Version.HTTP_URL)+pictureUrl;
        ImageView imageView = helper.getView(R.id.iv_image);
        imageView.setTag(R.id.imageId, pictureUrl);
        String url = (String) imageView.getTag(R.id.imageId);
        if (url!=null && url.equals(pictureUrl)) {
            GlideUtils.loadImageWithPlaceholderAndError(imageView, pictureUrl, R.mipmap.icon_wutu, R.mipmap.icon_wutu);
        }

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
