package com.jqsoft.livebody_verify_lib.view.tool;

import android.text.TextUtils;



import java.util.ArrayList;
import java.util.List;

/**
 * 获取集合数据工具类
 *
 * @author yedong
 * @date 2019/3/19
 */
public class ArrayListUtil {





    /**
     * 设置选中集合重新赋值
     * @param sDentitionList
     * @param selectedCode 选中的code
     */
    public static void setMultipleSelected(List<NameValueBeanWithNo> sDentitionList, String selectedCode) {
        String[] dentitionSelectedCode = new String[0];
        try {
            dentitionSelectedCode = selectedCode.split(",");
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i=0;i<sDentitionList.size();i++){
            for (int j = 0;j<dentitionSelectedCode.length;j++){
                if (TextUtils.equals(dentitionSelectedCode[j],sDentitionList.get(i).getStringValue())){
                    sDentitionList.get(i).setSelected(true);
                    break;
                }
            }
        }
    }

    /**
     * 设置通用其他的（如设置症状）效果的方法 可多选或单选
     * @param Symptoms 控件
     * @param symptomsList  症状集合
     * @param sSymptomsCode  选中的症状code
     * @param showContent 异常详情 （最后其他显示的内容）
     */
    public static void setCommentSymptom(HealTestWithEidTextOptionsLayout Symptoms, List<NameValueBeanWithNo> symptomsList,
                                   String sSymptomsCode, String showContent) {
        String[] codes = new String[0];
        try {
            codes = sSymptomsCode.split(",");
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i< symptomsList.size(); i++){  // 遍历症状
            for (int j =0;j<codes.length;j++){  // 遍历选中症状
                if (TextUtils.equals(symptomsList.get(i).getStringValue(),codes[j])){
                    symptomsList.get(i).setSelected(true);
                    break;
                }
            }
        }
        if(codes.length>0){
        if (TextUtils.equals(symptomsList.get(symptomsList.size()-1).getStringValue(),codes[codes.length-1])){  // 最后状况是否先选中
            symptomsList.add(new NameValueBeanWithNo("输入框", "11", "3", false));
            Symptoms.setDataList(symptomsList);
            List<TextLayoutWithNo> textLayoutList = Symptoms.getTextLayoutList();
            textLayoutList.get(symptomsList.size()-1).getEditText().setText(showContent);
            textLayoutList.get(symptomsList.size()-1).getEditText().setEnabled(false);
        }else {
            Symptoms.setDataList(symptomsList);
        }
        }else{
            Symptoms.setDataList(symptomsList);
        }
        Symptoms.setclickable("false");  // 设置不可点击
    }

    /**
     * 寿县危险因素控制中的建议接种疫苗和其他
     * @param Symptoms 控件
     * @param symptomsList  症状集合
     * @param sSymptomsCode  选中的症状code
     */
    public static void setSymptom(HealTestWithEidTextOptionsLayout Symptoms, List<NameValueBeanWithNo> symptomsList,
                                         String sSymptomsCode) {
        String showContent = "";
        String[] codes = sSymptomsCode.split("#");
        List<String> names = new ArrayList<>();
        boolean isShowEd = false;  // 是否展示输入框
        for (int i = 0; i< symptomsList.size(); i++){  // 遍历症状
            String name = symptomsList.get(i).getName();
            for (int j =0;j<codes.length;j++){  // 遍历选中症状
                if (TextUtils.equals(name,codes[j])){
                    symptomsList.get(i).setSelected(true);
                }
            }
            names.add(name);
        }

        for (int k = 0;k<codes.length;k++){ // 设置最后一个选中
            if (!names.contains(codes[k])){
                showContent = codes[k];
                symptomsList.get(symptomsList.size()-1).setSelected(true);
                isShowEd = true;
            }
        }

        if (isShowEd){  // 是否展示输入框
            symptomsList.add(new NameValueBeanWithNo("输入框", "11", "3", false));
            Symptoms.setDataList(symptomsList);
            List<TextLayoutWithNo> textLayoutList = Symptoms.getTextLayoutList();
            textLayoutList.get(symptomsList.size()-1).getEditText().setText(showContent);
            textLayoutList.get(symptomsList.size()-1).getEditText().setEnabled(false);
        }else {
            Symptoms.setDataList(symptomsList);
        }

        Symptoms.setclickable("false");  // 设置不可点击
    }

    /**
     * 获取异常集合
     * @param array value资源文件数组
     * @return  需要的集合
     */
    public static List<NameValueBeanWithNo> getList(String[] array){
        List<NameValueBeanWithNo> list = new ArrayList<>();
        NameValueBeanWithNo bean;
        for(int i= 0;i<array.length;i++){
            if (i == array.length-1){ // 是否最后一个
                bean = new NameValueBeanWithNo(array[i], String.valueOf(i+1), "有描述", false);
            }else {
                bean = new NameValueBeanWithNo(array[i], String.valueOf(i+1), "", false);
            }
            list.add(bean);
        }
        return list;
    }

    /**
     * 获取健康体检集合
     * @param type code 是否是从0开始，还是从1开始
     * @param array  资源文件的中的数组
     * @param number  描述
     * @return 需要的集合
     */
    public static List<NameValueBeanWithNo> getList(int type,String[] array,String number){
        List<NameValueBeanWithNo> list = new ArrayList<>();
        NameValueBeanWithNo bean;
        if (type == 0 ){
            for (int i = 0;i<array.length;i++){
                bean = new NameValueBeanWithNo(array[i], String.valueOf(i), number, false);
                list.add(bean);
            }
        }else if (type == 1){
            for (int i = 0;i<array.length;i++){
                bean = new NameValueBeanWithNo(array[i], String.valueOf(i+1), number, false);
                list.add(bean);
            }
        }
        return list;
    }




}
