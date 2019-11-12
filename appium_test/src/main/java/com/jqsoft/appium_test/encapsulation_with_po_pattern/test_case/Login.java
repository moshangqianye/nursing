package com.jqsoft.appium_test.encapsulation_with_po_pattern.test_case;


import com.jqsoft.appium_test.encapsulation_with_po_pattern.InitAppium;
import com.jqsoft.appium_test.encapsulation_with_po_pattern.operate.LoginOperate;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * 登录测试用例
 * Created by LITP on 2016/9/22.
 */

public class Login extends InitAppium {

    private LoginOperate loginOperate;


    @BeforeClass
    public void initDriver(){
        Assert.assertNotNull(driver);
        loginOperate = new LoginOperate(driver);
    }

    /**
     * 测试帐号对 密码不对情况
     */
//    @Test(priority = 0)
//    public void loginErrorUser(){
//        boolean flag = loginOperate.login("13192624740","iuhihj");
//        Assertion.verifyEquals(flag,true,"帐号对密码错误是否登录成功");
//        print("帐号密码不对情况登录:"+ flag);
//    }

    /**
     * 测试帐号密码规格不对情况
     */
//    @Test(priority = 1)
//    public void loginErrorNum(){
//        boolean flag = loginOperate.login("1319262asdfsddsasdfsdfsdfsdfsdfsdf4740","dfgd#@$1234fgdsfgdsgdffds");
//        Assertion.verifyEquals(flag,true,"帐号密码格式不对是否登录成功");
//        print("帐号密码格式不对情况登录:"+ flag);
//    }


    /**
     * 测试帐号密码为中文情况
     */
//    @Test(priority = 2)
//    public void loginChinese(){
//        boolean flag = loginOperate.login("帐号","密码");
//        Assertion.verifyEquals(flag,true,"帐号密码为中文是否登录成功");
//        print("帐号密码为中文情况登录:"+ flag);
//    }



    /**
     * 测试帐号密码为空情况
     */
//    @Test(priority = 3)
//    public void loginEmpty(){
//        boolean flag = loginOperate.login("","");
//        Assertion.verifyEquals(flag,true,"帐号密码为空是否登录成功");
//        print("帐号密码为空情况登录:"+ flag);
//    }


    /**
     * 测试帐号密码正确情况
     */
    @Test(priority = 4)
    public void loginConfim() {
        boolean flag = loginOperate.login("342123193810151809","a123456");
        print("帐号密码对的情况登录:"+ flag);
        Assert.assertTrue(flag,"帐号密码对的情况登录");

    }

}