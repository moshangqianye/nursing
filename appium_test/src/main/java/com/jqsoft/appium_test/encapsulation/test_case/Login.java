package com.jqsoft.appium_test.encapsulation.test_case;


import com.jqsoft.appium_test.encapsulation.BaseAppium;
import com.jqsoft.appium_test.base.Builder;

import org.testng.Assert;
import org.testng.annotations.Test;


/**
* 继承上一篇文章的类，里面封装了一些方法
*/
public class Login extends BaseAppium {

    /**
    * 构造器里面设置Builder，设置测试用例的参数，因为父类里面设置了默认属性了
    * 所以这里不用设置也是可以的
    */
    Login() {
        super(new Builder()
                .setAppActivity(".di.ui.activity.LoginActivityNew")
                .setAppPath("居民健康内部测试版V1.0.0_aligned_signed.apk")

        );
    }

    @Test(priority = 0)
    public void startTest() throws InterruptedException {

//        sleep(5000);


//        //没有登录按钮，不是自动登录就是第一次打开
//        if(getCurrActivity().equals("AboutActivity")){
//            print("有关于界面");
//            for(int i=0; i<4; i++){
//                swipeToLeft(500);
//                sleep(500);
//            }
//            clickView(waitAutoById("about_go_button"),"about_go_button");
//            waitAuto();
//            //点击登录
//            clickView(findById("welcome_login_btn"),"welcome_login_btn");
//        }else if(isIdElementExist("welcome_login_btn",true)){
//            print("点击登录");
//            clickView(findById("welcome_login_btn"),"welcome_login_btn");
//        }else{//自动登录了就输出成功，返回
//            print("自动登录了，返回,结束测试");
//            return;
//
//        }

        waitAuto(5);

        //输入帐号密码
        input(findById("acet_username"), "342123193810151809");
        input(findById("acet_password"), "a123456");

        back();

        //点击登录
        clickView(findById("bt_login"),"登录按钮");

//        sleep(3);
//        //点击一下左下角，避免第一次打开的指导
//        press(10,getScreenHeight()-10);
        //首页是否存在，不存在就会抛出错误
        Assert.assertTrue(isNameElementExist("首页"),"判断是否到了首页");
    }

}