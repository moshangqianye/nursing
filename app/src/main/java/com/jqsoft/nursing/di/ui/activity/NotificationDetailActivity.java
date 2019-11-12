package com.jqsoft.nursing.di.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jqsoft.nursing.R;
import com.jqsoft.nursing.base.Constants;
import com.jqsoft.nursing.bean.grassroots_civil_administration.NotificationBean;
import com.jqsoft.nursing.di.ui.activity.base.AbstractActivity;
import com.jqsoft.nursing.util.Util;
import com.jqsoft.nursing.utils3.util.StringUtils;

import butterknife.BindView;

/**
 * Created by Administrator on 2017-08-23.
 * 通知公告明细
 */

public class NotificationDetailActivity extends AbstractActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.tv_datetime)
    TextView tvDatetime;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    WebView webView;
//    @BindView(R.id.wv_content)
//    WebView wvContent;
//    @BindView(R.id.tv_content)
//    TextView tvContent;

    private NotificationBean notificationBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notification_detail_layout;
    }

    @Override
    protected void initData() {
        notificationBean=(NotificationBean)getDeliveredSerializableByKey(Constants.NOTIFICATION_DETAIL_ACTIVITY_KEY);
    }

    @Override
    protected void initView() {
        setToolBar(toolbar, Constants.EMPTY_STRING);

        tvTitle.setText(getTitleString());
        tvAuthor.setText(getAuthorString());
        tvDatetime.setText(getDatetimeString());

        initWebview();
//        tvContent.setText(getContentString());
    }

    private void initWebview(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView = new WebView(getApplicationContext());
        webView.setLayoutParams(params);
        llContent.addView(webView);

        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();

//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
// 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
// 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可

//支持插件
//        webSettings.setPluginsEnabled(true);

//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setTextZoom(350);
//        webSettings.setTextSize(WebSettings.TextSize.LARGEST);

//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        String contentString = getContentString();
//        webView.loadData(contentString, "text/html;charset=utf-8", null);
        webView.loadDataWithBaseURL(null, contentString, "text/html", "utf-8", null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK && webView.canGoBack()){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }


    @Override
    protected void loadData() {

    }

    @Override
    protected void initInject() {
        super.initInject();
    }


    private String getTitleString(){
        if (notificationBean==null){
            return Constants.EMPTY_STRING;
        } else {
            String result = Util.trimString(notificationBean.getTitle());
            return result;
        }
    }

    private String getAuthorString(){
        String result = "作者:";
        String author = notificationBean.getAuthor();
        if (notificationBean==null){

        } else {
            String trimmedAuthor = Util.trimString(author);
            if (StringUtils.isBlank(trimmedAuthor)) {
                result=Constants.EMPTY_STRING;
            } else {
                result+=trimmedAuthor;
            }
        }
        return result;
    }

    private String getDatetimeString(){
        String result = "发布日期:";
        if (notificationBean==null){

        } else {
            String processedDateTime = Util.getProcessedDateTimeString(notificationBean.getReleaseTime());
            result+=processedDateTime;
        }
        return result;
    }

    private String getContentString(){
        if (notificationBean==null){
            return Constants.EMPTY_STRING;
        } else {
            String result = Util.trimString(notificationBean.getMessage());
            return result;
        }
    }
}
