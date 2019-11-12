package com.jqsoft.appium_test.encapsulation_with_po_pattern.page;


import com.jqsoft.appium_test.encapsulation_with_po_pattern.PageAppium;

import java.util.List;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * ui界面类，界面在这里处理
 * Created by LITP on 2016/9/22.
 */
public class LoginPage extends PageAppium {

    //登录界面的登录按钮
    public final String LOGIN_BUTTON_ID = "login_btn_login";
    //第一屏的登录按钮
    public final String WELCOME_LOGIN_BUTTON_ID = "welcome_login_btn";

    //成功登录到首页标识
    public final String INDEX_TEXT = "首页";

    //是否在关于屏标识
    public final String ABOUT_TEXT = "AboutActivity";

    //关于页面的按钮id
    public final String ABOUT_BUTTON_ID = "about_go_button";


    //帐号密码控件
    public final String NAME_PASS_ELEMENT = "android.widget.EditText";
    //首页控件
    public final String INDEX_ELEMENT = "ll_tap";

    //登录完成之后的activity名字
    public final String INDEX_ACTIVITY_NAME = "IndexActivity";

    public final String LOGIN_PAGE_USERNAME_EDITTEXT_ID="acet_username";
    public final String LOGIN_PAGE_PASSWORD_EDITTEXT_ID = "acet_password";
    public final String LOGIN_PAGE_LOGIN_BUTTON_ID="bt_login";

    public LoginPage(AndroidDriver driver){
        super(driver);
    }

    public AndroidElement getUsernameEditText(){
        return findById(LOGIN_PAGE_USERNAME_EDITTEXT_ID);
    }

    public AndroidElement getPasswordEditText(){
        return findById(LOGIN_PAGE_PASSWORD_EDITTEXT_ID);
    }

    public AndroidElement getLoginButton(){
        return findById(LOGIN_PAGE_LOGIN_BUTTON_ID);
    }


    /**
     * 是否在欢迎界面
     */
    public boolean isWelcome(){
        return isIdElementExist(WELCOME_LOGIN_BUTTON_ID,3,true);
    }


    /**
     * 获取关于界面的activity的名字
     * @return
     */
    public String getAboutText(){
        return ABOUT_TEXT;
    }


    /**
     * 获取关于界面的按钮
     * @return
     */
    public AndroidElement getAboutButton(){
        return waitAutoById(ABOUT_BUTTON_ID);
    }


    public AndroidElement getWelcmoeLoginButton(){
        return findById(WELCOME_LOGIN_BUTTON_ID);
    }


//    public AndroidElement getLoginButton(){
//        return findById(LOGIN_BUTTON_ID);
//    }

    /**
     * 获取账号密码框的控件
     * @return
     */
    public List<AndroidElement> getNamePassElement(){
        return getManyElementByClassName(NAME_PASS_ELEMENT,2);
    }


    /**
     * 首页标识，是否成功登录
     * @return
     */
    public boolean getIndexflag(){
        /*AndroidElement element =  waitAutoByXp(LoginPage.INDEX_TEXT);
        return  element != null;*/

        AndroidElement element = findById(INDEX_ELEMENT);
        return element != null;
    }


    /**
     * 获取首页的一个元素，让操作程序等待
     */
    public String getIndexElementId(){
        return INDEX_ELEMENT;
    }

    /**
     * 获取首页的一个元素，让操作程序等待
     */
    public AndroidElement getIndexElement(){
        return findById(INDEX_ELEMENT);
    }


    public String getIndexActivity(){
        return INDEX_ACTIVITY_NAME;
    }

}