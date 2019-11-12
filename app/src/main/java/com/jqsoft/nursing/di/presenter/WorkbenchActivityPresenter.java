package com.jqsoft.nursing.di.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.di.contract.SocialAssistanceObjectActivityContract;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.di_http.http.nursing.GCAService;
import com.jqsoft.nursing.rx.BaseSubscriber;
import com.jqsoft.nursing.util.Util;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/21.
 */

public class WorkbenchActivityPresenter implements SocialAssistanceObjectActivityContract.presenter {

    private final SocialAssistanceObjectActivityContract.View view;
    private final SharedPreferences sharedPreferences;
    private final GCAService signedResidentClientService;


    @Inject
    public WorkbenchActivityPresenter(SocialAssistanceObjectActivityContract.View view,
                                      @Named("default") SharedPreferences sharedPreferences,
                                      GCAService signedResidentClientService) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
        this.signedResidentClientService = signedResidentClientService;
    }

//    public void getSocialDatas(Map<String, String> map, final boolean isLoadMore) {
//        Fragment fragment = (Fragment)view;
//        AbstractFragment abstractFragment = (AbstractFragment) fragment;
//        final Context context = (Context)fragment.getActivity();
//        if (!Util.isNetworkConnected(context)){
//            view.onLoadListFailure(Constants.HINT_LOADING_DATA_FAILURE, isLoadMore);
//            return;
//        }
////        Map<String, String> ultimateMap = new HashMap<>();
////        String jsonString = JSON.toJSONString(map);
////        ultimateMap.put(Constants.REQUEST_KEY, jsonString);
//
//        signedResidentClientService.getBuildingInfo(map)
//                .compose(abstractFragment.<HttpResultNurseBaseBean<List<BuildingRoomFloorBean>>>bindToLifecycle())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
////                .subscribe(new Subscriber<GCAHttpResultBaseBean<TreatmentListResultWrapperBean>>() {
//                 .subscribe(new BaseSubscriber<HttpResultNurseBaseBean<List<BuildingRoomFloorBean>>>(context) {
//                        @Override
//                    public void onStart() {
//                        super.onStart();
////                        Util.showGifProgressDialog(((Context)view));
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        super.onCompleted();
////                        boolean isMainThread = Util.isMainThread();
////                        LogUtil.i("onCompleted runs in main thread:"+isMainThread);
////                        final Activity activity = (Activity) view;
////                        activity.runOnUiThread(new Runnable() {
////                            @Override
////                            public void run() {
////                                Util.hideGifProgressDialog(activity);
////
////                            }
////                        });
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        super.onError(e);
//                        view.onLoadListFailure(e.getMessage(), isLoadMore);
////                        Activity activity = (Activity) view;
////                        Util.hideGifProgressDialog(activity);
//
////                        if (isLoadMore){
////                            view.onLoadMoreListSuccess(null);
////                        } else {
////                            view.onLoadListSuccess(null);
////                        }
//
//                    }
//
//                    @Override
//                    public void onNext(HttpResultNurseBaseBean<List<BuildingRoomFloorBean>> bean) {
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
//                            view.onLoadListSuccess(bean);
//                        }
////                        else if (isResponseTokenTimeout) {
////                            Util.gotoLoginActivity(context, true, true);
////                        }
//                        else {
//                            String msg = Util.getNursingMessageFromHttpResponse(bean);
//                            view.onLoadListFailure(msg,false);
//                        }
//
//
//                    }
//
//
//                });
//
//    }
    public void getQrcodeInfo(Map<String, String> map, final boolean isLoadMore) {
//        Fragment fragment = (Fragment)view;
//        AbstractFragment abstractFragment = (AbstractFragment) fragment;
        AbstractActivity activity = (AbstractActivity)view;
        final Context context = (Context)activity;
        if (!Util.isNetworkConnected(context)){
            view.onLoadQrcodeFailure(Constants.HINT_LOADING_DATA_FAILURE, isLoadMore);
            return;
        }
//        Map<String, String> ultimateMap = new HashMap<>();
//        String jsonString = JSON.toJSONString(map);
//        ultimateMap.put(Constants.REQUEST_KEY, jsonString);

        signedResidentClientService.getQrcodeInfo(map)
                .compose(activity.<HttpResultNurseBaseBean<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<GCAHttpResultBaseBean<TreatmentListResultWrapperBean>>() {
                 .subscribe(new BaseSubscriber<HttpResultNurseBaseBean<String>>(context) {
                        @Override
                    public void onStart() {
                        super.onStart();
//                        Util.showGifProgressDialog(((Context)view));
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
//                        boolean isMainThread = Util.isMainThread();
//                        LogUtil.i("onCompleted runs in main thread:"+isMainThread);
//                        final Activity activity = (Activity) view;
//                        activity.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Util.hideGifProgressDialog(activity);
//
//                            }
//                        });

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.onLoadQrcodeFailure(e.getMessage(), isLoadMore);
//                        Activity activity = (Activity) view;
//                        Util.hideGifProgressDialog(activity);

//                        if (isLoadMore){
//                            view.onLoadMoreListSuccess(null);
//                        } else {
//                            view.onLoadListSuccess(null);
//                        }

                    }

                    @Override
                    public void onNext(HttpResultNurseBaseBean<String> bean) {
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
                            view.onLoadQrcodeSuccess(bean);
                        }
//                        else if (isResponseTokenTimeout) {
//                            Util.gotoLoginActivity(context, true, true);
//                        }
                        else {
                            String msg = Util.getNursingMessageFromHttpResponse(bean);
                            view.onLoadQrcodeFailure(msg,false);
                        }


                    }


                });

    }

//    public void getRoundsRecordCount(Map<String, String> map, final boolean isLoadMore) {
//        Fragment fragment = (Fragment)view;
//        AbstractFragment abstractFragment = (AbstractFragment) fragment;
//        final Context context = (Context)fragment.getActivity();
//        if (!Util.isNetworkConnected(context)){
//            view.onLoadListFailure(Constants.HINT_LOADING_DATA_FAILURE, isLoadMore);
//            return;
//        }
////        Map<String, String> ultimateMap = new HashMap<>();
////        String jsonString = JSON.toJSONString(map);
////        ultimateMap.put(Constants.REQUEST_KEY, jsonString);
//
//        signedResidentClientService.getRoundsRecordCount(map)
//                .compose(abstractFragment.<HttpResultNurseBaseBean<List<RoundsRecordCount>>>bindToLifecycle())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
////                .subscribe(new Subscriber<GCAHttpResultBaseBean<TreatmentListResultWrapperBean>>() {
//                .subscribe(new BaseSubscriber<HttpResultNurseBaseBean<List<RoundsRecordCount>>>(context) {
//                    @Override
//                    public void onStart() {
//                        super.onStart();
////                        Util.showGifProgressDialog(((Context)view));
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        super.onCompleted();
////                        boolean isMainThread = Util.isMainThread();
////                        LogUtil.i("onCompleted runs in main thread:"+isMainThread);
////                        final Activity activity = (Activity) view;
////                        activity.runOnUiThread(new Runnable() {
////                            @Override
////                            public void run() {
////                                Util.hideGifProgressDialog(activity);
////
////                            }
////                        });
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        super.onError(e);
//                        view.onLoadRoundsRecordCountFailure(e.getMessage(), isLoadMore);
////                        Activity activity = (Activity) view;
////                        Util.hideGifProgressDialog(activity);
//
////                        if (isLoadMore){
////                            view.onLoadMoreListSuccess(null);
////                        } else {
////                            view.onLoadListSuccess(null);
////                        }
//
//                    }
//
//                    @Override
//                    public void onNext(HttpResultNurseBaseBean<List<RoundsRecordCount>> bean) {
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
//                            view.onLoadRoundsRecordCountSuccess(bean);
//                        }
////                        else if (isResponseTokenTimeout) {
////                            Util.gotoLoginActivity(context, true, true);
////                        }
//                        else {
//                            String msg = Util.getNursingMessageFromHttpResponse(bean);
//                            view.onLoadRoundsRecordCountFailure(msg,false);
//                        }
//
//
//                    }
//
//
//                });
//
//    }



}