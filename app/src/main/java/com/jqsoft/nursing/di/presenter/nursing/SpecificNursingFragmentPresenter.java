package com.jqsoft.nursing.di.presenter.nursing;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.NursingBean;
import com.jqsoft.nursing.di.contract.nursing.SpecificNursingFragmentContract;
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

public class SpecificNursingFragmentPresenter implements SpecificNursingFragmentContract.presenter {

    private final SpecificNursingFragmentContract.View view;
    private final SharedPreferences sharedPreferences;
    private final GCAService GCAService;


    @Inject
    public SpecificNursingFragmentPresenter(SpecificNursingFragmentContract.View view,
                                            @Named("default") SharedPreferences sharedPreferences,
                                            GCAService GCAService) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
        this.GCAService = GCAService;
    }


    public void getNursingList(Map<String, String> map, final boolean isLoadMore){
        Fragment fragment = (Fragment)view;
        AbstractFragment abstractFragment = (AbstractFragment) fragment;
        final Context context = (Context)fragment.getActivity();
        if (!Util.isNetworkConnected(context)){
            view.onLoadNursingListDataFailure(Constants.HINT_LOADING_DATA_FAILURE, isLoadMore);
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
                        view.onLoadNursingListDataFailure(e.getMessage(), isLoadMore);
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
                            view.onLoadNursingListDataFailure(msg, isLoadMore);
                        }

                    }



                });

    }

}