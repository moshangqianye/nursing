package com.jqsoft.nursing.adapter.nursing;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jqsoft.nursing.R;
import com.jqsoft.nursing.bean.nursing.ChildDataBean;
import com.jqsoft.nursing.bean.nursing.RoundDataBean;
import com.jqsoft.nursing.bean.nursing.SaveRoundDataBean;
import com.jqsoft.nursing.di.ui.activity.nursing.ElderItemActivity;
import com.jqsoft.nursing.util.Util;
import com.mixiaoxiao.smoothcompoundbutton.SmoothCheckBox;
import com.mixiaoxiao.smoothcompoundbutton.SmoothCompoundButton;
import com.mixiaoxiao.smoothcompoundbutton.SmoothRadioButton;
import com.mixiaoxiao.smoothcompoundbutton.SmoothRadioGroup;


import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by classichu on 2018/3/26.
 */

public class MyPrimaryBaseAdapter extends BaseAdapter {
    private static final String TAG = "MyPrimaryBaseAdapter";
    private List<RoundDataBean> mMyBeanList;
    //private List<ChildDataBean> answerList;

    private String SINGLECHOOSE="0";
    private String Multiselect="1";

    private Context context;
    private   List<SaveRoundDataBean>  savequsId1 = new ArrayList<>();
    private   List<SaveRoundDataBean>  saveAllqusId1 = new ArrayList<>();

    private String myfKElderInfoID;

    public MyPrimaryBaseAdapter(List<RoundDataBean> myBeanList,Context context,String myfKElderInfoID) {
        mMyBeanList = myBeanList;
        this.context =context;
        this.myfKElderInfoID=myfKElderInfoID;
    }

    @Override
    public int getCount() {
        return mMyBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMyBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MyViewHolder myViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_checkable, parent, false);
            myViewHolder = new MyViewHolder();
            myViewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            myViewHolder.star = (TextView) convertView.findViewById(R.id.star);
//            myViewHolder.ll_muilt = (LinearLayout) convertView.findViewById(R.id.ll_muilt);
            myViewHolder.cb1 = (SmoothCheckBox) convertView.findViewById(R.id.id_cb1);
            myViewHolder.cb2 = (SmoothCheckBox) convertView.findViewById(R.id.id_cb2);
            myViewHolder.cb3 = (SmoothCheckBox) convertView.findViewById(R.id.id_cb3);
            myViewHolder.cb4 = (SmoothCheckBox) convertView.findViewById(R.id.id_cb4);
            myViewHolder.cb5 = (SmoothCheckBox) convertView.findViewById(R.id.id_cb5);
            myViewHolder.cb6 = (SmoothCheckBox) convertView.findViewById(R.id.id_cb6);
            myViewHolder.cb7 = (SmoothCheckBox) convertView.findViewById(R.id.id_cb7);
            myViewHolder.cb8 = (SmoothCheckBox) convertView.findViewById(R.id.id_cb8);
            myViewHolder.cb9 = (SmoothCheckBox) convertView.findViewById(R.id.id_cb9);
            myViewHolder.cb10 = (SmoothCheckBox) convertView.findViewById(R.id.id_cb10);

            myViewHolder.id_rg = (SmoothRadioGroup) convertView.findViewById(R.id.id_rg);
            myViewHolder.id_rb1 = (SmoothRadioButton) convertView.findViewById(R.id.id_rb1);
            myViewHolder.id_rb2 = (SmoothRadioButton) convertView.findViewById(R.id.id_rb2);
            myViewHolder.id_rb3 = (SmoothRadioButton) convertView.findViewById(R.id.id_rb3);
            myViewHolder.id_rb4 = (SmoothRadioButton) convertView.findViewById(R.id.id_rb4);
            myViewHolder.id_rb5 = (SmoothRadioButton) convertView.findViewById(R.id.id_rb5);
            myViewHolder.id_rb6 = (SmoothRadioButton) convertView.findViewById(R.id.id_rb6);
            myViewHolder.id_rb7 = (SmoothRadioButton) convertView.findViewById(R.id.id_rb7);
            myViewHolder.id_rb8 = (SmoothRadioButton) convertView.findViewById(R.id.id_rb8);
            myViewHolder.id_rb9 = (SmoothRadioButton) convertView.findViewById(R.id.id_rb9);
            myViewHolder.id_rb10 = (SmoothRadioButton) convertView.findViewById(R.id.id_rb10);

            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }

        saveAllqusId1= LitePal.findAll(SaveRoundDataBean.class);

      //  final RoundDataBean myBean = mMyBeanList.get(position);
       // helper.setText(R.id.tv_content, Util.trimString("Q"+(helper.getPosition()+1)+": "+item.getWardRoundName()));
        myViewHolder.tv_title.setText("Q"+(position+1)+": "+mMyBeanList.get(position).getWardRoundName());

        myViewHolder.cb1.setVisibility(View.GONE);
        myViewHolder.cb2.setVisibility(View.GONE);
        myViewHolder.cb3.setVisibility(View.GONE);
        myViewHolder.cb4.setVisibility(View.GONE);
        myViewHolder.cb5.setVisibility(View.GONE);
        myViewHolder.cb6.setVisibility(View.GONE);
        myViewHolder.cb7.setVisibility(View.GONE);
        myViewHolder.cb8.setVisibility(View.GONE);
        myViewHolder.cb9.setVisibility(View.GONE);
        myViewHolder.cb10.setVisibility(View.GONE);
        myViewHolder.star.setVisibility(View.GONE);

        myViewHolder.id_rb1.setVisibility(View.GONE);
        myViewHolder.id_rb2.setVisibility(View.GONE);
        myViewHolder.id_rb3.setVisibility(View.GONE);
        myViewHolder.id_rb4.setVisibility(View.GONE);
        myViewHolder.id_rb5.setVisibility(View.GONE);
        myViewHolder.id_rb6.setVisibility(View.GONE);
        myViewHolder.id_rb7.setVisibility(View.GONE);
        myViewHolder.id_rb8.setVisibility(View.GONE);
        myViewHolder.id_rb9.setVisibility(View.GONE);
        myViewHolder.id_rb10.setVisibility(View.GONE);

        myViewHolder.id_rb1.setChecked(false,false,false);
        myViewHolder.id_rb2.setChecked(false,false,false);
        myViewHolder.id_rb3.setChecked(false,false,false);
        myViewHolder.id_rb4.setChecked(false,false,false);
        myViewHolder.id_rb5.setChecked(false,false,false);
        myViewHolder.id_rb6.setChecked(false,false,false);
        myViewHolder.id_rb7.setChecked(false,false,false);
        myViewHolder.id_rb8.setChecked(false,false,false);
        myViewHolder.id_rb9.setChecked(false,false,false);
        myViewHolder.id_rb10.setChecked(false,false,false);

        if(mMyBeanList.get(position).getIsFillout().equals("0")){
            myViewHolder.star.setVisibility(View.GONE);
        }else {
            myViewHolder.star.setVisibility(View.VISIBLE);
        }




        if (mMyBeanList.get(position).getMultipleChoice().equals(Multiselect)){
            myViewHolder.cb1.setVisibility(View.GONE);
            myViewHolder.cb2.setVisibility(View.GONE);
            myViewHolder.cb3.setVisibility(View.GONE);
            myViewHolder.cb4.setVisibility(View.GONE);
            myViewHolder.cb5.setVisibility(View.GONE);
            myViewHolder.cb6.setVisibility(View.GONE);
            myViewHolder.cb7.setVisibility(View.GONE);
            myViewHolder.cb8.setVisibility(View.GONE);
            myViewHolder.cb9.setVisibility(View.GONE);
            myViewHolder.cb10.setVisibility(View.GONE);

            myViewHolder.id_rb1.setVisibility(View.GONE);
            myViewHolder.id_rb2.setVisibility(View.GONE);
            myViewHolder.id_rb3.setVisibility(View.GONE);

            switch (mMyBeanList.get(position).getChildren().size()) {
                case 0:

                    myViewHolder.cb1.setVisibility(View.GONE);
                    myViewHolder.cb2.setVisibility(View.GONE);
                    myViewHolder.cb3.setVisibility(View.GONE);
                    myViewHolder.cb4.setVisibility(View.GONE);
                    myViewHolder.cb5.setVisibility(View.GONE);
                    myViewHolder.cb6.setVisibility(View.GONE);
                    myViewHolder.cb7.setVisibility(View.GONE);
                    myViewHolder.cb8.setVisibility(View.GONE);
                    myViewHolder.cb9.setVisibility(View.GONE);
                    myViewHolder.cb10.setVisibility(View.GONE);

                    break;
                case 1:

                    myViewHolder.cb1.setVisibility(View.VISIBLE);
                    myViewHolder.cb2.setVisibility(View.GONE);
                    myViewHolder.cb3.setVisibility(View.GONE);

                    myViewHolder.cb1.setText(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());

                    myViewHolder.cb1.setChecked(mMyBeanList.get(position).getChildren().get(0).isChecked1,false,false);
                    myViewHolder.cb1.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            Log.d(TAG, "onCheckedChanged: buttonView:" + buttonView);
                            mMyBeanList.get(position).getChildren().get(0).isChecked1 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());

                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());

                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }

                            if(mMyBeanList.get(position).getChildren().get(0).isChecked1){


                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {


                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                            myViewHolder.cb1.setText(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());


                        }
                    });

                    for(int i=0;i<saveAllqusId1.size();i++){
                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(0).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb1.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }

                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                    }


                    break;
                case 2:

                    myViewHolder.cb1.setVisibility(View.VISIBLE);
                    myViewHolder.cb2.setVisibility(View.VISIBLE);
                    myViewHolder.cb3.setVisibility(View.GONE);

                    myViewHolder.cb1.setText(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                    myViewHolder.cb2.setText(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());

                    myViewHolder.cb1.setChecked(mMyBeanList.get(position).getChildren().get(0).isChecked1,false,false);
                    myViewHolder.cb1.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            Log.d(TAG, "onCheckedChanged: buttonView:" + buttonView);
                            mMyBeanList.get(position).getChildren().get(0).isChecked1 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());

                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());

                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }

                            if(mMyBeanList.get(position).getChildren().get(0).isChecked1){


                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {


                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                            myViewHolder.cb1.setText(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());


                        }
                    });

                    myViewHolder.cb2.setChecked(mMyBeanList.get(position).getChildren().get(1).isChecked2,false,false);
                    myViewHolder.cb2.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            Log.d(TAG, "onCheckedChanged: buttonView:" + buttonView);
                            mMyBeanList.get(position).getChildren().get(1).isChecked2 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(1).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(1).isChecked2){


                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {


                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                            myViewHolder.cb2.setText(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());

                        }
                    });


                   for(int i=0;i<saveAllqusId1.size();i++){
                       if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(0).getWardRoundID())
                               && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                           myViewHolder.cb1.setChecked(true);
                           SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                           saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                           saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                           saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());

                           saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                           saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                           if(mMyBeanList.get(position).getIsFillout().equals("1")){
                               saveRoundDataBean.setIsFillout("1");
                           }else {
                               saveRoundDataBean.setIsFillout("0");
                           }

                           ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                       }
                       if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(1).getWardRoundID())
                               && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                           myViewHolder.cb2.setChecked(true);
                           SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                           saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                           saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                           saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(1).getWardRoundID());

                           saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                           saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                           if(mMyBeanList.get(position).getIsFillout().equals("1")){
                               saveRoundDataBean.setIsFillout("1");
                           }else {
                               saveRoundDataBean.setIsFillout("0");
                           }
                           ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                       }
                   }



                    break;
                case 3:

                    myViewHolder.cb1.setVisibility(View.VISIBLE);
                    myViewHolder.cb2.setVisibility(View.VISIBLE);
                    myViewHolder.cb3.setVisibility(View.VISIBLE);

                    myViewHolder.cb1.setText(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                    myViewHolder.cb2.setText(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                    myViewHolder.cb3.setText(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());

                    myViewHolder.cb1.setChecked(mMyBeanList.get(position).getChildren().get(0).isChecked1,false,false);
                    myViewHolder.cb1.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听

                                return;
                            }

                            mMyBeanList.get(position).getChildren().get(0).isChecked1 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(0).isChecked1){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }
                        }
                    });
                    myViewHolder.cb2.setChecked(mMyBeanList.get(position).getChildren().get(1).isChecked2,false,false);
                    myViewHolder.cb2.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            Log.d(TAG, "onCheckedChanged: buttonView:" + buttonView);
                            mMyBeanList.get(position).getChildren().get(1).isChecked2 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(1).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());

                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(1).isChecked2){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }
                        }
                    });

                    myViewHolder.cb3.setChecked(mMyBeanList.get(position).getChildren().get(2).isChecked3,false,false);

                    myViewHolder.cb3.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(2).isChecked3 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(2).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());

                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(2).isChecked3){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });

                    for(int i=0;i<saveAllqusId1.size();i++){
                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(0).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb1.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }

                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }
                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(1).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb2.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(1).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(2).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb3.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(2).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }
                    }



                    break;
                case 4:

                    myViewHolder.cb1.setVisibility(View.VISIBLE);
                    myViewHolder.cb2.setVisibility(View.VISIBLE);
                    myViewHolder.cb3.setVisibility(View.VISIBLE);
                    myViewHolder.cb4.setVisibility(View.VISIBLE);

                    myViewHolder.cb1.setText(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                    myViewHolder.cb2.setText(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                    myViewHolder.cb3.setText(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                    myViewHolder.cb4.setText(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());

                    myViewHolder.cb1.setChecked(mMyBeanList.get(position).getChildren().get(0).isChecked1,false,false);
                    myViewHolder.cb1.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听

                                return;
                            }

                            mMyBeanList.get(position).getChildren().get(0).isChecked1 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());

                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(0).isChecked1){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }
                        }
                    });
                    myViewHolder.cb2.setChecked(mMyBeanList.get(position).getChildren().get(1).isChecked2,false,false);
                    myViewHolder.cb2.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            Log.d(TAG, "onCheckedChanged: buttonView:" + buttonView);
                            mMyBeanList.get(position).getChildren().get(1).isChecked2 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(1).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }

                            if(mMyBeanList.get(position).getChildren().get(1).isChecked2){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }
                        }
                    });

                    myViewHolder.cb3.setChecked(mMyBeanList.get(position).getChildren().get(2).isChecked3,false,false);

                    myViewHolder.cb3.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(2).isChecked3 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(2).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());

                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(2).isChecked3){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });

                    myViewHolder.cb4.setChecked(mMyBeanList.get(position).getChildren().get(3).isChecked4,false,false);

                    myViewHolder.cb4.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(3).isChecked4 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(3).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());

                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(3).isChecked4){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });


                    for(int i=0;i<saveAllqusId1.size();i++){
                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(0).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb1.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }

                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }
                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(1).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb2.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(1).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(2).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb3.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(2).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(3).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb4.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(3).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }
                    }



                    break;
                case 5:

                    myViewHolder.cb1.setVisibility(View.VISIBLE);
                    myViewHolder.cb2.setVisibility(View.VISIBLE);
                    myViewHolder.cb3.setVisibility(View.VISIBLE);
                    myViewHolder.cb4.setVisibility(View.VISIBLE);
                    myViewHolder.cb5.setVisibility(View.VISIBLE);

                    myViewHolder.cb1.setText(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                    myViewHolder.cb2.setText(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                    myViewHolder.cb3.setText(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                    myViewHolder.cb4.setText(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
                    myViewHolder.cb5.setText(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());

                    myViewHolder.cb1.setChecked(mMyBeanList.get(position).getChildren().get(0).isChecked1,false,false);
                    myViewHolder.cb1.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听

                                return;
                            }

                            mMyBeanList.get(position).getChildren().get(0).isChecked1 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());

                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(0).isChecked1){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }
                        }
                    });
                    myViewHolder.cb2.setChecked(mMyBeanList.get(position).getChildren().get(1).isChecked2,false,false);
                    myViewHolder.cb2.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            Log.d(TAG, "onCheckedChanged: buttonView:" + buttonView);
                            mMyBeanList.get(position).getChildren().get(1).isChecked2 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(1).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(1).isChecked2){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }
                        }
                    });

                    myViewHolder.cb3.setChecked(mMyBeanList.get(position).getChildren().get(2).isChecked3,false,false);

                    myViewHolder.cb3.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(2).isChecked3 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(2).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(2).isChecked3){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });

                    myViewHolder.cb4.setChecked(mMyBeanList.get(position).getChildren().get(3).isChecked4,false,false);

                    myViewHolder.cb4.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(3).isChecked4 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(3).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(3).isChecked4){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });

                    myViewHolder.cb5.setChecked(mMyBeanList.get(position).getChildren().get(4).isChecked5,false,false);

                    myViewHolder.cb5.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(4).isChecked5 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(4).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(4).isChecked5){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });


                    for(int i=0;i<saveAllqusId1.size();i++){
                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(0).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb1.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }

                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }
                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(1).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb2.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(1).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(2).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb3.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(2).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(3).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb4.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(3).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(4).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb5.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(4).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }
                    }




                    break;
                case 6:

                    myViewHolder.cb1.setVisibility(View.VISIBLE);
                    myViewHolder.cb2.setVisibility(View.VISIBLE);
                    myViewHolder.cb3.setVisibility(View.VISIBLE);
                    myViewHolder.cb4.setVisibility(View.VISIBLE);
                    myViewHolder.cb5.setVisibility(View.VISIBLE);
                    myViewHolder.cb6.setVisibility(View.VISIBLE);

                    myViewHolder.cb1.setText(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                    myViewHolder.cb2.setText(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                    myViewHolder.cb3.setText(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                    myViewHolder.cb4.setText(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
                    myViewHolder.cb5.setText(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());
                    myViewHolder.cb6.setText(mMyBeanList.get(position).getChildren().get(5).getWardRoundName());

                    myViewHolder.cb1.setChecked(mMyBeanList.get(position).getChildren().get(0).isChecked1,false,false);
                    myViewHolder.cb1.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听

                                return;
                            }

                            mMyBeanList.get(position).getChildren().get(0).isChecked1 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(0).isChecked1){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }
                        }
                    });
                    myViewHolder.cb2.setChecked(mMyBeanList.get(position).getChildren().get(1).isChecked2,false,false);
                    myViewHolder.cb2.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            Log.d(TAG, "onCheckedChanged: buttonView:" + buttonView);
                            mMyBeanList.get(position).getChildren().get(1).isChecked2 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(1).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(1).isChecked2){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }
                        }
                    });

                    myViewHolder.cb3.setChecked(mMyBeanList.get(position).getChildren().get(2).isChecked3,false,false);

                    myViewHolder.cb3.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(2).isChecked3 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(2).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(2).isChecked3){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });

                    myViewHolder.cb4.setChecked(mMyBeanList.get(position).getChildren().get(3).isChecked4,false,false);

                    myViewHolder.cb4.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(3).isChecked4 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(3).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(3).isChecked4){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });

                    myViewHolder.cb5.setChecked(mMyBeanList.get(position).getChildren().get(4).isChecked5,false,false);

                    myViewHolder.cb5.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(4).isChecked5 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(4).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(4).isChecked5){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });

                    myViewHolder.cb6.setChecked(mMyBeanList.get(position).getChildren().get(5).isChecked6,false,false);

                    myViewHolder.cb6.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(5).isChecked6 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(5).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(5).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(5).isChecked6){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });



                    for(int i=0;i<saveAllqusId1.size();i++){
                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(0).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb1.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }

                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }
                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(1).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb2.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(1).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(2).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb3.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(2).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(3).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb4.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(3).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(4).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb5.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(4).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(5).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb6.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(5).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(5).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }
                    }



                    break;
                case 7:

                    myViewHolder.cb1.setVisibility(View.VISIBLE);
                    myViewHolder.cb2.setVisibility(View.VISIBLE);
                    myViewHolder.cb3.setVisibility(View.VISIBLE);
                    myViewHolder.cb4.setVisibility(View.VISIBLE);
                    myViewHolder.cb5.setVisibility(View.VISIBLE);
                    myViewHolder.cb6.setVisibility(View.VISIBLE);
                    myViewHolder.cb7.setVisibility(View.VISIBLE);

                    myViewHolder.cb1.setText(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                    myViewHolder.cb2.setText(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                    myViewHolder.cb3.setText(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                    myViewHolder.cb4.setText(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
                    myViewHolder.cb5.setText(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());
                    myViewHolder.cb6.setText(mMyBeanList.get(position).getChildren().get(5).getWardRoundName());
                    myViewHolder.cb7.setText(mMyBeanList.get(position).getChildren().get(6).getWardRoundName());

                    myViewHolder.cb1.setChecked(mMyBeanList.get(position).getChildren().get(0).isChecked1,false,false);
                    myViewHolder.cb1.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听

                                return;
                            }

                            mMyBeanList.get(position).getChildren().get(0).isChecked1 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(0).isChecked1){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }
                        }
                    });
                    myViewHolder.cb2.setChecked(mMyBeanList.get(position).getChildren().get(1).isChecked2,false,false);
                    myViewHolder.cb2.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            Log.d(TAG, "onCheckedChanged: buttonView:" + buttonView);
                            mMyBeanList.get(position).getChildren().get(1).isChecked2 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(1).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(1).isChecked2){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }
                        }
                    });

                    myViewHolder.cb3.setChecked(mMyBeanList.get(position).getChildren().get(2).isChecked3,false,false);

                    myViewHolder.cb3.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(2).isChecked3 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(2).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(2).isChecked3){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });

                    myViewHolder.cb4.setChecked(mMyBeanList.get(position).getChildren().get(3).isChecked4,false,false);

                    myViewHolder.cb4.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(3).isChecked4 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(3).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(3).isChecked4){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });

                    myViewHolder.cb5.setChecked(mMyBeanList.get(position).getChildren().get(4).isChecked5,false,false);

                    myViewHolder.cb5.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(4).isChecked5 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(4).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(4).isChecked5){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });

                    myViewHolder.cb6.setChecked(mMyBeanList.get(position).getChildren().get(5).isChecked6,false,false);

                    myViewHolder.cb6.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(5).isChecked6 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(5).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(5).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(5).isChecked6){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });

                    myViewHolder.cb7.setChecked(mMyBeanList.get(position).getChildren().get(6).isChecked7,false,false);

                    myViewHolder.cb7.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(6).isChecked7 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(6).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(6).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(6).isChecked7){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });


                    for(int i=0;i<saveAllqusId1.size();i++){
                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(0).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb1.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }

                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }
                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(1).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb2.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(1).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(2).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb3.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(2).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(3).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb4.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(3).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(4).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb5.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(4).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(5).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb6.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(5).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(5).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(6).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb7.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(6).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(6).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }
                    }




                    break;
                case 8:

                    myViewHolder.cb1.setVisibility(View.VISIBLE);
                    myViewHolder.cb2.setVisibility(View.VISIBLE);
                    myViewHolder.cb3.setVisibility(View.VISIBLE);
                    myViewHolder.cb4.setVisibility(View.VISIBLE);
                    myViewHolder.cb5.setVisibility(View.VISIBLE);
                    myViewHolder.cb6.setVisibility(View.VISIBLE);
                    myViewHolder.cb7.setVisibility(View.VISIBLE);
                    myViewHolder.cb8.setVisibility(View.VISIBLE);

                    myViewHolder.cb1.setText(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                    myViewHolder.cb2.setText(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                    myViewHolder.cb3.setText(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                    myViewHolder.cb4.setText(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
                    myViewHolder.cb5.setText(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());
                    myViewHolder.cb6.setText(mMyBeanList.get(position).getChildren().get(5).getWardRoundName());
                    myViewHolder.cb7.setText(mMyBeanList.get(position).getChildren().get(6).getWardRoundName());
                    myViewHolder.cb8.setText(mMyBeanList.get(position).getChildren().get(7).getWardRoundName());

                    myViewHolder.cb1.setChecked(mMyBeanList.get(position).getChildren().get(0).isChecked1,false,false);
                    myViewHolder.cb1.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听

                                return;
                            }

                            mMyBeanList.get(position).getChildren().get(0).isChecked1 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(0).isChecked1){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }
                        }
                    });
                    myViewHolder.cb2.setChecked(mMyBeanList.get(position).getChildren().get(1).isChecked2,false,false);
                    myViewHolder.cb2.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            Log.d(TAG, "onCheckedChanged: buttonView:" + buttonView);
                            mMyBeanList.get(position).getChildren().get(1).isChecked2 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(1).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(1).isChecked2){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }
                        }
                    });

                    myViewHolder.cb3.setChecked(mMyBeanList.get(position).getChildren().get(2).isChecked3,false,false);

                    myViewHolder.cb3.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(2).isChecked3 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(2).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(2).isChecked3){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });

                    myViewHolder.cb4.setChecked(mMyBeanList.get(position).getChildren().get(3).isChecked4,false,false);

                    myViewHolder.cb4.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(3).isChecked4 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(3).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(3).isChecked4){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });

                    myViewHolder.cb5.setChecked(mMyBeanList.get(position).getChildren().get(4).isChecked5,false,false);

                    myViewHolder.cb5.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(4).isChecked5 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(4).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(4).isChecked5){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });

                    myViewHolder.cb6.setChecked(mMyBeanList.get(position).getChildren().get(5).isChecked6,false,false);

                    myViewHolder.cb6.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(5).isChecked6 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(5).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(5).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(5).isChecked6){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });

                    myViewHolder.cb7.setChecked(mMyBeanList.get(position).getChildren().get(6).isChecked7,false,false);

                    myViewHolder.cb7.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(6).isChecked7 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(6).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(6).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(6).isChecked7){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });

                    myViewHolder.cb8.setChecked(mMyBeanList.get(position).getChildren().get(7).isChecked8,false,false);

                    myViewHolder.cb8.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(7).isChecked8 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(7).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(7).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(7).isChecked8){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });



                    for(int i=0;i<saveAllqusId1.size();i++){
                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(0).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb1.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }

                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }
                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(1).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb2.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(1).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(2).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb3.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(2).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(3).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb4.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(3).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(4).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb5.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(4).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(5).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb6.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(5).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(5).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(6).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb7.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(6).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(6).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(7).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb8.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(7).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(7).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }
                    }

                    break;
                case 9:

                    myViewHolder.cb1.setVisibility(View.VISIBLE);
                    myViewHolder.cb2.setVisibility(View.VISIBLE);
                    myViewHolder.cb3.setVisibility(View.VISIBLE);
                    myViewHolder.cb4.setVisibility(View.VISIBLE);
                    myViewHolder.cb5.setVisibility(View.VISIBLE);
                    myViewHolder.cb6.setVisibility(View.VISIBLE);
                    myViewHolder.cb7.setVisibility(View.VISIBLE);
                    myViewHolder.cb8.setVisibility(View.VISIBLE);
                    myViewHolder.cb9.setVisibility(View.VISIBLE);

                    myViewHolder.cb1.setText(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                    myViewHolder.cb2.setText(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                    myViewHolder.cb3.setText(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                    myViewHolder.cb4.setText(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
                    myViewHolder.cb5.setText(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());
                    myViewHolder.cb6.setText(mMyBeanList.get(position).getChildren().get(5).getWardRoundName());
                    myViewHolder.cb7.setText(mMyBeanList.get(position).getChildren().get(6).getWardRoundName());
                    myViewHolder.cb8.setText(mMyBeanList.get(position).getChildren().get(7).getWardRoundName());
                    myViewHolder.cb9.setText(mMyBeanList.get(position).getChildren().get(8).getWardRoundName());

                    myViewHolder.cb1.setChecked(mMyBeanList.get(position).getChildren().get(0).isChecked1,false,false);
                    myViewHolder.cb1.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听

                                return;
                            }

                            mMyBeanList.get(position).getChildren().get(0).isChecked1 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(0).isChecked1){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }
                        }
                    });
                    myViewHolder.cb2.setChecked(mMyBeanList.get(position).getChildren().get(1).isChecked2,false,false);
                    myViewHolder.cb2.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            Log.d(TAG, "onCheckedChanged: buttonView:" + buttonView);
                            mMyBeanList.get(position).getChildren().get(1).isChecked2 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(1).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(1).isChecked2){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }
                        }
                    });

                    myViewHolder.cb3.setChecked(mMyBeanList.get(position).getChildren().get(2).isChecked3,false,false);

                    myViewHolder.cb3.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(2).isChecked3 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(2).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(2).isChecked3){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });

                    myViewHolder.cb4.setChecked(mMyBeanList.get(position).getChildren().get(3).isChecked4,false,false);

                    myViewHolder.cb4.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(3).isChecked4 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(3).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(3).isChecked4){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });

                    myViewHolder.cb5.setChecked(mMyBeanList.get(position).getChildren().get(4).isChecked5,false,false);

                    myViewHolder.cb5.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(4).isChecked5 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(4).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(4).isChecked5){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });

                    myViewHolder.cb6.setChecked(mMyBeanList.get(position).getChildren().get(5).isChecked6,false,false);

                    myViewHolder.cb6.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(5).isChecked6 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(5).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(5).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(5).isChecked6){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });

                    myViewHolder.cb7.setChecked(mMyBeanList.get(position).getChildren().get(6).isChecked7,false,false);

                    myViewHolder.cb7.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(6).isChecked7 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(6).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(6).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(6).isChecked7){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });

                    myViewHolder.cb8.setChecked(mMyBeanList.get(position).getChildren().get(7).isChecked8,false,false);

                    myViewHolder.cb8.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(7).isChecked8 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(7).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(7).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(7).isChecked8){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });

                    myViewHolder.cb9.setChecked(mMyBeanList.get(position).getChildren().get(8).isChecked9,false,false);

                    myViewHolder.cb9.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(8).isChecked9 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(8).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(8).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(8).isChecked9){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });


                    for(int i=0;i<saveAllqusId1.size();i++){
                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(0).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb1.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }

                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }
                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(1).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb2.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(1).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(2).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb3.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(2).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(3).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb4.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(3).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(4).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb5.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(4).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(5).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb6.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(5).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(5).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(6).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb7.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(6).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(6).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(7).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb8.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(7).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(7).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(8).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb9.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(8).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(8).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }
                    }



                    break;
                case 10:

                    myViewHolder.cb1.setVisibility(View.VISIBLE);
                    myViewHolder.cb2.setVisibility(View.VISIBLE);
                    myViewHolder.cb3.setVisibility(View.VISIBLE);
                    myViewHolder.cb4.setVisibility(View.VISIBLE);
                    myViewHolder.cb5.setVisibility(View.VISIBLE);
                    myViewHolder.cb6.setVisibility(View.VISIBLE);
                    myViewHolder.cb7.setVisibility(View.VISIBLE);
                    myViewHolder.cb8.setVisibility(View.VISIBLE);
                    myViewHolder.cb9.setVisibility(View.VISIBLE);
                    myViewHolder.cb10.setVisibility(View.VISIBLE);

                    myViewHolder.cb1.setText(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                    myViewHolder.cb2.setText(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                    myViewHolder.cb3.setText(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                    myViewHolder.cb4.setText(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
                    myViewHolder.cb5.setText(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());
                    myViewHolder.cb6.setText(mMyBeanList.get(position).getChildren().get(5).getWardRoundName());
                    myViewHolder.cb7.setText(mMyBeanList.get(position).getChildren().get(6).getWardRoundName());
                    myViewHolder.cb8.setText(mMyBeanList.get(position).getChildren().get(7).getWardRoundName());
                    myViewHolder.cb9.setText(mMyBeanList.get(position).getChildren().get(8).getWardRoundName());
                    myViewHolder.cb10.setText(mMyBeanList.get(position).getChildren().get(9).getWardRoundName());

                    myViewHolder.cb1.setChecked(mMyBeanList.get(position).getChildren().get(0).isChecked1,false,false);
                    myViewHolder.cb1.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听

                                return;
                            }

                            mMyBeanList.get(position).getChildren().get(0).isChecked1 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(0).isChecked1){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }
                        }
                    });
                    myViewHolder.cb2.setChecked(mMyBeanList.get(position).getChildren().get(1).isChecked2,false,false);
                    myViewHolder.cb2.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            Log.d(TAG, "onCheckedChanged: buttonView:" + buttonView);
                            mMyBeanList.get(position).getChildren().get(1).isChecked2 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(1).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(1).isChecked2){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }
                        }
                    });

                    myViewHolder.cb3.setChecked(mMyBeanList.get(position).getChildren().get(2).isChecked3,false,false);

                    myViewHolder.cb3.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(2).isChecked3 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(2).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(2).isChecked3){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });

                    myViewHolder.cb4.setChecked(mMyBeanList.get(position).getChildren().get(3).isChecked4,false,false);

                    myViewHolder.cb4.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(3).isChecked4 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(3).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(3).isChecked4){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });

                    myViewHolder.cb5.setChecked(mMyBeanList.get(position).getChildren().get(4).isChecked5,false,false);

                    myViewHolder.cb5.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(4).isChecked5 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(4).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(4).isChecked5){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });

                    myViewHolder.cb6.setChecked(mMyBeanList.get(position).getChildren().get(5).isChecked6,false,false);

                    myViewHolder.cb6.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(5).isChecked6 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(5).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(5).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(5).isChecked6){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });

                    myViewHolder.cb7.setChecked(mMyBeanList.get(position).getChildren().get(6).isChecked7,false,false);

                    myViewHolder.cb7.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(6).isChecked7 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(6).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(6).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(6).isChecked7){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });

                    myViewHolder.cb8.setChecked(mMyBeanList.get(position).getChildren().get(7).isChecked8,false,false);

                    myViewHolder.cb8.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(7).isChecked8 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(7).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(7).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(7).isChecked8){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });

                    myViewHolder.cb9.setChecked(mMyBeanList.get(position).getChildren().get(8).isChecked9,false,false);

                    myViewHolder.cb9.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(8).isChecked9 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(8).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(8).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(8).isChecked9){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });



                    myViewHolder.cb10.setChecked(mMyBeanList.get(position).getChildren().get(9).isChecked9,false,false);

                    myViewHolder.cb10.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothCompoundButton buttonView, boolean isChecked) {
                            if (buttonView == null || !buttonView.isPressed()) {
                                //防止setOnCheckedChangeListener循环监听
                                return;
                            }
                            mMyBeanList.get(position).getChildren().get(9).isChecked10 = isChecked;

                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(9).getWardRoundID());
                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(9).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            if(mMyBeanList.get(position).getChildren().get(9).isChecked10){
                                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                            }else {
                                ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                            }

                        }
                    });



                    for(int i=0;i<saveAllqusId1.size();i++){
                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(0).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb1.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }

                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }
                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(1).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb2.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(1).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(2).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb3.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(2).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(3).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb4.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(3).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(4).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb5.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(4).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(5).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb6.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(5).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(5).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(6).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb7.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(6).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(6).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(7).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb8.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(7).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(7).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(8).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb9.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(8).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(8).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(9).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.cb10.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(9).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(9).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }
                    }

                    break;

                default:

                    myViewHolder.cb1.setVisibility(View.GONE);
                    myViewHolder.cb2.setVisibility(View.GONE);
                    myViewHolder.cb3.setVisibility(View.GONE);
                    break;
            }
        }else if(mMyBeanList.get(position).getMultipleChoice().equals(SINGLECHOOSE)){
            myViewHolder.cb1.setVisibility(View.GONE);
            myViewHolder.cb2.setVisibility(View.GONE);
            myViewHolder.cb3.setVisibility(View.GONE);


            switch (mMyBeanList.get(position).getChildren().size()) {
                case 0:

                    myViewHolder.id_rb1.setVisibility(View.GONE);
                    myViewHolder.id_rb2.setVisibility(View.GONE);
                    myViewHolder.id_rb3.setVisibility(View.GONE);
                    myViewHolder.id_rb1.setChecked(false,false,false);
                    myViewHolder.id_rb2.setChecked(false,false,false);
                    myViewHolder.id_rb3.setChecked(false,false,false);
                    break;
                case 1:

                    myViewHolder.id_rg.setVisibility(View.VISIBLE);
                    myViewHolder.id_rb1.setVisibility(View.VISIBLE);
                    myViewHolder.id_rb2.setVisibility(View.GONE);
                    myViewHolder.id_rb3.setVisibility(View.GONE);
                    myViewHolder.id_rb1.setChecked(false,false,false);
                    myViewHolder.id_rb2.setChecked(false,false,false);
                    myViewHolder.id_rb3.setChecked(false,false,false);



                    myViewHolder.id_rb1.setText(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());

                    myViewHolder.id_rg.clearCheck();
                    if ("1".equals(mMyBeanList.get(position).getChildren().get(0).selectedRb)) {
                        myViewHolder.id_rg.check(R.id.id_rb1);
                    }

                    myViewHolder.id_rg.setOnCheckedChangeListener(new SmoothRadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothRadioGroup group, int checkedId) {
                            int myposition=0;
                            View checkedView = group.findViewById(checkedId);
                            if (checkedView == null || !checkedView.isPressed()) {
                                return;
                            }


                            mMyBeanList.get(position).getChildren().get(0).selectedRb = null;
                            if (R.id.id_rb1 == checkedId) {
                                myposition=0;
                                mMyBeanList.get(position).getChildren().get(0).selectedRb = "1";

                            }
                            for(int i=0;i<mMyBeanList.get(position).getChildren().size();i++){
                                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(i).getWardRoundID());
                                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(i).getWardRoundName());
                                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());

                                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                    saveRoundDataBean.setIsFillout("1");
                                }else {
                                    saveRoundDataBean.setIsFillout("0");
                                }
                                if(myposition==i){
                                    if(mMyBeanList.get(position).getChildren().get(myposition).selectedRb.equals("1")){
                                        ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                                    }
                                }else {
                                    ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                                }
                            }


                        }
                    });

                    for(int i=0;i<saveAllqusId1.size();i++){
                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(0).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.id_rb1.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }

                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                    }



                    break;
                case 2:

                    myViewHolder.id_rg.setVisibility(View.VISIBLE);
                    myViewHolder.id_rb1.setVisibility(View.VISIBLE);
                    myViewHolder.id_rb2.setVisibility(View.VISIBLE);
                    myViewHolder.id_rb3.setVisibility(View.GONE);
                    myViewHolder.id_rb1.setChecked(false,false,false);
                    myViewHolder.id_rb2.setChecked(false,false,false);
                    myViewHolder.id_rb3.setChecked(false,false,false);



                    myViewHolder.id_rb1.setText(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                    myViewHolder.id_rb2.setText(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());

                    myViewHolder.id_rg.clearCheck();
                    if ("1".equals(mMyBeanList.get(position).getChildren().get(0).selectedRb)) {
                        myViewHolder.id_rg.check(R.id.id_rb1);
                    } else if ("2".equals(mMyBeanList.get(position).getChildren().get(0).selectedRb)) {
                        myViewHolder.id_rg.check(R.id.id_rb2);
                    }
                    myViewHolder.id_rg.setOnCheckedChangeListener(new SmoothRadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothRadioGroup group, int checkedId) {
                            int myposition=0;
                            View checkedView = group.findViewById(checkedId);
                            if (checkedView == null || !checkedView.isPressed()) {
                                return;
                            }
                            Log.d(TAG, "onCheckedChanged: checkedId:" + checkedId);

                            mMyBeanList.get(position).getChildren().get(0).selectedRb = null;
                            if (R.id.id_rb1 == checkedId) {
                                myposition=0;
                                mMyBeanList.get(position).getChildren().get(0).selectedRb = "1";
                                mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                            } else if (R.id.id_rb2 == checkedId) {
                                myposition=1;
                                mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(1).selectedRb = "1";

                            }

                            for(int i=0;i<mMyBeanList.get(position).getChildren().size();i++){
                                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(i).getWardRoundID());
                                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(i).getWardRoundName());
                                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());

                                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                    saveRoundDataBean.setIsFillout("1");
                                }else {
                                    saveRoundDataBean.setIsFillout("0");
                                }
                                if(myposition==i){
                                    if(mMyBeanList.get(position).getChildren().get(myposition).selectedRb.equals("1")){
                                        ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                                    }
                                }else {
                                    ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                                }
                            }


                        }
                    });

                    for(int i=0;i<saveAllqusId1.size();i++){
                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(0).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.id_rb1.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }

                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }
                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(1).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.id_rb2.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(1).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }
                    }

                    break;
                case 3:
                    myViewHolder.id_rb1.setVisibility(View.VISIBLE);
                    myViewHolder.id_rb2.setVisibility(View.VISIBLE);
                    myViewHolder.id_rb3.setVisibility(View.VISIBLE);

                    myViewHolder.id_rb1.setText(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                    myViewHolder.id_rb2.setText(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                    myViewHolder.id_rb3.setText(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());

                    myViewHolder.id_rg.clearCheck();
                    if ("1".equals(mMyBeanList.get(position).getChildren().get(0).selectedRb)) {
                        myViewHolder.id_rg.check(R.id.id_rb1);
                    } else if ("1".equals(mMyBeanList.get(position).getChildren().get(1).selectedRb)) {
                        myViewHolder.id_rg.check(R.id.id_rb2);
                    }else if ("1".equals(mMyBeanList.get(position).getChildren().get(2).selectedRb)) {
                        myViewHolder.id_rg.check(R.id.id_rb3);
                    }

                    myViewHolder.id_rg.setOnCheckedChangeListener(new SmoothRadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothRadioGroup group, int checkedId) {
                            View checkedView = group.findViewById(checkedId);
                            int myposition=0;
                            if (checkedView == null || !checkedView.isPressed()) {
                                return;
                            }

                            mMyBeanList.get(position).getChildren().get(0).selectedRb = null;
                            if (R.id.id_rb1 == checkedId) {
                                myposition=0;
                                mMyBeanList.get(position).getChildren().get(0).selectedRb = "1";
                                mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                            } else if (R.id.id_rb2 == checkedId) {
                                myposition=1;
                                mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(1).selectedRb = "1";
                                mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                            }else if (R.id.id_rb3 == checkedId) {
                                myposition=2;
                                mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(2).selectedRb = "1";
                            }

                            for(int i=0;i<mMyBeanList.get(position).getChildren().size();i++){
                                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(i).getWardRoundID());
                                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(i).getWardRoundName());
                                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                    saveRoundDataBean.setIsFillout("1");
                                }else {
                                    saveRoundDataBean.setIsFillout("0");
                                }
                                if(myposition==i){
                                    if(mMyBeanList.get(position).getChildren().get(myposition).selectedRb.equals("1")){
                                        ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                                    }
                                }else {
                                    ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                                }
                            }

                        }
                    });

                    for(int i=0;i<saveAllqusId1.size();i++){
                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(0).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.id_rb1.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }

                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }
                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(1).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.id_rb2.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(1).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(2).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.id_rb3.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(2).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }
                    }


                    break;
                case 4:

                    myViewHolder.id_rb1.setVisibility(View.VISIBLE);
                    myViewHolder.id_rb2.setVisibility(View.VISIBLE);
                    myViewHolder.id_rb3.setVisibility(View.VISIBLE);
                    myViewHolder.id_rb4.setVisibility(View.VISIBLE);

                    myViewHolder.id_rb1.setText(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                    myViewHolder.id_rb2.setText(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                    myViewHolder.id_rb3.setText(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                    myViewHolder.id_rb4.setText(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());

                    myViewHolder.id_rg.clearCheck();
                    if ("1".equals(mMyBeanList.get(position).getChildren().get(0).selectedRb)) {
                        myViewHolder.id_rg.check(R.id.id_rb1);
                    } else if ("1".equals(mMyBeanList.get(position).getChildren().get(1).selectedRb)) {
                        myViewHolder.id_rg.check(R.id.id_rb2);
                    }else if ("1".equals(mMyBeanList.get(position).getChildren().get(2).selectedRb)) {
                        myViewHolder.id_rg.check(R.id.id_rb3);
                    }else if ("1".equals(mMyBeanList.get(position).getChildren().get(3).selectedRb)) {
                        myViewHolder.id_rg.check(R.id.id_rb4);
                    }

                    myViewHolder.id_rg.setOnCheckedChangeListener(new SmoothRadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothRadioGroup group, int checkedId) {
                            View checkedView = group.findViewById(checkedId);
                            int myposition=0;
                            if (checkedView == null || !checkedView.isPressed()) {
                                return;
                            }

                            mMyBeanList.get(position).getChildren().get(0).selectedRb = null;
                            if (R.id.id_rb1 == checkedId) {
                                myposition=0;
                                mMyBeanList.get(position).getChildren().get(0).selectedRb = "1";
                                mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                            } else if (R.id.id_rb2 == checkedId) {
                                myposition=1;
                                mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(1).selectedRb = "1";
                                mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                            }else if (R.id.id_rb3 == checkedId) {
                                myposition=2;
                                mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(2).selectedRb = "1";
                                mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                            }else if (R.id.id_rb4 == checkedId) {
                                myposition=3;
                                mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(3).selectedRb = "1";
                            }

                            for(int i=0;i<mMyBeanList.get(position).getChildren().size();i++){
                                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(i).getWardRoundID());
                                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(i).getWardRoundName());
                                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                    saveRoundDataBean.setIsFillout("1");
                                }else {
                                    saveRoundDataBean.setIsFillout("0");
                                }
                                if(myposition==i){
                                    if(mMyBeanList.get(position).getChildren().get(myposition).selectedRb.equals("1")){
                                        ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                                    }
                                }else {
                                    ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                                }
                            }

                        }
                    });

                    for(int i=0;i<saveAllqusId1.size();i++){
                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(0).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.id_rb1.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }

                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }
                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(1).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.id_rb2.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(1).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(2).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.id_rb3.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(2).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(3).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.id_rb4.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(3).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }
                    }


                    break;
                case 5:

                    myViewHolder.id_rb1.setVisibility(View.VISIBLE);
                    myViewHolder.id_rb2.setVisibility(View.VISIBLE);
                    myViewHolder.id_rb3.setVisibility(View.VISIBLE);
                    myViewHolder.id_rb4.setVisibility(View.VISIBLE);
                    myViewHolder.id_rb5.setVisibility(View.VISIBLE);

                    myViewHolder.id_rb1.setText(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                    myViewHolder.id_rb2.setText(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                    myViewHolder.id_rb3.setText(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                    myViewHolder.id_rb4.setText(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
                    myViewHolder.id_rb5.setText(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());

                    myViewHolder.id_rg.clearCheck();
                    if ("1".equals(mMyBeanList.get(position).getChildren().get(0).selectedRb)) {
                        myViewHolder.id_rg.check(R.id.id_rb1);
                    } else if ("1".equals(mMyBeanList.get(position).getChildren().get(1).selectedRb)) {
                        myViewHolder.id_rg.check(R.id.id_rb2);
                    }else if ("1".equals(mMyBeanList.get(position).getChildren().get(2).selectedRb)) {
                        myViewHolder.id_rg.check(R.id.id_rb3);
                    }else if ("1".equals(mMyBeanList.get(position).getChildren().get(3).selectedRb)) {
                        myViewHolder.id_rg.check(R.id.id_rb4);
                    }else if ("1".equals(mMyBeanList.get(position).getChildren().get(4).selectedRb)) {
                        myViewHolder.id_rg.check(R.id.id_rb5);
                    }

                    myViewHolder.id_rg.setOnCheckedChangeListener(new SmoothRadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(SmoothRadioGroup group, int checkedId) {
                            View checkedView = group.findViewById(checkedId);
                            int myposition=0;
                            if (checkedView == null || !checkedView.isPressed()) {
                                return;
                            }

                            mMyBeanList.get(position).getChildren().get(0).selectedRb = null;
                            if (R.id.id_rb1 == checkedId) {
                                myposition=0;
                                mMyBeanList.get(position).getChildren().get(0).selectedRb = "1";
                                mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                            } else if (R.id.id_rb2 == checkedId) {
                                myposition=1;
                                mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(1).selectedRb = "1";
                                mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                            }else if (R.id.id_rb3 == checkedId) {
                                myposition=2;
                                mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(2).selectedRb = "1";
                                mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                            }else if (R.id.id_rb4 == checkedId) {
                                myposition=3;
                                mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(3).selectedRb = "1";
                                mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                            }else if (R.id.id_rb5 == checkedId) {
                                myposition=4;
                                mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                                mMyBeanList.get(position).getChildren().get(4).selectedRb = "1";
                            }


                            for(int i=0;i<mMyBeanList.get(position).getChildren().size();i++){
                                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(i).getWardRoundID());
                                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(i).getWardRoundName());
                                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                    saveRoundDataBean.setIsFillout("1");
                                }else {
                                    saveRoundDataBean.setIsFillout("0");
                                }
                                if(myposition==i){
                                    if(mMyBeanList.get(position).getChildren().get(myposition).selectedRb.equals("1")){
                                        ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                                    }
                                }else {
                                    ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                                }
                            }

                        }
                    });


                    for(int i=0;i<saveAllqusId1.size();i++){
                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(0).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.id_rb1.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }

                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }
                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(1).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.id_rb2.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(1).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(2).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.id_rb3.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(2).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(3).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.id_rb4.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(3).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }

                        if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(4).getWardRoundID())
                                && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                            myViewHolder.id_rb5.setChecked(true);
                            SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                            saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                            saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                            saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(4).getWardRoundID());

                            saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());
                            saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                            if(mMyBeanList.get(position).getIsFillout().equals("1")){
                                saveRoundDataBean.setIsFillout("1");
                            }else {
                                saveRoundDataBean.setIsFillout("0");
                            }
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

                        }
                    }


                    break;
                case 6:
                    singleOption6(myViewHolder,position);
                    break;
                case 7:
                    singleOption7(myViewHolder,position);
                    break;
                case 8:
                    singleOption8(myViewHolder,position);
                    break;
                case 9:
                    singleOption9(myViewHolder,position);
                    break;
                case 10:
                    singleOption10(myViewHolder,position);
                    break;
                default:
                    myViewHolder.cb1.setVisibility(View.GONE);
                    myViewHolder.cb2.setVisibility(View.GONE);
                    myViewHolder.cb3.setVisibility(View.GONE);
       //             myViewHolder.id_rg.setVisibility(View.GONE);
//                    myViewHolder.ll_muilt.setVisibility(View.GONE);

                    myViewHolder.id_rb1.setVisibility(View.GONE);
                    myViewHolder.id_rb2.setVisibility(View.GONE);
                    myViewHolder.id_rb3.setVisibility(View.GONE);
                    break;
            }

        }




        //  CheckBox
        return convertView;
    }

    class MyViewHolder {
        private TextView tv_title;
        private TextView star;

        private SmoothCheckBox cb1;
        private SmoothCheckBox cb2;
        private SmoothCheckBox cb3;
        private SmoothCheckBox cb4;
        private SmoothCheckBox cb5;
        private SmoothCheckBox cb6;
        private SmoothCheckBox cb7;
        private SmoothCheckBox cb8;
        private SmoothCheckBox cb9;
        private SmoothCheckBox cb10;

        private SmoothRadioGroup id_rg;
        private SmoothRadioButton id_rb1;
        private SmoothRadioButton id_rb2;
        private SmoothRadioButton id_rb3;
        private SmoothRadioButton id_rb4;
        private SmoothRadioButton id_rb5;
        private SmoothRadioButton id_rb6;
        private SmoothRadioButton id_rb7;
        private SmoothRadioButton id_rb8;
        private SmoothRadioButton id_rb9;
        private SmoothRadioButton id_rb10;

    }

    public void singleOption6(MyViewHolder myViewHolder, final int position){
        myViewHolder.id_rb1.setVisibility(View.VISIBLE);
        myViewHolder.id_rb2.setVisibility(View.VISIBLE);
        myViewHolder.id_rb3.setVisibility(View.VISIBLE);
        myViewHolder.id_rb4.setVisibility(View.VISIBLE);
        myViewHolder.id_rb5.setVisibility(View.VISIBLE);
        myViewHolder.id_rb6.setVisibility(View.VISIBLE);

        myViewHolder.id_rb1.setText(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
        myViewHolder.id_rb2.setText(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
        myViewHolder.id_rb3.setText(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
        myViewHolder.id_rb4.setText(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
        myViewHolder.id_rb5.setText(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());
        myViewHolder.id_rb6.setText(mMyBeanList.get(position).getChildren().get(5).getWardRoundName());

        myViewHolder.id_rg.clearCheck();
        if ("1".equals(mMyBeanList.get(position).getChildren().get(0).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb1);
        } else if ("1".equals(mMyBeanList.get(position).getChildren().get(1).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb2);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(2).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb3);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(3).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb4);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(4).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb5);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(5).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb6);
        }

        myViewHolder.id_rg.setOnCheckedChangeListener(new SmoothRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothRadioGroup group, int checkedId) {
                View checkedView = group.findViewById(checkedId);
                int myposition=0;
                if (checkedView == null || !checkedView.isPressed()) {
                    return;
                }

                mMyBeanList.get(position).getChildren().get(0).selectedRb = null;
                if (R.id.id_rb1 == checkedId) {
                    myposition=0;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                } else if (R.id.id_rb2 == checkedId) {
                    myposition=1;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                }else if (R.id.id_rb3 == checkedId) {
                    myposition=2;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                }else if (R.id.id_rb4 == checkedId) {
                    myposition=3;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                }else if (R.id.id_rb5 == checkedId) {
                    myposition=4;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                }else if (R.id.id_rb6 == checkedId) {
                    myposition=5;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "1";
                }


                for(int i=0;i<mMyBeanList.get(position).getChildren().size();i++){
                    SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                    saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                    saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                    saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(i).getWardRoundID());
                    saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(i).getWardRoundName());
                    saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                    if(mMyBeanList.get(position).getIsFillout().equals("1")){
                        saveRoundDataBean.setIsFillout("1");
                    }else {
                        saveRoundDataBean.setIsFillout("0");
                    }
                    if(myposition==i){
                        if(mMyBeanList.get(position).getChildren().get(myposition).selectedRb.equals("1")){
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                        }
                    }else {
                        ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                    }
                }

            }
        });


        for(int i=0;i<saveAllqusId1.size();i++){
            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(0).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb1.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }

                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }
            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(1).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb2.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(1).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(2).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb3.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(2).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(3).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb4.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(3).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(4).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb5.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(4).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(5).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb6.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(5).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(5).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }
        }
    }

    public void singleOption7(MyViewHolder myViewHolder, final int position){
        myViewHolder.id_rb1.setVisibility(View.VISIBLE);
        myViewHolder.id_rb2.setVisibility(View.VISIBLE);
        myViewHolder.id_rb3.setVisibility(View.VISIBLE);
        myViewHolder.id_rb4.setVisibility(View.VISIBLE);
        myViewHolder.id_rb5.setVisibility(View.VISIBLE);
        myViewHolder.id_rb6.setVisibility(View.VISIBLE);
        myViewHolder.id_rb7.setVisibility(View.VISIBLE);

        myViewHolder.id_rb1.setText(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
        myViewHolder.id_rb2.setText(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
        myViewHolder.id_rb3.setText(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
        myViewHolder.id_rb4.setText(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
        myViewHolder.id_rb5.setText(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());
        myViewHolder.id_rb6.setText(mMyBeanList.get(position).getChildren().get(5).getWardRoundName());
        myViewHolder.id_rb7.setText(mMyBeanList.get(position).getChildren().get(6).getWardRoundName());

        myViewHolder.id_rg.clearCheck();
        if ("1".equals(mMyBeanList.get(position).getChildren().get(0).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb1);
        } else if ("1".equals(mMyBeanList.get(position).getChildren().get(1).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb2);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(2).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb3);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(3).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb4);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(4).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb5);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(5).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb6);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(6).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb7);
        }

        myViewHolder.id_rg.setOnCheckedChangeListener(new SmoothRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothRadioGroup group, int checkedId) {
                View checkedView = group.findViewById(checkedId);
                int myposition=0;
                if (checkedView == null || !checkedView.isPressed()) {
                    return;
                }

                mMyBeanList.get(position).getChildren().get(0).selectedRb = null;
                if (R.id.id_rb1 == checkedId) {
                    myposition=0;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                } else if (R.id.id_rb2 == checkedId) {
                    myposition=1;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                }else if (R.id.id_rb3 == checkedId) {
                    myposition=2;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                }else if (R.id.id_rb4 == checkedId) {
                    myposition=3;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                }else if (R.id.id_rb5 == checkedId) {
                    myposition=4;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                }else if (R.id.id_rb6 == checkedId) {
                    myposition=5;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                }else if (R.id.id_rb7 == checkedId) {
                    myposition=6;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "1";
                }


                for(int i=0;i<mMyBeanList.get(position).getChildren().size();i++){
                    SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                    saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                    saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                    saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(i).getWardRoundID());
                    saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(i).getWardRoundName());
                    saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                    if(mMyBeanList.get(position).getIsFillout().equals("1")){
                        saveRoundDataBean.setIsFillout("1");
                    }else {
                        saveRoundDataBean.setIsFillout("0");
                    }
                    if(myposition==i){
                        if(mMyBeanList.get(position).getChildren().get(myposition).selectedRb.equals("1")){
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                        }
                    }else {
                        ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                    }
                }

            }
        });

        for(int i=0;i<saveAllqusId1.size();i++){
            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(0).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb1.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }

                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }
            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(1).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb2.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(1).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(2).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb3.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(2).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(3).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb4.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(3).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(4).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb5.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(4).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(5).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb6.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(5).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(5).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(6).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb7.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(6).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(6).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }
        }
    }


    public void singleOption8(MyViewHolder myViewHolder, final int position){
        myViewHolder.id_rb1.setVisibility(View.VISIBLE);
        myViewHolder.id_rb2.setVisibility(View.VISIBLE);
        myViewHolder.id_rb3.setVisibility(View.VISIBLE);
        myViewHolder.id_rb4.setVisibility(View.VISIBLE);
        myViewHolder.id_rb5.setVisibility(View.VISIBLE);
        myViewHolder.id_rb6.setVisibility(View.VISIBLE);
        myViewHolder.id_rb7.setVisibility(View.VISIBLE);
        myViewHolder.id_rb8.setVisibility(View.VISIBLE);

        myViewHolder.id_rb1.setText(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
        myViewHolder.id_rb2.setText(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
        myViewHolder.id_rb3.setText(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
        myViewHolder.id_rb4.setText(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
        myViewHolder.id_rb5.setText(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());
        myViewHolder.id_rb6.setText(mMyBeanList.get(position).getChildren().get(5).getWardRoundName());
        myViewHolder.id_rb7.setText(mMyBeanList.get(position).getChildren().get(6).getWardRoundName());
        myViewHolder.id_rb8.setText(mMyBeanList.get(position).getChildren().get(7).getWardRoundName());

        myViewHolder.id_rg.clearCheck();
        if ("1".equals(mMyBeanList.get(position).getChildren().get(0).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb1);
        } else if ("1".equals(mMyBeanList.get(position).getChildren().get(1).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb2);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(2).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb3);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(3).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb4);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(4).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb5);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(5).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb6);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(6).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb7);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(7).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb8);
        }

        myViewHolder.id_rg.setOnCheckedChangeListener(new SmoothRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothRadioGroup group, int checkedId) {
                View checkedView = group.findViewById(checkedId);
                int myposition=0;
                if (checkedView == null || !checkedView.isPressed()) {
                    return;
                }

                mMyBeanList.get(position).getChildren().get(0).selectedRb = null;
                if (R.id.id_rb1 == checkedId) {
                    myposition=0;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(7).selectedRb = "2";
                } else if (R.id.id_rb2 == checkedId) {
                    myposition=1;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(7).selectedRb = "2";
                }else if (R.id.id_rb3 == checkedId) {
                    myposition=2;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(7).selectedRb = "2";
                }else if (R.id.id_rb4 == checkedId) {
                    myposition=3;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(7).selectedRb = "2";
                }else if (R.id.id_rb5 == checkedId) {
                    myposition=4;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(7).selectedRb = "2";
                }else if (R.id.id_rb6 == checkedId) {
                    myposition=5;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(7).selectedRb = "2";
                }else if (R.id.id_rb7 == checkedId) {
                    myposition=6;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(7).selectedRb = "2";
                }else if (R.id.id_rb8 == checkedId) {
                    myposition=7;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(7).selectedRb = "1";
                }


                for(int i=0;i<mMyBeanList.get(position).getChildren().size();i++){
                    SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                    saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                    saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                    saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(i).getWardRoundID());
                    saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(i).getWardRoundName());
                    saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                    if(mMyBeanList.get(position).getIsFillout().equals("1")){
                        saveRoundDataBean.setIsFillout("1");
                    }else {
                        saveRoundDataBean.setIsFillout("0");
                    }
                    if(myposition==i){
                        if(mMyBeanList.get(position).getChildren().get(myposition).selectedRb.equals("1")){
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                        }
                    }else {
                        ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                    }
                }

            }
        });

        for(int i=0;i<saveAllqusId1.size();i++){
            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(0).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb1.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }

                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }
            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(1).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb2.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(1).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(2).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb3.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(2).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(3).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb4.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(3).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(4).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb5.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(4).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(5).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb6.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(5).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(5).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(6).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb7.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(6).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(6).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(7).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb8.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(7).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(7).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }
        }
    }

    public void singleOption9(MyViewHolder myViewHolder, final int position){
        myViewHolder.id_rb1.setVisibility(View.VISIBLE);
        myViewHolder.id_rb2.setVisibility(View.VISIBLE);
        myViewHolder.id_rb3.setVisibility(View.VISIBLE);
        myViewHolder.id_rb4.setVisibility(View.VISIBLE);
        myViewHolder.id_rb5.setVisibility(View.VISIBLE);
        myViewHolder.id_rb6.setVisibility(View.VISIBLE);
        myViewHolder.id_rb7.setVisibility(View.VISIBLE);
        myViewHolder.id_rb8.setVisibility(View.VISIBLE);
        myViewHolder.id_rb9.setVisibility(View.VISIBLE);

        myViewHolder.id_rb1.setText(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
        myViewHolder.id_rb2.setText(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
        myViewHolder.id_rb3.setText(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
        myViewHolder.id_rb4.setText(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
        myViewHolder.id_rb5.setText(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());
        myViewHolder.id_rb6.setText(mMyBeanList.get(position).getChildren().get(5).getWardRoundName());
        myViewHolder.id_rb7.setText(mMyBeanList.get(position).getChildren().get(6).getWardRoundName());
        myViewHolder.id_rb8.setText(mMyBeanList.get(position).getChildren().get(7).getWardRoundName());
        myViewHolder.id_rb9.setText(mMyBeanList.get(position).getChildren().get(8).getWardRoundName());

        myViewHolder.id_rg.clearCheck();
        if ("1".equals(mMyBeanList.get(position).getChildren().get(0).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb1);
        } else if ("1".equals(mMyBeanList.get(position).getChildren().get(1).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb2);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(2).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb3);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(3).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb4);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(4).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb5);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(5).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb6);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(6).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb7);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(7).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb8);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(8).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb9);
        }

        myViewHolder.id_rg.setOnCheckedChangeListener(new SmoothRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothRadioGroup group, int checkedId) {
                View checkedView = group.findViewById(checkedId);
                int myposition=0;
                if (checkedView == null || !checkedView.isPressed()) {
                    return;
                }

                mMyBeanList.get(position).getChildren().get(0).selectedRb = null;
                if (R.id.id_rb1 == checkedId) {
                    myposition=0;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(7).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(8).selectedRb = "2";
                } else if (R.id.id_rb2 == checkedId) {
                    myposition=1;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(7).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(8).selectedRb = "2";
                }else if (R.id.id_rb3 == checkedId) {
                    myposition=2;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(7).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(8).selectedRb = "2";
                }else if (R.id.id_rb4 == checkedId) {
                    myposition=3;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(7).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(8).selectedRb = "2";
                }else if (R.id.id_rb5 == checkedId) {
                    myposition=4;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(7).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(8).selectedRb = "2";
                }else if (R.id.id_rb6 == checkedId) {
                    myposition=5;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(7).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(8).selectedRb = "2";
                }else if (R.id.id_rb7 == checkedId) {
                    myposition=6;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(7).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(8).selectedRb = "2";
                }else if (R.id.id_rb8 == checkedId) {
                    myposition=7;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(7).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(8).selectedRb = "2";
                }else if (R.id.id_rb9 == checkedId) {
                    myposition=8;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(7).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(8).selectedRb = "1";

                }



                for(int i=0;i<mMyBeanList.get(position).getChildren().size();i++){
                    SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                    saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                    saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                    saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(i).getWardRoundID());
                    saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(i).getWardRoundName());
                    saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                    if(mMyBeanList.get(position).getIsFillout().equals("1")){
                        saveRoundDataBean.setIsFillout("1");
                    }else {
                        saveRoundDataBean.setIsFillout("0");
                    }
                    if(myposition==i){
                        if(mMyBeanList.get(position).getChildren().get(myposition).selectedRb.equals("1")){
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                        }
                    }else {
                        ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                    }
                }

            }
        });


        for(int i=0;i<saveAllqusId1.size();i++){
            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(0).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb1.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }

                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }
            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(1).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb2.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(1).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(2).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb3.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(2).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(3).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb4.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(3).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(4).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb5.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(4).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(5).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb6.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(5).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(5).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(6).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb7.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(6).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(6).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(7).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb8.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(7).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(7).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(8).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb9.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(8).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(8).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }
        }
    }


    public void singleOption10(MyViewHolder myViewHolder, final int position){
        myViewHolder.id_rb1.setVisibility(View.VISIBLE);
        myViewHolder.id_rb2.setVisibility(View.VISIBLE);
        myViewHolder.id_rb3.setVisibility(View.VISIBLE);
        myViewHolder.id_rb4.setVisibility(View.VISIBLE);
        myViewHolder.id_rb5.setVisibility(View.VISIBLE);
        myViewHolder.id_rb6.setVisibility(View.VISIBLE);
        myViewHolder.id_rb7.setVisibility(View.VISIBLE);
        myViewHolder.id_rb8.setVisibility(View.VISIBLE);
        myViewHolder.id_rb9.setVisibility(View.VISIBLE);
        myViewHolder.id_rb10.setVisibility(View.VISIBLE);

        myViewHolder.id_rb1.setText(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
        myViewHolder.id_rb2.setText(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
        myViewHolder.id_rb3.setText(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
        myViewHolder.id_rb4.setText(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
        myViewHolder.id_rb5.setText(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());
        myViewHolder.id_rb6.setText(mMyBeanList.get(position).getChildren().get(5).getWardRoundName());
        myViewHolder.id_rb7.setText(mMyBeanList.get(position).getChildren().get(6).getWardRoundName());
        myViewHolder.id_rb8.setText(mMyBeanList.get(position).getChildren().get(7).getWardRoundName());
        myViewHolder.id_rb9.setText(mMyBeanList.get(position).getChildren().get(8).getWardRoundName());
        myViewHolder.id_rb10.setText(mMyBeanList.get(position).getChildren().get(9).getWardRoundName());

        myViewHolder.id_rg.clearCheck();
        if ("1".equals(mMyBeanList.get(position).getChildren().get(0).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb1);
        } else if ("1".equals(mMyBeanList.get(position).getChildren().get(1).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb2);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(2).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb3);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(3).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb4);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(4).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb5);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(5).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb6);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(6).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb7);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(7).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb8);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(8).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb9);
        }else if ("1".equals(mMyBeanList.get(position).getChildren().get(9).selectedRb)) {
            myViewHolder.id_rg.check(R.id.id_rb10);
        }

        myViewHolder.id_rg.setOnCheckedChangeListener(new SmoothRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothRadioGroup group, int checkedId) {
                View checkedView = group.findViewById(checkedId);
                int myposition=0;
                if (checkedView == null || !checkedView.isPressed()) {
                    return;
                }

                mMyBeanList.get(position).getChildren().get(0).selectedRb = null;
                if (R.id.id_rb1 == checkedId) {
                    myposition=0;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(7).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(8).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(9).selectedRb = "2";
                } else if (R.id.id_rb2 == checkedId) {
                    myposition=1;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(7).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(8).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(9).selectedRb = "2";
                }else if (R.id.id_rb3 == checkedId) {
                    myposition=2;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(7).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(8).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(9).selectedRb = "2";
                }else if (R.id.id_rb4 == checkedId) {
                    myposition=3;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(7).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(8).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(9).selectedRb = "2";
                }else if (R.id.id_rb5 == checkedId) {
                    myposition=4;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(7).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(8).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(9).selectedRb = "2";
                }else if (R.id.id_rb6 == checkedId) {
                    myposition=5;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(7).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(8).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(9).selectedRb = "2";
                }else if (R.id.id_rb7 == checkedId) {
                    myposition=6;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(7).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(8).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(9).selectedRb = "2";
                }else if (R.id.id_rb8 == checkedId) {
                    myposition=7;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(7).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(8).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(9).selectedRb = "2";
                }else if (R.id.id_rb9 == checkedId) {
                    myposition=8;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(7).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(8).selectedRb = "1";
                    mMyBeanList.get(position).getChildren().get(9).selectedRb = "2";

                }else if (R.id.id_rb10 == checkedId) {
                    myposition=9;
                    mMyBeanList.get(position).getChildren().get(0).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(1).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(2).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(3).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(4).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(5).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(6).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(7).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(8).selectedRb = "2";
                    mMyBeanList.get(position).getChildren().get(9).selectedRb = "1";

                }



                for(int i=0;i<mMyBeanList.get(position).getChildren().size();i++){
                    SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                    saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                    saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                    saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(i).getWardRoundID());
                    saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(i).getWardRoundName());
                    saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                    if(mMyBeanList.get(position).getIsFillout().equals("1")){
                        saveRoundDataBean.setIsFillout("1");
                    }else {
                        saveRoundDataBean.setIsFillout("0");
                    }
                    if(myposition==i){
                        if(mMyBeanList.get(position).getChildren().get(myposition).selectedRb.equals("1")){
                            ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
                        }
                    }else {
                        ((ElderItemActivity) context).removeMuData(saveRoundDataBean);
                    }
                }

            }
        });

        for(int i=0;i<saveAllqusId1.size();i++){
            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(0).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb1.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(0).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(0).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }

                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }
            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(1).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb2.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(1).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(1).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(2).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb3.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(2).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(2).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(3).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb4.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(3).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(3).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(4).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb5.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(4).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(4).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(5).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb6.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(5).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(5).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(6).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb7.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(6).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(6).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(7).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb8.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(7).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(7).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(8).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb9.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(8).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(8).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }

            if(saveAllqusId1.get(i).getWardOptionID().contains(mMyBeanList.get(position).getChildren().get(9).getWardRoundID())
                    && saveAllqusId1.get(i).getfKElderInfoID().equals(myfKElderInfoID)){
                myViewHolder.id_rb10.setChecked(true);
                SaveRoundDataBean saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID(myfKElderInfoID);
                saveRoundDataBean.setWardItemID(mMyBeanList.get(position).getWardRoundID());
                saveRoundDataBean.setWardOptionID(mMyBeanList.get(position).getChildren().get(9).getWardRoundID());

                saveRoundDataBean.setWardOptionName(mMyBeanList.get(position).getChildren().get(9).getWardRoundName());
                saveRoundDataBean.setWardItemName(mMyBeanList.get(position).getWardRoundName());
                if(mMyBeanList.get(position).getIsFillout().equals("1")){
                    saveRoundDataBean.setIsFillout("1");
                }else {
                    saveRoundDataBean.setIsFillout("0");
                }
                ((ElderItemActivity) context).saveMUData(saveRoundDataBean);

            }
        }
    }

}
