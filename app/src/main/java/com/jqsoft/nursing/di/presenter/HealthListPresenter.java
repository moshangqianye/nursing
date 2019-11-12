package com.jqsoft.nursing.di.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.HealthListBean;
import com.jqsoft.nursing.di.ui.fragment.base.AbstractFragment;
import com.jqsoft.nursing.di.view.IHealthListView;
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
 * @author yedong
 * @date 2019/1/16
 * 老人健康列表Presenter
 */
public class HealthListPresenter {

    private IHealthListView view;
    private final SharedPreferences sharedPreferences;
    private final GCAService GCAService;

    @Inject
    public HealthListPresenter(IHealthListView view,
                               @Named("default") SharedPreferences sharedPreferences,
                               GCAService GCAService) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
        this.GCAService = GCAService;
    }

    /**
     * 获取老人列表
     *
     * @param map
     * @param isLoadMore
     */
    public void getLoadHealthList(Map<String, String> map, final boolean isLoadMore) {
        Fragment fragment = (Fragment) view;
        AbstractFragment abstractFragment = (AbstractFragment) fragment;
        final Context context = fragment.getActivity();
        if (!Util.isNetworkConnected(context)) {
            view.onLoadHealthListFail(Constants.HINT_LOADING_DATA_FAILURE, isLoadMore);
            return;
        }

//        GCAService.getHealthList(map)
//                .compose(abstractFragment.<HttpResultNurseBaseBean<List<HealthListBean>>>bindToLifecycle())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<HttpResultNurseBaseBean<List<HealthListBean>>>() {
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
//                        view.onLoadHealthListFail(e.getMessage(), isLoadMore);
//                    }
//
//                    @Override
//                    public void onNext(HttpResultNurseBaseBean<List<HealthListBean>> bean) {
//                        boolean isSuccessful = Util.isResponseNurseSuccessful(bean);
//                        if (isSuccessful) {
//                            view.onLoadHealthListSuccess(bean);
//                        } else {
//                            String msg = Util.getNursingMessageFromHttpResponse(bean);
//                            view.onLoadHealthListFail(msg, isLoadMore);
//                        }
//
//                    }
//                });
    }

}
