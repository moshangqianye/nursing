package com.jqsoft.appium_test.raw;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.AndroidElement;

/**
 * Created by Administrator on 2017-10-23.
 */

public class AllActivityTest extends AppiumTestBase {
    /**
     * 要执行的的测试方法
     */
    @Test
    public void flow(){
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.findElement(By.id("acet_username")).sendKeys("342123193810151809");
//        driver.hidekeyboard();
        driver.findElement(By.id("acet_password")).sendKeys("a123456");

//        driver.hideKeyboard();
//        driver.manage().ime().deactivate();
//        driver.getKeyboard().pressKey(String.valueOf(AndroidKeyCode.KEYCODE_BACK));
        driver.navigate().back();

        driver.findElement(By.id("bt_login")).click();

        AndroidElement indexPageElement = driver.findElementByXPath("//android.widget.TextView[@text='首页']");
        AndroidElement onlineSignPageElement = driver.findElementByXPath("//android.widget.TextView[@text='在线签约']");
        AndroidElement signInfoPageElement = driver.findElementByXPath("//android.widget.TextView[@text='签约信息']");
        AndroidElement myPageElement = driver.findElementByXPath("//android.widget.TextView[@text='我的']");
        assertExist("首页tab页存在", indexPageElement);
        assertExist("在线签约tab页存在", onlineSignPageElement);
        assertExist("签约信息tab页存在", signInfoPageElement);
        assertExist("我的tab页存在", myPageElement);

        AndroidElement searchHead = getElementById("ll_index_head");
        searchHead.click();
        driver.navigate().back();

//        AndroidElement chartPaidSignElement = getElementByXPath("//android.widget.TextView[@text='有偿签约']");
//        assertExist("有偿签约图表存在", chartPaidSignElement);

//        swipeToLeft(10);

        AndroidElement latest7DaysElement = getElementByXPath("//android.widget.TextView[@text='近7天需要执行项目数']");
        latest7DaysElement.click();
        back();
        AndroidElement timeoutExecutionElement = getElementByXPath("//android.widget.TextView[@text='超时未执行项目数']");
        timeoutExecutionElement.click();
        back();

        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
//        driver.swipe(width / 2, height / 2, width / 2, height, 1);


        AndroidElement serviceAssessElement = getElementByXPath("//android.widget.TextView[@text='服务评价']");
        serviceAssessElement.click();
        back();
        AndroidElement signApplicationElement = getElementByXPath("//android.widget.TextView[@text='签约申请']");
        signApplicationElement.click();
        back();
        AndroidElement appointmentExecutionElement = getElementByXPath("//android.widget.TextView[@text='预约执行']");
        appointmentExecutionElement.click();
        back();


//        sendWithIds(new String[][]{
//                {"acet_username", "3412221909"},
//                {"acet_password", "a123456"},
//                {"bt_login", ""}
//        });
//        clickIfExistId("bt_login");
//        assertTextWithIds(new String[][]{
//                {"login success", }
//        });

//        //利用Xpath的方法寻找text值为Add Contact的控件
//        WebElement el = driver.findElement(By.xpath(".//*[@text='Add Contact']"));
//        //点击这个控件
//        el.click();
//        //利用类名获取界面上所有的EditText
//        List<AndroidElement> textFieldsList = driver.findElementsByClassName("android.widget.EditText");
//        //第一个EditText输入内容Some Name
//        textFieldsList.get(0).sendKeys("Some Name");
//        //第三个EditText输入内容Some Name
//        textFieldsList.get(2).sendKeys("Some@example.com");
//        //在坐(100,500)滑动到(100,100) 时间为2毫秒
//        driver.swipe(100, 500, 100, 100, 2);
//        //用xpath的方式寻找到text值为Save的控件，然后点击
//        driver.findElementByXPath(".//*[@text='Save']").click();
    }
}
