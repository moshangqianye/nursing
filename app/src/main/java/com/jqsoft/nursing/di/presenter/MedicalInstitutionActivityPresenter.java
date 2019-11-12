package com.jqsoft.nursing.di.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.MedicalInstitutionListBean;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.di.contract.MedicalInstitutionActivityContract;
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

public class MedicalInstitutionActivityPresenter implements MedicalInstitutionActivityContract.presenter {

    private final MedicalInstitutionActivityContract.View view;
    private final SharedPreferences sharedPreferences;
    private final GCAService GCAService;


    @Inject
    public MedicalInstitutionActivityPresenter(MedicalInstitutionActivityContract.View view,
                                               @Named("default") SharedPreferences sharedPreferences,
                                               GCAService GCAService) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
        this.GCAService = GCAService;
    }

    public void main(Map<String, String> map, final boolean isLoadMore) {
        final Context context = (Context)view;
        AbstractActivity abstractActivity = (AbstractActivity) context;
        if (!Util.isNetworkConnected(context)){
            view.onLoadListFailure(Constants.HINT_LOADING_DATA_FAILURE);
            return;
        }

        GCAService.getMedicalInstitutionData(1)
                .compose(abstractActivity.<HttpResultBaseBean<MedicalInstitutionListBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResultBaseBean<MedicalInstitutionListBean>>() {
                    @Override
                    public void onCompleted() {
                        Util.hideGifProgressDialog(context);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Util.hideGifProgressDialog(context);
                        view.onLoadListFailure(e.getMessage());
                        if (isLoadMore){
                            view.onLoadMoreListSuccess(null);
                        } else {
                            view.onLoadListSuccess(null);
                        }

                    }

                    @Override
                    public void onNext(HttpResultBaseBean<MedicalInstitutionListBean> bean) {
                        if (isLoadMore){
                            view.onLoadMoreListSuccess(bean);
                        } else {
                            view.onLoadListSuccess(bean);
                        }
                    }


                });

    }
}