package com.jqsoft.nursing.di.ui.activity;

import android.os.Build;

import com.jqsoft.nursing.R;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.base.ParametersFactory;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultNewBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.SRCLoginDataDictionaryBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.SRCLoginSalvationBean;
import com.jqsoft.nursing.bean.resident.SRCLoginAreaBean;
import com.jqsoft.nursing.bean.resident.SRCLoginBean;
import com.jqsoft.nursing.di.contract.SRCLoginContract;
import com.jqsoft.nursing.di.module.SRCLoginModule;
import com.jqsoft.nursing.di.presenter.SRCLoginPresenter;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.di_app.DaggerApplication;
import com.jqsoft.nursing.rx.RxBus;
import com.jqsoft.nursing.util.Util;
import com.jqsoft.nursing.utils.LogUtil;

import org.litepal.LitePal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017-07-18.
 */
//应用介绍页面
public class IntroductionActivity extends AbstractActivity implements SRCLoginContract.View {
//    @BindView(R.id.liv_image)
//    LargeImageView livImage;

    CompositeSubscription mCompositeSubscription;

    @Inject
    SRCLoginPresenter loginPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_introduction;
    }

    @Override
    protected void initData() {
//        test();
//        hasCompatibleCPU();
    }


    private void test(){
        String arch = "";//cpu类型
        try {
            Class<?> clazz = Class.forName("Android.os.SystemProperties");
            Method get = clazz.getDeclaredMethod("get", new Class[] {String.class});
            arch = (String)get.invoke(clazz, new Object[] {"ro.product.cpu.abi"});
        } catch(Exception e) {
            e.printStackTrace();
        }
        LogUtil.i("arch "+arch);
    }

    public boolean hasCompatibleCPU() {
        // If already checked return cached result

        String CPU_ABI = android.os.Build.CPU_ABI;
        String CPU_ABI2 = "none";
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) { // CPU_ABI2
            // since
            // 2.2
            try {
                CPU_ABI2 = (String) android.os.Build.class.getDeclaredField(
                        "CPU_ABI2").get(null);
            } catch (Exception e) {
                return false;
            }
        }

        if (CPU_ABI.equals("armeabi-v7a") || CPU_ABI2.equals("armeabi-v7a")) {
            return true;
        }

        try {
            FileReader fileReader = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fileReader);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("ARMv7")) {
                    return true;
                }

            }
            fileReader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    protected void initView() {
//        livImage.setImage(R.mipmap.i_introduction);
//        livImage.setEnabled(false);
        initTimer();

      /*  List<SRCLoginAreaBean> LoginAreaList = LitePal.findAll(SRCLoginAreaBean.class);
        List<SRCLoginSalvationBean> LoginSalvationList = LitePal.findAll(SRCLoginSalvationBean.class);
        List<SRCLoginDataDictionaryBean> LoginDictionaryList = LitePal.findAll(SRCLoginDataDictionaryBean.class);
*/

     //   gotoLoginActivity();
       /* int LoginAreaList = LitePal.count(SRCLoginAreaBean.class);

        int LoginSalvationList = LitePal.count(SRCLoginSalvationBean.class);

        int LoginDictionaryList = LitePal.count(SRCLoginDataDictionaryBean.class);


        if(LoginAreaList >5000  && LoginSalvationList!=0 && LoginDictionaryList!=0){
            gotoLoginActivity();

        }else {
            Toast.makeText(getApplicationContext(),"正在初始化数据,请耐心等候!",Toast.LENGTH_LONG).show();
            initAlldata();

        }*/
    }

    @Override
    protected void loadData() {
      //  registerFinishIntroductionActivityEvent();

    }

    protected void initInject() {
        DaggerApplication.get(this)
                .getAppComponent()
                .addArea(new SRCLoginModule(this))
                .inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
      //  unregisterFinishIntroductionActivityEvent();
    }

    private void initTimer(){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
//                gotoWorkbenchActivity();
                gotoLoginActivity();
            }
        };
        timer.schedule(timerTask, Constants.INTRODUCTION_DISPLAY_DURATION);
    }

    private void gotoLoginActivity(){
//        ActivityUtils.launchActivity(Constants.PACKAGE_NAME, Constants.LOGIN_ACTIVITY_NAME);

      //  Util.hideGifProgressDialog(this);
        Util.gotoActivity(this, LoginActivityNew.class);
        finish();
    }

    private void gotoWorkbenchActivity(){
        Util.gotoActivity(this, WorkbenchActivity.class);
    }

    private void registerFinishIntroductionActivityEvent(){
        Subscription subscription = RxBus.getDefault().toObservable(Constants.EVENT_TYPE_FINISH_INTRODUCTION_ACTIVITY, Boolean.class)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean b) {
                        if (b){
                            finish();
                        }
                    }
                });
        if (mCompositeSubscription==null){
            mCompositeSubscription=new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    private void unregisterFinishIntroductionActivityEvent(){
        if (mCompositeSubscription!=null && mCompositeSubscription.hasSubscriptions()){
            mCompositeSubscription.unsubscribe();
        }
    }




//    @Override
//    public void onLoginSuccess(HttpResultBaseBean<SRCLoginBean> bean) {
//
//    }



    @Override
    public void onLoginSuccess(HttpResultNewBaseBean<String> bean) {

    }

    @Override
    public void onLoginFailure(String message) {

    }

    @Override
    public void onLoginAreaSuccess(HttpResultBaseBean<List<SRCLoginAreaBean>> bean) {
        List<SRCLoginAreaBean> lrb = bean.getData();
      //  List<SRCLoginAreaBean> LoginAreaList = LitePal.findAll(SRCLoginAreaBean.class);

        int LoginAreaList=LitePal.count(SRCLoginAreaBean.class);
        if(lrb.size()==0){
            //  String s1=lrb.get(0).getAreaName();
        }else {
            if(LoginAreaList==0){
                /*for(int i=0;i<lrb.size();i++){
                    lrb.get(i).save();
                }*/

                LitePal.saveAll(lrb);







            }else {

            }

            //    Util.showToast(this, "区域初始化完成");


            int LoginSalvationList=LitePal.count(SRCLoginSalvationBean.class);

          //  List<SRCLoginSalvationBean> LoginSalvationList = LitePal.findAll(SRCLoginSalvationBean.class);
            if(LoginSalvationList==0){
                Map<String, String> map3 = ParametersFactory.getLoginMapSalvation(this);
                loginPresenter.mainSalvation(map3);
            }else {

            }



        }
    }

    @Override
    public void onLoginAreaFailure(String message) {
        Util.hideGifProgressDialog(this);
        Util.showToast(this, message);
        gotoLoginActivity();
    }

    @Override
    public void onLoginDataDictionatySuccess(HttpResultBaseBean<List<SRCLoginDataDictionaryBean>> bean) {
        List<SRCLoginDataDictionaryBean> lrb = bean.getData();

      //  List<SRCLoginDataDictionaryBean> LoginDictionaryList = LitePal.findAll(SRCLoginDataDictionaryBean.class);
        int LoginDictionaryList=LitePal.count(SRCLoginDataDictionaryBean.class);

        if(lrb.size()==0){
            //  String s1=lrb.get(0).getAreaName();
        }else {
            if(LoginDictionaryList==0){
                LitePal.saveAll(lrb);
            }else {

            }

            int LoginDictionaryList2=LitePal.count(SRCLoginDataDictionaryBean.class);
            int LoginSalvationList=LitePal.count(SRCLoginSalvationBean.class);


            if(LoginDictionaryList2!=0 &&LoginSalvationList!=0){
                gotoLoginActivity();

            }else {
                gotoLoginActivity();

            }

        }
    }

    @Override
    public void onLoginDataDictionatyFailure(String message) {
        Util.hideGifProgressDialog(this);
        Util.showToast(this, message);
        gotoLoginActivity();
    }

    @Override
    public void onLoginSalvationSuccess(HttpResultBaseBean<List<SRCLoginSalvationBean>> bean) {
        List<SRCLoginSalvationBean> lrb = bean.getData();

      //  List<SRCLoginSalvationBean> LoginSalvationList = LitePal.findAll(SRCLoginSalvationBean.class);

        int LoginSalvationList =LitePal.count(SRCLoginSalvationBean.class);

        if(lrb.size()==0){
            //  String s1=lrb.get(0).getAreaName();
        }else {
            if(LoginSalvationList==0){

                LitePal.saveAll(lrb);
            }else {


            }

            //   Util.showToast(this, "救助事项初始化完成");

        //    List<SRCLoginDataDictionaryBean> LoginDictionaryList = LitePal.findAll(SRCLoginDataDictionaryBean.class);

            int LoginDictionaryList=LitePal.count(SRCLoginDataDictionaryBean.class);
            if(LoginDictionaryList==0){
                Map<String, String> map2 = ParametersFactory.getLoginMapDictionary(this);
                loginPresenter.mainDictionary(map2);
            }else {

            }

        }
    }

    @Override
    public void onLoginSalvationFailure(String message) {
        Util.hideGifProgressDialog(this);
        Util.showToast(this, message);
        gotoLoginActivity();
    }

    private void initAlldata(){
        List<SRCLoginAreaBean> LoginAreaList = LitePal.findAll(SRCLoginAreaBean.class);
        if(LoginAreaList.size()<5000){
            LitePal.deleteAll(SRCLoginAreaBean.class);
            Map<String, String> map1 = ParametersFactory.getLoginMapArea(this);
            loginPresenter.mainArea(map1);
        }else {
            List<SRCLoginSalvationBean> LoginSalvationList = LitePal.findAll(SRCLoginSalvationBean.class);
            if(LoginSalvationList.size()==0){
                Map<String, String> map3 = ParametersFactory.getLoginMapSalvation(this);
                loginPresenter.mainSalvation(map3);
            }else {
                List<SRCLoginDataDictionaryBean> LoginDictionaryList = LitePal.findAll(SRCLoginDataDictionaryBean.class);
                if(LoginDictionaryList.size()==0){
                    Map<String, String> map2 = ParametersFactory.getLoginMapDictionary(this);
                    loginPresenter.mainDictionary(map2);
                }else {
                    gotoLoginActivity();
                }
            }
        }


      /*  List<SRCLoginSalvationBean> LoginSalvationList = LitePal.findAll(SRCLoginSalvationBean.class);
        if(LoginSalvationList.size()==0){
            Map<String, String> map3 = ParametersFactory.getLoginMapSalvation(this);
            loginPresenter.mainSalvation(map3);
        }else {

        }

        List<SRCLoginDataDictionaryBean> LoginDictionaryList = LitePal.findAll(SRCLoginDataDictionaryBean.class);
        if(LoginDictionaryList.size()==0){
            Map<String, String> map2 = ParametersFactory.getLoginMapDictionary(this);
            loginPresenter.mainDictionary(map2);
        }else {

        }*/
    }
}
