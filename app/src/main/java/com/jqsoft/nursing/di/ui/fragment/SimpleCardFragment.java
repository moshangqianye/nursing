package com.jqsoft.nursing.di.ui.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.flyco.roundview.RoundTextView;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.base.IdentityManager;
import com.jqsoft.nursing.bean.grassroots_civil_administration.CenterListBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.HelpListBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.PersonCollectionBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;
import com.jqsoft.nursing.di.contract.UseCollectionFragmentContract;
import com.jqsoft.nursing.di.module.UseCollectionFragmentModule;
import com.jqsoft.nursing.di.presenter.UseCollectionFragmentPresenter;
import com.jqsoft.nursing.di.ui.activity.AboutInfoActivity;
import com.jqsoft.nursing.di.ui.activity.BuildingRoomActivity;
import com.jqsoft.nursing.di.ui.activity.ChangePasswordActivity;
import com.jqsoft.nursing.di.ui.activity.LoginActivityNew;
import com.jqsoft.nursing.di.ui.activity.MyMessageActivity;
import com.jqsoft.nursing.di.ui.activity.PersonCollectionActivity;
import com.jqsoft.nursing.di.ui.activity.PersonalInfoActivity;
import com.jqsoft.nursing.di.ui.activity.SettingServerActivity;
import com.jqsoft.nursing.di.ui.fragment.base.AbstractFragment;
import com.jqsoft.nursing.di_app.DaggerApplication;
import com.jqsoft.nursing.rx.RxBus;
import com.jqsoft.nursing.util.CleanMessageUtil;
import com.jqsoft.nursing.util.Util;
import com.jqsoft.nursing.utils.GlideUtils;
import com.jqsoft.nursing.utils2.VersionUtil;
import com.jqsoft.nursing.utils3.util.PreferencesUtils;
import com.tencent.bugly.beta.Beta;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

//import com.jqsoft.nursing.di.ui.activity.MyMessageActivity;


@SuppressLint("ValidFragment")
public class SimpleCardFragment extends AbstractFragment implements View.OnClickListener,
        UseCollectionFragmentContract.View{
    @BindView(R.id.ll_update)
    LinearLayout ll_update;
    @BindView(R.id.main_fl_title)
    RelativeLayout main_fl_title;
@BindView(R.id.Collection)
    LinearLayout Collection;
@BindView(R.id.ll_personinfo)
    LinearLayout ll_personinfo;
    private View rootView;
    @BindView(R.id.ll_about)
    LinearLayout ll_about;
//    @BindView(R.id.ll_changepassword)
//    LinearLayout ll_changepassword;
    @BindView(R.id.ll_clean)
    LinearLayout ll_clean;
    @BindView(R.id.ll_setip)
    LinearLayout ll_setip;
    @BindView(R.id.tv_areaid)
    TextView tv_areaid;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.ll_mymessage)
    LinearLayout ll_mymessage;
//    @BindView(R.id.server_size)
//    TextView server_size;
    List<CenterListBean> CenterList;
    List<HelpListBean>   HelpList;
//    @BindView(R.id.collection_size)
//    TextView collection_size;
    @BindView(R.id.ClearChche)
    TextView ClearChche;

    @BindView(R.id.btn_zancun)
    RoundTextView btn_zancun;

    @BindView(R.id.ll_clean_new)
    LinearLayout ll_clean_new;

    @BindView(R.id.view_room)
    View view_room;

    @BindView(R.id.view_bed)
    View view_bed;

    @BindView(R.id.head)
    CircleImageView head;

    @BindView(R.id.tv_version)
    TextView tv_version;

    CompositeSubscription mCompositeSubscription;

    public static boolean isManual = false;


    @Inject
    UseCollectionFragmentPresenter mPresenter;
    public static SimpleCardFragment getInstance(String title) {
        SimpleCardFragment sf = new SimpleCardFragment();
        return sf;
    }

    @Override
    protected void initInject() {
        DaggerApplication.get(getContext())
                .getAppComponent()
                .addUseCollectionFragment(new UseCollectionFragmentModule(this))
                .inject(this);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fr_simple_card;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unregisterUpgradeEvent();

    }

    private void handleUpgradeEvent(int i){
        switch (i){
            case Constants.EVENT_TYPE_BUGLY_UPGRADE_SUCCESS:
                Util.showToast(getActivity(), Constants.HINT_GET_UPGRADE_INFO_SUCCESS);
                Util.hideGifProgressDialog(getActivity());
                break;
            case Constants.EVENT_TYPE_BUGLY_UPGRADE_FAILURE:
                Util.showToast(getActivity(), Constants.HINT_GET_UPGRADE_INFO_FAILURE);
                Util.hideGifProgressDialog(getActivity());
                break;
            case Constants.EVENT_TYPE_BUGLY_UPGRADE_UPGRADING:
               // Util.showGifProgressDialog(getActivity());
                break;
            case Constants.EVENT_TYPE_BUGLY_UPGRADE_NO_VERSION:
                if (isManual) {
                    Util.showToast(getActivity(), Constants.HINT_GET_UPGRADE_INFO_ALREADY_LATEST);
                }
                Util.hideGifProgressDialog(getActivity());
                break;
            case Constants.EVENT_TYPE_BUGLY_UPGRADE_DOWNLOAD_COMPLETED:
                break;
            default:
                break;
        }
    }

    private void registerUpgradeEvent(){
        Subscription subscription = RxBus.getDefault().toObservable(Constants.EVENT_TYPE_BUGLY_UPGRADE_CODE, Integer.class)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        handleUpgradeEvent(integer);
                    }
                });
        if (mCompositeSubscription==null){
            mCompositeSubscription=new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    private void unregisterUpgradeEvent(){
        if (mCompositeSubscription!=null && mCompositeSubscription.hasSubscriptions()){
            mCompositeSubscription.unsubscribe();
        }
    }


    @Override
    protected void initData() {
        registerUpgradeEvent();

    }

    @Override
    protected void initView() {
       String nurseHeadImg= IdentityManager.getHeadImg(getActivity());
        GlideUtils.loadImageWithPlaceholderAndError(head, nurseHeadImg, R.mipmap.icon_touxiang, R.mipmap.icon_touxiang);
        Bundle bundle = getArguments();
        String type = (String) bundle.getSerializable("type");
        if(type.equals("1")){
            //护工登录
            ll_clean.setVisibility(View.VISIBLE);
            ll_setip.setVisibility(View.VISIBLE);
            view_room.setVisibility(View.VISIBLE);
            view_bed.setVisibility(View.VISIBLE);

        }else {
            ll_clean.setVisibility(View.GONE);
            ll_setip.setVisibility(View.GONE);
            view_room.setVisibility(View.GONE);
            view_bed.setVisibility(View.GONE);
        }


        main_fl_title.setOnClickListener(this);
        ll_mymessage.setOnClickListener(this);
        Collection.setOnClickListener(this);
        ll_personinfo.setOnClickListener(this);
        ll_about.setOnClickListener(this);
        ll_clean_new.setOnClickListener(this);
        ll_clean.setOnClickListener(this);
        ll_setip.setOnClickListener(this);
        ll_update.setOnClickListener(this);
//        String areaid= Identity.srcInfo.getArea().trim();
        String name= IdentityManager.getLoginSuccessUsername(getContext());
        username.setText(name);
        String jigou= IdentityManager.getOrgnizationName(getContext());
        tv_areaid.setText(jigou);
//        List<SettingServerBean> Serverlist = LitePal.findAll(SettingServerBean.class);
//        server_size.setText(String.valueOf(Serverlist.size()));
   //     ClearChche.setOnClickListener(this);
        btn_zancun.setOnClickListener(this);

        showVersion();

    }

    @Override
    protected void loadData() {
//        String name= IdentityManager.getLoginSuccessUsername(getContext());
//        Map<String, String> map = ParametersFactory.getGCAPersonCollectionMap(getContext(),name,
//                "collectionData.queryMyCollectionReceptions");
//        mPresenter.main(map, false);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Collection:
                Util.gotoActivity(getActivity(), PersonCollectionActivity.class);
                break;
            case  R.id.ll_personinfo:
                Util.gotoActivity(getActivity(), PersonalInfoActivity.class);
                break;
            case  R.id.ll_about:
                Util.gotoActivity(getActivity(), AboutInfoActivity.class);
                break;
//            case  R.id.ll_changepassword:
//                Util.gotoActivity(getActivity(), ChangePasswordActivity.class);
//                break;
            case  R.id.ll_clean:
                Bundle bundle = new Bundle();
                bundle.putString("SelectLogo","1");
                Util.gotoActivityWithBundle(getActivity(), BuildingRoomActivity.class,bundle);
                break;
            case  R.id.ll_setip:
                Bundle bundle2 = new Bundle();
                bundle2.putString("SelectLogo","0");
                Util.gotoActivityWithBundle(getActivity(), BuildingRoomActivity.class,bundle2);
                break;

            case  R.id. btn_zancun:
                warnExit();
                break;
            case  R.id.ll_update:
                isManual=true;
                Beta.checkUpgrade();
//                Toast.makeText(getActivity(),"已经是最新版本了!",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.ll_mymessage:
                Util.gotoActivity(getActivity(), MyMessageActivity.class);
//                Toast.makeText(getActivity(),"即将上限，敬请期待!",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.main_fl_title:

                Util.gotoActivity(getActivity(), PersonalInfoActivity.class);
                break;
            case  R.id.ll_clean_new:
                show();

                break;

        }
    }


    @Override
    public void onLoadListSuccess(GCAHttpResultBaseBean<List<PersonCollectionBean>> beanList) {
        List<PersonCollectionBean> list = getListFromResult(beanList);
        PersonCollectionBean personCollectionBeanlist=list.get(0);
        CenterList =personCollectionBeanlist.getCenterList();
        HelpList =personCollectionBeanlist.getHelpList();

//        collection_size.setText(String.valueOf(CenterList.size()+HelpList.size()));

    }

    @Override
    public void onLoadMoreListSuccess(GCAHttpResultBaseBean<List<PersonCollectionBean> > beanList) {


    }

    @Override
    public void onLoadListFailure(String message, boolean isLoadMore) {

    }
    public List<PersonCollectionBean> getListFromResult(GCAHttpResultBaseBean<List<PersonCollectionBean>> beanList) {
        if (beanList != null) {
            List<PersonCollectionBean> list = beanList.getData();
            return list;
        } else {
            return null;
        }
    }
private void  show() {
    StringBuilder sb = new StringBuilder();
    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    AlertDialog dialogInfo = null;
    try {
        dialogInfo = builder.setTitle("清除缓存")
                .setMessage("清除缓存会导致下载的内容删除，是否确定?(缓存大小："+CleanMessageUtil.getTotalCacheSize(getContext())+")")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


    //                    text_clear.setText("0.0B");

                        dialog.dismiss();

                            CleanMessageUtil.clearAllCache(getContext());


                        Toast.makeText(getContext(), "缓存已清除", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", null)
                .create();
    } catch (Exception e) {
        e.printStackTrace();
    }
    dialogInfo.show();
}


    public void warnExit() {
        Util.showMaterialDialog(getActivity(), Constants.HINT, Constants.HINT_CONFIRM_EXITING, Constants.CANCEL,
                new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }, Constants.CONFIRM, new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        getActivity().finish();
                        doExit();
                    }
                }, true);
    }

    private void doExit() {
        //   Util.logoutJMessageAndExitApplication();

        PreferencesUtils.putBoolean(getActivity(), Constants.WHETHER_REMEMBER_PASSWORD_KEY, false);
        PreferencesUtils.putString(getActivity(), Constants.REMEMBERED_PASSWORD_KEY, Constants.EMPTY_STRING);

//        Util.exitApplication();
        exitApp();

    }

    private void exitApp(){
        RxBus.getDefault().post(Constants.EVENT_TYPE_FINISH_ACTIVITY, true);

        Intent intent = new Intent();
        intent.setClass(getActivity(),LoginActivityNew.class);
        getActivity().startActivity(intent);

//        ActivityUtils.launchActivity(Constants.PACKAGE_NAME, Constants.LOGIN_ACTIVITY_NAME);
       /* gotoActivity(getActivity(), LoginActivityNew.class);*/

//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(0);

    }

    public void showVersion() {
        String version = VersionUtil.getVersionName(getActivity());
        version = Constants.VERSION_PREFIX + version;
        tv_version.setText(version);
    }

}