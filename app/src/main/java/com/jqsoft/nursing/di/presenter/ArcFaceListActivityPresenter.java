package com.jqsoft.nursing.di.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;

import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.base.HttpResultNewBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.HealthListBean;
import com.jqsoft.nursing.di.contract.ArcFaceListActivityContract;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
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

 * copyfrom老人健康列表Presenter
 */
public class ArcFaceListActivityPresenter {

    private ArcFaceListActivityContract.View view;
    private final SharedPreferences sharedPreferences;
    private final GCAService GCAService;

    @Inject
    public ArcFaceListActivityPresenter(ArcFaceListActivityContract.View view,
                                        @Named("default") SharedPreferences sharedPreferences,
                                        GCAService GCAService) {
        this.view = view;
        this.sharedPreferences = sharedPreferences;
        this.GCAService = GCAService;
    }

    /**
     * 获取列表
     *
     * @param map
     * @param isLoadMore
     */
    public void getLoadHealthList(Map<String, String> map, final boolean isLoadMore) {
        final Context context = (Context)view;
        AbstractActivity abstractActivity = (AbstractActivity) context;

        if (!Util.isNetworkConnected(context)) {
            view.onLoadHealthListFail(Constants.HINT_LOADING_DATA_FAILURE, isLoadMore);
            return;
        }

        GCAService.getHealthList(map)
                .compose(abstractActivity.<HttpResultNewBaseBean<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResultNewBaseBean<String>>() {
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
                        view.onLoadHealthListFail(e.getMessage(), isLoadMore);
                    }

                    @Override
                    public void onNext(HttpResultNewBaseBean<String> bean) {
                        boolean isSuccessful = Util.isResponseNewSuccessful(bean);
                        if (isSuccessful) {
                            view.onLoadHealthListSuccess(bean);
                        } else {
                            String msg = Util.getNewMessageFromHttpResponse(bean);
                            view.onLoadHealthListFail(msg, isLoadMore);
                        }

                    }
                });
    }


    /**
     * 老人注销
     *
     * @param map
     */
    public void getLoadEndList(Map<String, String> map) {
        final Context context = (Context)view;
        AbstractActivity abstractActivity = (AbstractActivity) context;

        if (!Util.isNetworkConnected(context)) {
            view.onLoadHealtEndFail(Constants.HINT_LOADING_DATA_FAILURE);
            return;
        }

        GCAService.getHealthEnd(map)
                .compose(abstractActivity.<HttpResultNewBaseBean<String>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResultNewBaseBean<String>>() {
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
                        view.onLoadHealtEndFail(e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResultNewBaseBean<String> bean) {
                        boolean isSuccessful = Util.isResponseNewSuccessful(bean);
                        if (isSuccessful) {
                            view.onLoadHealthEndSuccess(bean);
                        } else {
                            String msg = Util.getNewMessageFromHttpResponse(bean);
                            view.onLoadHealtEndFail(msg);
                        }

                    }
                });
    }

}
