package com.jqsoft.nursing.di.presenter.nursing;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.DeanCockpitBedBean;
import com.jqsoft.nursing.di.contract.nursing.DeanCockpitBedFragmentContract;
import com.jqsoft.nursing.di.ui.fragment.base.AbstractFragment;
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

public class DeanCockpitBedFragmentPresenter implements DeanCockpitBedFragmentContract.presenter {

    private final DeanCockpitBedFragmentContract.View view;
    private final SharedPreferences sharedPreferences;
    private final GCAService GCAService;


    @Inject
    public DeanCockpitBedFragmentPresenter(DeanCockpitBedFragmentContract.View view,
                                           @Named("default") SharedPreferences sharedPreferences,
                                           GCAService GCAService) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
        this.GCAService = GCAService;
    }


    public void getDeanCockpitBedList(Map<String, String> map){
        Fragment fragment = (Fragment)view;
        AbstractFragment abstractFragment = (AbstractFragment) fragment;
        final Context context = (Context)fragment.getActivity();
        if (!Util.isNetworkConnected(context)){
            view.onLoadDeanCockpitBedListDataFailure(Constants.HINT_LOADING_DATA_FAILURE);
            return;
        }

//        Map<String, String> ultimateMap = Util.getMapForHttpRequestFromMap(map);
//        Map<String, String> ultimateMap = new HashMap<>();
//        String jsonString = JSON.toJSONString(map);
//        ultimateMap.put(Constants.REQUEST_KEY, jsonString);
        GCAService.getDeanCockpitBedList(map)
                .compose(abstractFragment.<HttpResultNurseBaseBean<List<DeanCockpitBedBean>>>bindToLifecycle())
//                .delay(8000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResultNurseBaseBean<List<DeanCockpitBedBean>>>() {
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
                        view.onLoadDeanCockpitBedListDataFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResultNurseBaseBean<List<DeanCockpitBedBean>> bean) {
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
                        if (isSuccessful){
                                view.onLoadDeanCockpitBedListDataSuccess(bean);
                        }
//                        else if (isResponseTokenTimeout) {
//                            Util.gotoLoginActivity(context, true, true);
//                        }
                        else {
                            String msg = Util.getNursingMessageFromHttpResponse(bean);
                            view.onLoadDeanCockpitBedListDataFailure(msg);
                        }

                    }



                });

    }
//    public void getOrgnizationList(Map<String, String> map){
//        Fragment fragment = (Fragment)view;
//        AbstractFragment abstractFragment = (AbstractFragment) fragment;
//        final Context context = (Context)fragment.getActivity();
//        if (!Util.isNetworkConnected(context)){
//            view.onLoadOrgnizationListDataFailure(Constants.HINT_LOADING_DATA_FAILURE);
//            return;
//        }
//
////        Map<String, String> ultimateMap = Util.getMapForHttpRequestFromMap(map);
////        Map<String, String> ultimateMap = new HashMap<>();
////        String jsonString = JSON.toJSONString(map);
////        ultimateMap.put(Constants.REQUEST_KEY, jsonString);
//        GCAService.getOrgnizationList(map)
//                .compose(abstractFragment.<HttpResultNurseBaseBean<List<OrgnizationBean>>>bindToLifecycle())
////                .delay(8000, TimeUnit.MILLISECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<HttpResultNurseBaseBean<List<OrgnizationBean>>>() {
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
//                        view.onLoadOrgnizationListDataFailure(e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(HttpResultNurseBaseBean<List<OrgnizationBean>> bean) {
////                        Util.decodeBase64Bean(bean);
//                        boolean isSuccessful = Util.isResponseNurseSuccessful(bean);
////                        boolean isResponseTokenTimeout = Util.isGCAResponseTokenTimeout(bean);
////                        boolean isEmpty = Util.isGCAResponseEmpty(bean);
////                        if (isEmpty){
////                            isSuccessful=true;
////                            if (bean!=null){
////                                bean.setData(new ArrayList<NursingBean>());
////                            }
////                        }
//                        if (isSuccessful){
//                                view.onLoadOrgnizationListDataSuccess(bean);
//                        }
////                        else if (isResponseTokenTimeout) {
////                            Util.gotoLoginActivity(context, true, true);
////                        }
//                        else {
//                            String msg = Util.getNursingMessageFromHttpResponse(bean);
//                            view.onLoadOrgnizationListDataFailure(msg);
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