package com.jqsoft.nursing.di.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.CaseListBean;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.di.ui.fragment.base.AbstractFragment;
import com.jqsoft.nursing.di.view.ICaseListView;
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
 * @date 2019/1/17
 * 病例记录Presenter
 */
public class CaseListPresenter {

    private ICaseListView view;
    private final SharedPreferences sharedPreferences;
    private final com.jqsoft.nursing.di_http.http.nursing.GCAService GCAService;

    @Inject
    public CaseListPresenter(ICaseListView view,
                             @Named("default") SharedPreferences sharedPreferences,
                             GCAService GCAService) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
        this.GCAService = GCAService;
    }


    /**
     * 获取病例记录
     *
     * @param map
     * @param isLoadMore
     */
    public void getCaseList(Map<String, String> map, final boolean isLoadMore) {
        Fragment fragment = (Fragment)view;
        AbstractFragment abstractFragment = (AbstractFragment) fragment;
        final Context context = fragment.getActivity();
        if (!Util.isNetworkConnected(context)) {
            view.onLoadCaseListFail(Constants.HINT_LOADING_DATA_FAILURE, isLoadMore);
            return;
        }

        GCAService.getCaseList(map)
                .compose(abstractFragment.<HttpResultNurseBaseBean<List<CaseListBean>>>bindToLifecycle())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResultNurseBaseBean<List<CaseListBean>>>() {
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
                        view.onLoadCaseListFail(e.getMessage(), isLoadMore);
                    }

                    @Override
                    public void onNext(HttpResultNurseBaseBean<List<CaseListBean>> bean) {
                        boolean isSuccessful = Util.isResponseNurseSuccessful(bean);
                        if (isSuccessful) {
                            view.onLoadCaseListSuccess(bean);
                        } else {
                            String msg = Util.getNursingMessageFromHttpResponse(bean);
                            view.onLoadCaseListFail(msg, isLoadMore);
                        }

                    }
                });
    }
}
