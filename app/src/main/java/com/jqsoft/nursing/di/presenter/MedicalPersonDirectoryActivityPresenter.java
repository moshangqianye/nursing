package com.jqsoft.nursing.di.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.response.MedicalPersonDirectoryResultBean;
import com.jqsoft.nursing.di.contract.MedicalPersonDirectoryActivityContract;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.di_http.http.nursing.GCAService;
import com.jqsoft.nursing.util.Util;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MedicalPersonDirectoryActivityPresenter implements MedicalPersonDirectoryActivityContract.presenter {

    private final MedicalPersonDirectoryActivityContract.View view;
    private final SharedPreferences sharedPreferences;
    private final GCAService GCAService;


    @Inject
    public MedicalPersonDirectoryActivityPresenter(MedicalPersonDirectoryActivityContract.View view,
                                                   @Named("default") SharedPreferences sharedPreferences,
                                                   GCAService GCAService) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
        this.GCAService = GCAService;
    }

    public void main(RequestBody body, final boolean isLoadMore) {
        final Context context = (Context)view;
        AbstractActivity abstractActivity = (AbstractActivity) context;
        if (!Util.isNetworkConnected(context)){
            view.onLoadListFailure(Constants.HINT_LOADING_DATA_FAILURE, false);
            return;
        }

        GCAService.getMedicalPersonDirectoryData(body)
                .compose(abstractActivity.<HttpResultBaseBean<List<MedicalPersonDirectoryResultBean>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<HttpResultBaseBean<TreatmentListResultWrapperBean>>() {
                .subscribe(new Subscriber<HttpResultBaseBean<List<MedicalPersonDirectoryResultBean>>>() {
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
                        view.onLoadListFailure(e.getMessage(),false);
                    }

                    @Override
                    public void onNext(HttpResultBaseBean<List<MedicalPersonDirectoryResultBean>> bean) {
                        Util.decodeBase64Bean(bean);
                        boolean isSuccessful = Util.isResponseSuccessful(bean);
                        if (isSuccessful){
                            view.onLoadListSuccess(bean);
                        } else {
                            String msg = Util.getMessageFromHttpResponse(bean);
                            view.onLoadListFailure(msg,false);
                        }
                    }

                });

    }


}