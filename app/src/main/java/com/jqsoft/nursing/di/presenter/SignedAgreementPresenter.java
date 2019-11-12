package com.jqsoft.nursing.di.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.PersonSignAgreement;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.di.contract.SignedAgreementContract;
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
 * Created by Jerry on 2017/8/22.
 */

public class SignedAgreementPresenter implements SignedAgreementContract.presenter {
    private final SignedAgreementContract.View view;
    private final SharedPreferences sharedPreferences;
    private final GCAService GCAService;

    @Inject
    public SignedAgreementPresenter(SignedAgreementContract.View view,
                                    @Named("default") SharedPreferences sharedPreferences,
                                    GCAService GCAService) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
        this.GCAService = GCAService;
    }

    public void getPersonSignAgreement(RequestBody body) {
        final Context context = (Context) view;
        AbstractActivity abstractActivity = (AbstractActivity) context;
        if (!Util.isNetworkConnected(context)) {
            view.onLoginFailure(Constants.HINT_LOADING_DATA_FAILURE);
            return;
        }
        GCAService.getPersonSignAgreement(body)
                .compose(abstractActivity.<HttpResultBaseBean<PersonSignAgreement>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResultBaseBean<PersonSignAgreement>>() {
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
                        view.onLoginFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResultBaseBean<PersonSignAgreement> bean) {
                        Util.decodeBase64Bean(bean);
                        boolean isSuccessful = Util.isResponseSuccessful(bean);
                        if (isSuccessful) {
                            view.onLoginSuccess(bean);
                        } else {
                            String msg = Util.getMessageFromHttpResponse(bean);
                            view.onLoginFailure(msg);
                        }
                    }

                });

    }


}
