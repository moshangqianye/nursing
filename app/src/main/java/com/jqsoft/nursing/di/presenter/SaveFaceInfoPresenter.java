package com.jqsoft.nursing.di.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultNewBaseBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.SRCLoginDataDictionaryBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.SRCLoginSalvationBean;
import com.jqsoft.nursing.bean.resident.SRCLoginAreaBean;

import com.jqsoft.nursing.di.contract.SaveFaceInfoContract;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.di_http.http.nursing.GCAService;
import com.jqsoft.nursing.util.Util;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/21.
 */

public class SaveFaceInfoPresenter implements SaveFaceInfoContract.presenter {

    private final SaveFaceInfoContract.View view;
    private final SharedPreferences sharedPreferences;
    private final GCAService GCAService;


    @Inject
    public SaveFaceInfoPresenter(SaveFaceInfoContract.View view,
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
            view.onSaveFaceInfoFailure(Constants.HINT_LOADING_DATA_FAILURE);
            return;
        }
//        RequestBody body = Util.getBodyFromMap(map);

//        GCAService.loginSRC(body)
        GCAService.saveFaceSRC(map)
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
                        view.onSaveFaceInfoFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResultNewBaseBean<String> bean) {
//                        Util.decodeBase64Bean(bean);
                        Util.hideGifProgressDialog(context);
                        boolean isSuccessful = Util.isResponseNewSuccessful(bean);
                        if (isSuccessful){
                            Util.hideGifProgressDialog(context);
                            view.onSaveFaceInfoSuccess(bean);
                        } else {
                            Util.hideGifProgressDialog(context);
                            String msg = Util.getNewMessageFromHttpResponse(bean);
                            view.onSaveFaceInfoFailure(msg);
                        }
                    }

                });

    }


    public void main1(RequestBody body){
        final Context context = (Context)view;
        Util.showGifProgressDialog(context);
        AbstractActivity abstractActivity = (AbstractActivity) context;
        if (!Util.isNetworkConnected(context)){
            view.onSaveFaceInfoFailure(Constants.HINT_LOADING_DATA_FAILURE);
            return;
        }
//        RequestBody body = Util.getBodyFromMap(map);

//        GCAService.loginSRC(body)
        GCAService.saveFaceSRC1(body)
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
                        view.onSaveFaceInfoFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResultNewBaseBean<String> bean) {
//                        Util.decodeBase64Bean(bean);
                        Util.hideGifProgressDialog(context);
                        boolean isSuccessful = Util.isResponseNewSuccessful(bean);
                        if (isSuccessful){
                            Util.hideGifProgressDialog(context);
                            view.onSaveFaceInfoSuccess(bean);
                        } else {
                            //  String msg = Util.getMessageFromHttpResponse(bean);
                            // view.onLoginFailure(msg);
                        }
                    }

                });

    }


}
