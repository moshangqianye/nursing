package com.jqsoft.nursing.adapter.nursing;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.adapter.base.BaseQuickAdapterEx;

import com.jqsoft.nursing.bean.nursing.ChildDataBean;
import com.jqsoft.nursing.bean.nursing.RoundDataBean;

import com.jqsoft.nursing.bean.nursing.SaveRoundDataBean;
import com.jqsoft.nursing.di.ui.activity.nursing.ElderItemActivity;
import com.jqsoft.nursing.util.Util;
import com.mixiaoxiao.smoothcompoundbutton.SmoothCheckBox;
import com.mixiaoxiao.smoothcompoundbutton.SmoothCompoundButton;
import com.mixiaoxiao.smoothcompoundbutton.SmoothRadioButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


//智能引导适配器
public class IgElderItemAdapter extends BaseQuickAdapterEx<RoundDataBean, BaseViewHolder> {
    public static final int TYPE_SINGLE_LINE=1;
    public static final int TYPE_MULTIPLE_LINE=2;
    private String SINGLECHOOSE="0";
    private String Multiselect="1";
    private Map<String, Boolean> checkmap = new HashMap<>();
    List<RoundDataBean> data;
    private List<Map<String, String>> mData;// 存储的EditText值
        public Map<String, String> editorValue = new HashMap<String, String>();//
    private int type=TYPE_MULTIPLE_LINE;
    private Context context;
    private List<ChildDataBean> answerList;
    private int mposition;
    private int myposition;
    private boolean myflag=false;
    private SparseBooleanArray mCheckStates=new SparseBooleanArray();

        int i=0;
    public IgElderItemAdapter(List<RoundDataBean> data, int type, Context context) {
        super(R.layout.item_igguide_single_line, data);
        this.type = type;
        this.data = data;
        this.context=context;
    }

//    private void initMap() {
//        for (int i = 0; i < data.size(); i++) {
//            checkmap.put(i, true);
//        }
//    }


    public void restdata(){
        checkmap.clear();
    }

    @Override
    public int getItemViewType(int position) {
        myposition=position;

        return super.getItemViewType(position);
    }

    public void checkmorepush(final SmoothCheckBox CheckBoxs , final String QusId, final String ansId){

        CheckBoxs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBoxs.setChecked(!myflag);
               myflag=!myflag;
            }
        });
        /*

        mCheckStates.clear();
        CheckBoxs.setOnCheckedChangeListener(null);//先设置一次CheckBox的选中监听器，传入参数null
        CheckBoxs.setTag(myposition);
        CheckBoxs.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCompoundButton smoothCompoundButton, boolean b) {
              //  MyCollectionBaseBean baseBean = data.getFavoritesList().get(position);
                int pos= (int) smoothCompoundButton.getTag();

                SaveRoundDataBean  saveRoundDataBean =new SaveRoundDataBean();
                saveRoundDataBean.setfKElderInfoID("");
                saveRoundDataBean.setWardItemID(QusId);
                //   saveRoundDataBean.setWardItemName();
                saveRoundDataBean.setWardOptionID(ansId);
                if (b) {
                    mCheckStates.put(pos,true);
                  //  saveRoundDataBean.setWardOptionName();

                //    ((ElderItemActivity) context).saveMUData(QusId, ansId);
//                    ((ElderItemActivity) context).saveMUData(saveRoundDataBean);
//                    checkmap.put(ansId, b);


                }else if (!b){
                    mCheckStates.delete(pos);
//                    ((ElderItemActivity)context).removeMuData(saveRoundDataBean);
//                    checkmap.put(ansId, b);
                }
            }


        });

        CheckBoxs.setChecked(mCheckStates.get(myposition,false));
        if(CheckBoxs.isChecked()){
           // System.out.println("==选中了=");
        }else{
            //System.out.println("==取消了=");
        }



//        if (checkmap.get(ansId) == null) {
//            checkmap.put(ansId, false);
//        }
//        CheckBoxs.setChecked(checkmap.get(ansId));

*/
    }
    public void checkSingpush(final SmoothRadioButton  choose, final String QusId, final String data){

        choose.setOnCheckedChangeListener(new SmoothRadioButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCompoundButton smoothCompoundButton, boolean b) {
                if (b){
                    ((ElderItemActivity)context).saveData(QusId,data);
                    checkmap.put(data, b);
                }
                else if (!b){
                    ((ElderItemActivity)context).removeSingleChooseData(QusId,data);
                    checkmap.put(data, b);
                }
            }


        });
        //
        if (checkmap.get(data) == null) {
            checkmap.put(data, false);
        }
        choose.setChecked(checkmap.get(data));

    }





    @Override
    protected void convert(final BaseViewHolder helper, final RoundDataBean item) {


        final SmoothCheckBox choose= helper.getView(R.id.choose);
        final SmoothCheckBox choose1= helper.getView(R.id.choose1);
        final SmoothCheckBox choose2= helper.getView(R.id.choose2);
        final SmoothCheckBox choose3= helper.getView(R.id.choose3);
        final SmoothCheckBox choose4= helper.getView(R.id.choose4);
        final SmoothCheckBox choose5= helper.getView(R.id.choose5);
        final SmoothRadioButton muchoose= helper.getView(R.id.muchoose);
        final SmoothRadioButton muchoose1= helper.getView(R.id.muchoose1);
        final SmoothRadioButton muchoose2= helper.getView(R.id.muchoose2);
        final SmoothRadioButton muchoose3= helper.getView(R.id.muchoose3);
        final SmoothRadioButton muchoose4= helper.getView(R.id.muchoose4);



        final SmoothRadioButton[]  radioButtonArray = new SmoothRadioButton[]{muchoose, muchoose1, muchoose2, muchoose3, muchoose4};
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




        answerList=item.getChildren();
        if (helper.getPosition()<2){
            helper.setVisible(R.id.star,true);
        }else {

            helper.setVisible(R.id.star,false);
        }




        helper.setText(R.id.tv_content, Util.trimString("Q"+(helper.getPosition()+1)+": "+item.getWardRoundName()));
        if (item.getMultipleChoice()!=null){
        if (item.getMultipleChoice().equals(Multiselect)){
            helper.setVisible(R.id.muchoose,false);
            helper.setVisible(R.id.muchoose1,false);
            helper.setVisible(R.id.muchoose2,false);
            helper.setVisible(R.id.muchoose3,false);
            helper.setVisible(R.id.muchoose4,false);
        switch (answerList.size()) {
            case 0:
                helper.setVisible(R.id.muchoose,false);
                helper.setVisible(R.id.muchoose1,false);
                helper.setVisible(R.id.muchoose2,false);
                helper.setVisible(R.id.muchoose3,false);
                helper.setVisible(R.id.muchoose4,false);
                helper.setVisible(R.id.choose,false);
                helper.setVisible(R.id.choose1,false);
                helper.setVisible(R.id.choose2,false);
                helper.setVisible(R.id.choose3,false);
                helper.setVisible(R.id.choose4,false);
                helper.setVisible(R.id.choose5,false);
                break;
            case 2:

                helper.setVisible(R.id.choose, true);
                helper.setVisible(R.id.choose1, true);
                helper.setText(R.id.choose, Util.trimString(answerList.get(0).getWardRoundName()));
                helper.setText(R.id.choose1, Util.trimString(answerList.get(1).getWardRoundName()));

                checkmorepush(choose,item.getWardRoundID(),answerList.get(0).getWardRoundID());
                checkmorepush(choose1,item.getWardRoundID(),answerList.get(1).getWardRoundID());

//                checkmorepush(checkboxs,item.getQuestionId(),item.getQuestionId()+"_"+answerList.get(1).getAnswerId());
                    break;
            case 3:

                helper.setVisible(R.id.choose, true);
                helper.setVisible(R.id.choose1, true);
                helper.setVisible(R.id.choose2, true);
                helper.setText(R.id.choose, Util.trimString(answerList.get(0).getWardRoundName()));
                helper.setText(R.id.choose1, Util.trimString(answerList.get(1).getWardRoundName()));
                helper.setText(R.id.choose2, Util.trimString(answerList.get(2).getWardRoundName()));
                checkmorepush(choose,item.getWardRoundID(),answerList.get(0).getWardRoundID());
                checkmorepush(choose1,item.getWardRoundID(),answerList.get(1).getWardRoundID());
                checkmorepush(choose2,item.getWardRoundID(),answerList.get(2).getWardRoundID());

                break;
            case 4:

                helper.setVisible(R.id.choose, true);
                helper.setVisible(R.id.choose1, true);
                helper.setVisible(R.id.choose2, true);
                helper.setVisible(R.id.choose3, true);
                helper.setText(R.id.choose, Util.trimString(answerList.get(0).getWardRoundName()));
                helper.setText(R.id.choose1, Util.trimString(answerList.get(1).getWardRoundName()));
                helper.setText(R.id.choose2, Util.trimString(answerList.get(2).getWardRoundName()));
                helper.setText(R.id.choose3, Util.trimString(answerList.get(3).getWardRoundName()));
                checkmorepush(choose,item.getWardRoundID(),answerList.get(0).getWardRoundID());
                checkmorepush(choose1,item.getWardRoundID(),answerList.get(1).getWardRoundID());
                checkmorepush(choose2,item.getWardRoundID(),answerList.get(2).getWardRoundID());
                checkmorepush(choose3,item.getWardRoundID(),answerList.get(3).getWardRoundID());
                break;
            case 5:

                helper.setVisible(R.id.choose, true);
                helper.setVisible(R.id.choose1, true);
                helper.setVisible(R.id.choose2, true);
                helper.setVisible(R.id.choose3, true);
                helper.setVisible(R.id.choose4, true);
                helper.setText(R.id.choose, Util.trimString(answerList.get(0).getWardRoundName()));
                helper.setText(R.id.choose1, Util.trimString(answerList.get(1).getWardRoundName()));
                helper.setText(R.id.choose2, Util.trimString(answerList.get(2).getWardRoundName()));
                helper.setText(R.id.choose3, Util.trimString(answerList.get(3).getWardRoundName()));
                helper.setText(R.id.choose4, Util.trimString(answerList.get(4).getWardRoundName()));
                checkmorepush(choose,item.getWardRoundID(),answerList.get(0).getWardRoundID());
                checkmorepush(choose1,item.getWardRoundID(),answerList.get(1).getWardRoundID());
                checkmorepush(choose2,item.getWardRoundID(),answerList.get(2).getWardRoundID());
                checkmorepush(choose3,item.getWardRoundID(),answerList.get(3).getWardRoundID());
                checkmorepush(choose4,item.getWardRoundID(),answerList.get(4).getWardRoundID());
                break;
            case 6:
                helper.setVisible(R.id.choose, true);
                helper.setVisible(R.id.choose1, true);
                helper.setVisible(R.id.choose2, true);
                helper.setVisible(R.id.choose3, true);
                helper.setVisible(R.id.choose4, true);
                helper.setVisible(R.id.choose5, true);
                helper.setText(R.id.choose, Util.trimString(answerList.get(0).getWardRoundName()));
                helper.setText(R.id.choose1, Util.trimString(answerList.get(1).getWardRoundName()));
                helper.setText(R.id.choose2, Util.trimString(answerList.get(2).getWardRoundName()));
                helper.setText(R.id.choose3, Util.trimString(answerList.get(3).getWardRoundName()));
                helper.setText(R.id.choose4, Util.trimString(answerList.get(4).getWardRoundName()));
                helper.setText(R.id.choose5, Util.trimString(answerList.get(5).getWardRoundName()));
                checkmorepush(choose,item.getWardRoundID(),answerList.get(0).getWardRoundID());
                checkmorepush(choose1,item.getWardRoundID(),answerList.get(1).getWardRoundID());
                checkmorepush(choose2,item.getWardRoundID(),answerList.get(2).getWardRoundID());
                checkmorepush(choose3,item.getWardRoundID(),answerList.get(3).getWardRoundID());
                checkmorepush(choose4,item.getWardRoundID(),answerList.get(4).getWardRoundID());
                checkmorepush(choose5,item.getWardRoundID(),answerList.get(5).getWardRoundID());
                break;



            default:
                break;}



    }else
        if (item.getMultipleChoice().equals(SINGLECHOOSE))
    {
            helper.setVisible(R.id.choose,false);
            helper.setVisible(R.id.choose1,false);
            helper.setVisible(R.id.choose2,false);
            helper.setVisible(R.id.choose3,false);
            helper.setVisible(R.id.choose4,false);
            helper.setVisible(R.id.choose5,false);


                switch (answerList.size()){

                    case 0:
                        helper.setVisible(R.id.muchoose,false);
                        helper.setVisible(R.id.muchoose1,false);
                        helper.setVisible(R.id.muchoose2,false);
                        helper.setVisible(R.id.muchoose3,false);
                        helper.setVisible(R.id.muchoose4,false);
                        helper.setVisible(R.id.choose,false);
                        helper.setVisible(R.id.choose1,false);
                        helper.setVisible(R.id.choose2,false);
                        helper.setVisible(R.id.choose3,false);
                        helper.setVisible(R.id.choose4,false);
                        helper.setVisible(R.id.choose5,false);
                        break;
                case 2:

                    helper.setVisible(R.id.muchoose4,false);
                    helper.setVisible(R.id.muchoose3,false);
                    helper.setVisible(R.id.muchoose2,false);
                    helper.setVisible(R.id.muchoose,true);
                    helper.setVisible(R.id.muchoose1,true);
                    helper.setText(R.id.muchoose, Util.trimString(answerList.get(0).getWardRoundName()));
                    helper.setText(R.id.muchoose1, Util.trimString(answerList.get(1).getWardRoundName()));


                    checkSingpush(muchoose,item.getWardRoundID(),answerList.get(0).getWardRoundID());
                    checkSingpush(muchoose1,item.getWardRoundID(),answerList.get(1).getWardRoundID());


            break;
            case 3:



                helper.setVisible(R.id.muchoose4,false);
                helper.setVisible(R.id.muchoose3,false);
                    helper.setVisible(R.id.muchoose,true);
                    helper.setVisible(R.id.muchoose1,true);
                    helper.setVisible(R.id.muchoose2,true);
                    helper.setText(R.id.muchoose, Util.trimString(answerList.get(0).getWardRoundName()));
                    helper.setText(R.id.muchoose1, Util.trimString(answerList.get(1).getWardRoundName()));
                    helper.setText(R.id.muchoose2, Util.trimString(answerList.get(2).getWardRoundName()));

                    checkSingpush(muchoose,item.getWardRoundID(),answerList.get(0).getWardRoundID());
                    checkSingpush(muchoose1,item.getWardRoundID(),answerList.get(1).getWardRoundID());
                    checkSingpush(muchoose2,item.getWardRoundID(),answerList.get(2).getWardRoundID());

                break;
            case 4:
                helper.setVisible(R.id.muchoose4,false);
                    helper.setVisible(R.id.muchoose,true);
                    helper.setVisible(R.id.muchoose1,true);
                    helper.setVisible(R.id.muchoose2,true);
                    helper.setVisible(R.id.muchoose3,true);
                    helper.setText(R.id.muchoose, Util.trimString(answerList.get(0).getWardRoundName()));
                    helper.setText(R.id.muchoose1, Util.trimString(answerList.get(1).getWardRoundName()));
                    helper.setText(R.id.muchoose2, Util.trimString(answerList.get(2).getWardRoundName()));
                    helper.setText(R.id.muchoose3, Util.trimString(answerList.get(3).getWardRoundName()));


                checkSingpush(muchoose,item.getWardRoundID(),answerList.get(0).getWardRoundID());
                checkSingpush(muchoose1,item.getWardRoundID(),answerList.get(1).getWardRoundID());
                checkSingpush(muchoose2,item.getWardRoundID(),answerList.get(2).getWardRoundID());
                checkSingpush(muchoose3,item.getWardRoundID(),answerList.get(3).getWardRoundID());

                break;
            case 5:

                    helper.setVisible(R.id.muchoose,true);
                    helper.setVisible(R.id.muchoose1,true);
                    helper.setVisible(R.id.muchoose2,true);
                    helper.setVisible(R.id.muchoose3,true);
                    helper.setVisible(R.id.muchoose4,true);
                    helper.setText(R.id.muchoose, Util.trimString(answerList.get(0).getWardRoundName()));
                    helper.setText(R.id.muchoose1, Util.trimString(answerList.get(1).getWardRoundName()));
                    helper.setText(R.id.muchoose2, Util.trimString(answerList.get(2).getWardRoundName()));
                    helper.setText(R.id.muchoose3, Util.trimString(answerList.get(3).getWardRoundName()));
                    helper.setText(R.id.muchoose4, Util.trimString(answerList.get(4).getWardRoundName()));

                checkSingpush(muchoose,item.getWardRoundID(),answerList.get(0).getWardRoundID());
                checkSingpush(muchoose1,item.getWardRoundID(),answerList.get(1).getWardRoundID());
                checkSingpush(muchoose2,item.getWardRoundID(),answerList.get(2).getWardRoundID());
                checkSingpush(muchoose3,item.getWardRoundID(),answerList.get(3).getWardRoundID());
                checkSingpush(muchoose4,item.getWardRoundID(),answerList.get(4).getWardRoundID());

                break;

            default:
                break;
        } }

        }else {
            //没有anstype
            helper.setVisible(R.id.muchoose,false);
            helper.setVisible(R.id.muchoose1,false);
            helper.setVisible(R.id.muchoose2,false);
            helper.setVisible(R.id.muchoose3,false);
            helper.setVisible(R.id.muchoose4,false);
            helper.setVisible(R.id.choose,false);
            helper.setVisible(R.id.choose1,false);
            helper.setVisible(R.id.choose2,false);
            helper.setVisible(R.id.choose3,false);
            helper.setVisible(R.id.choose4,false);
            helper.setVisible(R.id.choose5,false);

        }
        }


    }




