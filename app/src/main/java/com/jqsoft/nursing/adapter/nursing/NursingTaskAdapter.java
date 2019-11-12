package com.jqsoft.nursing.adapter.nursing;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.nursing.NursingTaskBean;
import com.jqsoft.nursing.di.ui.activity.nursing.NursingDetailActivity;
import com.jqsoft.nursing.di.ui.fragment.nursing.NursingDetailTasksFragment;
import com.jqsoft.nursing.util.Util;
import com.jqsoft.nursing.utils.GlideUtils;
import com.jqsoft.nursing.utils.LogUtil;
import com.jqsoft.nursing.view.ZQImageViewRoundOval;

import java.util.List;

/**
 * 当日护理老人详细信息
 * Created by Administrator on 2017-12-27.
 */

public class NursingTaskAdapter extends BaseQuickAdapterEx<NursingTaskBean, BaseViewHolder> {
    private Context context;
    private NursingDetailTasksFragment fragment;
    public NursingTaskAdapter(List data, NursingDetailTasksFragment fragment) {
        super(R.layout.item_nursing_detail_task_layout, data);
        this.fragment=fragment;
        this.context=fragment.getActivity();
    }

    @Override
    protected void convert(final BaseViewHolder helper, final NursingTaskBean item) {
         String pictureUrl = Util.trimString(item.getServicePic());
//        pictureUrl= Util.getUrlPathWithoutLastSlashCharacter(Version.HTTP_URL)+pictureUrl;
        ZQImageViewRoundOval imageView = helper.getView(R.id.civ_image);
        imageView.setType(ZQImageViewRoundOval.TYPE_CIRCLE);

        imageView.setTag(R.id.imageId, pictureUrl);
        String url = (String) imageView.getTag(R.id.imageId);
        if (url!=null && url.equals(pictureUrl)) {
            GlideUtils.loadImageWithPlaceholderAndError(imageView, pictureUrl, R.mipmap.icon_touxiang, R.mipmap.icon_touxiang);
        }

        helper.setText(R.id.tv_name, Util.trimString(item.getServiceItemName()));
        helper.setText(R.id.tv_execution_person, "执行人:"+Util.trimString(item.getSignName()));
        helper.setText(R.id.tv_begin_end_time, Util.trimString(item.getBeginTime())+ Constants.HYPHEN_STRING+Util.trimString(item.getEndTime()));

        String time =item.getExcuteTime();
        if(time==null){

        }else {
           time= time.replace("T"," ");
            if(time.length()>17){
                time=time.substring(0,16);

            }else {

            }
        }

        helper.setText(R.id.tv_execution_time, "执行时间:"+Util.trimString(time));
//        helper.setText(R.id.tv_sum_price, item.getServicePrice());

        View speakView = helper.getView(R.id.iv_speak);
        Util.setViewClickListener(speakView, new Runnable() {
            @Override
            public void run() {
                Util.showAudioModeHint(mContext);
                String s = getSpeakingText(item);
                speak(s);
            }
        });

        final TextView beginNursingView = helper.getView(R.id.tv_begin_nursing);
        String status = item.getExecuteStatusString();
        beginNursingView.setText(status);
        Util.setViewClickListener(beginNursingView, new Runnable() {
            @Override
            public void run() {
                boolean isDone = Util.isNursingTaskDone(item.getIsExcute());
                if (isDone){
                    Util.showToast(mContext, "已完成");
                } else {
                    int position = helper.getAdapterPosition();
                    LogUtil.i("NursingTaskAdapter position:"+position);
                    endNursing(position, item);
                }
            }
        });
    }

    private void endNursing(int position, NursingTaskBean item){
        fragment.endNursing(position, item);
    }

    private void speak(String s){
//        TTSWrapper.getInstance(context).speak(s);
        if (context instanceof NursingDetailActivity){
            NursingDetailActivity nda = (NursingDetailActivity)context;
//            nda.speak(s);
            nda.requestAudioFocus(s);
        }
    }

    private String getSpeakingText(NursingTaskBean item){
        if (item!=null){
            StringBuilder sb = new StringBuilder();
            sb.append("护理项目名称:");
            sb.append(Util.trimString(item.getServiceItemName())+Constants.COMMA_STRING);
            sb.append("开始时间:");
            sb.append(Util.trimString(item.getBeginTime())+Constants.COMMA_STRING);
            sb.append("结束时间:");
            sb.append(Util.trimString(item.getEndTime())+Constants.PERIOD_STRING);
            String result = sb.toString();
            return result;
        } else {
            return Constants.EMPTY_STRING;
        }
    }
}
