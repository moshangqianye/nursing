package com.jqsoft.nursing.datasource;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.jqsoft.nursing.R;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.FunctionImageBean;
import com.jqsoft.nursing.bean.ImageAndTextBean;
import com.jqsoft.nursing.content.GuideListContentPerCategory;
import com.jqsoft.nursing.content.ModuleListContentPerCategory;
import com.jqsoft.nursing.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-06-23.
 */

public class DataSourceFactory {
    public static List<FunctionImageBean> getIndexPageFunctionImageTextGroupList() {
        List<FunctionImageBean> result = new ArrayList<>();
//        String sAreaLeavel=Util.choicArea();
        String sAreaLeavel="area_1";
        if(sAreaLeavel.equals("area_1")  ){
//            FunctionImageBean b1 = new FunctionImageBean("救助对象", String.valueOf(Constants.FUNCTION_1), R.mipmap.icon);
//            FunctionImageBean b2 = new FunctionImageBean("救助历史", String.valueOf(Constants.FUNCTION_2), R.mipmap.icon);
//            FunctionImageBean b3 = new FunctionImageBean("地图服务", String.valueOf(Constants.FUNCTION_3), R.mipmap.icon);
//            FunctionImageBean b4 = new FunctionImageBean("我的收藏", String.valueOf(Constants.FUNCTION_4), R.mipmap.icon);
//            FunctionImageBean b5 = new FunctionImageBean("受理中心", String.valueOf(Constants.FUNCTION_5), R.mipmap.icon);
//            FunctionImageBean b6 = new FunctionImageBean("救助站", String.valueOf(Constants.FUNCTION_6), R.mipmap.icon);
//            FunctionImageBean b7 = new FunctionImageBean("办事指南", String.valueOf(Constants.FUNCTION_7), R.mipmap.icon);
//            FunctionImageBean b8 = new FunctionImageBean("咨询建议", String.valueOf(Constants.FUNCTION_8), R.mipmap.icon);
//            result.add(b1);
//            result.add(b2);
//            result.add(b3);
//            result.add(b4);
//            result.add(b5);
//            result.add(b6);
//            result.add(b7);
//            result.add(b8);
        }else if(sAreaLeavel.equals("area_3" )|| sAreaLeavel.equals("area_2")){
//
//            FunctionImageBean b1 = new FunctionImageBean("受理中心", String.valueOf(Constants.FUNCTION_1), R.mipmap.g_index_module_souli);
//            FunctionImageBean b2 = new FunctionImageBean("办事指南", String.valueOf(Constants.FUNCTION_2), R.mipmap.g_index_module_banshi);
//            FunctionImageBean b3 = new FunctionImageBean("智能引导", String.valueOf(Constants.FUNCTION_3), R.mipmap.g_index_module_zhineng);
//            FunctionImageBean b4 = new FunctionImageBean("我的收藏", String.valueOf(Constants.FUNCTION_4), R.mipmap.g_index_module_soucang);
//            FunctionImageBean b5 = new FunctionImageBean("主动发现", String.valueOf(Constants.FUNCTION_5), R.mipmap.g_index_module_zhudong);
//            FunctionImageBean b6 = new FunctionImageBean("代为申请", String.valueOf(Constants.FUNCTION_6), R.mipmap.g_index_module_daiwei);
//            FunctionImageBean b7 = new FunctionImageBean("办理进度", String.valueOf(Constants.FUNCTION_7), R.mipmap.g_index_module_banli);
//            FunctionImageBean b8 = new FunctionImageBean("入户评议", String.valueOf(Constants.FUNCTION_8), R.mipmap.g_index_module_ruhu);
//            result.add(b1);
//            result.add(b2);
//            result.add(b3);
//            result.add(b4);
//            result.add(b5);
//            result.add(b6);
//            result.add(b7);
//            result.add(b8);
        }else {
//            FunctionImageBean b1 = new FunctionImageBean("救助对象", String.valueOf(Constants.FUNCTION_1), R.mipmap.g_index_minzheng);
//            FunctionImageBean b2 = new FunctionImageBean("救助历史", String.valueOf(Constants.FUNCTION_2), R.mipmap.g_index_lishi);
//            FunctionImageBean b3 = new FunctionImageBean("地图服务", String.valueOf(Constants.FUNCTION_3), R.mipmap.g_index_ditu);
//            FunctionImageBean b4 = new FunctionImageBean("我的收藏", String.valueOf(Constants.FUNCTION_4), R.mipmap.g_index_shoucang);
//            FunctionImageBean b5 = new FunctionImageBean("受理中心", String.valueOf(Constants.FUNCTION_5), R.mipmap.g_index_souli);
//            FunctionImageBean b6 = new FunctionImageBean("救助站", String.valueOf(Constants.FUNCTION_6), R.mipmap.g_index_jiuzhuzhan);
//            FunctionImageBean b7 = new FunctionImageBean("办事指南", String.valueOf(Constants.FUNCTION_7), R.mipmap.g_index_banshi);
//            FunctionImageBean b8 = new FunctionImageBean("咨询建议", String.valueOf(Constants.FUNCTION_8), R.mipmap.g_index_zixun);
//            result.add(b1);
//            result.add(b2);
//            result.add(b3);
//            result.add(b4);
//            result.add(b5);
//            result.add(b6);
//            result.add(b7);
//            result.add(b8);
        }



        return result;
    }


    public static List<View> getSignResidentClientAllModuleListView(Context context) {
        int[] mySignImageIdArray = new int[]{
//                R.mipmap.icon_civil_news, R.mipmap.icon_notification_adv

        };
//        int[] mySignImageIdArray = new int[]{R.mipmap.r_online_sign_application,R.mipmap.r_my_sign_info,R.mipmap.r_my_sign_agreement
//                ,R.mipmap.r_sign_policy_translate,R.mipmap.r_sign_service_package_translate
//        };
        String[] mySignTitleArray = new String[]{"政策新闻", "通知公告"

        };
//        String[] mySignTitleArray = new String[]{"在线签约申请", "我的签约信息", "我的签约协议",
//                "签约政策解读", "签约服务包解读"
//        };

        String sAreaLeavel=Util.choicArea();
        int[] myServiceImageIdArray;
        String[] myServiceTitleArray;
        if(sAreaLeavel.equals("area_1") || sAreaLeavel.equals("area_2") ){
            myServiceImageIdArray = new int[]{
//                    R.mipmap.icon_civil_object,  R.mipmap.icon_social_history,
//                    R.mipmap.icon_map_servers
                    //           , R.mipmap.icon_social_orgnization
                    //            , R.mipmap.icon_referto
            };
           myServiceTitleArray = new String[]{
                    "救助对象",  "救助历史",
                    "地图服务"
                    //          , "社会组织"
                    //           , "查询"
            };
        } else if(sAreaLeavel.equals("area_3")){
            myServiceImageIdArray = new int[]{
//                    R.mipmap.icon_civil_object, R.mipmap.icon_handle_progress, R.mipmap.icon_social_history,
//                    R.mipmap.icon_map_servers
                    //           , R.mipmap.icon_social_orgnization
                    //            , R.mipmap.icon_referto
            };
            myServiceTitleArray = new String[]{
                    "救助对象", "办理进度", "救助历史",
                    "地图服务"
                    //          , "社会组织"
                    //           , "查询"
            };
        }else {
            myServiceImageIdArray = new int[]{
//                    R.mipmap.icon_civil_object,  R.mipmap.icon_social_history,
//                    R.mipmap.icon_map_servers
                    //           , R.mipmap.icon_social_orgnization
                    //            , R.mipmap.icon_referto
            };
           myServiceTitleArray = new String[]{
                    "救助对象",  "救助历史",
                    "地图服务"
                    //          , "社会组织"
                    //           , "查询"
            };
        }



        int[] systemManagementImageIdArray = new int[]{
//                R.mipmap.icon_guide, R.mipmap.icon_accesscenter, R.mipmap.icon_magic_guide,
//                R.mipmap.icon_social_station, R.mipmap.icon_usually_question, R.mipmap.icon_advanteage
        };
        String[] systemManagementTitleArray = new String[]{
                "办事指南", "受理中心", "智能引导",
                "救助站", "常见问题", "咨询建议"
        };


        String[] categoryTitleArray = new String[]{"资讯", "业务", "便民"};
        int[][] categoryImageArray = new int[][]{mySignImageIdArray, myServiceImageIdArray, systemManagementImageIdArray};
        String[][] categoryTextArray = new String[][]{mySignTitleArray, myServiceTitleArray, systemManagementTitleArray};

        List<View> viewList = new ArrayList<>();
        for (int i = 0; i < categoryTitleArray.length; ++i) {
//            if (i > 0) {
//                View separatorView = getSeparatorView(context, 12);
//                viewList.add(separatorView);
//
//            }
            String title = categoryTitleArray[i];
            int[] imageArray = categoryImageArray[i];
            String[] titleArray = categoryTextArray[i];
            List<ImageAndTextBean> list = new ArrayList<>();
            for (int j = 0; j < imageArray.length; ++j) {
                String subtitle = titleArray[j];
                int id = getGCAIdFromTitle(subtitle);
                ImageAndTextBean bean = new ImageAndTextBean(id, imageArray[j], subtitle);
                list.add(bean);


            }
            int tag = Constants.SETTING_PER_CATEGORY_TAG_START + i;
            ModuleListContentPerCategory category = new ModuleListContentPerCategory(context);
            category.initView(tag, title, list);
            viewList.add(category.getView());


        }
        return viewList;
    }
    public static List<View> getStatisticsSocialRescueModuleListView(Context context) {
//        int[] mySignImageIdArray = new int[]{R.mipmap.g_statistics_module_social_rescue
//
//        };
//        String[] mySignTitleArray = new String[]{Constants.MODULE_TITLE_STATISTICS_SOCIAL_RESCUE
//
//        };

        int[] socialRescueImageIdArray = new int[]{
              /*  R.mipmap.g_social_rescue_module_urban_rural_subsistence,
                R.mipmap.g_social_rescue_module_raise_poor_people,
                R.mipmap.g_social_rescue_module_low_salary_family,
                R.mipmap.g_social_rescue_module_medical_assistance,
                R.mipmap.g_social_rescue_module_temporary_assistance,
                R.mipmap.g_social_rescue_module_disaster_assistance,
                R.mipmap.g_social_rescue_module_check_family_economy*/
//                R.mipmap.icon_dibao_new,
//                R.mipmap.item_tekun_new,
//                R.mipmap.icon_dishouru_new,
//                R.mipmap.item_yiliao_new,
//                R.mipmap.icon_linshi_new,
//                R.mipmap.icon_souzai_new,
//                R.mipmap.icon_jiating_new
        };

        String[] socialRescueTitleArray = new String[]{
                Constants.MODULE_TITLE_STATISTICS_SOCIAL_RESCUE_RURAL_URBAN_SUBSISTENCE,
                Constants.MODULE_TITLE_STATISTICS_SOCIAL_RESCUE_VERY_POOR_BRINGUP,
                Constants.MODULE_TITLE_STATISTICS_SOCIAL_RESCUE_LOW_SALARY_FAMILY,
                Constants.MODULE_TITLE_STATISTICS_SOCIAL_RESCUE_MEDICAL_ASSISTANCE,
                Constants.MODULE_TITLE_STATISTICS_SOCIAL_RESCUE_TEMP_ASSISTANCE,
                Constants.MODULE_TITLE_STATISTICS_SOCIAL_RESCUE_DISASTER_ASSISTANCE,
                Constants.MODULE_TITLE_STATISTICS_SOCIAL_RESCUE_FAMILY_ECONOMY_CHECK
//                "特困人员供养",
//                "低收入家庭",
//                "医疗救助",
//                "临时救助",
//                "受灾救助",
//                "家庭经济情况核对"
        };



        String[] categoryTitleArray = new String[]{"社会救助"};
        int[][] categoryImageArray = new int[][]{socialRescueImageIdArray};
        String[][] categoryTextArray = new String[][]{socialRescueTitleArray};

        List<View> viewList = new ArrayList<>();
        for (int i = 0; i < categoryTitleArray.length; ++i) {
//            if (i > 0) {
//                View separatorView = getSeparatorView(context, 12);
//                viewList.add(separatorView);
//
//            }
            String title = categoryTitleArray[i];
            int[] imageArray = categoryImageArray[i];
            String[] titleArray = categoryTextArray[i];
            List<ImageAndTextBean> list = new ArrayList<>();
            for (int j = 0; j < imageArray.length; ++j) {
                String subtitle = titleArray[j];
                int id = getGCAIdFromTitle(subtitle);
                ImageAndTextBean bean = new ImageAndTextBean(id, imageArray[j], subtitle);
                list.add(bean);


            }
            int tag = Constants.STATISTICS_SOCIAL_RESCUE_PER_CATEGORY_TAG_START + i;
            ModuleListContentPerCategory category = new ModuleListContentPerCategory(context);
            category.initView(tag, title, list);
            viewList.add(category.getView());


        }
        return viewList;
    }


    //获取所有模块列表
    public static List<View> getHandleModuleViewList(Context context) {
        List<View> viewList = getSignHandleBussisAllModuleListView(context);
        return viewList;
    }


    //获取所有模块列表
    public static List<View> getStatisticsModuleViewList(Context context) {
        List<View> viewList = getStatisticsSocialRescueModuleListView(context);
        return viewList;
    }
    public static List<View> getModuleViewList(Context context) {
        List<View> viewList = getSignResidentClientAllModuleListView(context);

//        int[] myImageIdArray = new int[]{R.mipmap.i_all_my_info,R.mipmap.i_all_sign_info,R.mipmap.i_all_resident_directory
//        };
//            String[] myTitleArray = new String[]{"我的信息", "签约信息", "居民通讯录"
//        };
//
//        int[] residentServiceImageIdArray=new int[]{
//            R.mipmap.i_all_online_sign,R.mipmap.i_all_sign_application,R.mipmap.i_all_appointment_execution
//        };
//        String[] residentServiceTitleArray=new String[]{
//            "签约服务包解读", "签约申请", "预约执行"
//        };
//        int[] intelligentManagementImageIdArray=new int[]{
//            R.mipmap.i_all_online_consultation, R.mipmap.i_all_intelligent_honour_agreement_remind,R.mipmap.i_all_sign_service_assess
//        };
//        String[]  intelligentManagementTitleArray=new String[]{
//                "在线咨询","智能履约提醒","签约服务评价"
//        };
//
//        int[] dataAnalysisImageIdArray=new int[]{
//                R.mipmap.i_all_report_analysis,R.mipmap.i_all_sign_service_income
//        };
//        String[] dataAnalysisTitleArray=new String[]{
//                "报表分析","签约服务收入"
//        };
//
//        int[] thirdPartyServiceImageIdArray=new int[]{
//            R.mipmap.i_all_basic_common_hygienism,R.mipmap.i_all_hospital_raise_combination,R.mipmap.i_all_appointment_registration
//        };
//        String[] thirdPartyServiceTitleArray=new String[]{
//            "基本公共卫生", "医养结合","预约挂号"
//        };
//
//        int[] systemManagementImageIdArray=new int[]{
//            R.mipmap.i_all_setting
//        };
//        String[] systemManagementTitleArray=new String[]{
//                "设置"
//        };
//
//
//        String[] categoryTitleArray = new String[]{"我的", "居民服务", "智能管理", "数据分析", "第三方服务", "系统管理"};
//        int[][] categoryImageArray = new int[][]{myImageIdArray, residentServiceImageIdArray,intelligentManagementImageIdArray,
//        dataAnalysisImageIdArray,thirdPartyServiceImageIdArray,systemManagementImageIdArray};
//        String[][] categoryTextArray = new String[][]{myTitleArray, residentServiceTitleArray, intelligentManagementTitleArray,
//        dataAnalysisTitleArray, thirdPartyServiceTitleArray,systemManagementTitleArray};
//
//        List<View> viewList = new ArrayList<>();
//        for (int i = 0; i < categoryTitleArray.length; ++i){
//            if (i>0){
//                View separatorView = getSeparatorView(context, 12);
//                viewList.add(separatorView);
//
//            }
//            String title = categoryTitleArray[i];
//            int[] imageArray = categoryImageArray[i];
//            String[] titleArray = categoryTextArray[i];
//            List<ImageAndTextBean> list = new ArrayList<>();
//            for (int j = 0; j < imageArray.length; ++j){
//                String subtitle = titleArray[j];
//                int id = getIdFromTitle(subtitle);
//                ImageAndTextBean bean = new ImageAndTextBean(id, imageArray[j], subtitle);
//                list.add(bean);
//
//
//            }
//            ModuleListContentPerCategory category=new ModuleListContentPerCategory(context);
//            category.initView(title, list);
//            viewList.add(category.getView());
//
//
//        }

//        List<ImageAndTextBean> list = new ArrayList<>();
//        for (int i = 0; i < 4; ++i){
//            ImageAndTextBean bean = new ImageAndTextBean(i+1, imageIdArray[i], titleArray[i]);
//            list.add(bean);
//        }
//        ModuleListContentPerCategory category=new ModuleListContentPerCategory(context);
//        category.initView("我的", list);
//        viewList.add(category.getView());
//
//        View separatorView = getSeparatorView(context, 12);
//        viewList.add(separatorView);
//
//        List<ImageAndTextBean> list2 = new ArrayList<>();
//        for (int i = 4; i < 8; ++i){
//            ImageAndTextBean bean = new ImageAndTextBean(i+1, imageIdArray[i], titleArray[i]);
//            list2.add(bean);
//        }
//        ModuleListContentPerCategory category2=new ModuleListContentPerCategory(context);
//        category2.initView("生活", list2);
//        viewList.add(category2.getView());
//
//        View separatorView2 = getSeparatorView(context, 12);
//        viewList.add(separatorView2);
//
//
//        List<ImageAndTextBean> list3 = new ArrayList<>();
//        for (int i = 8; i < 13; ++i){
//            ImageAndTextBean bean = new ImageAndTextBean(i+1, imageIdArray[i], titleArray[i]);
//            list3.add(bean);
//        }
//        ModuleListContentPerCategory category3=new ModuleListContentPerCategory(context);
//        category3.initView("服务", list3);
//        viewList.add(category3.getView());
//
//        View separatorView3 = getSeparatorView(context, 12);
//        viewList.add(separatorView3);
//
//
//        List<ImageAndTextBean> list4 = new ArrayList<>();
//        for (int i = 13; i < 14; ++i){
//            ImageAndTextBean bean = new ImageAndTextBean(i+1, imageIdArray[i], titleArray[i]);
//            list4.add(bean);
//        }
//        ModuleListContentPerCategory category4 = new ModuleListContentPerCategory(context);
//        category4.initView("其他", list4);
//        viewList.add(category4.getView());

        return viewList;
    }

    //    public static int getIdFromTitle(String title){
//        title= Util.trimString(title);
//        int result = 0;
//        if (Constants.MODULE_TITLE_MY_INFO.equals(title)){
//            result=Constants.MODULE_ID_MY_INFO;
//        } else if (Constants.MODULE_TITLE_SIGN_INFO.equals(title)){
//            result=Constants.MODULE_ID_SIGN_RESIDENT_INFO;
//        }else if (Constants.MODULE_TITLE_RESIDENT_DIRECTORY.equals(title)){
//            result=Constants.MODULE_ID_RESIDENT_DIRECTORY;
//        }else if (Constants.MODULE_TITLE_ONLINE_SIGN.equals(title)){
//            result=Constants.MODULE_ID_ONLINE_SIGN;
//        }else if (Constants.MODULE_TITLE_SIGN_APPLICATION.equals(title)){
//            result=Constants.MODULE_ID_RESIDENT_SIGN_APPLICATION;
//        }else if (Constants.MODULE_TITLE_APPOINTMENT_EXECUTION.equals(title)){
//            result=Constants.MODULE_ID_RESIDENT_APPOINTMENT_SERVICE;
//        }else if (Constants.MODULE_TITLE_ONLINE_CONSULTATION.equals(title)){
//            result=Constants.MODULE_ID_ONLINE_ADVISORY;
//        }else if (Constants.MODULE_TITLE_INTELLIGENT_HONOUR_AGREEMENT_REMIND.equals(title)){
//            result=Constants.MODULE_ID_INTELLIGENT_HONOUR_AGREEMENT_ALERT;
//        }else if (Constants.MODULE_TITLE_SIGN_SERVICE_ASSESS.equals(title)){
//            result=Constants.MODULE_ID_SIGN_SERVICE_ASSESS;
//        }else if (Constants.MODULE_TITLE_REPORT_ANALYSIS.equals(title)){
//            result=Constants.MODULE_ID_REPORT_ANALYSIS;
//        }else if (Constants.MODULE_TITLE_SIGN_SERVICE_INCOME.equals(title)){
//            result=Constants.MODULE_ID_SIGN_SERVICE_INCOME;
//        }else if (Constants.MODULE_TITLE_BASIC_COMMON_HYGIENISM.equals(title)){
//            result=Constants.MODULE_ID_BASE_COMMON_SANITATION;
//        }else if (Constants.MODULE_TITLE_HOSPITAL_RAISE_COMBINATION.equals(title)){
//            result=Constants.MODULE_ID_HOSPITAL_BRINGUP_COMBINATION;
//        }else if (Constants.MODULE_TITLE_APPOINTMENT_REGISTRATION.equals(title)){
//            result=Constants.MODULE_ID_APPOINTMENT_REGISTRATION;
//        }else if (Constants.MODULE_TITLE_SETTING.equals(title)){
//            result=Constants.MODULE_ID_SETTING;
//        }
//        return result;
//    }
    public static int getGCAIdFromTitle(String title) {
        title = Util.trimString(title);
        int result = 0;
        if (Constants.MODULE_TITLE_STATISTICS_SOCIAL_RESCUE_RURAL_URBAN_SUBSISTENCE.equals(title)){
            result=Constants.MODULE_ID_STATISTICS_SOCIAL_RESCUE_RURAL_URBAN_SUBSISTENCE;
        } else if (Constants.MODULE_TITLE_STATISTICS_SOCIAL_RESCUE_VERY_POOR_BRINGUP.equals(title)){
            result=Constants.MODULE_ID_STATISTICS_SOCIAL_RESCUE_VERY_POOR_BRINGUP;
        } else if (Constants.MODULE_TITLE_STATISTICS_SOCIAL_RESCUE_LOW_SALARY_FAMILY.equals(title)){
            result=Constants.MODULE_ID_STATISTICS_SOCIAL_RESCUE_LOW_SALARY_FAMILY;
        } else if (Constants.MODULE_TITLE_STATISTICS_SOCIAL_RESCUE_MEDICAL_ASSISTANCE.equals(title)){
            result=Constants.MODULE_ID_STATISTICS_SOCIAL_RESCUE_MEDICAL_ASSISTANCE;
        } else if (Constants.MODULE_TITLE_STATISTICS_SOCIAL_RESCUE_TEMP_ASSISTANCE.equals(title)){
            result=Constants.MODULE_ID_STATISTICS_SOCIAL_RESCUE_TEMP_ASSISTANCE;
        } else if (Constants.MODULE_TITLE_STATISTICS_SOCIAL_RESCUE_DISASTER_ASSISTANCE.equals(title)){
            result=Constants.MODULE_ID_STATISTICS_SOCIAL_RESCUE_DISASTER_ASSISTANCE;
        } else if (Constants.MODULE_TITLE_STATISTICS_SOCIAL_RESCUE_FAMILY_ECONOMY_CHECK.equals(title)){
            result=Constants.MODULE_ID_STATISTICS_SOCIAL_RESCUE_FAMILY_ECONOMY_CHECK;
        }
        else if (Constants.MODULE_TITLE_POLICY_NEWS.equals(title)) {
            result = Constants.MODULE_ID_POLICY_NEWS;
        } else if (Constants.MODULE_TITLE_NOTIFICATION.equals(title)) {
            result = Constants.MODULE_ID_NOTIFICATION;
        } else if (Constants.MODULE_TITLE_MAP_SERVICE.equals(title)){
            result = Constants.MODULE_ID_MAP_SERVICE;
        }
        else if(Constants.MODULE_TILLE_SOCILARGNATION.equals(title)){
            result = Constants.MODULE_ID_SOCIAILORGNATION;
        }
        else if(Constants.MODULE_TILLE_QUEER.equals(title)){
            result = Constants.MODULE_ID_QUERY;
        }
        else if(Constants.MODULE_TILLE_SOCIALSTATION.equals(title)){
            result = Constants.MODULE_ID_SOCIALSTATION;
        }
        else if(Constants.MODULE_TILLE_USUALLYTRABLE.equals(title)){
            result = Constants.MODULE_ID_USUALLYTRUBLE;
        }
        else if(Constants.MODULE_TILLE_REJESTION.equals(title)){
            result = Constants.MODULE_ID_REJESTION;
        }
        else if (Constants.MODULE_TITLE_GUIDE.equals(title)) {
            result = Constants.MODULE_ID_GUIDE;
        } else if (Constants.MODULE_TITLE_POLITICS.equals(title)) {
            result = Constants.MODULE_ID_POLITICS;
        } else if (Constants.MODULE_TITLE_CDPF.equals(title)) {
            result = Constants.MODULE_ID_CDPF;
        } else if (Constants.MODULE_TITLE_CIVIL.equals(title)) {
            result = Constants.MODUBLE_ID_CIVIL;
        } else if (Constants.MODULE_TITLE_Hygiene.equals(title)) {
            result = Constants.MODULE_ID_Hygiene;
        } else if (Constants.MODULE_TITLE_EDU.equals(title)) {
            result = Constants.MODULE_ID_EDU;
        } else if (Constants.MODULE_TITLE_Union.equals(title)) {
            result = Constants.MODULE_ID_Union;
        } else if (Constants.MODULE_TITLE_agency.equals(title)) {
            result = Constants.MODULE_ID_agency;
        } else if (Constants.MODULE_TITLE_build.equals(title)) {
            result = Constants.MODULE_ID_build;
        } else if (Constants.MODULE_TITLE_Business.equals(title)) {
            result = Constants.MODULE_ID_Business;
        } else if (Constants.MODULE_TITLE_Committee.equals(title)) {
            result = Constants.MODULE_ID_Committee;
        } else if (Constants.MODULE_TITLE_judicial.equals(title)) {
            result = Constants.MODULE_ID_judicial;
        } else if (Constants.MODULE_TITLE_Government.equals(title)) {
            result = Constants.MODULE_ID_Government;
        } else if (Constants.MODULE_TITLE_Federation.equals(title)) {
            result = Constants.MODULE_ID_Federation;
        } else if (Constants.MODULE_TITLE_HANDLEPROGRESS.equals(title)) {
            result = Constants.MODUBLE_ID_HANDLEPROGRESS;
        } else if (Constants.MODULE_TITLE_GUIDE.equals(title)) {
            result = Constants.MODULE_ID_GUIDE;
        } else if (Constants.MODULE_TITLE_POLITICS.equals(title)) {
            result = Constants.MODULE_ID_POLITICS;
        } else if (Constants.MODULE_TITLE_CDPF.equals(title)) {
            result = Constants.MODULE_ID_CDPF;
        } else if (Constants.MODULE_TITLE_CIVIL.equals(title)) {
            result = Constants.MODUBLE_ID_CIVIL;
        } else if (Constants.MODULE_TITLE_Hygiene.equals(title)) {
            result = Constants.MODULE_ID_Hygiene;
        } else if (Constants.MODULE_TITLE_EDU.equals(title)) {
            result = Constants.MODULE_ID_EDU;
        } else if (Constants.MODULE_TITLE_Union.equals(title)) {
            result = Constants.MODULE_ID_Union;
        } else if (Constants.MODULE_TITLE_agency.equals(title)) {
            result = Constants.MODULE_ID_agency;
        } else if (Constants.MODULE_TITLE_build.equals(title)) {
            result = Constants.MODULE_ID_build;
        } else if (Constants.MODULE_TITLE_Business.equals(title)) {
            result = Constants.MODULE_ID_Business;
        } else if (Constants.MODULE_TITLE_Committee.equals(title)) {
            result = Constants.MODULE_ID_Committee;
        } else if (Constants.MODULE_TITLE_judicial.equals(title)) {
            result = Constants.MODULE_ID_judicial;
        } else if (Constants.MODULE_TITLE_Government.equals(title)) {
            result = Constants.MODULE_ID_Government;
        } else if (Constants.MODULE_TITLE_Federation.equals(title)) {
            result = Constants.MODULE_ID_Federation;
        } else if (Constants.MODULE_TITLE_IG_GUIDE.equals(title)) {
            result = Constants.MODULE_ID_IG_GUIDE;
        } else if (Constants.MODULE_TITLE_HANDLEPROGRESS.equals(title)) {
            result = Constants.MODUBLE_ID_HANDLEPROGRESS;
        } else if (Constants.MODLE_TITLE_HANDLEHIS.equals(title)) {
            result = Constants.MODUBLE_ID_HANDLEHIS;
        } else if (Constants.MODULE_TITLE_Reception.equals(title)) {
            result = Constants.MODULE_ID_Reception;
        }
        else if (Constants.MODULE_TITLE_ZHUDONG_FAXIAN.equals(title)) {
            result = Constants.MODULE_ID_FIND;
        }else if (Constants.MODULE_TITLE_CHENGXIANG_DIBAO.equals(title)) {
            result = Constants.MODULE_ID_CHENGXIANG_DIBAO;
        }else if (Constants.MODULE_TITLE_TEKUN_GONGYANG.equals(title)) {
            result = Constants.MODUBLE_ID_TEKUN;
        }else if (Constants.MODULE_TITLE_YILIAO_JIUZHU.equals(title)) {
            result = Constants.MODUBLE_ID_YILIAO_JIUZHU;
        }else if (Constants.MODLE_TITLE_LINSHI_JIUZHU.equals(title)) {
            result = Constants.MODUBLE_ID_LINSHI_JIUZHU;
        }else if (Constants.MODULE_TITLE_SOUZAI_JIUZHU.equals(title)) {
            result = Constants.MODULE_ID_SOUZAI_JIUZHU;
        }else if (Constants.MODULE_TILLE_RUHU_DIAOCHA.equals(title)) {
            result = Constants.MODULE_ID_RUHU_DIAOCHA;
        }else if (Constants.MODULE_TILLE_MINZHU_PINGYI.equals(title)) {
            result = Constants.MODULE_ID_MINZHU_PINYI;
        }
//        if (Constants.MODULE_TITLE_ONLINE_SIGN_APPLICATION.equals(title)){
//            result=Constants.MODULE_ID_ONLINE_SIGN_APPLICATION;
//        } else if (Constants.MODULE_TITLE_MY_SIGN_INFO.equals(title)){
//            result=Constants.MODULE_ID_MY_SIGN_INFO;
//        }else if (Constants.MODULE_TITLE_MY_SIGN_AGREEMENT.equals(title)){
//            result=Constants.MODULE_ID_MY_SIGN_AGREEMENT;
//        }else if (Constants.MODULE_TITLE_SIGN_POLICY_TRANSLATE.equals(title)){
//            result=Constants.MODULE_ID_SIGN_POLICY_TRANSLATE;
//        }else if (Constants.MODULE_TITLE_SIGN_SERVICE_PACKAGE_TRANSLATE.equals(title)){
//            result=Constants.MODULE_ID_SIGN_SERVICE_PACKAGE_TRANSLATE;
//        }else if (Constants.MODULE_TITLE_INTELLIGENT_HONOUR_AGREEMENT_REMIND.equals(title)){
//            result=Constants.MODULE_ID_INTELLIGENT_HONOUR_AGREEMENT_REMIND;
//        }else if (Constants.MODULE_TITLE_SIGN_SERVICE_ASSESS.equals(title)){
//            result=Constants.MODULE_ID_SIGN_SERVICE_ASSESS;
//        }else if (Constants.MODULE_TITLE_APPOINTMENT_SIGN_SERVICE.equals(title)){
//            result=Constants.MODULE_ID_APPOINTMENT_SIGN_SERVICE;
//        }else if (Constants.MODULE_TITLE_CALL_DOCTOR.equals(title)){
//            result=Constants.MODULE_ID_CALL_DOCTOR;
//        }else if (Constants.MODULE_TITLE_ONLINE_CONSULTATION.equals(title)){
//            result=Constants.MODULE_ID_ONLINE_CONSULTATION;
//        }else if (Constants.MODULE_TITLE_SIGN_COMPLAINT.equals(title)){
//            result=Constants.MODULE_ID_SIGN_COMPLAINT;
//        }else if (Constants.MODULE_TITLE_SETTING.equals(title)){
//            result=Constants.MODULE_ID_SETTING;
//        }
        return result;
    }

    public static List<View> getGuideOfficeModuleListView(Context context, String[] mySignTitleArray) {
//        int[] mySignImageIdArray = new int[]{
//                R.mipmap.icon_politics, R.mipmap.icon_cdpf, R.mipmap.ic_hygiene, R.mipmap.icon_edu,
//        R.mipmap.icon_union, R.mipmap.icon_agency, R.mipmap.icon_build, R.mipmap.icon_business,
//                R.mipmap.icon_committee, R.mipmap.icon_judicial, R.mipmap.ic_government, R.mipmap.icon_federation,
//    };

        int[] mySignImageIdArray = new int[  mySignTitleArray.length];

    for (int i=0;i<mySignTitleArray.length;i++){
//        if (mySignTitleArray[i].equals("民政")){
//            mySignImageIdArray[i]= R.mipmap.icon_politics;
//
//        }else if (mySignTitleArray[i].equals("残联")){
//            mySignImageIdArray[i]= R.mipmap.icon_cdpf;
//        }else if (mySignTitleArray[i].equals("卫计委")){
//            mySignImageIdArray[i]= R.mipmap.ic_hygiene;
//        }else if (mySignTitleArray[i].equals("卫计")){
//            mySignImageIdArray[i]= R.mipmap.ic_hygiene;
//        }else if (mySignTitleArray[i].equals("教体局")){
//            mySignImageIdArray[i]= R.mipmap.icon_edu;
//        }else if (mySignTitleArray[i].equals("教育")){
//            mySignImageIdArray[i]= R.mipmap.icon_edu;
//        }else if (mySignTitleArray[i].equals("总工会")){
//            mySignImageIdArray[i]= R.mipmap.icon_union;
//        }else if (mySignTitleArray[i].equals("人社")){
//            mySignImageIdArray[i]= R.mipmap.icon_agency;
//        }else if (mySignTitleArray[i].equals("妇联")){
//            mySignImageIdArray[i]= R.mipmap.icon_federation;
//        }else if (mySignTitleArray[i].equals("住建")){
//            mySignImageIdArray[i]= R.mipmap.icon_build;
//        }else if (mySignTitleArray[i].equals("住建局")){
//            mySignImageIdArray[i]= R.mipmap.icon_build;
//        }else if (mySignTitleArray[i].equals("工商联")){
//            mySignImageIdArray[i]= R.mipmap.icon_business;
//        }else if (mySignTitleArray[i].equals("工会")){
//            mySignImageIdArray[i]= R.mipmap.icon_union;
//        }else if (mySignTitleArray[i].equals("司法")){
//            mySignImageIdArray[i]= R.mipmap.icon_judicial;
//        }else if (mySignTitleArray[i].equals("综治办")){
//            mySignImageIdArray[i]= R.mipmap.ic_government;
//        }else if (mySignTitleArray[i].equals("团区委")){
//            mySignImageIdArray[i]= R.mipmap.icon_committee;
//        }else if (mySignTitleArray[i].equals("信访")){
//            mySignImageIdArray[i]= R.mipmap.ic_messagefang;
//        }else if (mySignTitleArray[i].equals("慈善协会")){
//            mySignImageIdArray[i]= R.mipmap.ic_charitable;
//        }else if (mySignTitleArray[i].equals("其他救助")){
//            mySignImageIdArray[i]= R.mipmap.ic_others;
//        }



    }
//        mySignTitleArray = new String[]{
//                "民政", "残联", "卫计委", "教体局",
//                "总工会", "人社", "妇联", "住建",
//                "工商联", "团区委", "司法", "综治办"
//
//        };

//    String[] categoryTitleArray = new String[]{"资讯", "业务", "便民"};
        int[][] categoryImageArray = new int[][]{mySignImageIdArray};
        String[][] categoryTextArray = new String[][]{mySignTitleArray};

        List<View> viewList = new ArrayList<>();
        for (int i = 0; i < 1; ++i) {
            if (i > 0) {
                View separatorView = getSeparatorView(context, 12);
                viewList.add(separatorView);

            }
//        String title = categoryTitleArray[i];
            int[] imageArray = categoryImageArray[i];
            String[] titleArray = categoryTextArray[i];
            List<ImageAndTextBean> list = new ArrayList<>();
            for (int j = 0; j < imageArray.length; ++j) {
                String subtitle = titleArray[j];
                int id = getGCAIdFromTitle(subtitle);
                ImageAndTextBean bean = new ImageAndTextBean(id, imageArray[j], subtitle);
                list.add(bean);


            }
            int tag = Constants.SETTING_PER_CATEGORY_TAG_START + i;
            GuideListContentPerCategory category = new GuideListContentPerCategory(context);
            category.initView(tag, null, list);
            viewList.add(category.getView());


        }
        return viewList;
    }

    public static View getSeparatorView(Context context, int height) {
        View separatorView = new View(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        separatorView.setLayoutParams(params);
//        separatorView.setBackgroundResource(R.color.all_background_color);
        return separatorView;
    }





    public static List<View> getSignHandleBussisAllModuleListView(Context context) {
        int[] mySignImageIdArray = new int[]{
//                R.mipmap.icon

        };

        String[] mySignTitleArray = new String[]{"主动发现"

        };


        int[] myServiceImageIdArray;
        String[] myServiceTitleArray;

            myServiceImageIdArray = new int[]{
//                    R.mipmap.icon_dibao_new,  R.mipmap.item_tekun_new,
//                    R.mipmap.item_yiliao_new,  R.mipmap.icon_linshi_new,R.mipmap.icon_souzai_new
                    //           , R.mipmap.icon_social_orgnization
                    //            , R.mipmap.icon_referto
            };
            myServiceTitleArray = new String[]{
                    "城乡低保",  "特困人员供养",
                    "医疗救助",  "临时救助",  "受灾救助"
                    //          , "社会组织"
                    //           , "查询"
            };



        int[] systemManagementImageIdArray = new int[]{
//                R.mipmap.icon_ruhu_new, R.mipmap.icon_minzhu_new
        };
        String[] systemManagementTitleArray = new String[]{
                "入户调查", "民主评议"
        };


        String[] categoryTitleArray = new String[]{"主动发现", "代为申请", "调查评议"};
        int[][] categoryImageArray = new int[][]{mySignImageIdArray, myServiceImageIdArray, systemManagementImageIdArray};
        String[][] categoryTextArray = new String[][]{mySignTitleArray, myServiceTitleArray, systemManagementTitleArray};

        List<View> viewList = new ArrayList<>();
        for (int i = 0; i < categoryTitleArray.length; ++i) {
//            if (i > 0) {
//                View separatorView = getSeparatorView(context, 12);
//                viewList.add(separatorView);
//
//            }
            String title = categoryTitleArray[i];
            int[] imageArray = categoryImageArray[i];
            String[] titleArray = categoryTextArray[i];
            List<ImageAndTextBean> list = new ArrayList<>();
            for (int j = 0; j < imageArray.length; ++j) {
                String subtitle = titleArray[j];
                int id = getGCAIdFromTitle1(subtitle);
                ImageAndTextBean bean = new ImageAndTextBean(id, imageArray[j], subtitle);
                list.add(bean);


            }
            int tag = Constants.SETTING_PER_CATEGORY_TAG_START + i;
            ModuleListContentPerCategory category = new ModuleListContentPerCategory(context);
            category.initView(tag, title, list);
            viewList.add(category.getView());


        }
        return viewList;
    }



    public static int getGCAIdFromTitle1(String title) {
        title = Util.trimString(title);
        int result = 0;
       if (Constants.MODULE_TITLE_POLICY_NEWS.equals(title)) {
            result = Constants.MODULE_ID_POLICY_NEWS;
        } else if (Constants.MODULE_TITLE_NOTIFICATION.equals(title)) {
            result = Constants.MODULE_ID_NOTIFICATION;
        } else if (Constants.MODULE_TITLE_MAP_SERVICE.equals(title)){
            result = Constants.MODULE_ID_MAP_SERVICE;
        }
        else if(Constants.MODULE_TILLE_SOCILARGNATION.equals(title)){
            result = Constants.MODULE_ID_SOCIAILORGNATION;
        }
        else if(Constants.MODULE_TILLE_QUEER.equals(title)){
            result = Constants.MODULE_ID_QUERY;
        }
        else if(Constants.MODULE_TILLE_SOCIALSTATION.equals(title)){
            result = Constants.MODULE_ID_SOCIALSTATION;
        }
        else if(Constants.MODULE_TILLE_USUALLYTRABLE.equals(title)){
            result = Constants.MODULE_ID_USUALLYTRUBLE;
        }
        else if(Constants.MODULE_TILLE_REJESTION.equals(title)){
            result = Constants.MODULE_ID_REJESTION;
        }
        else if (Constants.MODULE_TITLE_GUIDE.equals(title)) {
            result = Constants.MODULE_ID_GUIDE;
        } else if (Constants.MODULE_TITLE_POLITICS.equals(title)) {
            result = Constants.MODULE_ID_POLITICS;
        } else if (Constants.MODULE_TITLE_CDPF.equals(title)) {
            result = Constants.MODULE_ID_CDPF;
        } else if (Constants.MODULE_TITLE_CIVIL.equals(title)) {
            result = Constants.MODUBLE_ID_CIVIL;
        } else if (Constants.MODULE_TITLE_Hygiene.equals(title)) {
            result = Constants.MODULE_ID_Hygiene;
        } else if (Constants.MODULE_TITLE_EDU.equals(title)) {
            result = Constants.MODULE_ID_EDU;
        } else if (Constants.MODULE_TITLE_Union.equals(title)) {
            result = Constants.MODULE_ID_Union;
        } else if (Constants.MODULE_TITLE_agency.equals(title)) {
            result = Constants.MODULE_ID_agency;
        } else if (Constants.MODULE_TITLE_build.equals(title)) {
            result = Constants.MODULE_ID_build;
        } else if (Constants.MODULE_TITLE_Business.equals(title)) {
            result = Constants.MODULE_ID_Business;
        } else if (Constants.MODULE_TITLE_Committee.equals(title)) {
            result = Constants.MODULE_ID_Committee;
        } else if (Constants.MODULE_TITLE_judicial.equals(title)) {
            result = Constants.MODULE_ID_judicial;
        } else if (Constants.MODULE_TITLE_Government.equals(title)) {
            result = Constants.MODULE_ID_Government;
        } else if (Constants.MODULE_TITLE_Federation.equals(title)) {
            result = Constants.MODULE_ID_Federation;
        } else if (Constants.MODULE_TITLE_HANDLEPROGRESS.equals(title)) {
            result = Constants.MODUBLE_ID_HANDLEPROGRESS;
        } else if (Constants.MODULE_TITLE_GUIDE.equals(title)) {
            result = Constants.MODULE_ID_GUIDE;
        } else if (Constants.MODULE_TITLE_POLITICS.equals(title)) {
            result = Constants.MODULE_ID_POLITICS;
        } else if (Constants.MODULE_TITLE_CDPF.equals(title)) {
            result = Constants.MODULE_ID_CDPF;
        } else if (Constants.MODULE_TITLE_CIVIL.equals(title)) {
            result = Constants.MODUBLE_ID_CIVIL;
        } else if (Constants.MODULE_TITLE_Hygiene.equals(title)) {
            result = Constants.MODULE_ID_Hygiene;
        } else if (Constants.MODULE_TITLE_EDU.equals(title)) {
            result = Constants.MODULE_ID_EDU;
        } else if (Constants.MODULE_TITLE_Union.equals(title)) {
            result = Constants.MODULE_ID_Union;
        } else if (Constants.MODULE_TITLE_agency.equals(title)) {
            result = Constants.MODULE_ID_agency;
        } else if (Constants.MODULE_TITLE_build.equals(title)) {
            result = Constants.MODULE_ID_build;
        } else if (Constants.MODULE_TITLE_Business.equals(title)) {
            result = Constants.MODULE_ID_Business;
        } else if (Constants.MODULE_TITLE_Committee.equals(title)) {
            result = Constants.MODULE_ID_Committee;
        } else if (Constants.MODULE_TITLE_judicial.equals(title)) {
            result = Constants.MODULE_ID_judicial;
        } else if (Constants.MODULE_TITLE_Government.equals(title)) {
            result = Constants.MODULE_ID_Government;
        } else if (Constants.MODULE_TITLE_Federation.equals(title)) {
            result = Constants.MODULE_ID_Federation;
        } else if (Constants.MODULE_TITLE_IG_GUIDE.equals(title)) {
            result = Constants.MODULE_ID_IG_GUIDE;
        } else if (Constants.MODULE_TITLE_HANDLEPROGRESS.equals(title)) {
            result = Constants.MODUBLE_ID_HANDLEPROGRESS;
        } else if (Constants.MODLE_TITLE_HANDLEHIS.equals(title)) {
            result = Constants.MODUBLE_ID_HANDLEHIS;
        } else if (Constants.MODULE_TITLE_Reception.equals(title)) {
            result = Constants.MODULE_ID_Reception;
        }
        else if (Constants.MODULE_TITLE_ZHUDONG_FAXIAN.equals(title)) {
            result = Constants.MODULE_ID_FIND;
        }else if (Constants.MODULE_TITLE_CHENGXIANG_DIBAO.equals(title)) {
            result = Constants.MODULE_ID_CHENGXIANG_DIBAO;
        }else if (Constants.MODULE_TITLE_TEKUN_GONGYANG.equals(title)) {
            result = Constants.MODUBLE_ID_TEKUN;
        }else if (Constants.MODULE_TITLE_YILIAO_JIUZHU.equals(title)) {
            result = Constants.MODUBLE_ID_YILIAO_JIUZHU;
        }else if (Constants.MODLE_TITLE_LINSHI_JIUZHU.equals(title)) {
            result = Constants.MODUBLE_ID_LINSHI_JIUZHU;
        }else if (Constants.MODULE_TITLE_SOUZAI_JIUZHU.equals(title)) {
            result = Constants.MODULE_ID_SOUZAI_JIUZHU;
        }else if (Constants.MODULE_TILLE_RUHU_DIAOCHA.equals(title)) {
            result = Constants.MODULE_ID_RUHU_DIAOCHA;
        }else if (Constants.MODULE_TILLE_MINZHU_PINGYI.equals(title)) {
            result = Constants.MODULE_ID_MINZHU_PINYI;
        }
//        if (Constants.MODULE_TITLE_ONLINE_SIGN_APPLICATION.equals(title)){
//            result=Constants.MODULE_ID_ONLINE_SIGN_APPLICATION;
//        } else if (Constants.MODULE_TITLE_MY_SIGN_INFO.equals(title)){
//            result=Constants.MODULE_ID_MY_SIGN_INFO;
//        }else if (Constants.MODULE_TITLE_MY_SIGN_AGREEMENT.equals(title)){
//            result=Constants.MODULE_ID_MY_SIGN_AGREEMENT;
//        }else if (Constants.MODULE_TITLE_SIGN_POLICY_TRANSLATE.equals(title)){
//            result=Constants.MODULE_ID_SIGN_POLICY_TRANSLATE;
//        }else if (Constants.MODULE_TITLE_SIGN_SERVICE_PACKAGE_TRANSLATE.equals(title)){
//            result=Constants.MODULE_ID_SIGN_SERVICE_PACKAGE_TRANSLATE;
//        }else if (Constants.MODULE_TITLE_INTELLIGENT_HONOUR_AGREEMENT_REMIND.equals(title)){
//            result=Constants.MODULE_ID_INTELLIGENT_HONOUR_AGREEMENT_REMIND;
//        }else if (Constants.MODULE_TITLE_SIGN_SERVICE_ASSESS.equals(title)){
//            result=Constants.MODULE_ID_SIGN_SERVICE_ASSESS;
//        }else if (Constants.MODULE_TITLE_APPOINTMENT_SIGN_SERVICE.equals(title)){
//            result=Constants.MODULE_ID_APPOINTMENT_SIGN_SERVICE;
//        }else if (Constants.MODULE_TITLE_CALL_DOCTOR.equals(title)){
//            result=Constants.MODULE_ID_CALL_DOCTOR;
//        }else if (Constants.MODULE_TITLE_ONLINE_CONSULTATION.equals(title)){
//            result=Constants.MODULE_ID_ONLINE_CONSULTATION;
//        }else if (Constants.MODULE_TITLE_SIGN_COMPLAINT.equals(title)){
//            result=Constants.MODULE_ID_SIGN_COMPLAINT;
//        }else if (Constants.MODULE_TITLE_SETTING.equals(title)){
//            result=Constants.MODULE_ID_SETTING;
//        }
        return result;
    }
}
