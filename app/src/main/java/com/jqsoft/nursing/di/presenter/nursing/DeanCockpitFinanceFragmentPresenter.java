package com.jqsoft.nursing.di.presenter.nursing;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.DeanCockpitFinanceBean;
import com.jqsoft.nursing.di.contract.nursing.DeanCockpitFinanceFragmentContract;
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

public class DeanCockpitFinanceFragmentPresenter implements DeanCockpitFinanceFragmentContract.presenter {

    private final DeanCockpitFinanceFragmentContract.View view;
    private final SharedPreferences sharedPreferences;
    private final GCAService GCAService;


    @Inject
    public DeanCockpitFinanceFragmentPresenter(DeanCockpitFinanceFragmentContract.View view,
                                               @Named("default") SharedPreferences sharedPreferences,
                                               GCAService GCAService) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
        this.GCAService = GCAService;
    }


    public void getDeanCockpitFinanceList(Map<String, String> map){
        Fragment fragment = (Fragment)view;
        AbstractFragment abstractFragment = (AbstractFragment) fragment;
        final Context context = (Context)fragment.getActivity();
        if (!Util.isNetworkConnected(context)){
            view.onLoadDeanCockpitFinanceListDataFailure(Constants.HINT_LOADING_DATA_FAILURE);
            return;
        }

//        Map<String, String> ultimateMap = Util.getMapForHttpRequestFromMap(map);
//        Map<String, String> ultimateMap = new HashMap<>();
//        String jsonString = JSON.toJSONString(map);
//        ultimateMap.put(Constants.REQUEST_KEY, jsonString);
        GCAService.getDeanCockpitFinanceList(map)
                .compose(abstractFragment.<HttpResultNurseBaseBean<List<DeanCockpitFinanceBean>>>bindToLifecycle())
//                .delay(8000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResultNurseBaseBean<List<DeanCockpitFinanceBean>>>() {
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
                        view.onLoadDeanCockpitFinanceListDataFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResultNurseBaseBean<List<DeanCockpitFinanceBean>> bean) {
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
                                view.onLoadDeanCockpitFinanceListDataSuccess(bean);
                        }
//                        else if (isResponseTokenTimeout) {
//                            Util.gotoLoginActivity(context, true, true);
//                        }
                        else {
                            String msg = Util.getNursingMessageFromHttpResponse(bean);
                            view.onLoadDeanCockpitFinanceListDataFailure(msg);
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