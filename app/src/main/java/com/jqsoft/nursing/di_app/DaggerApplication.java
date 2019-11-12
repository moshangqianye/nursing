package com.jqsoft.nursing.di_app;

import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;

import com.blankj.utilcode.utils.Utils;
import com.caption.netmonitorlibrary.netStateLib.NetChangeObserver;
import com.caption.netmonitorlibrary.netStateLib.NetStateReceiver;
import com.caption.netmonitorlibrary.netStateLib.NetUtils;
import com.evernote.android.job.JobManager;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.base.IdentityManager;
import com.jqsoft.nursing.base.MyUncaughtExceptionHandler;
import com.jqsoft.nursing.base.Version;
import com.jqsoft.nursing.di_http.http.nursing.GCARetrofit;
import com.jqsoft.nursing.keepalive.job_scheduler.AndroidJob;
import com.jqsoft.nursing.keepalive.job_scheduler.AndroidJobCreator;
import com.jqsoft.nursing.rx.RxBus;
import com.jqsoft.nursing.service.FirService;
import com.jqsoft.nursing.service.GrayService;
import com.jqsoft.nursing.service.LongTermService;
import com.jqsoft.nursing.service.MyJobService;
import com.jqsoft.nursing.service.SecService;
import com.jqsoft.nursing.util.Util;
import com.jqsoft.nursing.utils.LogUtil;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.upgrade.UpgradeStateListener;
import com.tencent.bugly.crashreport.CrashReport;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;
import org.xutils.x;

//import cn.jpush.android.api.JPushInterface;

/**
 * <b>类名称：</b> DaggerApplication <br/>
 * <b>类描述：</b> Application类<br/>
 * <b>创建人：</b> Lincoln <br/>
 * <b>修改人：</b> Lincoln <br/>
 * <b>修改时间：</b> 2016年08月11日 下午5:21<br/>
 * <b>修改备注：</b> <br/>
 *
 * @version 1.0.0 <br/>
 */
//public class DaggerApplication extends TinkerApplication {
public class DaggerApplication extends MultiDexApplication {

    private static DaggerApplication instance;

    private static AppComponent appComponent;

    private RefWatcher mRefWatcher;
    private String goalIP;
    private String tableType;
    private int iFlag=0;

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getGoalIP() {
        return goalIP;
    }

    public void setGoalIP(String goalIP) {
        this.goalIP = goalIP;
    }

    public NetChangeObserver netChangeObserver;

    public DaggerApplication() {
        super();
//        super(ShareConstants.TINKER_ENABLE_ALL, "com.jqsoft.nursing.di_app.MyApplicationLike",
//                "com.tencent.tinker.loader.TinkerLoader", false);
    }

    public static DaggerApplication getInstance() {
        return instance;
    }

    public static DaggerApplication get(Context context) {
        return (DaggerApplication) context.getApplicationContext();
    }

    public static RefWatcher getRefWatcher() {
        return getInstance().mRefWatcher;
    }

    private void setupApplicationComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            this.setupApplicationComponent();
        }
        return appComponent;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
            if (process.pid == pid) {
                processName = process.processName;
            }
        }
        if ("com.jqsoft.launcher".equals(processName)) {
            iFlag=0;
            instance = this;
            Fresco.initialize(this);
            mRefWatcher = Version.ENABLE_LEAK_CANARY ? LeakCanary.install(this) : RefWatcher.DISABLED;
//        setupLeakCanary();


            clearSavedSRCLoginResultBean();

//            startAndroidJob();
//        startJobSchedulerService();
//        startGrayService();
//        startTwoDaemonService();

//        startLongTermService();

            forbidReboot();


            //禁用tinker后把初始化代码放到这里
            // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
            // 调试时，将第三个参数改为true
            Utils.init(this);//一个utils库的初始化 https://github.com/Blankj/AndroidUtilCode/blob/master/README-CN.md
            LitePal.initialize(this);
            initBugly();

            initNetworkStatusListener();

//            initStrictMode();

//        JPushInterface.setDebugMode(Version.JPUSH_DEBUG_MODE);
//        JPushInterface.init(this);
//
//        JMessageClient.setDebugMode(Version.JMESSAGE_DEBUG_MODE);
//        JMessageClient.init(this, Version.JMESSAGE_ENABLE_ROAMING);
            //--------------------------------


            //腾讯优图识别身份证
            x.Ext.init(this);
            //数据库初始化

            initQrcodeLibrary();
        }



    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterNetworkStatusListener();
    }

    private void initQrcodeLibrary(){
        ZXingLibrary.initDisplayOpinion(this);

    }

    private void forbidReboot() {
        Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.UncaughtExceptionHandler ueh = new MyUncaughtExceptionHandler(defaultHandler);
        Thread.setDefaultUncaughtExceptionHandler(ueh);
        // 禁止崩溃后重启
//        UncaughtExceptionHandlerImpl.getInstance().init(this, BuildConfig.DEBUG/*, true, 0, LoginActivityNew.class*/);
    }


    //清除保存到SharedPreferences中的登录返回的信息
    private void clearSavedSRCLoginResultBean() {
        IdentityManager.setObjectToShare(this, null, Constants.SRC_LOGIN_RESULT_BEAN_KEY);
    }

//    private void setupLeakCanary(){
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        mRefWatcher = LeakCanary.install(this);
//
//    }

    private void startAndroidJob() {
        try {
            JobManager jobManager = JobManager.create(this);
            jobManager.addJobCreator(new AndroidJobCreator());
//        jobManager.schedule();
//        JobManager.instance().getConfig().setAllowSmallerIntervalsForMarshmallow(true); // Don't use this in production

            AndroidJob.scheduleJob();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i("startAndroidJob exception:" + e.getLocalizedMessage());
        }
    }

    private void startJobSchedulerService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            JobInfo jobInfo = new JobInfo.Builder(Constants.JOB_SERVICE_JOB_ID, new ComponentName(getPackageName(), MyJobService.class.getName()))
                    .setPeriodic(2000)
                    .setPersisted(true)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .build();
            jobScheduler.schedule(jobInfo);
        }
    }

    private void startGrayService() {
        Intent grayServiceIntent = new Intent(this, GrayService.class);
        startService(grayServiceIntent);
    }

    private void startTwoDaemonService() {
        Intent firstServiceIntent = new Intent(this, FirService.class);
        startService(firstServiceIntent);

        Intent secondServiceIntent = new Intent(this, SecService.class);
        startService(secondServiceIntent);
    }

    private void startLongTermService() {
        Intent intent = new Intent(this, LongTermService.class);
        startService(intent);
    }

    private void stopLongTermService() {
        Intent intent = new Intent(this, LongTermService.class);
        stopService(intent);
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

    public void unregisterNetworkStatusListener() {
        NetStateReceiver.removeRegisterObserver(netChangeObserver);
        NetStateReceiver.unRegisterNetworkStateReceiver(this);

    }

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

    public void initParameters() {
        String baseHttpUrl = Util.getMetaDataFromManifest(this, "HTTP_ACCESS_URL");
        String sBaseHttpUrl1 =  Util.getMetaDataFromManifest(this, "HTTP_ACCESS_URL");

//        List<SettingServerBean> Serverlist = LitePal.findAll(SettingServerBean.class);
//        String name = IdentityManager.getLoginSuccessUsername(getApplicationContext());
//        if (Serverlist.size() == 0) {
//
//          //  String str = "<p>sad2f</p>";
//            String regex = "http://(.*)/fdss-api/";
//            Pattern pattern = Pattern.compile(regex);
//            Matcher matcher = pattern.matcher(baseHttpUrl);
//
//
//            String sBaseHttpUrl ="";
//
//            while (matcher.find()) {
//               // System.out.println(matcher.group(1));
//                sBaseHttpUrl=  matcher.group(1);
//            }
//
//
//
//            SettingServerBean bean = new SettingServerBean(sBaseHttpUrl, "默认服务器地址", name, "1");
//            if (bean.save()) {
//                baseHttpUrl = "http://" + bean.getIp() + "/sri/";
//                sBaseHttpUrl1="http://" + bean.getIp()+"/";
//
//            }
//
//
//        } else {
//            for (int i = 0; i < Serverlist.size(); i++) {
//                if (Serverlist.get(i).getIsUse().equals("1")) {
//                    baseHttpUrl = "http://" + Serverlist.get(i).getIp() + "/sri/";
//                    sBaseHttpUrl1="http://" + Serverlist.get(i).getIp()+"/";
//                }
//            }
//        }
//        GCARetrofit.BASE_URL =  Util.getMetaDataFromManifest(this, "HTTP_ACCESS_URL");
        GCARetrofit.BASE_URL = baseHttpUrl;
        Version.HTTP_URL = "";
        Version.FIND_FILE_URL_BASE = "";
        Version.FILE_URL_BASE = "";
      //  Version.FILE_URL_BASE = Version.HTTP_URL.substring(0, Version.HTTP_URL.length() - 1);
        Version.LOGIN_APP_NAME = Util.getMetaDataFromManifest(this, "LOGIN_APP_NAME");
        String buglyId = Util.getMetaDataFromManifest(this, "BUGLY_APP_ID");
        buglyId = Util.getRealBuglyAppId(buglyId);
        Version.BUGLY_APP_ID = buglyId;


    }


    public int getiFlag() {
        return iFlag;
    }

    public void setiFlag(int iFlag) {
        this.iFlag = iFlag;
    }
}
