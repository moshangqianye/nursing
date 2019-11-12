package com.jqsoft.nursing.di.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultNewBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.SRCLoginDataDictionaryBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.SRCLoginSalvationBean;
import com.jqsoft.nursing.bean.resident.SRCLoginAreaBean;
import com.jqsoft.nursing.bean.resident.SRCLoginBean;
import com.jqsoft.nursing.di.contract.SRCLoginContract;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.di_http.http.nursing.GCAService;
import com.jqsoft.nursing.util.Util;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/21.
 */

public class SRCLoginPresenter implements SRCLoginContract.presenter {

    private final SRCLoginContract.View view;
    private final SharedPreferences sharedPreferences;
    private final GCAService GCAService;


    @Inject
    public SRCLoginPresenter(SRCLoginContract.View view,
                             @Named("default") SharedPreferences sharedPreferences,
                             GCAService GCAService) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
        this.GCAService = GCAService;
    }

    public void main(Map<String, String> map){
        final Context context = (Context)view;
        Util.showGifProgressDialog(context);
        AbstractActivity abstractActivity = (AbstractActivity) context;
        if (!Util.isNetworkConnected(context)){
            view.onLoginFailure(Constants.HINT_LOADING_DATA_FAILURE);
            return;
        }
//        RequestBody body = Util.getBodyFromMap(map);

//        GCAService.loginSRC(body)
        GCAService.loginSRC(map)
                .compose(abstractActivity.<HttpResultNewBaseBean<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResultNewBaseBean<String>>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        Util.showGifProgressDialog(context);

                    }

                    @Override
                    public void onCompleted() {
                    //    Util.hideGifProgressDialog(context);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Util.hideGifProgressDialog(context);
                        view.onLoginFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResultNewBaseBean<String> bean) {
//                        Util.decodeBase64Bean(bean);
                        boolean isSuccessful = Util.isResponseNewSuccessful(bean);
                        if (isSuccessful){
                            Util.hideGifProgressDialog(context);
                            view.onLoginSuccess(bean);
                        } else {
                            String msg =bean.getErrorMsg();
                            if(msg==null){
                                msg="返回数据为空";
                            }
                            view.onLoginFailure(msg);
                        }
                    }

                });

    }

    public void mainarea(Map<String, String> map){
        final Context context = (Context)view;
        Util.showGifProgressDialog(context);
        AbstractActivity abstractActivity = (AbstractActivity) context;
        if (!Util.isNetworkConnected(context)){
            view.onLoginFailure(Constants.HINT_LOADING_DATA_FAILURE);
            return;
        }
//        RequestBody body = Util.getBodyFromMap(map);

//        GCAService.loginSRC(body)
        GCAService.GetAreaInfoSRC(map)
                .compose(abstractActivity.<HttpResultNewBaseBean<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResultNewBaseBean<String>>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        Util.showGifProgressDialog(context);

                    }

                    @Override
                    public void onCompleted() {
                        //    Util.hideGifProgressDialog(context);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Util.hideGifProgressDialog(context);
                        view.onLoginFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResultNewBaseBean<String> bean) {
//                        Util.decodeBase64Bean(bean);
                        boolean isSuccessful = Util.isResponseNewSuccessful(bean);
                        if (isSuccessful){
                            Util.hideGifProgressDialog(context);
                            view.onLoginSuccess(bean);
                        } else {
                            //  String msg = Util.getMessageFromHttpResponse(bean);
                            // view.onLoginFailure(msg);
                        }
                    }

                });

    }





    public void mainArea(Map<String, String> map){
        final Context context = (Context)view;
        AbstractActivity abstractActivity = (AbstractActivity) context;
        if (!Util.isNetworkConnected(context)){
            view.onLoginAreaFailure(Constants.HINT_LOADING_DATA_FAILURE);
            return;
        }
        GCAService.loginAreaSRC(map)
                .compose(abstractActivity.<HttpResultBaseBean<List<SRCLoginAreaBean>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResultBaseBean<List<SRCLoginAreaBean>>>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        Util.showGifProgressDialog(context);

                    }

                    @Override
                    public void onCompleted() {
                      //  Util.hideGifProgressDialog(context);

                    }

                    @Override
                        public void onError(Throwable e) {
                        Util.hideGifProgressDialog(context);
                        view.onLoginAreaFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResultBaseBean<List<SRCLoginAreaBean>> bean) {
//                        Util.decodeBase64Bean(bean);
                        boolean isSuccessful = Util.isResponseSuccessful(bean);
                        if (isSuccessful){
                            view.onLoginAreaSuccess(bean);
                        } else {
                            String msg = Util.getMessageFromHttpResponse(bean);
                            view.onLoginAreaFailure(msg);
                        }
                    }

                });

    }


    public void mainDictionary(Map<String, String> map){
        final Context context = (Context)view;
        AbstractActivity abstractActivity = (AbstractActivity) context;
        if (!Util.isNetworkConnected(context)){
            view.onLoginDataDictionatyFailure(Constants.HINT_LOADING_DATA_FAILURE);
            return;
        }
        GCAService.loginDictionarySRC(map)
                .compose(abstractActivity.<HttpResultBaseBean<List<SRCLoginDataDictionaryBean>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResultBaseBean<List<SRCLoginDataDictionaryBean>>>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        Util.showGifProgressDialog(context);

                    }

                    @Override
                    public void onCompleted() {
                     //   Util.hideGifProgressDialog(context);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Util.hideGifProgressDialog(context);
                        view.onLoginDataDictionatyFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResultBaseBean<List<SRCLoginDataDictionaryBean>> bean) {
//                        Util.decodeBase64Bean(bean);
                        boolean isSuccessful = Util.isResponseSuccessful(bean);
                        if (isSuccessful){
                            view.onLoginDataDictionatySuccess(bean);
                        } else {
                            String msg = Util.getMessageFromHttpResponse(bean);
                            view.onLoginDataDictionatyFailure(msg);
                        }
                    }

                });

    }


    public void mainSalvation(Map<String, String> map){
        final Context context = (Context)view;
        AbstractActivity abstractActivity = (AbstractActivity) context;
        if (!Util.isNetworkConnected(context)){
            view.onLoginSalvationFailure(Constants.HINT_LOADING_DATA_FAILURE);
            return;
        }
        GCAService.loginSalvationSRC(map)
                .compose(abstractActivity.<HttpResultBaseBean<List<SRCLoginSalvationBean>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResultBaseBean<List<SRCLoginSalvationBean>>>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        Util.showGifProgressDialog(context);

                    }

                    @Override
                    public void onCompleted() {
                    //    Util.hideGifProgressDialog(context);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Util.hideGifProgressDialog(context);
                        view.onLoginSalvationFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResultBaseBean<List<SRCLoginSalvationBean>> bean) {
//                        Util.decodeBase64Bean(bean);
                        boolean isSuccessful = Util.isResponseSuccessful(bean);
                        if (isSuccessful){
                            view.onLoginSalvationSuccess(bean);
                        } else {
                            String msg = Util.getMessageFromHttpResponse(bean);
                            view.onLoginSalvationFailure(msg);
                        }
                    }

                });

    }

}
