package com.jqsoft.nursing.adapter.nursing;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;


import com.jqsoft.nursing.R;
import com.jqsoft.nursing.bean.nursing.ChildDataBean;
import com.jqsoft.nursing.bean.nursing.RoundDataBean;
import com.jqsoft.nursing.util.OnItemClickLisenter;
import com.jqsoft.nursing.util.Util;
import com.mixiaoxiao.smoothcompoundbutton.SmoothCheckBox;
import com.mixiaoxiao.smoothcompoundbutton.SmoothCompoundButton;
import com.mixiaoxiao.smoothcompoundbutton.SmoothRadioButton;

import java.util.List;

/**
 * 地址的适配器
 * Created by XiaoFu on 2018-01-11 15:00.
 * 注释：
 */

public class RoundListAdapter extends RecyclerView.Adapter<RoundListAdapter.MyHolder> {
    private Context mContext;

    private int selectPosition = -1;
    private OnItemClickLisenter mOnItemClickLisenter;
    private int type=0;
    private Context context;
    List<RoundDataBean> data;
    private List<ChildDataBean> answerList;

    private String SINGLECHOOSE="0";
    private String Multiselect="1";
    private SparseBooleanArray mCheckStates=new SparseBooleanArray();
    private SparseBooleanArray mCheckStates1=new SparseBooleanArray();

    private SparseBooleanArray mCheckStates3=new SparseBooleanArray();
    private SparseBooleanArray mCheckStates4=new SparseBooleanArray();
    private SparseBooleanArray mCheckStates5=new SparseBooleanArray();

    public RoundListAdapter(List<RoundDataBean> data, int type, Context context) {

        this.type = type;
        this.data = data;
        this.context=context;
    }



    public void setSelectPosition(int position) {
        this.selectPosition = position;
        notifyDataSetChanged();
    }

    public int getSelectPositon(){
        return selectPosition;
    }

    public void setOnItemClickLisenter(OnItemClickLisenter onItemClickLisenter) {
        this.mOnItemClickLisenter = onItemClickLisenter;
    }

    @Override
    public RoundListAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyHolder myHolder = new MyHolder(LayoutInflater.from(context).inflate(R.layout.item_igguide_single_line, parent, false));
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {


        final SmoothRadioButton[]  radioButtonArray = new SmoothRadioButton[]{holder.muchoose, holder.muchoose1, holder.muchoose2, holder.muchoose3, holder.muchoose4};
        for (int i = 0; i < radioButtonArray.length; ++i) {
            final SmoothRadioButton radioButton = radioButtonArray[i];
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < radioButtonArray.length; ++j) {
                        SmoothRadioButton button = radioButtonArray[j];
                        if (button != radioButton) {
                            button.setChecked(false);
                        }
                    }
                    radioButton.setChecked(true);

                }
            });}

        answerList=data.get(position).getChildren();

        if (data.get(position).getMultipleChoice()!=null){
            holder.muchoose.setVisibility(View.GONE);
            holder.muchoose1.setVisibility(View.GONE);
            holder.muchoose2.setVisibility(View.GONE);
            holder.muchoose3.setVisibility(View.GONE);
            holder.muchoose4.setVisibility(View.GONE);
            holder.muchoose.setChecked(false);
            holder.muchoose1.setChecked(false);
            holder.muchoose2.setChecked(false);
            holder.muchoose3.setChecked(false);
            holder.muchoose4.setChecked(false);
//            holder.muchoose.setTag(position);
//            holder.muchoose1.setTag(position);
//            holder.muchoose2.setTag(position);
//            holder.muchoose3.setTag(position);
//            holder.muchoose4.setTag(position);





            holder.choose.setVisibility(View.GONE);
            holder.choose1.setVisibility(View.GONE);
            holder.choose2.setVisibility(View.GONE);
            holder.choose3.setVisibility(View.GONE);
            holder.choose4.setVisibility(View.GONE);
            holder.choose5.setVisibility(View.GONE);


            if (data.get(position).getMultipleChoice().equals(Multiselect)){
                holder.muchoose.setVisibility(View.GONE);
                holder.muchoose1.setVisibility(View.GONE);
                holder.muchoose2.setVisibility(View.GONE);
                holder.muchoose3.setVisibility(View.GONE);
                holder.muchoose4.setVisibility(View.GONE);

                switch (answerList.size()) {
                    case 0:

                        holder.muchoose.setVisibility(View.GONE);
                        holder.muchoose1.setVisibility(View.GONE);
                        holder.muchoose2.setVisibility(View.GONE);
                        holder.muchoose3.setVisibility(View.GONE);
                        holder.muchoose4.setVisibility(View.GONE);

                        holder.choose.setVisibility(View.GONE);
                        holder.choose1.setVisibility(View.GONE);
                        holder.choose2.setVisibility(View.GONE);
                        holder.choose3.setVisibility(View.GONE);
                        holder.choose4.setVisibility(View.GONE);
                        holder.choose5.setVisibility(View.GONE);

                        break;
                    case 2:

                        holder.choose.setVisibility(View.VISIBLE);
                        holder.choose1.setVisibility(View.VISIBLE);

                        holder.choose.setText(Util.trimString(answerList.get(0).getWardRoundName()));
                        holder.choose1.setText(Util.trimString(answerList.get(1).getWardRoundName()));
//
//                        helper.setText(R.id.choose, Util.trimString(answerList.get(0).getWardRoundName()));
//                        helper.setText(R.id.choose1, Util.trimString(answerList.get(1).getWardRoundName()));
//
//                        checkmorepush(choose,item.getWardRoundID(),answerList.get(0).getWardRoundID());
//                        checkmorepush(choose1,item.getWardRoundID(),answerList.get(1).getWardRoundID());

//                checkmorepush(checkboxs,item.getQuestionId(),item.getQuestionId()+"_"+answerList.get(1).getAnswerId());
                        break;
                    case 3:

                        holder.choose.setVisibility(View.VISIBLE);
                        holder.choose1.setVisibility(View.VISIBLE);
                        holder.choose2.setVisibility(View.VISIBLE);

                        holder.choose.setText(Util.trimString(answerList.get(0).getWardRoundName()));
                        holder.choose1.setText(Util.trimString(answerList.get(1).getWardRoundName()));
                        holder.choose2.setText(Util.trimString(answerList.get(2).getWardRoundName()));
//
//                        helper.setVisible(R.id.choose, true);
//                        helper.setVisible(R.id.choose1, true);
//                        helper.setVisible(R.id.choose2, true);
//                        helper.setText(R.id.choose, Util.trimString(answerList.get(0).getWardRoundName()));
//                        helper.setText(R.id.choose1, Util.trimString(answerList.get(1).getWardRoundName()));
//                        helper.setText(R.id.choose2, Util.trimString(answerList.get(2).getWardRoundName()));
//                        checkmorepush(choose,item.getWardRoundID(),answerList.get(0).getWardRoundID());
//                        checkmorepush(choose1,item.getWardRoundID(),answerList.get(1).getWardRoundID());
//                        checkmorepush(choose2,item.getWardRoundID(),answerList.get(2).getWardRoundID());

                        break;
                    case 4:

                        holder.choose.setVisibility(View.VISIBLE);
                        holder.choose1.setVisibility(View.VISIBLE);
                        holder.choose2.setVisibility(View.VISIBLE);
                        holder.choose3.setVisibility(View.VISIBLE);

                        holder.choose.setText(Util.trimString(answerList.get(0).getWardRoundName()));
                        holder.choose1.setText(Util.trimString(answerList.get(1).getWardRoundName()));
                        holder.choose2.setText(Util.trimString(answerList.get(2).getWardRoundName()));
                        holder.choose3.setText(Util.trimString(answerList.get(3).getWardRoundName()));

//                        helper.setVisible(R.id.choose, true);
//                        helper.setVisible(R.id.choose1, true);
//                        helper.setVisible(R.id.choose2, true);
//                        helper.setVisible(R.id.choose3, true);
//                        helper.setText(R.id.choose, Util.trimString(answerList.get(0).getWardRoundName()));
//                        helper.setText(R.id.choose1, Util.trimString(answerList.get(1).getWardRoundName()));
//                        helper.setText(R.id.choose2, Util.trimString(answerList.get(2).getWardRoundName()));
//                        helper.setText(R.id.choose3, Util.trimString(answerList.get(3).getWardRoundName()));
//                        checkmorepush(choose,item.getWardRoundID(),answerList.get(0).getWardRoundID());
//                        checkmorepush(choose1,item.getWardRoundID(),answerList.get(1).getWardRoundID());
//                        checkmorepush(choose2,item.getWardRoundID(),answerList.get(2).getWardRoundID());
//                        checkmorepush(choose3,item.getWardRoundID(),answerList.get(3).getWardRoundID());
                        break;
                    case 5:

                        holder.choose.setVisibility(View.VISIBLE);
                        holder.choose1.setVisibility(View.VISIBLE);
                        holder.choose2.setVisibility(View.VISIBLE);
                        holder.choose3.setVisibility(View.VISIBLE);
                        holder.choose4.setVisibility(View.VISIBLE);

                        holder.choose.setText(Util.trimString(answerList.get(0).getWardRoundName()));
                        holder.choose1.setText(Util.trimString(answerList.get(1).getWardRoundName()));
                        holder.choose2.setText(Util.trimString(answerList.get(2).getWardRoundName()));
                        holder.choose3.setText(Util.trimString(answerList.get(3).getWardRoundName()));
                        holder.choose4.setText(Util.trimString(answerList.get(4).getWardRoundName()));

//                        helper.setVisible(R.id.choose, true);
//                        helper.setVisible(R.id.choose1, true);
//                        helper.setVisible(R.id.choose2, true);
//                        helper.setVisible(R.id.choose3, true);
//                        helper.setVisible(R.id.choose4, true);
//                        helper.setText(R.id.choose, Util.trimString(answerList.get(0).getWardRoundName()));
//                        helper.setText(R.id.choose1, Util.trimString(answerList.get(1).getWardRoundName()));
//                        helper.setText(R.id.choose2, Util.trimString(answerList.get(2).getWardRoundName()));
//                        helper.setText(R.id.choose3, Util.trimString(answerList.get(3).getWardRoundName()));
//                        helper.setText(R.id.choose4, Util.trimString(answerList.get(4).getWardRoundName()));
//                        checkmorepush(choose,item.getWardRoundID(),answerList.get(0).getWardRoundID());
//                        checkmorepush(choose1,item.getWardRoundID(),answerList.get(1).getWardRoundID());
//                        checkmorepush(choose2,item.getWardRoundID(),answerList.get(2).getWardRoundID());
//                        checkmorepush(choose3,item.getWardRoundID(),answerList.get(3).getWardRoundID());
//                        checkmorepush(choose4,item.getWardRoundID(),answerList.get(4).getWardRoundID());
                        break;
                    case 6:

                        holder.choose.setVisibility(View.VISIBLE);
                        holder.choose1.setVisibility(View.VISIBLE);
                        holder.choose2.setVisibility(View.VISIBLE);
                        holder.choose3.setVisibility(View.VISIBLE);
                        holder.choose4.setVisibility(View.VISIBLE);
                        holder.choose5.setVisibility(View.VISIBLE);

                        holder.choose.setText(Util.trimString(answerList.get(0).getWardRoundName()));
                        holder.choose1.setText(Util.trimString(answerList.get(1).getWardRoundName()));
                        holder.choose2.setText(Util.trimString(answerList.get(2).getWardRoundName()));
                        holder.choose3.setText(Util.trimString(answerList.get(3).getWardRoundName()));
                        holder.choose4.setText(Util.trimString(answerList.get(4).getWardRoundName()));
                        holder.choose5.setText(Util.trimString(answerList.get(5).getWardRoundName()));

//                        helper.setVisible(R.id.choose, true);
//                        helper.setVisible(R.id.choose1, true);
//                        helper.setVisible(R.id.choose2, true);
//                        helper.setVisible(R.id.choose3, true);
//                        helper.setVisible(R.id.choose4, true);
//                        helper.setVisible(R.id.choose5, true);
//                        helper.setText(R.id.choose, Util.trimString(answerList.get(0).getWardRoundName()));
//                        helper.setText(R.id.choose1, Util.trimString(answerList.get(1).getWardRoundName()));
//                        helper.setText(R.id.choose2, Util.trimString(answerList.get(2).getWardRoundName()));
//                        helper.setText(R.id.choose3, Util.trimString(answerList.get(3).getWardRoundName()));
//                        helper.setText(R.id.choose4, Util.trimString(answerList.get(4).getWardRoundName()));
//                        helper.setText(R.id.choose5, Util.trimString(answerList.get(5).getWardRoundName()));
//                        checkmorepush(choose,item.getWardRoundID(),answerList.get(0).getWardRoundID());
//                        checkmorepush(choose1,item.getWardRoundID(),answerList.get(1).getWardRoundID());
//                        checkmorepush(choose2,item.getWardRoundID(),answerList.get(2).getWardRoundID());
//                        checkmorepush(choose3,item.getWardRoundID(),answerList.get(3).getWardRoundID());
//                        checkmorepush(choose4,item.getWardRoundID(),answerList.get(4).getWardRoundID());
//                        checkmorepush(choose5,item.getWardRoundID(),answerList.get(5).getWardRoundID());
                        break;



                    default:
                        break;}



            }else if (data.get(position).getMultipleChoice().equals(SINGLECHOOSE))
            {
                holder.choose.setVisibility(View.GONE);
                holder.choose1.setVisibility(View.GONE);
                holder.choose2.setVisibility(View.GONE);
                holder.choose3.setVisibility(View.GONE);
                holder.choose4.setVisibility(View.GONE);
                holder.choose5.setVisibility(View.GONE);


//                helper.setVisible(R.id.choose,false);
//                helper.setVisible(R.id.choose1,false);
//                helper.setVisible(R.id.choose2,false);
//                helper.setVisible(R.id.choose3,false);
//                helper.setVisible(R.id.choose4,false);
//                helper.setVisible(R.id.choose5,false);


                switch (answerList.size()){

                    case 0:
                        holder.muchoose.setVisibility(View.GONE);
                        holder.muchoose1.setVisibility(View.GONE);
                        holder.muchoose2.setVisibility(View.GONE);
                        holder.muchoose3.setVisibility(View.GONE);
                        holder.muchoose4.setVisibility(View.GONE);

                        holder.choose.setVisibility(View.GONE);
                        holder.choose1.setVisibility(View.GONE);
                        holder.choose2.setVisibility(View.GONE);
                        holder.choose3.setVisibility(View.GONE);
                        holder.choose4.setVisibility(View.GONE);
                        holder.choose5.setVisibility(View.GONE);


                        break;
                    case 2:

                        holder.muchoose.setVisibility(View.VISIBLE);
                        holder.muchoose1.setVisibility(View.VISIBLE);
                        holder.muchoose2.setVisibility(View.GONE);
                        holder.muchoose3.setVisibility(View.GONE);
                        holder.muchoose4.setVisibility(View.GONE);

//                        holder.muchoose.setTag(position);
//                        holder.muchoose1.setTag(position);
//                        holder.muchoose2.setTag(position);
//                        holder.muchoose3.setTag(position);
//                        holder.muchoose4.setTag(position);

                        holder.muchoose.setText(Util.trimString(answerList.get(0).getWardRoundName()));
                        holder.muchoose1.setText(Util.trimString(answerList.get(1).getWardRoundName()));

                        mCheckStates.clear();
                   //    holder.muchoose.setTag(position);//在最开始适配的时候，将每一个CheckBox设置一个当前的Tag值，这样每个CheckBox都有了一个固定的标识
                        holder.muchoose.setOnCheckedChangeListener(new SmoothRadioButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(SmoothCompoundButton smoothCompoundButton, boolean isCheckBox) {
//                                int pos= (int) smoothCompoundButton.getTag();//得到当前CheckBox的Tag值，由于之前保存过，所以不会出现索引错乱
//                                if (isCheckBox){
//                                    //点击时将当前CheckBox的索引值和Boolean存入SparseBooleanArray中
//                                    mCheckStates.put(pos,true);
//                                }else {
//                                    //否则将 当前CheckBox对象从SparseBooleanArray中移除
//                                    mCheckStates.delete(pos);
//                                }

                                if (smoothCompoundButton == null || !smoothCompoundButton.isPressed()) {
                                    //防止setOnCheckedChangeListener循环监听
                                    return;
                                }
                               // Log.d(TAG, "onCheckedChanged: buttonView:" + buttonView);
                                answerList.get(0).flag = isCheckBox;

                            }


                        });
                        //得到CheckBox的Boolean值后，将当前索引的CheckBox状态改变
//                        boolean s1=mCheckStates.get(position,false);
//
//                        if(s1){
//                            holder.muchoose.setChecked(true);
//                        }else {
//                            holder.muchoose.setChecked(false);
//                        }
//
//                        if(holder.muchoose.isChecked()){
//                            System.out.println("==选中了=");
//                        }else{
//                            System.out.println("==取消了=");
//                        }


                        mCheckStates1.clear();
                        holder.muchoose1.setTag(position);//在最开始适配的时候，将每一个CheckBox设置一个当前的Tag值，这样每个CheckBox都有了一个固定的标识
                        holder.muchoose1.setOnCheckedChangeListener(new SmoothRadioButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(SmoothCompoundButton smoothCompoundButton, boolean isCheckBox) {
                                int pos= (int) smoothCompoundButton.getTag();//得到当前CheckBox的Tag值，由于之前保存过，所以不会出现索引错乱
                                if (isCheckBox){
                                    //点击时将当前CheckBox的索引值和Boolean存入SparseBooleanArray中
                                    mCheckStates1.put(pos,true);
                                }else {
                                    //否则将 当前CheckBox对象从SparseBooleanArray中移除
                                    mCheckStates1.delete(pos);
                                }
                            }


                        });

                        boolean s2=mCheckStates1.get(position,false);
                        //得到CheckBox的Boolean值后，将当前索引的CheckBox状态改变
                        if(s2){
                            holder.muchoose1.setChecked(true);
                        }else {
                            holder.muchoose1.setChecked(false);
                        }

                        if(holder.muchoose1.isChecked()){
                            System.out.println("==选中了=");
                        }else{
                            System.out.println("==取消了=");
                        }

                        break;
                    case 3:



                        holder.muchoose.setVisibility(View.VISIBLE);
                        holder.muchoose1.setVisibility(View.VISIBLE);
                        holder.muchoose2.setVisibility(View.VISIBLE);
                        holder.muchoose3.setVisibility(View.GONE);
                        holder.muchoose4.setVisibility(View.GONE);
//                        holder.muchoose.setTag(position);
//                        holder.muchoose1.setTag(position);
//                        holder.muchoose2.setTag(position);
//                        holder.muchoose3.setTag(position);
//                        holder.muchoose4.setTag(position);

                        holder.muchoose.setChecked(false);
                        holder.muchoose1.setChecked(false);
                        holder.muchoose2.setChecked(false);


                        holder.muchoose.setText(Util.trimString(answerList.get(0).getWardRoundName()));
                        holder.muchoose1.setText(Util.trimString(answerList.get(1).getWardRoundName()));
                        holder.muchoose2.setText(Util.trimString(answerList.get(2).getWardRoundName()));


                        mCheckStates3.clear();
                        holder.muchoose.setTag(position);//在最开始适配的时候，将每一个CheckBox设置一个当前的Tag值，这样每个CheckBox都有了一个固定的标识
                        holder.muchoose.setOnCheckedChangeListener(new SmoothRadioButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(SmoothCompoundButton smoothCompoundButton, boolean isCheckBox) {
                                int pos= (int) smoothCompoundButton.getTag();//得到当前CheckBox的Tag值，由于之前保存过，所以不会出现索引错乱
                                if (isCheckBox){
                                    //点击时将当前CheckBox的索引值和Boolean存入SparseBooleanArray中
                                    mCheckStates3.put(pos,true);
                                }else {
                                    //否则将 当前CheckBox对象从SparseBooleanArray中移除
                                    mCheckStates3.delete(pos);
                                }
                            }


                        });
                        boolean s3 =mCheckStates3.get(position,false);
                        //得到CheckBox的Boolean值后，将当前索引的CheckBox状态改变
                        if(s3){
                            holder.muchoose.setChecked(s3);
                        }else {
                            holder.muchoose.setChecked(s3);
                        }

                        if(holder.muchoose.isChecked()){
                            System.out.println("==选中了=");
                        }else{
                            System.out.println("==取消了=");
                        }





                        mCheckStates4.clear();
                        holder.muchoose1.setTag(position);//在最开始适配的时候，将每一个CheckBox设置一个当前的Tag值，这样每个CheckBox都有了一个固定的标识
                        holder.muchoose1.setOnCheckedChangeListener(new SmoothRadioButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(SmoothCompoundButton smoothCompoundButton, boolean isCheckBox) {
                                int pos= (int) smoothCompoundButton.getTag();//得到当前CheckBox的Tag值，由于之前保存过，所以不会出现索引错乱
                                if (isCheckBox){
                                    //点击时将当前CheckBox的索引值和Boolean存入SparseBooleanArray中
                                    mCheckStates4.put(pos,true);
                                }else {
                                    //否则将 当前CheckBox对象从SparseBooleanArray中移除
                                    mCheckStates4.delete(pos);
                                }
                            }


                        });
                        //得到CheckBox的Boolean值后，将当前索引的CheckBox状态改变

                        boolean s4 =mCheckStates4.get(position,false);
                        if(s4){

                                holder.muchoose1.setChecked(true);


                        }else {
                            holder.muchoose1.setChecked(false);
                        }

                        if(holder.muchoose1.isChecked()){
                            System.out.println("==选中了=");
                        }else{
                            System.out.println("==取消了=");
                        }




                        mCheckStates5.clear();
                        holder.muchoose2.setTag(position);//在最开始适配的时候，将每一个CheckBox设置一个当前的Tag值，这样每个CheckBox都有了一个固定的标识
                        holder.muchoose2.setOnCheckedChangeListener(new SmoothRadioButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(SmoothCompoundButton smoothCompoundButton, boolean isCheckBox) {
                                int pos= (int) smoothCompoundButton.getTag();//得到当前CheckBox的Tag值，由于之前保存过，所以不会出现索引错乱
                                if (isCheckBox){
                                    //点击时将当前CheckBox的索引值和Boolean存入SparseBooleanArray中
                                    mCheckStates5.put(pos,true);
                                }else {
                                    //否则将 当前CheckBox对象从SparseBooleanArray中移除
                                    mCheckStates5.delete(pos);
                                }
                            }


                        });
                        //得到CheckBox的Boolean值后，将当前索引的CheckBox状态改变
                        boolean s5=mCheckStates5.get(position,false);
                        if(s5){
                            holder.muchoose2.setChecked(true);
                        }else {
                            holder.muchoose2.setChecked(false);
                        }

                        if(holder.muchoose2.isChecked()){
                            System.out.println("==选中了=");
                        }else{
                            System.out.println("==取消了=");
                        }

                        break;
                    case 4:


                        holder.muchoose.setVisibility(View.VISIBLE);
                        holder.muchoose1.setVisibility(View.VISIBLE);
                        holder.muchoose2.setVisibility(View.VISIBLE);
                        holder.muchoose3.setVisibility(View.VISIBLE);
                        holder.muchoose4.setVisibility(View.GONE);

                        holder.muchoose.setText(Util.trimString(answerList.get(0).getWardRoundName()));
                        holder.muchoose1.setText(Util.trimString(answerList.get(1).getWardRoundName()));
                        holder.muchoose2.setText(Util.trimString(answerList.get(2).getWardRoundName()));
                        holder.muchoose3.setText(Util.trimString(answerList.get(3).getWardRoundName()));

//                        helper.setVisible(R.id.muchoose4,false);
//                        helper.setVisible(R.id.muchoose,true);
//                        helper.setVisible(R.id.muchoose1,true);
//                        helper.setVisible(R.id.muchoose2,true);
//                        helper.setVisible(R.id.muchoose3,true);
//                        helper.setText(R.id.muchoose, Util.trimString(answerList.get(0).getWardRoundName()));
//                        helper.setText(R.id.muchoose1, Util.trimString(answerList.get(1).getWardRoundName()));
//                        helper.setText(R.id.muchoose2, Util.trimString(answerList.get(2).getWardRoundName()));
//                        helper.setText(R.id.muchoose3, Util.trimString(answerList.get(3).getWardRoundName()));
//
//
//                        checkSingpush(muchoose,item.getWardRoundID(),answerList.get(0).getWardRoundID());
//                        checkSingpush(muchoose1,item.getWardRoundID(),answerList.get(1).getWardRoundID());
//                        checkSingpush(muchoose2,item.getWardRoundID(),answerList.get(2).getWardRoundID());
//                        checkSingpush(muchoose3,item.getWardRoundID(),answerList.get(3).getWardRoundID());

                        break;
                    case 5:


                        holder.muchoose.setVisibility(View.VISIBLE);
                        holder.muchoose1.setVisibility(View.VISIBLE);
                        holder.muchoose2.setVisibility(View.VISIBLE);
                        holder.muchoose3.setVisibility(View.VISIBLE);
                        holder.muchoose4.setVisibility(View.VISIBLE);

                        holder.muchoose.setText(Util.trimString(answerList.get(0).getWardRoundName()));
                        holder.muchoose1.setText(Util.trimString(answerList.get(1).getWardRoundName()));
                        holder.muchoose2.setText(Util.trimString(answerList.get(2).getWardRoundName()));
                        holder.muchoose3.setText(Util.trimString(answerList.get(3).getWardRoundName()));
                        holder.muchoose4.setText(Util.trimString(answerList.get(4).getWardRoundName()));

//
//                        helper.setVisible(R.id.muchoose,true);
//                        helper.setVisible(R.id.muchoose1,true);
//                        helper.setVisible(R.id.muchoose2,true);
//                        helper.setVisible(R.id.muchoose3,true);
//                        helper.setVisible(R.id.muchoose4,true);
//                        helper.setText(R.id.muchoose, Util.trimString(answerList.get(0).getWardRoundName()));
//                        helper.setText(R.id.muchoose1, Util.trimString(answerList.get(1).getWardRoundName()));
//                        helper.setText(R.id.muchoose2, Util.trimString(answerList.get(2).getWardRoundName()));
//                        helper.setText(R.id.muchoose3, Util.trimString(answerList.get(3).getWardRoundName()));
//                        helper.setText(R.id.muchoose4, Util.trimString(answerList.get(4).getWardRoundName()));
//
//                        checkSingpush(muchoose,item.getWardRoundID(),answerList.get(0).getWardRoundID());
//                        checkSingpush(muchoose1,item.getWardRoundID(),answerList.get(1).getWardRoundID());
//                        checkSingpush(muchoose2,item.getWardRoundID(),answerList.get(2).getWardRoundID());
//                        checkSingpush(muchoose3,item.getWardRoundID(),answerList.get(3).getWardRoundID());
//                        checkSingpush(muchoose4,item.getWardRoundID(),answerList.get(4).getWardRoundID());

                        break;

                    default:
                        break;
                } }

        }else {
            holder.muchoose.setVisibility(View.GONE);
            holder.muchoose1.setVisibility(View.GONE);
            holder.muchoose2.setVisibility(View.GONE);
            holder.muchoose3.setVisibility(View.GONE);
            holder.muchoose4.setVisibility(View.GONE);

            holder.choose.setVisibility(View.GONE);
            holder.choose1.setVisibility(View.GONE);
            holder.choose2.setVisibility(View.GONE);
            holder.choose3.setVisibility(View.GONE);
            holder.choose4.setVisibility(View.GONE);
            holder.choose5.setVisibility(View.GONE);

            //没有anstype
//            helper.setVisible(R.id.muchoose,false);
//            helper.setVisible(R.id.muchoose1,false);
//            helper.setVisible(R.id.muchoose2,false);
//            helper.setVisible(R.id.muchoose3,false);
//            helper.setVisible(R.id.muchoose4,false);
//            helper.setVisible(R.id.choose,false);
//            helper.setVisible(R.id.choose1,false);
//            helper.setVisible(R.id.choose2,false);
//            helper.setVisible(R.id.choose3,false);
//            helper.setVisible(R.id.choose4,false);
//            helper.setVisible(R.id.choose5,false);

        }








}

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {
//        TextView mTvTitle;
//        TextView mTvMessage;
//        CheckBox mCheckBox;
            SmoothRadioButton muchoose;
        SmoothRadioButton muchoose1;
        SmoothRadioButton muchoose2;
        SmoothRadioButton muchoose3;
        SmoothRadioButton muchoose4;

         SmoothCheckBox choose;
        SmoothCheckBox choose1;
        SmoothCheckBox choose2;
        SmoothCheckBox choose3;
        SmoothCheckBox choose4;
        SmoothCheckBox choose5;

        public MyHolder(View itemView) {
            super(itemView);
//            final SmoothRadioButton muchoose= helper.getView(R.id.muchoose);
//            final SmoothRadioButton muchoose1= helper.getView(R.id.muchoose1);
//            final SmoothRadioButton muchoose2= helper.getView(R.id.muchoose2);
//            final SmoothRadioButton muchoose3= helper.getView(R.id.muchoose3);
//            final SmoothRadioButton muchoose4= helper.getView(R.id.muchoose4);

            muchoose = (SmoothRadioButton) itemView.findViewById(R.id.muchoose);
            muchoose1 = (SmoothRadioButton) itemView.findViewById(R.id.muchoose1);
            muchoose2 = (SmoothRadioButton) itemView.findViewById(R.id.muchoose2);
            muchoose3 = (SmoothRadioButton) itemView.findViewById(R.id.muchoose3);
            muchoose4 = (SmoothRadioButton) itemView.findViewById(R.id.muchoose4);

            choose = (SmoothCheckBox) itemView.findViewById(R.id.choose);
            choose1 = (SmoothCheckBox) itemView.findViewById(R.id.choose1);
            choose2 = (SmoothCheckBox) itemView.findViewById(R.id.choose2);
            choose3 = (SmoothCheckBox) itemView.findViewById(R.id.choose3);
            choose4 = (SmoothCheckBox) itemView.findViewById(R.id.choose4);
            choose5 = (SmoothCheckBox) itemView.findViewById(R.id.choose5);


        }
    }

}
