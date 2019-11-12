package com.jqsoft.nursing.di.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.response_new.IntelligentHonourAgreementOverviewResultBean;
import com.jqsoft.nursing.di.contract.IntelligentHonourAgreementRemindActivityContract;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.di_http.http.nursing.GCAService;
import com.jqsoft.nursing.util.Util;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/21.
 */

public class IntelligentHonourAgreementRemindActivityPresenter implements IntelligentHonourAgreementRemindActivityContract.presenter {

    private final IntelligentHonourAgreementRemindActivityContract.View view;
    private final SharedPreferences sharedPreferences;
    private final GCAService GCAService;


    @Inject
    public IntelligentHonourAgreementRemindActivityPresenter(IntelligentHonourAgreementRemindActivityContract.View view,
                                                             @Named("default") SharedPreferences sharedPreferences,
                                                             GCAService GCAService) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
        this.GCAService = GCAService;
    }

    public void getIntelligentHonourAgreementOverview(RequestBody body){
        final Context context = (Context)view;
        AbstractActivity abstractActivity = (AbstractActivity)context;
        if (!Util.isNetworkConnected(context)){
            view.onLoadIntelligentHonourAgreementOverviewDataFailure(Constants.HINT_LOADING_DATA_FAILURE);
            return;
        }

        GCAService.getIntelligentHonourAgreementOverview(body)
                .compose(abstractActivity.<HttpResultBaseBean<IntelligentHonourAgreementOverviewResultBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResultBaseBean<IntelligentHonourAgreementOverviewResultBean>>() {
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
                        view.onLoadIntelligentHonourAgreementOverviewDataFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResultBaseBean<IntelligentHonourAgreementOverviewResultBean> bean) {
                        Util.decodeBase64Bean(bean);
                        boolean isSuccessful = Util.isResponseSuccessful(bean);
                        boolean isResponseTokenTimeout = Util.isResponseTokenTimeout(bean);
                        if (isSuccessful){
                            view.onLoadIntelligentHonourAgreementOverviewDataSuccess(bean);
                        }  else if (isResponseTokenTimeout) {
                            Util.gotoLoginActivity(context, true, true);
                        } else {
                            String msg = Util.getMessageFromHttpResponse(bean);
                            view.onLoadIntelligentHonourAgreementOverviewDataFailure(msg);
                        }

                    }



                });

    }
}