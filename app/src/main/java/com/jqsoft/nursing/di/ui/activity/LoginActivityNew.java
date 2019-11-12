package com.jqsoft.nursing.di.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.caption.netmonitorlibrary.netStateLib.NetChangeObserver;
import com.caption.netmonitorlibrary.netStateLib.NetStateReceiver;
import com.caption.netmonitorlibrary.netStateLib.NetUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding2.view.RxView;
import com.jqsoft.livebody_verify_lib.bean.Constant;
import com.jqsoft.livebody_verify_lib.bean.IDCardNumberVerificationBean;
import com.jqsoft.livebody_verify_lib.callback.MyResultCallback;
import com.jqsoft.livebody_verify_lib.util.Base64Util;
import com.jqsoft.livebody_verify_lib.util.WebServiceUtils;
import com.jqsoft.nursing.R;
import com.jqsoft.nursing.arcface.HeadCollectActivity;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.base.Identity;
import com.jqsoft.nursing.base.IdentityManager;
import com.jqsoft.nursing.base.ParametersFactory;
import com.jqsoft.nursing.base.Version;
import com.jqsoft.nursing.bean.ArcDataBean;
import com.jqsoft.nursing.bean.DictionaryAreaData;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultNewBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.SRCLoginDataDictionaryBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.SRCLoginSalvationBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.SettingServerBean;
import com.jqsoft.nursing.bean.nursing.LoginResultNewBean;
import com.jqsoft.nursing.bean.resident.SRCLoginAreaBean;
import com.jqsoft.nursing.bean.resident.SRCLoginBean;
import com.jqsoft.nursing.bean.response.LoginResultBean;
import com.jqsoft.nursing.bean.response_new.LoginResultBean2;
import com.jqsoft.nursing.di.contract.SRCLoginContract;
import com.jqsoft.nursing.di.module.SRCLoginModule;
import com.jqsoft.nursing.di.presenter.SRCLoginPresenter;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.di_app.DaggerApplication;
import com.jqsoft.nursing.di_http.http.nursing.GCARetrofit;
import com.jqsoft.nursing.listener.NoDoubleClickListener;
import com.jqsoft.nursing.rx.RxBus;
import com.jqsoft.nursing.util.ExampleUtil;
import com.jqsoft.nursing.util.ToastUtil;
import com.jqsoft.nursing.util.Util;
import com.jqsoft.nursing.utils.LogUtil;
import com.jqsoft.nursing.utils2.VersionUtil;
import com.jqsoft.nursing.utils3.util.PreferencesUtils;
import com.jqsoft.nursing.utils3.util.StringUtils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.upgrade.UpgradeStateListener;
import com.tencent.bugly.crashreport.CrashReport;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.runtime.Permission;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.request.RequestCall;

import net.qiujuer.genius.ui.widget.CheckBox;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.jqsoft.nursing.base.Constants.WORKBENCH_ON_NEW_INTENT_INITIAL_INDEX_KEY;

//import cn.jpush.android.api.JPushInterface;
//import cn.jpush.android.api.TagAliasCallback;

//import com.jqsoft.nursing.listener.IMProcessSuccessListener;
//import com.jqsoft.nursing.util.ExampleUtil;
//import cn.jpush.android.api.JPushInterface;
//import cn.jpush.android.api.TagAliasCallback;
//import cn.jpush.im.android.api.JMessageClient;
//import cn.jpush.im.android.api.callback.GetUserInfoCallback;
//import cn.jpush.im.android.api.model.UserInfo;
//import cn.jpush.im.api.BasicCallback;

/**
 * Created by Administrator on 2017-07-12.
 */
//最新效果图的登录界面
public    class LoginActivityNew extends AbstractActivity implements SRCLoginContract.View {
    @BindView(R.id.tv_app_name)
    TextView tvAppName;
    @BindView(R.id.acet_username)
    AppCompatEditText acetUsername;
    @BindView(R.id.bt_username_clear)
    Button btUsernameClear;
    @BindView(R.id.acet_password)
    AppCompatEditText acetPassword;
    @BindView(R.id.bt_password_clear)
    Button btPasswordClear;
    @BindView(R.id.bt_password_eye)
    Button btPasswordEye;
    @BindView(R.id.cb_rp)
    CheckBox cbRp;
    @BindView(R.id.bt_login)
    AppCompatButton btLogin;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_version)
    TextView tvVersion;


    @BindView(R.id.sv_bg)
    ScrollView  sv_bg;

    @BindView(R.id.btn_save)
    Button btn_save;

    @Inject
    SRCLoginPresenter loginPresenter;

    public static final String TAG = "LoginActivityNew";

    public static boolean HAS_LOGGED_IN = false;

    public boolean shouldShowBackButton = false;
    public boolean shouldFinishWhenLoginSuccess = false;

    @OnClick(R.id.bt_username_clear)
    public void onClearUsername() {
        acetUsername.setText("");
//        acetPassword.setText("");
    }

    @OnClick(R.id.bt_password_clear)
    public void onClearPassword() {
        acetPassword.setText("");
    }

    @OnClick(R.id.bt_password_eye)
    public void onChangePasswordVisibility() {
        int inputType = acetPassword.getInputType();
        if (inputType == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            btPasswordEye.setBackgroundResource(R.mipmap.eye_open_blue);
            acetPassword.setInputType((InputType.TYPE_CLASS_TEXT));
        } else {
//            btPasswordEye.setBackgroundResource(R.mipmap.eye_close_purple);
            btPasswordEye.setBackgroundResource(R.mipmap.eye_open_gray);
            acetPassword.setInputType((InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD));
        }
        acetPassword.setSelection(acetPassword.getText().toString().length());
    }

    //    @OnClick(R.id.login)
    public void onLogin(boolean isManual) {
//        String s = null;
//        int length = s.length();

//        boolean willRememberPassword = cbRememberPassword.isCheck();
        boolean willRememberPassword = cbRp.isChecked();
        LogUtil.i("willRememberPassword:" + willRememberPassword);
        rememberPassword(willRememberPassword);

//        boolean isVisible = Util.isAppVisibleToUser(this);
//        LogUtil.i("isVisible:"+isVisible);
//        boolean isRunning = Util.isAppRunning(this);
//        LogUtil.i("isRunning:"+isRunning);
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Util.showNotification(LoginActivityNew.this, "消息", "王主任发送了一条消息");
////                boolean visible = Util.isAppVisibleToUser(LoginActivityNew.this);
////                LogUtil.i("now the app is visible:"+visible);
////                boolean running = Util.isAppRunning(LoginActivityNew.this);
////                LogUtil.i("now the app is running:"+running);
//            }
//        }, 5000);



        if (!checkLogin(isManual)) {
            return;
        } else {
//             doLogin();
//            getToken();
            Util.showGifProgressDialog(getApplicationContext());
            new Thread(runNote).start();

        }


//        testKey();
//        Util.showToast(this, "登录按钮被点击了");
//        LoginActivity.HAS_LOGGED_IN = true;
//        ActivityUtils.launchActivity(Constants.PACKAGE_NAME,
//                Constants.WORKBENCH_ACTIVITY_NAME);
    }

    public boolean checkLogin(boolean isManual) {
        String username = getUsername();
        String password = getPassword();
        if (StringUtils.isBlank(username)) {
            if (isManual) {
                Util.showToast(this, Constants.HINT_PLEASE_INPUT_USERNAME);
            }
            return false;
        } else if (StringUtils.isBlank(password)) {
            if (isManual) {
                Util.showToast(this, Constants.HINT_PLEASE_INPUT_PASSWORD);
            }
            return false;
        } else {
            return true;
        }
    }

    public void doLogin() {


      //  getLogininfo();

        String sToken=PreferencesUtils.getString(LoginActivityNew.this,"token");
        Map<String, String> map = ParametersFactory.getLoginMapFNew(this, getUsername(), getPassword(),sToken);
        loginPresenter.main(map);
      /*  Util.showGifProgressDialog(getApplicationContext());
        Map<String, String> map = ParametersFactory.getLoginMapFromUsernameAndPassword(this, getUsername(), getPassword());
        loginPresenter.main(map);*/

    }



    private void initAlldata(){
        int LoginAreaList = LitePal.count(SRCLoginAreaBean.class);
      //  List<SRCLoginAreaBean> LoginAreaList = LitePal.findAll(SRCLoginAreaBean.class);
        if(LoginAreaList<5000){
            LitePal.deleteAll(SRCLoginAreaBean.class);
            Map<String, String> map1 = ParametersFactory.getLoginMapArea(this);
            loginPresenter.mainArea(map1);
        }else {

        }


        List<SRCLoginSalvationBean> LoginSalvationList = LitePal.findAll(SRCLoginSalvationBean.class);
        if(LoginSalvationList.size()==0){
            Map<String, String> map3 = ParametersFactory.getLoginMapSalvation(this);
            loginPresenter.mainSalvation(map3);
        }else {

        }

        List<SRCLoginDataDictionaryBean> LoginDictionaryList = LitePal.findAll(SRCLoginDataDictionaryBean.class);
        if(LoginDictionaryList.size()==0){
            Map<String, String> map2 = ParametersFactory.getLoginMapDictionary(this);
            loginPresenter.mainDictionary(map2);
        }else {

        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_new;
    }

    @Override
    protected void initData() {

        Intent intent = getIntent();
        shouldShowBackButton = Util.getShouldShowBackButton(intent);
        shouldFinishWhenLoginSuccess = Util.getShouldFinishWhenLoginSuccess(intent);
        cbRp.setChecked(true);
        copyDatabase();

//        Resources resources = getApplication().getResources();
//        Drawable btnDrawable = resources.getDrawable(R.drawable.llbg);
//        sv_bg.setBackgroundDrawable(btnDrawable);
    }



    protected void initInject() {
        DaggerApplication.get(this)
                .getAppComponent()
                .addLogin(new SRCLoginModule(this))
                .inject(this);
    }

    public String getUsername() {
        return Util.trimString(acetUsername.getText().toString());
    }

    public String getPassword() {
        return Util.trimString(acetPassword.getText().toString());
    }


    private int getDeliveredWorkbenchPageIndex(){
        int targetIndex = getDeliveredIntByKey(WORKBENCH_ON_NEW_INTENT_INITIAL_INDEX_KEY);
        LogUtil.i("LoginActivityNew getDeliveredWorkbenchPageIndex before process targetIndex:"+targetIndex);
        if (targetIndex<Constants.WORKBENCH_INDEX || targetIndex>Constants.WORKBENCH_MINE){
            targetIndex=Constants.WORKBENCH_INDEX;
        }
        LogUtil.i("LoginActivityNew getDeliveredWorkbenchPageIndex after process targetIndex:"+targetIndex);
        return targetIndex;
    }

    public void initView() {



        setAcrsoftFace();

        requestCamera(Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_PHONE_STATE, Permission.CAMERA,Permission.ACCESS_COARSE_LOCATION,Permission.ACCESS_FINE_LOCATION);

        TextWatcher userTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String trimmedString = Util.trimString(s.toString());
                if (trimmedString.length() > 0) {
                    btUsernameClear.setVisibility(View.VISIBLE);
                } else {
                    btUsernameClear.setVisibility(View.INVISIBLE);
                }

            }
        };
        acetUsername.addTextChangedListener(userTextWatcher);

        TextWatcher passwordTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String trimmedString = Util.trimString(s.toString());
                if (trimmedString.length() > 0) {
                    btPasswordClear.setVisibility(View.VISIBLE);
                } else {
                    btPasswordClear.setVisibility(View.INVISIBLE);
                }
            }
        };
        acetPassword.addTextChangedListener(passwordTextWatcher);
        acetPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO){
                    onLogin(true);
                }
                return false;
            }
        });

        setClearButtonVisibility();

        initRememberPasswordCheckBox();

        btLogin.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                super.onNoDoubleClick(v);
//                startActivityInAnotherApp();
//                Util.gotoActivity(LoginActivityNew.this,CalendarActivity.class);
                onLogin(true);
            }
        });

        RxView.clicks(tvForgetPassword)
                .throttleFirst(Constants.RXBINDING_THROTTLE, TimeUnit.SECONDS)
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object value) {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.CARD_NO_KEY, getUsername());
                        Util.gotoActivityWithBundle(LoginActivityNew.this, RetrievePasswordActivity.class, bundle);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        RxView.clicks(tvRegister)
                .throttleFirst(Constants.RXBINDING_THROTTLE, TimeUnit.SECONDS)
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object value) {
                     //   Util.gotoActivity(LoginActivityNew.this, RegisterActivity.class);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        showVersion();

//        testKey();

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.gotoActivity(LoginActivityNew.this, SettingServerActivity.class);
            }
        });
//        onLogin(false);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

//    int REQUEST_CODE=100;
//    private void startActivityInAnotherApp(){
//        try {
//            PackageManager pm = getPackageManager();
////            Intent intent = pm.getLaunchIntentForPackage("com.example.gareamedicalexaple");
////            startActivity(intent);
//
//            Intent intent2 = new Intent();
//            intent2.setClassName("com.example.gareamedicalexaple", "com.example.gareamedicalexample.EcgExample");
//            startActivityForResult(intent2, REQUEST_CODE);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    private void finishIntroductionActivity(){
        RxBus.getDefault().post(Constants.EVENT_TYPE_FINISH_INTRODUCTION_ACTIVITY, true);
    }

    @Override
    protected void loadData() {

    }

    public void showVersion() {
        String version = VersionUtil.getVersionName(this);
        version = Constants.VERSION_PREFIX + version;
        tvVersion.setText(version);
    }

    public void testKey() {
        String s = "<p>1.家庭医生</p><p >&nbsp;2.</p>";
        String replacedS = Util.getReplacedXmlTagString(s);
        LogUtil.i("replacedS:"+replacedS);

//        LoginResultBean2 bean = SimulateData.getLoginResultBean2();
//        LoginResultBean2 bean2 = SimulateData.getLoginResultBean2();
//        TestResultBean trb = new TestResultBean("QTNDRTc2MjUtRTcxRC00NTg0LTlGNjEtODM0Mzc2RkM4QzYx","YWRtaW4=",
//                "566h55CG5ZGY", "", "","MDAwMA==", "RkJBMzdGN0EtREJFOC00REJGLUJCREMtOTAwMEIyQzdCRUI4",
//                "MDAwMDAwMDAtMA==", "566h55CG5py65p6E", "MzA=", "Mw==", "MzQxMjAy", "6aKN5bee5Yy6", bean);
//        TestResultBean trb2 = new TestResultBean("QTNDRTc2MjUtRTcxRC00NTg0LTlGNjEtODM0Mzc2RkM4QzYx","YWRtaW4=",
//                "566h55CG5ZGY", "", "","MDAwMA==", "RkJBMzdGN0EtREJFOC00REJGLUJCREMtOTAwMEIyQzdCRUI4",
//                "MDAwMDAwMDAtMA==", "566h55CG5py65p6E", "MzA=", "Mw==", "MzQxMjAy", "6aKN5bee5Yy6", bean2);
//        List<TestResultBean> list = new ArrayList<>();
//        list.add(trb);
//        list.add(trb2);
//        BeanBase64Decoder.decodeBase64Bean(list);
//        LogUtil.i("test key ended");


//        Identity identity = Identity.getInstance();
//        identity.getLoginMapFromUsernameAndPassword(getUsername(), getPassword());

//        CommonParameters cp = identity.getCommonParametersObject();
//        LoginParameters lp = new LoginParameters(cp.getKey(), cp.getTimestamp(), cp.getToken(), cp.getSig(), cp.getV(),
//                "user", "pass");
////        LoginParameters lp = new LoginParameters("key", "timestamp", "token", "sig", "v",
////                "user", "pass");
//        identity.getParametersKeysAndValuesExcludeSignature(cp);
//        identity.getParametersKeysAndValuesExcludeSignature(lp);
    }

    public void initRememberPasswordCheckBox() {
        boolean isRemembered = PreferencesUtils.getBoolean(this, Constants.WHETHER_REMEMBER_PASSWORD_KEY, false);
        LogUtil.i("saved isRememberedPassword:" + isRemembered);
        cbRp.setChecked(isRemembered);
        cbRp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rememberPassword(isChecked);
            }
        });
//        cbRememberPassword.setChecked(isRemembered);
//        cbRememberPassword.setChecked(true);
//        cbRememberPassword.setOncheckListener(new CheckBox.OnCheckListener() {
//            @Override
//            public void onCheck(CheckBox checkBox, boolean b) {
//                rememberPassword(b);
//            }
//        });
        if (isRemembered) {
            String rememberedUsername = PreferencesUtils.getString(this, Constants.REMEMBERED_USERNAME_KEY, Constants.EMPTY_STRING);
            String rememberedPassword = PreferencesUtils.getString(this, Constants.REMEMBERED_PASSWORD_KEY, Constants.EMPTY_STRING);
            acetUsername.setText(rememberedUsername);
            acetPassword.setText(rememberedPassword);
        } else {
            String rememberedUsername = PreferencesUtils.getString(this, Constants.REMEMBERED_USERNAME_KEY, Constants.EMPTY_STRING);
            acetUsername.setText(rememberedUsername);
//            acetUsername.setText(Constants.EMPTY_STRING);
            acetPassword.setText(Constants.EMPTY_STRING);
        }
    }

    public void rememberPassword(boolean b) {
        PreferencesUtils.putBoolean(LoginActivityNew.this, Constants.WHETHER_REMEMBER_PASSWORD_KEY, b);
        if (b) {
            String username = Util.trimString(acetUsername.getText().toString());
            String password = Util.trimString(acetPassword.getText().toString());
            PreferencesUtils.putString(LoginActivityNew.this, Constants.REMEMBERED_USERNAME_KEY, username);
            PreferencesUtils.putString(LoginActivityNew.this, Constants.REMEMBERED_PASSWORD_KEY, password);
        } else {
            String username = Util.trimString(acetUsername.getText().toString());
            PreferencesUtils.putString(LoginActivityNew.this, Constants.REMEMBERED_USERNAME_KEY, username);
//            PreferencesUtils.putString(LoginActivityNew.this, Constants.REMEMBERED_USERNAME_KEY, Constants.EMPTY_STRING);
            PreferencesUtils.putString(LoginActivityNew.this, Constants.REMEMBERED_PASSWORD_KEY, Constants.EMPTY_STRING);
        }

    }

    public void setClearButtonVisibility() {
        String username = acetUsername.getText().toString();
        if (username.length() > 0) {
            btUsernameClear.setVisibility(View.VISIBLE);
        } else {
            btUsernameClear.setVisibility(View.INVISIBLE);
        }

        String password = acetPassword.getText().toString();
        if (password.length() > 0) {
            btPasswordClear.setVisibility(View.VISIBLE);
        } else {
            btPasswordClear.setVisibility(View.INVISIBLE);
        }
//        btPasswordClear.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        HAS_LOGGED_IN = false;

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//            if(isReLogin){
//                Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
//                mHomeIntent.addCategory(Intent.CATEGORY_HOME);
//                mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                LoginActivityNew.this.startActivity(mHomeIntent);
//            }else{
            LoginActivityNew.this.finish();
//            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void assignIdentityData(HttpResultBaseBean<LoginResultBean> bean) {
        if (bean != null) {
            LoginResultBean lrb = bean.getData();
            Identity.initWithData(lrb);
        }
    }

//    public void assignIdentityData2(HttpResultBaseBean<LoginResultBean2> bean) {
//        if (bean != null) {
//            LoginResultBean2 lrb = bean.getData();
////            Util.decodeBase64Bean(lrb);
////            decodeBase64FieldData(lrb);
//            Identity.info = lrb;
//
//            Version.FILE_URL_BASE= Version.FILE_URL_BASE+Identity.info.getSmanagementdivisioncode()
//                    +"/";
//
//        }
//    }

    public void assignSRCIdentityData(HttpResultNurseBaseBean<SRCLoginBean> bean) {
        if (bean != null) {
            SRCLoginBean lrb = bean.getData();

            Identity.srcInfo = lrb;
            Identity.srcInfo.setLoginSuccessUsername(getUsername());
            Identity.srcInfo.setLoginSuccessPassword(getPassword());
            Identity.srcInfo.setTrueName(lrb.getTrueName());
            Identity.srcInfo.setsOrgName(lrb.getsOrgName());
            Identity.srcInfo.setEmployeeSex(lrb.getEmployeeSex());
            Identity.srcInfo.setEmployeeName(lrb.getEmployeeName());
            Identity.srcInfo.setUserName(lrb.getUserName());
            Identity.srcInfo.setRealName(lrb.getRealName());

            Identity.srcInfo.setRoleId(lrb.getRoleId());
            Identity.srcInfo.setAreaId(lrb.getAreaId());
            Identity.srcInfo.setAreaLevel(lrb.getAreaLevel());

            IdentityManager.setObjectToShare(this, Identity.srcInfo, Constants.SRC_LOGIN_RESULT_BEAN_KEY);

           /* String srcAreaCode = Identity.srcInfo.getAreaCode();
            if(TextUtils.isEmpty(srcAreaCode) ){
                Version.FILE_URL_BASE= Version.FILE_URL_BASE+"null"
                        +"/";
            }else{
                Version.FILE_URL_BASE= Version.FILE_URL_BASE+Util.trimString(Identity.srcInfo.getAreaCode())
                        +"/";
            }*/

        }
    }

    public void decodeBase64FieldData(LoginResultBean2 lrb) {
        if (lrb != null) {
            lrb.setGuserid(Util.getDecodedBase64String(lrb.getGuserid()));
            lrb.setSloginname(Util.getDecodedBase64String(lrb.getSloginname()));
            lrb.setSusername(Util.getDecodedBase64String(lrb.getSusername()));
            lrb.setSphone(Util.getDecodedBase64String(lrb.getSphone()));
            lrb.setSsexname(Util.getDecodedBase64String(lrb.getSsexname()));
            lrb.setShiploginname(Util.getDecodedBase64String(lrb.getShiploginname()));
            lrb.setSorganizationkey(Util.getDecodedBase64String(lrb.getSorganizationkey()));
            lrb.setSorgInstitutioncode(Util.getDecodedBase64String(lrb.getSorgInstitutioncode()));
            lrb.setSorganizationname(Util.getDecodedBase64String(lrb.getSorganizationname()));
            lrb.setSorganizationtypecode(Util.getDecodedBase64String(lrb.getSorganizationtypecode()));
            lrb.setSorganizationlevelcode(Util.getDecodedBase64String(lrb.getSorganizationlevelcode()));
            lrb.setSmanagementdivisioncode(Util.getDecodedBase64String(lrb.getSmanagementdivisioncode()));
            lrb.setSmanagementdivisionname(Util.getDecodedBase64String(lrb.getSmanagementdivisionname()));
        }
    }

//    public void registerAndLoginJMessage(final String userId, final String password, final IMProcessSuccessListener imProcessSuccessListener) {
//        Util.showGifProgressDialog(this);
//        JMessageClient.register(userId, password, new BasicCallback() {
//
//            @Override
//            public void gotResult(final int status, final String desc) {
//                if (status == Constants.JMESSAGE_REGISTER_SUCCESS_CODE ||
//                        status == Constants.JMESSAGE_REGISTER_USER_EXIST_CODE) {
//                    Util.showToast(LoginActivityNew.this, Constants.HINT_REGISTER_IM_SUCCESS);
//                    JMessageClient.login(userId, password, new BasicCallback() {
//                        @Override
//                        public void gotResult(final int status, String desc) {
//                            if (status == Constants.JMESSAGE_LOGIN_SUCCESS_CODE) {
//                                Util.showToast(LoginActivityNew.this, Constants.HINT_LOGIN_IM_SUCCESS);
//                                String username = JMessageClient.getMyInfo().getUserName();
//                                String appKey = JMessageClient.getMyInfo().getAppKey();
//                                JMessageClient.getUserInfo(userId, new GetUserInfoCallback() {
//                                    @Override
//                                    public void gotResult(int i, String s, UserInfo userInfo) {
//                                        if (Constants.JMESSAGE_GET_USER_INFO_SUCCESS_CODE == i) {
//                                            Identity.imUserInfo = userInfo;
//                                            Util.showToast(LoginActivityNew.this, Constants.HINT_IM_GET_USER_INFO_SUCCESS);
//                                            if (imProcessSuccessListener != null) {
//                                                imProcessSuccessListener.onIMProcessSuccess(userInfo);
//                                            }
//                                        } else {
//                                            Util.showToast(LoginActivityNew.this, Constants.HINT_IM_GET_USER_INFO_FAILURE);
//                                        }
//                                        Util.hideGifProgressDialog(LoginActivityNew.this);
//                                    }
//                                });
////                                UserEntry user = UserEntry.getUser(username, appKey);
////                                if (null == user) {
////                                    user = new UserEntry(username, appKey);
////                                    user.save();
////                                }
////                                mContext.onRegistSuccess();
//                            } else {
//                                Util.showToast(LoginActivityNew.this, Constants.HINT_LOGIN_IM_FAILURE);
//                                Util.hideGifProgressDialog(LoginActivityNew.this);
////                                mLoginDialog.dismiss();
////                                HandleResponseCode.onHandle(mContext, status, false);
//                            }
//                        }
//                    });
//                } else {
//                    Util.hideGifProgressDialog(LoginActivityNew.this);
//                    Util.showToast(LoginActivityNew.this, Constants.HINT_REGISTER_IM_FAILURE);
//                }
//            }
//        });
//    }

    private void setAlias(String alias) {
//        if (Util.getAliasSetSuccess(getApplication())){
//            return;
//        }
        alias = Util.trimString(alias);
        if (TextUtils.isEmpty(alias)) {
            Util.showToast(LoginActivityNew.this, Constants.HINT_ALIAS_EMPTY);
            return;
        }
        if (!ExampleUtil.isValidTagAndAlias(alias)) {
            Util.showToast(LoginActivityNew.this, Constants.HINT_ALIAS_INVALID);
            return;
        }

        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }

//    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
//        @Override
//        public void gotResult(int code, String alias, Set<String> tags) {
//            String logs;
//            switch (code) {
//                case Constants.JPUSH_SET_ALIAS_SUCCESS_CODE:
//                    logs = "Set tag and alias success";
//                    LogUtil.i(TAG, logs);
//                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
//                    Util.setAliasStatus(LoginActivityNew.this, true);
////                    Util.showToast(LoginActivityNew.this, Constants.HINT_JPUSH_SET_ALIAS_SUCCESS);
//                    break;
//                case Constants.JPUSH_SET_ALIAS_FAILURE_CODE:
//                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
//                    LogUtil.i(TAG, logs);
//                    // 延迟 60 秒来调用 Handler 设置别名
//                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
//                    break;
//                default:
//                    logs = "Failed with errorCode = " + code;
//                    LogUtil.i(TAG, logs);
//            }
//        }
//    };
    private static final int MSG_SET_ALIAS = Constants.HANDLER_SET_ALIAS_MSG_ID;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
//                case MSG_SET_ALIAS:
//                    LogUtil.i(TAG, "Set alias in handler.");
//                    // 调用 JPush 接口来设置别名。
//                    JPushInterface.setAliasAndTags(LoginActivityNew.this,
//                            (String) msg.obj,
//                            null,
//                            mAliasCallback);
//                    break;
                default:
                    LogUtil.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };

    public void gotoWorkbenchActivity() {
//        if (shouldFinishWhenLoginSuccess) {
//            finish();
//        } else {
//
//        }

//        ActivityUtils.launchActivity(Constants.PACKAGE_NAME,
//                Constants.WORKBENCH_ACTIVITY_NAME);


        Bundle bundle = new Bundle();
        int targetIndex = getDeliveredWorkbenchPageIndex();
        boolean isPushNotificationExist = getDeliveredBooleanByKey(Constants.WORKBENCH_PUSH_NOTIFICATION_EXIST_KEY);
        String pi = getDeliveredStringByKey(Constants.WORKBENCH_PUSH_NOTIFICATION_INTENT_KEY);
        String userUuid = getDeliveredStringByKey(Constants.WORKBENCH_PUSH_NOTIFICATION_USER_UUID_KEY);
        LogUtil.i("LoginActivityNew gotoWorkbenchActivity targetIndex:"+targetIndex);
        bundle.putInt(Constants.WORKBENCH_ON_NEW_INTENT_INITIAL_INDEX_KEY, targetIndex);
        bundle.putBoolean(Constants.WORKBENCH_PUSH_NOTIFICATION_EXIST_KEY, isPushNotificationExist);
        bundle.putString(Constants.WORKBENCH_PUSH_NOTIFICATION_INTENT_KEY, pi);
        bundle.putString(Constants.WORKBENCH_PUSH_NOTIFICATION_USER_UUID_KEY, userUuid);

        String readResult = getDeliveredStringByKey(Constants.NURSING_READ_RESULT_KEY);
        boolean isFromNfc = getDeliveredBooleanByKey(Constants.NURSING_IS_FROM_NFC_KEY);
        bundle.putString(Constants.NURSING_READ_RESULT_KEY, readResult);
        bundle.putBoolean(Constants.NURSING_IS_FROM_NFC_KEY, isFromNfc);
        Util.gotoActivityWithBundle(this, ArcFaceListActivity.class, bundle);
    }

//    @Override
//    public void onLoginSuccess(HttpResultBaseBean<SRCLoginBean> bean) {
//
//
//    //    Util.showToast(this, Constants.HINT_LOGIN_SUCCESS);
//        HAS_LOGGED_IN = true;
//
//
//
//
//        assignSRCIdentityData(bean);
//        String alias = Util.trimString(IdentityManager.getCardNo(this));
//        //禁用极光推送
////        setAlias(alias);
//        gotoWorkbenchActivity();
//
//
//
//
//        if (shouldFinishWhenLoginSuccess){
//            finish();
//        } else {
//
//        }
//        finishIntroductionActivity();
//
//    }

    @Override
    public void onLoginSuccess(HttpResultNewBaseBean<String> bean) {


        Util.showToast(this, Constants.HINT_LOGIN_SUCCESS);
        HAS_LOGGED_IN = true;

            Gson gson =new Gson();
            Type type = new TypeToken<LoginResultNewBean>() {}.getType();
           LoginResultNewBean loginResultNewBean =  gson.fromJson(bean.getBackInfo().toString(),type);


        String jsonString = gson.toJson(loginResultNewBean);
        PreferencesUtils.putString(LoginActivityNew.this,"UserLoginInfo",jsonString);
//        assignSRCIdentityData(bean);

        gotoWorkbenchActivity();




        if (shouldFinishWhenLoginSuccess){
            finish();
        } else {

        }
        finishIntroductionActivity();
    }

    @Override
    public void onLoginFailure(String message) {
        Util.hideGifProgressDialog(this);
        HAS_LOGGED_IN = false;
////        Util.hideLoadingDialog();
//        Util.hideGifProgressDialog(this);
        Util.showToast(this, message);
//        Util.showToast(this, message);

//        LogUtil.i("登录失败");

        finishIntroductionActivity();

    }

    @Override
    public void onLoginAreaSuccess(HttpResultBaseBean<List<SRCLoginAreaBean>> bean) {
      //  Util.hideGifProgressDialog(this);
        List<SRCLoginAreaBean> lrb = bean.getData();
        List<SRCLoginAreaBean> LoginAreaList = LitePal.findAll(SRCLoginAreaBean.class);


        if(lrb.size()==0){
          //  String s1=lrb.get(0).getAreaName();
        }else {
            if(LoginAreaList.size()==0){
                LitePal.saveAll(lrb);
               /* for(int i=0;i<lrb.size();i++){
                    lrb.get(i).save();
                }*/
            }else {

            }

        //    Util.showToast(this, "区域初始化完成");




            List<SRCLoginSalvationBean> LoginSalvationList = LitePal.findAll(SRCLoginSalvationBean.class);
            if(LoginSalvationList.size()==0){
                Map<String, String> map3 = ParametersFactory.getLoginMapSalvation(this);
                loginPresenter.mainSalvation(map3);
            }else {

            }



        }

    }

    @Override
    public void onLoginAreaFailure(String message) {
        Util.hideGifProgressDialog(this);
        Util.showToast(this, message);
    }

    @Override
    public void onLoginDataDictionatySuccess(HttpResultBaseBean<List<SRCLoginDataDictionaryBean>> bean) {
     //   Util.hideGifProgressDialog(this);
        List<SRCLoginDataDictionaryBean> lrb = bean.getData();

        List<SRCLoginDataDictionaryBean> LoginDictionaryList = LitePal.findAll(SRCLoginDataDictionaryBean.class);


        if(lrb.size()==0){
            //  String s1=lrb.get(0).getAreaName();
        }else {
            if(LoginDictionaryList.size()==0){
                LitePal.saveAll(lrb);
                /*for(int i=0;i<lrb.size();i++){
                    lrb.get(i).save();
                }*/
            }else {

            }

          //  Util.showToast(this, "数据字典初始化完成");


            List<SRCLoginAreaBean> LoginAreaList = LitePal.findAll(SRCLoginAreaBean.class);

            List<SRCLoginSalvationBean> LoginSalvationList = LitePal.findAll(SRCLoginSalvationBean.class);


            if(LoginAreaList.size()!=0 && LoginSalvationList.size()!=0 && LoginDictionaryList.size()!=0){

                Map<String, String> map = ParametersFactory.getLoginMapFromUsernameAndPassword(this, getUsername(), getPassword());
                loginPresenter.main(map);
            }else {
                initAlldata();
              //  Toast.makeText(getApplicationContext(),"正在初始化数据",Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onLoginDataDictionatyFailure(String message) {
        Util.hideGifProgressDialog(this);
        Util.showToast(this, message);
    }

    @Override
    public void onLoginSalvationSuccess(HttpResultBaseBean<List<SRCLoginSalvationBean>> bean) {

        List<SRCLoginSalvationBean> lrb = bean.getData();

        List<SRCLoginSalvationBean> LoginSalvationList = LitePal.findAll(SRCLoginSalvationBean.class);

        if(lrb.size()==0){
            //  String s1=lrb.get(0).getAreaName();
        }else {
            if(LoginSalvationList.size()==0){
                LitePal.saveAll(lrb);
               /* for(int i=0;i<lrb.size();i++){
                    boolean s1=   lrb.get(i).save();
                    if(s1){

                    }else {
                        //  Util.showToast(this, "救助事项初始化完成");
                    }
                }*/
            }else {


            }

         //   Util.showToast(this, "救助事项初始化完成");

            List<SRCLoginDataDictionaryBean> LoginDictionaryList = LitePal.findAll(SRCLoginDataDictionaryBean.class);
            if(LoginDictionaryList.size()==0){
                Map<String, String> map2 = ParametersFactory.getLoginMapDictionary(this);
                loginPresenter.mainDictionary(map2);
            }else {

            }

        }
    }

    @Override
    public void onLoginSalvationFailure(String message) {
        Util.hideGifProgressDialog(this);
        Util.showToast(this, message);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    public void initParameters() {
        String baseHttpUrl = Util.getMetaDataFromManifest(this, "HTTP_ACCESS_URL");


        List<SettingServerBean> Serverlist = LitePal.findAll(SettingServerBean.class);
        String name = IdentityManager.getLoginSuccessUsername(getApplicationContext());
        if (Serverlist.size() == 0) {
            String sBaseHttpUrl = baseHttpUrl.substring(7, 26);
            SettingServerBean bean = new SettingServerBean(sBaseHttpUrl, "默认服务器地址", name, "1");
            if (bean.save()) {
                baseHttpUrl = "http://" + bean.getIp() + "/sri/";
            }


        } else {
            for (int i = 0; i < Serverlist.size(); i++) {
                if (Serverlist.get(i).getIsUse().equals("1")) {
                    baseHttpUrl = "http://" + Serverlist.get(i).getIp() + "/sri/";
                }
            }
        }
//        GCARetrofit.BASE_URL =  Util.getMetaDataFromManifest(this, "HTTP_ACCESS_URL");
        GCARetrofit.BASE_URL = baseHttpUrl;
        Version.HTTP_URL = baseHttpUrl;
        Version.FILE_URL_BASE = Version.HTTP_URL.substring(0, Version.HTTP_URL.length() - 1);
        Version.LOGIN_APP_NAME = Util.getMetaDataFromManifest(this, "LOGIN_APP_NAME");
        String buglyId = Util.getMetaDataFromManifest(this, "BUGLY_APP_ID");
        buglyId = Util.getRealBuglyAppId(buglyId);
        Version.BUGLY_APP_ID = buglyId;
        initNetworkStatusListener();

        initStrictMode();


    }
    public NetChangeObserver netChangeObserver;
    public void initNetworkStatusListener() {
        netChangeObserver = new NetChangeObserver() {
            @Override
            public void onNetConnected(NetUtils.NetType type) {
                LogUtil.i("connection to server success,net type:" + type);
//                Util.showToast(DaggerApplication.this, Constants.HINT_NET_CONNECTION_SUCCESS);
            }

            @Override
            public void onNetDisConnect() {
                LogUtil.i("connection to server failure");
//                Util.showToast(DaggerApplication.this, Constants.HINT_NET_CONNECTION_FAILURE);
            }
        };
        NetStateReceiver.registerNetworkStateReceiver(this);
        NetStateReceiver.registerObserver(netChangeObserver);
    }


    public void initStrictMode() {
        if (Version.ENABLE_STRICT_MODE) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());

            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        }
    }



    public void initBugly() {
//        Context context = getApplication();
        Context context = this;
        String packageName = context.getPackageName();
        String processName = Util.getProcessName(android.os.Process.myPid());
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));

//        设置是否显示消息通知
//        如果你不想在通知栏显示下载进度，你可以将这个接口设置为false，默认值为true。
        Beta.enableNotification = true;

//        设置Wifi下自动下载
//        如果你想在Wifi网络下自动下载，可以将这个接口设置为true，默认值为false。
        Beta.autoDownloadOnWifi = false;

        /* 设置更新状态回调接口 */
        Beta.upgradeStateListener = new UpgradeStateListener() {
            @Override
            public void onUpgradeSuccess(boolean isManual) {
                RxBus.getDefault().post(Constants.EVENT_TYPE_BUGLY_UPGRADE_CODE, Constants.EVENT_TYPE_BUGLY_UPGRADE_SUCCESS);
            }

            @Override
            public void onUpgradeFailed(boolean isManual) {
                RxBus.getDefault().post(Constants.EVENT_TYPE_BUGLY_UPGRADE_CODE, Constants.EVENT_TYPE_BUGLY_UPGRADE_FAILURE);
            }

            @Override
            public void onUpgrading(boolean isManual) {
                RxBus.getDefault().post(Constants.EVENT_TYPE_BUGLY_UPGRADE_CODE, Constants.EVENT_TYPE_BUGLY_UPGRADE_UPGRADING);
            }

            @Override
            public void onDownloadCompleted(boolean b) {
                RxBus.getDefault().post(Constants.EVENT_TYPE_BUGLY_UPGRADE_CODE, Constants.EVENT_TYPE_BUGLY_UPGRADE_DOWNLOAD_COMPLETED);
            }

            @Override
            public void onUpgradeNoVersion(boolean isManual) {
                RxBus.getDefault().post(Constants.EVENT_TYPE_BUGLY_UPGRADE_CODE, Constants.EVENT_TYPE_BUGLY_UPGRADE_NO_VERSION);
            }
        };


        initParameters();


//        CrashReport.initCrashreport(getapplication(), version.bugly_app_id, version.bugly_debug_mode, strategy);

//        Bugly.init(this, Version.BUGLY_APP_ID, Version.BUGLY_DEBUG_MODE);
        Bugly.init(this, Version.BUGLY_APP_ID, Version.BUGLY_DEBUG_MODE, strategy);
    }


//    public void getLogininfo() {
//
//        String requestUrl = "http://192.168.44.140:6868/api.phone/"+
//                     "Login";
//        String methodName = "Login";
//
//        Map<String, String> map = new HashMap<>();
//
//        String sPassword="123456";
//        String publicKey="B41B7598-417F-46EC-90E6-D4BE901AB571";
//        String privateKey ="E9E047B6-9B2F-4BE2-B151-CE945FB3D1AB";
//
//        String sNewPassword= DESUtil.encode("E9E047B6",sPassword);
//
//        map.put("UserName", "hltest");
//        map.put("UserPass", sNewPassword);
//
//        String bodyJson = JSON.toJSONString(map);
//
//        String timestamp = Util.getTimestampLeading10CharactersString();
//        String sSign =Util.getEncryptedDigestNew(privateKey, publicKey,timestamp);
//
//        JSONObject data = new JSONObject();
//        try {
//            data.put("Key", privateKey);
//            data.put("Sign",sSign);
//            data.put("Data", "");
//            data.put("AddParams", bodyJson);
//            data.put("Timestamp", timestamp);
//        } catch (JSONException e1) {
//            e1.printStackTrace();
//        }
//        new MyAsyncTask() {
//            @Override
//            public void dopre() {
//
//            }
//
//            @Override
//            public void dopost(String result) {
//                String s=result;
//
//
//            }
//
//
//        }.execute(methodName, data.toString(), requestUrl);
//    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            try {

                JSONObject data = new JSONObject();

                String content = WebServiceUtils.requestNoParams("GetToken",
                       "http://192.168.45.20:5444/api/Auth/GetToken?appid=0b32e011-ab3c-40d0-bbc0-6a55af191b6b");
                if (!TextUtils.isEmpty(content)) {

                } else {

                }
            } catch (Exception e) {
                e.printStackTrace();

            }

        }
    };
    RequestCall loadHeadImageCall;
    RequestCall call;
    RequestCall pushLiveBodyCall;
    String sMessage="";
    String Token="";
    private void loadIDCardNumberHeadImageInfo(final String idNumber) {

        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        String url = "http://192.168.45.20:5444/api/Auth/GetToken?appid=0b32e011-ab3c-40d0-bbc0-6a55af191b6b";
        Request request = new Request.Builder().url(url).get().build();
        try {
            response = okHttpClient.newCall(request).execute();

            String content=response.body().string();
            if (!TextUtils.isEmpty(content)) {
                try {
                    JSONObject json = new JSONObject(content);
                    String isSuccess = json.getString("Success");
                    sMessage = json.getString("ErrorMsg");
                    Token=json.getString("Token");
                    if (!TextUtils.isEmpty(Token)) {
                        myhandler.sendEmptyMessage(Constant.MSG_LOAD_OVER);
                    } else {
                        myhandler.sendEmptyMessage(Constant.MSG_LOAD_ERROR);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    sMessage = e.getMessage();
                    myhandler.sendEmptyMessage(Constant.MSG_LOAD_EXC);
//                        myhandler.sendEmptyMessage(Constant.MSG_LOAD_ERROR);
                }
            } else {
                myhandler.sendEmptyMessage(Constant.MSG_LOAD_EMPTY);
            }

        } catch (IOException e) {
            e.printStackTrace();
            sMessage = e.getMessage();
            myhandler.sendEmptyMessage(Constant.MSG_LOAD_EXC);
        }

    }
    Runnable runNote = new Runnable() {
        @Override
        public void run() {
            loadIDCardNumberHeadImageInfo("");
        }
    };


    //    private String jsonexc;
    @SuppressLint("HandlerLeak")
    private Handler myhandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.SENDLOG:

                    break;
                case Constant.MSG_LOAD_OVER:
                    PreferencesUtils.putString(LoginActivityNew.this,"token",Token);
//                    new Thread(runArea).start();
                    doLogin();

                    break;
                case Constant.MSG_LOAD_EXC:
                    Util.hideGifProgressDialog(LoginActivityNew.this);
                    ToastUtil.show(LoginActivityNew.this,sMessage);
                    break;
                case Constant.APPLy_SCUSSE:

                    break;
                case Constant.APPLY_ERAR:

                    break;
                case Constant.APPLY_FAIL:

                    break;
                case Constant.MSG_LOAD_EMPTY:
                    Util.hideGifProgressDialog(LoginActivityNew.this);
                    ToastUtil.show(LoginActivityNew.this,"返回数据为空");
                    break;
                case Constant.MSG_LOAD_ERROR:

                    break;
                case Constant.GETBASEPHOTO_LOAD_OVER:

                    break;
                case Constant.GETBASEPHOTO_LOAD_SUCESS:

                    break;
                default:

            }

        }
    };



    private void loadIDCardNumberHeadImageInfo1(final String idNumber) {

        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        String url = "http://192.168.45.20:5444/api/DistinguishAPP/GetAreaInfo?Token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJKUUFQSS5XZWJBUEkiLCJleHAiOjE1NzEzODA3NjksImlhdCI6MTU3MTIwNzk2OSwiYXBwSWQiOiIyYzA3ZjBhNC1kZTY1LTQ2N2YtYjZhYi0wZjE2ZWE2NTA3ZDIifQ.K0q8HLBZB_xwADFTu9MzkYIToI5pklW7_Q5uIxSbom0";
        Request request = new Request.Builder().url(url).get().build();
        try {
            response = okHttpClient.newCall(request).execute();

            String content=response.body().string();
            if (!TextUtils.isEmpty(content)) {
                try {
                    List<DictionaryAreaData> dataList = new ArrayList<>();
                    dataList.clear();


                    JSONObject json = new JSONObject(content);
                    String isSuccess = json.getString("Success");
                    sMessage = json.getString("ErrorMsg");
                    Token=json.getString("BackInfo");
                    JSONArray jsonArray1 = new JSONArray(Token);
                    LitePal.deleteAll(DictionaryAreaData.class);

                        for (int i = 0; i < jsonArray1.length(); i++) {


                                DictionaryAreaData info = new DictionaryAreaData();
                                JSONObject jsonData = (JSONObject) jsonArray1.get(i);
                                info.setArealevel(jsonData.getString("iLevel"));
                                info.setAreaname(jsonData.getString("sName"));
                                info.setDTIMESTAMP("");
                                info.setKey("");
                                info.setLevelName(jsonData
                                        .getString("sLevelName"));
                                info.setSsid(jsonData
                                        .getString("sID"));
                                info.setSuperId(jsonData
                                        .getString("sSuperID"));

                                dataList.add(info);
//                                info.save();
                                // BphsConstants.sAndroidReader=false;


                        }
//                  List<DictionaryAreaData> dataList1 =LitePal.findAll(DictionaryAreaData.class);
//                        int s=dataList1.size();

                    LitePal.saveAll(dataList);
                    myhandler.sendEmptyMessage(Constant.MSG_LOAD_EMPTY);



                } catch (JSONException e) {
                    e.printStackTrace();
                    sMessage = e.getMessage();
                    myhandler.sendEmptyMessage(Constant.MSG_LOAD_EXC);
//                        myhandler.sendEmptyMessage(Constant.MSG_LOAD_ERROR);
                }
            } else {
                myhandler.sendEmptyMessage(Constant.MSG_LOAD_EMPTY);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    Runnable runArea = new Runnable() {
        @Override
        public void run() {
            loadIDCardNumberHeadImageInfo1("");
        }
    };



    public void save() {
        String dbpath = "/data/data/com.jqsoft.nursing/databases/DBDictionaryInfoNew.db";
//        String dbpath = "/data/data/com.jqsoft.signed_doctor_client.ah_tongling_yian/databases/DBDictionaryInfo.db";
//        String dbpath = "/data/data/com.jqsoft.signed_doctor_client.ah_lingbi/databases/DBDictionaryInfo.db";
//        String dbpath = "/data/data/com.jqsoft.signed_doctor_client.ah_luan_shucheng/databases/DBDictionaryInfo.db";
        boolean success=copyFile(dbpath, Environment.getExternalStorageDirectory() + "/"
                + "/DBDictionaryInfoNew.db");
        if(success){
            Toast.makeText(LoginActivityNew.this, "完成", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(LoginActivityNew.this, "失败", Toast.LENGTH_SHORT).show();
        }
    }


    public static boolean copyFile(String source, String dest) {
        try {
            File f1 = new File(source);
            File f2 = new File(dest);
            InputStream in = new FileInputStream(f1);

            OutputStream out = new FileOutputStream(f2);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0)
                out.write(buf, 0, len);

            in.close();
            out.close();
        } catch (FileNotFoundException ex) {
            return false;
        } catch (IOException e) {
            return false;
        }

        return true;

    }

    private void copyDatabase(){

        String path = "/data/data/" + LoginActivityNew.this.getPackageName() + "/databases";
        File file = new File(path + "/" + "DBDictionaryInfoNew.db");
        if(file.exists()){
            //下面代码不要删除
//			try{
//				Connector.getDatabase();
//				SharedPreferences userSettings = getSharedPreferences("setting", 0);
//				int  clearSaveState= userSettings.getInt("clearSaveState",0);
//				if (clearSaveState==0){
//					LitePal.deleteAll(DictionaryAreaData.class);
//					file.delete();
//					copyDbFile(SplashActivity.this, "DBDictionaryInfoNew.db");
//					SharedPreferences.Editor editor = userSettings.edit();
//					editor.putInt("clearSaveState",1);
//					editor.apply();
//				}
//			}catch (Exception e){
//				copyDbFile(SplashActivity.this, "DBDictionaryInfoNew.db");
//			}

        }else {
            copyDbFile(LoginActivityNew.this, "DBDictionaryInfoNew.db");
        }


    }


    /**
     * 将assets文件夹下/db的本地库拷贝到/data/data/下
     *
     * @param context
     * @param tab_name
     */
    public static void copyDbFile(Context context, String tab_name) {
        InputStream in = null;
        FileOutputStream out = null;
        /**data/data/路径*/
        String path = "/data/data/" + context.getPackageName() + "/databases";
        File file = new File(path + "/" + tab_name);

        try {

            //创建文件夹
            File file_ = new File(path);
            if (!file_.exists())
                file_.mkdirs();

            if (file.exists())//删除已经存在的
                file.deleteOnExit();
            //创建新的文件
            if (!file.exists())
                file.createNewFile();

            in = context.getAssets().open( tab_name ); // 从assets目录下复制
            out = new FileOutputStream(file);
            int length = -1;
            byte[] buf = new byte[1024];
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
            out.flush();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }



    private void requestCamera( String... permissionsGroup){
        AndPermission.with(LoginActivityNew.this)
                .runtime()
                .permission(permissionsGroup)
                .rationale(new Rationale<List<String>>() {
                               @Override
                               public void showRationale(Context context, List<String> data, final RequestExecutor executor) {
                                   new AlertDialog.Builder(context)
                                           .setTitle("权限申请")
                                           .setMessage("这里需要申请" + data.get(0))
                                           .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                                               @Override
                                               public void onClick(DialogInterface dialog, int which) {
                                                   executor.execute();
                                               }
                                           })
                                           .setNegativeButton("取消", null)
                                           .create()
                                           .show();
                               }
                           }
                )
                .onGranted(new Action<List<String>>() {
                               @Override
                               public void onAction(List<String> permission) {
//                            Intent intent = new Intent();
//                            // 指定开启系统相机的Action
//                            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//                            intent.addCategory(Intent.CATEGORY_DEFAULT);
//                            startActivityForResult(intent, 1);
                               }
                           }
                )
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(LoginActivityNew.this, permissions)) {
                            new AlertDialog.Builder(LoginActivityNew.this)
                                    .setTitle("权限获取失败")
                                    .setMessage("没有相机权限和读写存储权限该功能不能使用，请进入应用设置中点击权限管理，设置相机和读写存储为允许!")
                                    .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent();
                                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                    Uri uri = Uri.fromParts("package", LoginActivityNew.this.getPackageName(), null);
                                                    intent.setData(uri);
                                                    LoginActivityNew.this.startActivity(intent);
                                                }
                                            }
                                    )
                                    .setNegativeButton("取消", null)
                                    .create()
                                    .show();
                        } else {
                            Toast.makeText(LoginActivityNew.this, "开启权限失败", Toast.LENGTH_SHORT);
                        }
                    }
                })
                .start();
    }

    private void setAcrsoftFace(){

        ArcDataBean arcDataBean=new ArcDataBean();
        SharedPreferences userSettings = getSharedPreferences("setting", 0);
        SharedPreferences.Editor editor = userSettings.edit();
        if (arcDataBean == null) {
            arcDataBean.setNoBrushAgeRange("0-18,65-2000");
            arcDataBean.setAction("4");
            arcDataBean.setRange("5");
            arcDataBean.setFrames("50");
            arcDataBean.setSimilarity("0-8:0.5,8-50:0.6,50-2000:0.4");
            arcDataBean.setScreenOf("0.1");
            arcDataBean.setManCardVerify("0-8:0.4,8-50:0.7,50-2000:0.4");
        } else if (TextUtils.isEmpty(arcDataBean.getNoBrushAgeRange())) {
            arcDataBean.setNoBrushAgeRange("0-18,65-2000");
        }
        else if (TextUtils.isEmpty(arcDataBean.getAction())) {
            arcDataBean.setAction("4");
        } else if (TextUtils.isEmpty(arcDataBean.getRange())) {
            arcDataBean.setRange("5");
        } else if (TextUtils.isEmpty(arcDataBean.getFrames())) {
            arcDataBean.setFrames("50");
        } else if (TextUtils.isEmpty(arcDataBean.getSimilarity())) {
            arcDataBean.setSimilarity("0-8:0.5,8-50:0.6,50-2000:0.4");
        } else if (TextUtils.isEmpty(arcDataBean.getScreenOf())) {
            arcDataBean.setScreenOf("0.1");
        } else if (TextUtils.isEmpty(arcDataBean.getManCardVerify())) {
            arcDataBean.setManCardVerify("0-8:0.4,8-50:0.6,50-2000:0.4");
        }
        try {
            editor.putString("LkzWsXxlb", arcDataBean.getLkzWsXxlb());
            editor.putString("NoBrushAgeRange", arcDataBean.getNoBrushAgeRange());
            editor.putInt("Action", Integer.parseInt("4"));
            editor.putInt("Range", Integer.parseInt(arcDataBean.getRange()));
            editor.putInt("Frames", Integer.parseInt(arcDataBean.getFrames()));
            editor.putString("Similarity", arcDataBean.getSimilarity());
            editor.putString("ScreenOf", arcDataBean.getScreenOf());
            editor.putString("ManCardVerify", arcDataBean.getManCardVerify());

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (arcDataBean != null) {
            if (!TextUtils.isEmpty(arcDataBean.getsAPPShuaKa())) {
                int shuake = Integer.parseInt(arcDataBean.getsAPPShuaKa());
//                BphsConstants.IdVISIABLE = shuake;   // 1:是，：否，默认
//                BphsConstants.saveCardID = shuake;  //< 右上角拍照是否接着刷卡  1表示刷卡>
            }
            if (!TextUtils.isEmpty(arcDataBean.getsAPPTanChuangShowButton())) {
//                UserLoginInfo.setChecKStyle(arcDataBean.getsAPPTanChuangShowButton()); //弹出框是否带有人脸 、身份证刷卡
            }
            if (!TextUtils.isEmpty(arcDataBean.getsAPPDiaoYueLIS())) {
                editor.putString("appdiaoyuelis", arcDataBean.getsAPPDiaoYueLIS());
            }
            if (!TextUtils.isEmpty(arcDataBean.getsAPPDiaoYueBChao())) {
                editor.putString("appdiaoyuebchao", arcDataBean.getsAPPDiaoYueBChao());
            }
            if (!TextUtils.isEmpty(arcDataBean.getiAPPFaceNoPassCount())) {
                editor.putString("iAPPFaceNoPassCount", arcDataBean.getiAPPFaceNoPassCount());
            }
            if (!TextUtils.isEmpty(arcDataBean.getsAPPFaceFaZhi())) {
                editor.putString("sAPPFaceFaZhi", arcDataBean.getsAPPFaceFaZhi());
            }
            if (!TextUtils.isEmpty(arcDataBean.getsCheckFacePhoto())) {//是否开启申请人工审核照片功能
                editor.putString("sCheckFacePhoto", arcDataBean.getsCheckFacePhoto());
            } else {
                editor.putString("sCheckFacePhoto", "0");
            }
            if (!TextUtils.isEmpty(arcDataBean.getsDeletePhoto())) {///是否开启申请删除底片功能	1:开启 ,0:不开启，默认0
                editor.putString("sDeletePhoto", arcDataBean.getsDeletePhoto());
            } else {
                editor.putString("sDeletePhoto", "0");
            }

        }
        editor.apply();
    }

}
