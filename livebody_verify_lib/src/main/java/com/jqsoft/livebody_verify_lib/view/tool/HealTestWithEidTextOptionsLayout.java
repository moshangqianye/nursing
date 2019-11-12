package com.jqsoft.livebody_verify_lib.view.tool;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.jqsoft.livebody_verify_lib.R;
import com.jqsoft.livebody_verify_lib.activity.CardImageLiveFaceVerifyActivity;
import com.jqsoft.livebody_verify_lib.bean.Constant;
import com.jqsoft.livebody_verify_lib.util.ListUtils;
import com.jqsoft.livebody_verify_lib.util.StringUtils;
import com.jqsoft.livebody_verify_lib.util.Util;


import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.List;




/**
 *
 * Created by Administrator on 2018-05-22.
 */

public class HealTestWithEidTextOptionsLayout extends LinearLayout {
//    @BindView(R.id.iv_necessity)
    ImageView ivNecessity;
//    @BindView(R.id.tv_text)
    TextView tvName;
//    @BindView(R.id.fl_container)
    FlowLayout flContainer;

    boolean isCompulsory;
    String name;
    boolean isNameVisible;
    Context context;
    boolean isSingleSelect=true;

    List<NameValueBeanWithNo> optionList = new ArrayList<>();
    List<TextLayoutWithNo> textLayoutList = new ArrayList<>();

    public HealTestWithEidTextOptionsLayout(Context context, boolean isCompulsory, String name, boolean isNameVisible, boolean isSingleSelect) {
        super(context);
        this.context=context;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate( R.layout.layout_mp_name_options, this);
        ivNecessity =(ImageView)view.findViewById(R.id.iv_necessity);
        tvName=(TextView)view.findViewById(R.id.tv_text);
        flContainer=(FlowLayout)view.findViewById(R.id.fl_container);

//        ButterKnife.bind(this);
        this.context=context;
        this.isCompulsory=isCompulsory;
        this.name=name;
        this.isNameVisible=isNameVisible;
        this.isSingleSelect=isSingleSelect;

        init();

    }
    public void  setclickable(String isclickable){
        if (isclickable.equals("false")){
            for (int i=0;i<textLayoutList.size();i++){
                textLayoutList.get(i).setClickable(false);
            }
        }
    }

    public HealTestWithEidTextOptionsLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate( R.layout.layout_hb1_name_options, this);
//        View.inflate(context, R.layout.layout_hb1_name_options, this);

        this.context=context;
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.NecessityNameOptionsLayout);
        isCompulsory=attributes.getBoolean(R.styleable.NecessityNameOptionsLayout_is_compulsory, false);
        name=attributes.getString(R.styleable.NecessityNameOptionsLayout_name_text);
        isNameVisible=attributes.getBoolean(R.styleable.NecessityNameOptionsLayout_is_name_visible, true);
        isSingleSelect=attributes.getBoolean(R.styleable.NecessityNameOptionsLayout_is_single_select, true);
        attributes.recycle();

        init();
    }

    private void init(){
        showOrHideNecessity();
        tvName.setText(name);
        showOrHideName();
    }

    public List<NameValueBeanWithNo> getOptionList() {
        return optionList;
    }
    public List<TextLayoutWithNo> getTextLayoutList() {
        return textLayoutList;
    }

    public String getSelectValue(){
        int selectedIndex = -1;
        String result = Constant.EMPTY_STRING;

//        if (selectedIndex!=-1){
        for (int i = 0; i < optionList.size(); ++i){
            NameValueBeanWithNo nvb = optionList.get(i);
            if (nvb.isSelected()){
                if (result.equals(Constant.EMPTY_STRING)){
                    result=nvb.getStringValue();
                }else {
                    result=result+","+nvb.getStringValue();
                }
            }
        }
//        }
        return result;
    }
    public String getSelectName(){
        int selectedIndex = -1;
        String result = Constant.EMPTY_STRING;

//        if (selectedIndex!=-1){
        for (int i = 0; i < optionList.size(); ++i){
            NameValueBeanWithNo nvb = optionList.get(i);
            if (nvb.isSelected()){
                if (result.equals( Constant.EMPTY_STRING)){
                    result=nvb.getName();
                }else {
                    result=result+","+nvb.getName();
                }
            }
        }
//        }
        return result;
    }
    public void clearFristChoose(){
        int isSelected=0;
        if (textLayoutList.size()>0&& optionList.size()>0){

                textLayoutList.get(0).setState(false);
                optionList.get(0).setSelected(false);

        }


    }
    public void clearsecondChoose(){
        int isSelected=0;
        if (textLayoutList.size()>0&& optionList.size()>0){

            textLayoutList.get(1).setState(false);
            optionList.get(2).setSelected(false);

        }


    }
    public void setFristChoose(){
        int isSelected=0;
        if (textLayoutList.size()>0&& optionList.size()>0){
            for (int i=0;i<optionList.size();i++){
                if ( optionList.get(i).isSelected()){
                    isSelected=1;
                }
            }
            if (isSelected==0){
                textLayoutList.get(0).setState(true);
                optionList.get(0).setSelected(true);
            }
        }


    }
    public void setSecondChoose(){
        int isSelected=0;
        if (textLayoutList.size()>0&& optionList.size()>0){
            for (int i=0;i<optionList.size();i++){
                if ( optionList.get(i).isSelected()){
                    isSelected=1;
                }
            }
            if (isSelected==0){
                textLayoutList.get(1).setState(true);
                optionList.get(1).setSelected(true);
            }
        }


    }
    public void setDataList(final List<NameValueBeanWithNo> list){
        textLayoutList.clear();
        int a=flContainer.getHorizontalFadingEdgeLength();
        flContainer.removeAllViewsInLayout();

        optionList=list;
        textLayoutList.clear();
        if (!ListUtils.isEmpty(optionList)){
            for (int i = 0; i < optionList.size(); ++i) {
                final NameValueBeanWithNo nvb = optionList.get(i);
                final TextLayoutWithNo textLayout = new TextLayoutWithNo(getContext(), nvb, isSingleSelect);
                FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(6, 6, 6, 6);
                textLayout.setLayoutParams(layoutParams);
                textLayout.setPadding(6, 6, 6, 6);
                textLayout.setGravity(Gravity.CENTER);
                final CardImageLiveFaceVerifyActivity activity=(CardImageLiveFaceVerifyActivity) context;
                final int finalI = i;
                textLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isSingleSelect){
                            if (list.get(finalI).isSelected()) {
                                textLayout.setState(false);
                            } else {
                                clearTextLayoutListState();
                                textLayout.setState(true);
                            }

                            int t=0;
                            for (int i=0;i<list.size();i++){
                                if (list.get(i).getName().equals("有")&&list.get(i).getNumber().equals("防护措施有无")) {
                                    t = i;
                                } else if (list.get(i).getName().equals("其他")){
                                    t=i;
                                } else if (list.get(i).getName().equals("异常")&&list.get(i).getNumber().equals("有描述")){
                                    t=i;
                                }  else if (list.get(i).getName().equals("有")&&list.get(i).getNumber().equals("有描述")){
                                    t=i;
                                } else  if (list.get(i).getName().equals("有")&&list.get(i).getNumber().equals("神经系统疾病")) {
                                     t = i;
                                }  else  if (list.get(i).getName().equals("有")&&list.get(i).getNumber().equals("其他系统疾病")) {
                                    t = i;
                                }else if(list.get(i).getName().equals("异常")&&list.get(i).getNumber().equals("010")){
                                    t= i;
                                }
                            }

                            if (list.get(finalI).getName().equals("有")&&list.get(finalI).getNumber().equals("职业病危害有无")){
//
                                RxBusBaseMessage sx = new RxBusBaseMessage();
                                sx.setCode(1);
                                sx.setObject(true);
                                RxBus.getDefault().post(Constant.ENENT_BUS_TIME, sx);
//                                activity.getHealthTestFragment2().setsOccupationalDiseases_lineVisvale(true);
                            }else if (list.get(finalI).getName().equals("无")&&list.get(finalI).getNumber().equals("职业病危害有无")){
                                RxBusBaseMessage sx = new RxBusBaseMessage();
                                sx.setCode(1);
                                sx.setObject(false);
                                RxBus.getDefault().post(Constant.ENENT_BUS_TIME, sx);

//                                activity.getHealthTestFragment2().setsOccupationalDiseases_lineVisvale(false);
                            }else if (list.get(finalI).getName().equals("是")&&list.get(finalI).getNumber().equals("是否健康教育")){

                                RxBusBaseMessage sx = new RxBusBaseMessage();
                                sx.setCode(2);
                                sx.setObject(nvb.isSelected());
                                RxBus.getDefault().post(Constant.ENENT_BUS_TIME, sx);
//                                activity.getHealthTestFragment5().setishealtheduvisibale(nvb.isSelected());
                            }else if (list.get(finalI).getName().equals("否")&&list.get(finalI).getNumber().equals("是否健康教育")){
                                RxBusBaseMessage sx = new RxBusBaseMessage();
                                sx.setCode(2);
                                sx.setObject(false);
                                RxBus.getDefault().post(Constant.ENENT_BUS_TIME, sx);
//                                activity.getHealthTestFragment5().setishealtheduvisibale(false);
                            }else if (list.get(finalI).getName().equals("有异常")&&list.get(finalI).getNumber().equals("是否有异常")){
                                RxBusBaseMessage sx = new RxBusBaseMessage();
                                sx.setCode(3);
                                sx.setObject(true);
                                RxBus.getDefault().post(Constant.ENENT_BUS_TIME, sx);

//                                activity.getHealthTestFragment5().setline_sHealthEvaluationvisibale(true);
                            }else if (list.get(finalI).getName().equals("体检无异常")&&list.get(finalI).getNumber().equals("是否有异常")){

                                RxBusBaseMessage sx = new RxBusBaseMessage();
                                sx.setCode(3);
                                sx.setObject(false);
                                RxBus.getDefault().post(Constant.ENENT_BUS_TIME, sx);
//                                activity.getHealthTestFragment5().setline_sHealthEvaluationvisibale(false);
                            }else if (list.get(finalI).getName().equals("从不")&&list.get(finalI).getNumber().equals("饮酒频率")){
                                RxBusBaseMessage sx = new RxBusBaseMessage();
                                sx.setCode(4);
                                sx.setObject(false);
                                RxBus.getDefault().post(Constant.ENENT_BUS_TIME, sx);

//                                activity.getHealthTestFragment2().setyinjiuVisbale(false);
                            }else if (list.get(finalI).getName().equals("偶尔")&&list.get(finalI).getNumber().equals("饮酒频率")){

                                RxBusBaseMessage sx = new RxBusBaseMessage();
                                sx.setCode(4);
                                sx.setObject(true);
                                RxBus.getDefault().post(Constant.ENENT_BUS_TIME, sx);

//                                activity.getHealthTestFragment2().setyinjiuVisbale(true);
                            }else if (list.get(finalI).getName().equals("经常")&&list.get(finalI).getNumber().equals("饮酒频率")){
                                RxBusBaseMessage sx = new RxBusBaseMessage();
                                sx.setCode(4);
                                sx.setObject(true);
                                RxBus.getDefault().post(Constant.ENENT_BUS_TIME, sx);

//                                activity.getHealthTestFragment2().setyinjiuVisbale(true);
                            }else if (list.get(finalI).getName().equals("每天")&&list.get(finalI).getNumber().equals("饮酒频率")){
                                RxBusBaseMessage sx = new RxBusBaseMessage();
                                sx.setCode(4);
                                sx.setObject(true);
                                RxBus.getDefault().post(Constant.ENENT_BUS_TIME, sx);

//                                activity.getHealthTestFragment2().setyinjiuVisbale(true);
                            }else if (list.get(finalI).getName().equals("从不吸烟")&&list.get(finalI).getNumber().equals("吸烟状况")){
//                                RxBusBaseMessage sx = new RxBusBaseMessage();
//                                sx.setCode(5);
//                                sx.setObject(new SmokeBean(true,"从不吸烟"));
//                                RxBus.getDefault().post(Constant.ENENT_BUS_TIME, sx);
//                                activity.getHealthTestFragment2().setsSmokingVisbale(true,"从不吸烟");
                            }else if (list.get(finalI).getName().equals("已戒烟")&&list.get(finalI).getNumber().equals("吸烟状况")){

//                                RxBusBaseMessage sx = new RxBusBaseMessage();
//                                sx.setCode(5);
//                                sx.setObject(new SmokeBean(true,"已戒烟"));
//                                RxBus.getDefault().post(Constant.ENENT_BUS_TIME, sx);
//                                activity.getHealthTestFragment2().setsSmokingVisbale(true,"已戒烟");
                            }else if (list.get(finalI).getName().equals("吸烟")&&list.get(finalI).getNumber().equals("吸烟状况")){

//                                RxBusBaseMessage sx = new RxBusBaseMessage();
//                                sx.setCode(5);
//                                sx.setObject(new SmokeBean(true,"吸烟"));
//                                RxBus.getDefault().post(Constant.ENENT_BUS_TIME, sx);
//                                activity.getHealthTestFragment2().setsSmokingVisbale(true,"吸烟");
                            }else if (list.get(finalI).getName().equals("不锻炼")&&list.get(finalI).getNumber().equals("锻炼频率")){

                                RxBusBaseMessage sx = new RxBusBaseMessage();
                                sx.setCode(6);
                                sx.setObject(false);
                                RxBus.getDefault().post(Constant.ENENT_BUS_TIME, sx);

//                                activity.getHealthTestFragment2().setsPhysicalExerciseVisbale(false);
                            }else if (list.get(finalI).getName().equals("每天")&&list.get(finalI).getNumber().equals("锻炼频率")){
                                RxBusBaseMessage sx = new RxBusBaseMessage();
                                sx.setCode(6);
                                sx.setObject(true);
                                RxBus.getDefault().post(Constant.ENENT_BUS_TIME, sx);
//                                activity.getHealthTestFragment2().setsPhysicalExerciseVisbale(true);
                            }else if (list.get(finalI).getName().equals("每周一次以上")&&list.get(finalI).getNumber().equals("锻炼频率")){
                                RxBusBaseMessage sx = new RxBusBaseMessage();
                                sx.setCode(6);
                                sx.setObject(true);
                                RxBus.getDefault().post(Constant.ENENT_BUS_TIME, sx);
//                                activity.getHealthTestFragment2().setsPhysicalExerciseVisbale(true);
                            }else if (list.get(finalI).getName().equals("偶尔")&&list.get(finalI).getNumber().equals("锻炼频率")){
                                RxBusBaseMessage sx = new RxBusBaseMessage();
                                sx.setCode(6);
                                sx.setObject(true);
                                RxBus.getDefault().post(Constant.ENENT_BUS_TIME, sx);
//                                activity.getHealthTestFragment2().setsPhysicalExerciseVisbale(true);
                            }else if (list.get(finalI).getName().equals("未戒酒")&&list.get(finalI).getNumber().equals("是否戒酒")){
                                RxBusBaseMessage sx = new RxBusBaseMessage();
                                sx.setCode(7);
                                sx.setObject(false);
                                RxBus.getDefault().post(Constant.ENENT_BUS_TIME, sx);

//                                activity.getHealthTestFragment2().setsIsAlcoholVisbale(false);
                            }else if (list.get(finalI).getName().equals("已戒酒")&&list.get(finalI).getNumber().equals("是否戒酒")){

                                RxBusBaseMessage sx = new RxBusBaseMessage();
                                sx.setCode(7);
                                sx.setObject(true);
                                RxBus.getDefault().post(Constant.ENENT_BUS_TIME, sx);
//                                activity.getHealthTestFragment2().setsIsAlcoholVisbale(true);
                            }

                            if (finalI==t&& !(t ==0)){
                                if (nvb.isSelected()){
                                    final TextLayoutWithNo textLayout = new TextLayoutWithNo(getContext(), new NameValueBeanWithNo("输入框","","",false), isSingleSelect);
                                    FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);
                                    layoutParams.setMargins(6, 6, 6, 6);
                                    textLayout.setLayoutParams(layoutParams);
                                    textLayout.setPadding(6, 6, 6, 6);
                                    textLayout.setGravity(Gravity.CENTER);
                                    textLayoutList.add(textLayout);
                                    flContainer.addView(textLayout);
                                }else {
                                    if (textLayoutList.size()>(list.size())){
                                        textLayoutList.remove(list.size());
                                        flContainer.removeViewAt(list.size());
                                    }
                                }

                            }else {
                                if (textLayoutList.size()>(list.size())){
                                    textLayoutList.remove(list.size());
                                    flContainer.removeViewAt(list.size());
                                }
                            }
                        } else {

                            int t1=100;
                            for (int i1 =0;i1<list.size();i1++){
                                if (list.get(i1).getName().equals("无症状")||list.get(i1).getName().equals("未发现")){
                                    t1=i1;
                                }else if (list.get(i1).getName().equals("正常")||list.get(i1).getName().equals("齿列")){
                                    t1=i1;
                                }else if(list.get(i1).getName().equals("无")||list.get(i1).getName().equals("罗音")){
                                    t1=i1;
                                }else if(list.get(i1).getName().equals("未见异常")||list.get(i1).getName().equals("肛门指诊")){
                                    t1=i1;
                                }else if(list.get(i1).getName().equals("未见异常")||list.get(i1).getName().equals("乳腺")){
                                    t1=i1;
                                }
                            }
                            if (finalI==t1){
                                for (int t=0;t<textLayoutList.size();t++){
                                    textLayoutList.get(t).setState(false);
                                }
                                textLayout.setState(!nvb.isSelected());
                                if (textLayoutList.size()>list.size()){
                                    textLayoutList.remove(list.size());
                                    flContainer.removeViewAt(list.size());
                                }

                            }else {
                                if (t1==100){
                                    textLayout.setState(!nvb.isSelected());
                                }else  {
                                    textLayoutList.get(t1).setState(false);
                                    textLayout.setState(!nvb.isSelected());
                                }
                            }
                            int t=0;
                            for (int i=0;i<list.size();i++){
                                if (list.get(i).getName().equals("其他")){
                                    t=i;
                                } else if (list.get(i).getName().equals("其他疫苗")){
                                    t=i;
                                }else  if (list.get(i).getName().equals("有")&&list.get(i).getNumber().equals("神经系统疾病")) {
                                    t = i;
                                }  else  if (list.get(i).getName().equals("有")&&list.get(i).getNumber().equals("其他系统疾病")) {
                                    t = i;
                                }
                            }
                            if (list.get(finalI).getName().equals("减体重")&&list.get(finalI).getNumber().equals("危险因素控制")) {
//                                RxBusBaseMessage sx = new RxBusBaseMessage();
//                                sx.setCode(8);
//                                sx.setObject(new SmokeBean(nvb.isSelected(),"sTarget_line"));
//                                RxBus.getDefault().post(Constant.ENENT_BUS_TIME, sx);
//                                activity.getHealthTestFragment5().setsRiskControVisbale("sTarget_line",nvb.isSelected());
                            } else  if (list.get(finalI).getName().equals("建议接种疫苗")&&list.get(finalI).getNumber().equals("危险因素控制")) {
//                                RxBusBaseMessage sx = new RxBusBaseMessage();
//                                sx.setCode(8);
//                                sx.setObject(new SmokeBean(nvb.isSelected(),"sSuggestionVaccine_line"));
//                                RxBus.getDefault().post(Constant.ENENT_BUS_TIME, sx);
//                                activity.getHealthTestFragment5().setsRiskControVisbale("sSuggestionVaccine_line",nvb.isSelected());
                            }else  if (list.get(finalI).getName().equals("其他")&&list.get(finalI).getNumber().equals("危险因素控制")) {
//                                t=0;
//                                RxBusBaseMessage sx = new RxBusBaseMessage();
//                                sx.setCode(8);
//                                sx.setObject(new SmokeBean(nvb.isSelected(),"sHealthOther_line"));
//                                RxBus.getDefault().post(Constant.ENENT_BUS_TIME, sx);
//                                activity.getHealthTestFragment5().setsRiskControVisbale("sHealthOther_line",nvb.isSelected());
                            } else  if (list.get(finalI).getName().equals("缺齿")&&list.get(finalI).getNumber().equals("齿列")) {

//                                RxBusBaseMessage sx = new RxBusBaseMessage();
//                                sx.setCode(9);
//                                sx.setObject(new SmokeBean(nvb.isSelected(),"quechi_line"));
//                                RxBus.getDefault().post(Constant.ENENT_BUS_TIME, sx);


//                                activity.getHealthTestFragment1().setsDentitionvisbale("quechi_line",nvb.isSelected());
                            }else  if (list.get(finalI).getName().equals("龋齿")&&list.get(finalI).getNumber().equals("齿列")) {

//                                RxBusBaseMessage sx = new RxBusBaseMessage();
//                                sx.setCode(9);
//                                sx.setObject(new SmokeBean(nvb.isSelected(),"quchi_line"));
//                                RxBus.getDefault().post(Constant.ENENT_BUS_TIME, sx);

//                                activity.getHealthTestFragment1().setsDentitionvisbale("quchi_line",nvb.isSelected());
                            }else  if (list.get(finalI).getName().equals("义齿(假牙)")&&list.get(finalI).getNumber().equals("齿列")) {
//                                activity.getHealthTestFragment1().setsDentitionvisbale("yichi_line",nvb.isSelected());

//                                RxBusBaseMessage sx = new RxBusBaseMessage();
//                                sx.setCode(9);
//                                sx.setObject(new SmokeBean(nvb.isSelected(),"yichi_line"));
//                                RxBus.getDefault().post(Constant.ENENT_BUS_TIME, sx);

                            }else  if (list.get(finalI).getName().equals("正常")&&list.get(finalI).getNumber().equals("齿列")) {

//
//                                RxBusBaseMessage sx = new RxBusBaseMessage();
//                                sx.setCode(9);
//                                sx.setObject(new SmokeBean(nvb.isSelected(),"正常"));
//                                RxBus.getDefault().post(Constant.ENENT_BUS_TIME, sx);
//                                activity.getHealthTestFragment1().setsDentitionvisbale("正常",nvb.isSelected());
                            }

                            if (finalI==t&& !(t ==0)){
                                if (nvb.isSelected()){
                                    final TextLayoutWithNo textLayout = new TextLayoutWithNo(getContext(), new NameValueBeanWithNo("输入框","","",false), isSingleSelect);
                                    FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);
                                    layoutParams.setMargins(6, 6, 6, 6);
                                    textLayout.setLayoutParams(layoutParams);
                                    textLayout.setPadding(6, 6, 6, 6);
                                    textLayout.setGravity(Gravity.CENTER);
                                    textLayoutList.add(textLayout);
                                    flContainer.addView(textLayout);
                                }else {
                                    if (textLayoutList.size()>(list.size())){
                                        textLayoutList.remove(list.size());
                                        flContainer.removeViewAt(list.size());
                                    }
                                }

                            }else {
//                                if (textLayoutList.size()>(list.size())){
//                                    textLayoutList.remove(list.size());
//                                    flContainer.removeViewAt(list.size());
//                                }
                            }





//                            textLayout.setState(!nvb.isSelected());
                        }
                    }
                });
                textLayoutList.add(textLayout);
                flContainer.addView(textLayout);
            }

        }
    }
    private void clearStrState(String str){

        int t=0;
        for (int i = 0; i < optionList.size(); ++i){
            NameValueBeanWithNo nvb = optionList.get(i);
            if (nvb.getName().equals(str)){
                nvb.setSelected(false);
                t=i;
            }
            }
        textLayoutList.get(t).setState(false);
//        for (int i = 0; i < textLayoutList.size(); ++i){
//            TextLayoutWithNo tl = textLayoutList.get(i);
//            tl.setState(false);
//        }


    }
    public void clearTextLayoutListState(){
        for (int i = 0; i < textLayoutList.size(); ++i){
            TextLayoutWithNo tl = textLayoutList.get(i);
            tl.setState(false);
        }
        for (int i = 0; i < optionList.size(); ++i){
            NameValueBeanWithNo nvb = optionList.get(i);
            nvb.setSelected(false);
        }

    }


    private void showOrHideNecessity() {
        int visibility = INVISIBLE;
        if (isCompulsory){
            visibility=VISIBLE;
        } else {
            visibility=INVISIBLE;
        }
//        ivNecessity.setVisibility(visibility);
    }

    private void showOrHideName() {
        int visibility = INVISIBLE;
        if (isNameVisible){
            visibility=VISIBLE;
        } else {
            visibility=INVISIBLE;
        }
        tvName.setVisibility(visibility);
    }


    public boolean isCompulsory() {
        return isCompulsory;
    }

    public void setCompulsory(boolean compulsory) {
        isCompulsory = compulsory;
    }

    public boolean isSingleSelect() {
        return isSingleSelect;
    }

    public void setSingleSelect(boolean singleSelect) {
        isSingleSelect = singleSelect;
    }
    public NameValueBeanWithNo getSelectBean(){
        int selectedIndex = -1;
        NameValueBeanWithNo selectedNvb = null;
        selectedIndex = getSelectedIndex();
        String result = Constant.EMPTY_STRING;
        if (selectedIndex!=-1){
             selectedNvb = optionList.get(selectedIndex);
            result=selectedNvb.getName();
        }
        return selectedNvb;
    }
    public String getSingleSelectName(){
        int selectedIndex = -1;
        selectedIndex = getSelectedIndex();
        String result = Constant.EMPTY_STRING;
        if (selectedIndex!=-1){
            NameValueBeanWithNo selectedNvb = optionList.get(selectedIndex);
            result=selectedNvb.getName();
        }
        return result;
    }

    public String getSingleSelectValue(){
        int selectedIndex = -1;
        selectedIndex = getSelectedIndex();
        String result = Constant.EMPTY_STRING;
        if (selectedIndex!=-1){
            NameValueBeanWithNo selectedNvb = optionList.get(selectedIndex);
            result=selectedNvb.getValue();
        }
        return result;
    }

    public String getTitleString(){
        String s = Util.trimString(name);
        return s;
    }

    private int getSelectedIndex() {
        int selectedIndex = -1;
        for (int i = 0; i < optionList.size(); ++i){
            NameValueBeanWithNo nvb = optionList.get(i);
            if (nvb.isSelected()){
                selectedIndex=i;
                break;
            }
        }
        return selectedIndex;
    }



    public boolean isValid(){
        if (isSingleSelect){
            String singleSelectValue = getSingleSelectValue();
            boolean isValid = !StringUtils.isBlank(singleSelectValue);
            return isValid;
        } else {
            return true;
        }
    }

    public void setSingleSelectText(String name){
        name= Util.trimString(name);
        for (int i = 0; i < optionList.size(); ++i){
            NameValueBeanWithNo nvb = optionList.get(i);
            if (name.equals(nvb.getName())){
                clearTextLayoutListState();
                TextLayoutWithNo textLayout = textLayoutList.get(i);
                nvb.setSelected(true);
                textLayout.setState(true);
                break;
            }
        }

    }
}
