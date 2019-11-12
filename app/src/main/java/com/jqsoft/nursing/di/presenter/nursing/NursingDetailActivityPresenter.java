package com.jqsoft.nursing.di.presenter.nursing;

import android.content.Context;
import android.content.SharedPreferences;

import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.base.HttpResultEmptyBean;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.NursingTaskBean;
import com.jqsoft.nursing.bean.nursing.NursingTaskNewBean;
import com.jqsoft.nursing.di.contract.nursing.NursingDetailActivityContract;
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

public class NursingDetailActivityPresenter implements NursingDetailActivityContract.presenter {

    private final NursingDetailActivityContract.View view;
    private final SharedPreferences sharedPreferences;
    private final GCAService GCAService;


    @Inject
    public NursingDetailActivityPresenter(NursingDetailActivityContract.View view,
                                          @Named("default") SharedPreferences sharedPreferences,
                                          GCAService GCAService) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
        this.GCAService = GCAService;
    }

    public void main(Map<String, String> map) {
        final Context context = (Context)view;
        AbstractActivity abstractActivity = (AbstractActivity)context;
        if (!Util.isNetworkConnected(context)){
            view.onLoadListFailure(Constants.HINT_LOADING_DATA_FAILURE);
            return;
        }

        GCAService
                .getNursingTaskList(map)
                .compose(abstractActivity.<HttpResultNurseBaseBean<NursingTaskNewBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new Subscriber<HttpResultNurseBaseBean<NursingTaskNewBean>>() {
                        @Override
                    public void onStart() {
                        super.onStart();
                        Util.showGifProgressDialog(context);
                    }

                    @Override
                    public void onCompleted() {
                        Util.hideGifProgressDialog(context);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Util.hideGifProgressDialog(context);
                        view.onLoadListFailure(e.getMessage());

                    }

                    @Override
                    public void onNext(HttpResultNurseBaseBean<NursingTaskNewBean> bean) {
//                        Util.decodeBase64Bean(bean);
                        boolean isSuccessful = Util.isResponseNurseSuccessful(bean);
                        if (isSuccessful){
                                view.onLoadListSuccess(bean);
                        }  else{
                            String msg = Util.getNursingMessageFromHttpResponse(bean);
                            view.onLoadListFailure(msg);
                        }

                    }


                });

    }

    public void endNursingTask(Map<String, String> map) {
        final Context context = (Context)view;
        AbstractActivity abstractActivity = (AbstractActivity)context;
        if (!Util.isNetworkConnected(context)){
            view.onEndNursingTaskFailure(Constants.HINT_LOADING_DATA_FAILURE);
            return;
        }

        GCAService
                .endNursingTask(map)
                .compose(abstractActivity.<HttpResultNurseBaseBean<List<HttpResultEmptyBean>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new Subscriber<HttpResultNurseBaseBean<List<HttpResultEmptyBean>>>() {
                        @Override
                    public void onStart() {
                        super.onStart();
                        Util.showGifProgressDialog(context);
                    }

                    @Override
                    public void onCompleted() {
                        Util.hideGifProgressDialog(context);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Util.hideGifProgressDialog(context);
                        view.onEndNursingTaskFailure(e.getMessage());

                    }

                    @Override
                    public void onNext(HttpResultNurseBaseBean<List<HttpResultEmptyBean>> bean) {
//                        Util.decodeBase64Bean(bean);
                        boolean isSuccessful = Util.isResponseNurseSuccessful(bean);
                        if (isSuccessful){
                                view.onEndNursingTaskSuccess(bean);
                        }  else{
                            String msg = Util.getNursingMessageFromHttpResponse(bean);
                            view.onEndNursingTaskFailure(msg);
                        }

                    }


                });

    }



}