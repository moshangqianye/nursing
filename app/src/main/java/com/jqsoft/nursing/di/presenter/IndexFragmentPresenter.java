package com.jqsoft.nursing.di.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.NotificationBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.PolicyBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;
import com.jqsoft.nursing.bean.nursing.NursingBean;
import com.jqsoft.nursing.di.contract.IndexFragmentContract;
import com.jqsoft.nursing.di.ui.fragment.base.AbstractFragment;
import com.jqsoft.nursing.di_http.http.nursing.GCAService;
import com.jqsoft.nursing.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
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

public class IndexFragmentPresenter implements IndexFragmentContract.presenter {

    private final IndexFragmentContract.View view;
    private final SharedPreferences sharedPreferences;
    private final GCAService GCAService;


    @Inject
    public IndexFragmentPresenter(IndexFragmentContract.View view,
                          @Named("default") SharedPreferences sharedPreferences,
                          GCAService GCAService) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
        this.GCAService = GCAService;
    }

    public void getNotificationList(Map<String, String> map){
        Fragment fragment = (Fragment)view;
        AbstractFragment abstractFragment = (AbstractFragment) fragment;
        final Context context = (Context)fragment.getActivity();
        if (!Util.isNetworkConnected(context)){
            view.onLoadNotificationDataFailure(Constants.HINT_LOADING_DATA_FAILURE);
            return;
        }
        Map<String, String> ultimateMap = new HashMap<>();
        String jsonString = JSON.toJSONString(map);
        ultimateMap.put(Constants.REQUEST_KEY, jsonString);

        GCAService.getNotificationList(ultimateMap)
                .compose(abstractFragment.<GCAHttpResultBaseBean<List<NotificationBean>>>bindToLifecycle())
//                .delay(8000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GCAHttpResultBaseBean<List<NotificationBean>>>() {
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
                        view.onLoadNotificationDataFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(GCAHttpResultBaseBean<List<NotificationBean>> bean) {
//                        Util.decodeBase64Bean(bean);
                        boolean isSuccessful = Util.isGCAResponseSuccessful(bean);
                        boolean isResponseTokenTimeout = Util.isGCAResponseTokenTimeout(bean);
                        boolean isEmpty = Util.isGCAResponseEmpty(bean);
                        if (isEmpty){
                            isSuccessful=true;
                            if (bean!=null){
                                bean.setData(new ArrayList<NotificationBean>());
                            }
                        }
                        if (isSuccessful){
                            view.onLoadNotificationDataSuccess(bean);
                        }  else if (isResponseTokenTimeout) {
                            Util.gotoLoginActivity(context, true, true);
                        } else {
                            String msg = Util.getGCAMessageFromHttpResponse(bean);
                            view.onLoadNotificationDataFailure(msg);
                        }

                    }



                });

    }


    public void getPolicyList(Map<String, String> map){
        Fragment fragment = (Fragment)view;
        AbstractFragment abstractFragment = (AbstractFragment) fragment;
        final Context context = (Context)fragment.getActivity();
        if (!Util.isNetworkConnected(context)){
            view.onLoadPolicyDataFailure(Constants.HINT_LOADING_DATA_FAILURE);
            return;
        }

        Map<String, String> ultimateMap = Util.getMapForHttpRequestFromMap(map);
//        Map<String, String> ultimateMap = new HashMap<>();
//        String jsonString = JSON.toJSONString(map);
//        ultimateMap.put(Constants.REQUEST_KEY, jsonString);
        GCAService.getPolicyList(ultimateMap)
                .compose(abstractFragment.<GCAHttpResultBaseBean<List<PolicyBean>>>bindToLifecycle())
//                .delay(8000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GCAHttpResultBaseBean<List<PolicyBean>>>() {
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
                        view.onLoadPolicyDataFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(GCAHttpResultBaseBean<List<PolicyBean>> bean) {
//                        Util.decodeBase64Bean(bean);
                        boolean isSuccessful = Util.isGCAResponseSuccessful(bean);
                        boolean isResponseTokenTimeout = Util.isGCAResponseTokenTimeout(bean);
                        boolean isEmpty = Util.isGCAResponseEmpty(bean);
                        if (isEmpty){
                            isSuccessful=true;
                            if (bean!=null){
                                bean.setData(new ArrayList<PolicyBean>());
                            }
                        }
                        if (isSuccessful){
                            view.onLoadPolicyDataSuccess(bean);
                        }  else if (isResponseTokenTimeout) {
                            Util.gotoLoginActivity(context, true, true);
                        } else {
                            String msg = Util.getGCAMessageFromHttpResponse(bean);
                            view.onLoadPolicyDataFailure(msg);
                        }

                    }



                });

    }

    public void getNursingList(Map<String, String> map,final boolean isLoadMore){
        Fragment fragment = (Fragment)view;
        AbstractFragment abstractFragment = (AbstractFragment) fragment;
        final Context context = (Context)fragment.getActivity();
        if (!Util.isNetworkConnected(context)){
            view.onLoadNursingListDataFailure(Constants.HINT_LOADING_DATA_FAILURE);
            return;
        }

//        Map<String, String> ultimateMap = Util.getMapForHttpRequestFromMap(map);
//        Map<String, String> ultimateMap = new HashMap<>();
//        String jsonString = JSON.toJSONString(map);
//        ultimateMap.put(Constants.REQUEST_KEY, jsonString);
        GCAService.getNursingList(map)
                .compose(abstractFragment.<HttpResultNurseBaseBean<List<NursingBean>>>bindToLifecycle())
//                .delay(8000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResultNurseBaseBean<List<NursingBean>>>() {
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
                        view.onLoadNursingListDataFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResultNurseBaseBean<List<NursingBean>> bean) {
//                        Util.decodeBase64Bean(bean);
                        boolean isSuccessful = Util.isResponseNurseSuccessful(bean);
//                        boolean isResponseTokenTimeout = Util.isGCAResponseTokenTimeout(bean);
//                        boolean isEmpty = Util.isGCAResponseEmpty(bean);
//                        if (isEmpty){
//                            isSuccessful=true;
//                            if (bean!=null){
//                                bean.setData(new ArrayList<NursingBean>());
//                            }
//                        }
//                        if (isSuccessful){
//                            view.onLoadNursingListDataSuccess(bean);
//                        }

                        if (isSuccessful){
                            if (isLoadMore){
                                view.onLoadNursingListMoreDataSuccess(bean);
                            } else {
                                view.onLoadNursingListDataSuccess(bean);
                            }
                        }

//                        else if (isResponseTokenTimeout) {
//                            Util.gotoLoginActivity(context, true, true);
//                        }
                        else {
                            String msg = Util.getNursingMessageFromHttpResponse(bean);
                            view.onLoadNursingListDataFailure(msg);
                        }

                    }



                });

    }

//    public void getSignInfoOverview(RequestBody body){
//        Fragment fragment = (Fragment)view;
//        AbstractFragment abstractFragment = (AbstractFragment) fragment;
//        final Context context = (Context)fragment.getActivity();
//        if (!Util.isNetworkConnected(context)){
//            view.onLoadSignOverviewDataFailure(Constants.HINT_LOADING_DATA_FAILURE);
//            return;
//        }
//
//        GCAService.getSignInfoOverviewData(body)
//                .compose(abstractFragment.<HttpResultBaseBean<List<SignInfoOverviewResultBean>>>bindToLifecycle())
////                .delay(8000, TimeUnit.MILLISECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<HttpResultBaseBean<List<SignInfoOverviewResultBean>>>() {
//                    @Override
//                    public void onStart() {
//                        super.onStart();
//                        Util.showGifProgressDialog(context);
//
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        Util.hideGifProgressDialog(context);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Util.hideGifProgressDialog(context);
//                        view.onLoadSignOverviewDataFailure(e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(HttpResultBaseBean<List<SignInfoOverviewResultBean>> bean) {
//                        Util.decodeBase64Bean(bean);
//                        boolean isSuccessful = Util.isResponseSuccessful(bean);
//                        boolean isResponseTokenTimeout = Util.isResponseTokenTimeout(bean);
//                        if (isSuccessful){
//                            view.onLoadSignOverviewDataSuccess(bean);
//                        }  else if (isResponseTokenTimeout) {
//                            Util.gotoLoginActivity(context, true, true);
//                        } else {
//                            String msg = Util.getMessageFromHttpResponse(bean);
//                            view.onLoadSignOverviewDataFailure(msg);
//                        }
//
//                    }
//
//
//
//                });
//
//    }

//    public void getIntelligentHonourAgreementOverview(RequestBody body){
//        Fragment fragment = (Fragment)view;
//        AbstractFragment abstractFragment = (AbstractFragment) fragment;
//        final Context context = (Context)fragment.getActivity();
//        if (!Util.isNetworkConnected(context)){
//            view.onLoadIntelligentHonourAgreementOverviewDataFailure(Constants.HINT_LOADING_DATA_FAILURE);
//            return;
//        }
//
//        GCAService.getIntelligentHonourAgreementOverview(body)
//                .compose(abstractFragment.<HttpResultBaseBean<IntelligentHonourAgreementOverviewResultBean>>bindToLifecycle())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<HttpResultBaseBean<IntelligentHonourAgreementOverviewResultBean>>() {
//                    @Override
//                    public void onStart() {
//                        super.onStart();
//                        Util.showGifProgressDialog(context);
//
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        Util.hideGifProgressDialog(context);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Util.hideGifProgressDialog(context);
//                        view.onLoadIntelligentHonourAgreementOverviewDataFailure(e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(HttpResultBaseBean<IntelligentHonourAgreementOverviewResultBean> bean) {
//                        Util.decodeBase64Bean(bean);
//                        boolean isSuccessful = Util.isResponseSuccessful(bean);
//                        boolean isResponseTokenTimeout = Util.isResponseTokenTimeout(bean);
//                        if (isSuccessful){
//                            view.onLoadIntelligentHonourAgreementOverviewDataSuccess(bean);
//                        }  else if (isResponseTokenTimeout) {
//                            Util.gotoLoginActivity(context, true, true);
//                        } else {
//                            String msg = Util.getMessageFromHttpResponse(bean);
//                            view.onLoadIntelligentHonourAgreementOverviewDataFailure(msg);
//                        }
//
//                    }
//
//
//
//                });
//
//    }
}