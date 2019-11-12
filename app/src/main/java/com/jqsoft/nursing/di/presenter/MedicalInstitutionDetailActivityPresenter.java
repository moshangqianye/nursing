package com.jqsoft.nursing.di.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.MedicalInstitutionDetailBean;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.di.contract.MedicalInstitutionDetailActivityContract;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.di_http.http.nursing.GCAService;
import com.jqsoft.nursing.util.Util;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/21.
 */

public class MedicalInstitutionDetailActivityPresenter implements MedicalInstitutionDetailActivityContract.presenter {

    private final MedicalInstitutionDetailActivityContract.View view;
    private final SharedPreferences sharedPreferences;
    private final GCAService GCAService;


    @Inject
    public MedicalInstitutionDetailActivityPresenter(MedicalInstitutionDetailActivityContract.View view,
                                                     @Named("default") SharedPreferences sharedPreferences,
                                                     GCAService GCAService) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
        this.GCAService = GCAService;
    }

    public void main(Map<String, String> map) {
        final Context context = (Context)view;
        AbstractActivity abstractActivity = (AbstractActivity) context;
        if (!Util.isNetworkConnected(context)){
            view.onLoadInfoFailure(Constants.HINT_LOADING_DATA_FAILURE);
            return;
        }

        GCAService.getMedicalInstitutionDetailData(1)
                .compose(abstractActivity.<HttpResultBaseBean<MedicalInstitutionDetailBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResultBaseBean<MedicalInstitutionDetailBean>>() {
                    @Override
                    public void onCompleted() {
                        Util.hideGifProgressDialog(context);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Util.hideGifProgressDialog(context);
                        view.onLoadInfoFailure(e.getMessage());
                        view.onLoadInfoSuccess(null);
                    }

                    @Override
                    public void onNext(HttpResultBaseBean<MedicalInstitutionDetailBean> bean) {
                        view.onLoadInfoSuccess(null);
                    }


                });

    }
}