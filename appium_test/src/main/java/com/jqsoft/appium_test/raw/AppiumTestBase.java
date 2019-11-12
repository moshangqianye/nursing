package com.jqsoft.appium_test.raw;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.NoSuchElementException;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;


public class AppiumTestBase {
    //Driver
    protected AppiumDriver<AndroidElement> driver;

    private static int SWIPE_DEFAULT_PERCENT = 5;    //默认滑动比例

    /**
     * 配置启动driver
     * @throws Exception
     */
    @BeforeClass
    public void setUp() throws Exception {

        try {
            File classpathRoot = new File(System.getProperty("user.dir"));
            //app的目录
            File appDir = new File(classpathRoot, "src/main/java/apps/");
            //app的名字，对应你apps目录下的文件
            File app = new File(appDir, "居民健康内部测试版V1.0.0_aligned_signed.apk");
            //创建Capabilities
            DesiredCapabilities capabilities = new DesiredCapabilities();
            //设置要调试的模拟器的名字
            capabilities.setCapability("deviceName","vivo X9");
            //设置模拟器的系统版本
            capabilities.setCapability("platformVersion", "6.0.1");
//        //设置要调试的模拟器的名字
//        capabilities.setCapability("deviceName","minote");
//        //设置模拟器的系统版本
//        capabilities.setCapability("platformVersion", "4.4.2");
            String appAbsolutePath = app.getAbsolutePath();
            //设置app的路径
            capabilities.setCapability("app", appAbsolutePath);
            //设置app的包名
            capabilities.setCapability("appPackage", "com.jqsoft.nursing");
            //设置app的启动activity
            capabilities.setCapability("appActivity", ".di.ui.activity.LoginActivityNew");
            capabilities.setCapability("unicodeKeyboard", "True");//支持中文输入，会自动安装Unicode 输入法。默认值为 false
            capabilities.setCapability("resetKeyboard", "True"); //在设定了 unicodeKeyboard 关键字的 Unicode 测试结束后，重置输入法到原有状态

            //启动driver
//        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
            driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    @AfterClass
    public void tearDown() throws Exception {
        //测试完毕，关闭driver，不关闭将会导致会话还存在，下次启动就会报错
        driver.quit();
    }




    protected void back(){
        driver.navigate().back();
    }

//    //从宽度的3/4 向左滑动到1/4,,  y轴是中间
//    public void swipeToLeft(int during) {
//        int width = driver.manage().window().getSize().width;
//        int height = driver.manage().window().getSize().height;
//        driver.swipe(width * 3 / 4, height / 2, width / 4, height / 2, during);
//    }

    protected AndroidElement getElementById(String id){
        if (id==null||id.length()==0){
            return null;
        } else {
            AndroidElement ae = null;
            try {
                ae=driver.findElementById(id);
            } catch (Exception e) {
                e.printStackTrace();
                ae=null;
            }
            return ae;
        }
    }

    protected AndroidElement getElementByXPath(String xpath){
        if (xpath==null||xpath.length()==0){
            return null;
        } else {
            AndroidElement ae = null;
            try {
                ae=driver.findElementByXPath(xpath);
            } catch (Exception e) {
                e.printStackTrace();
                ae=null;
            }
            return ae;
        }
    }

    protected void clickIfExistId(String id) {
        try {
            driver.findElementById(id).click();
        } catch (NoSuchElementException e) {
            System.out.println("Can not find element with id " + id + " . Skip!");
        }
    }

    protected void clickIfExistIds(String[] ids) {
        for (String id : ids) {
            clickIfExistId(id);
        }
    }

    protected void sendWithId(SendAction action) {
        WebElement element = driver.findElementById(action.id);
        if (action.text == null || action.text.length() == 0) {
            element.click();
        } else {
            element.sendKeys(action.text);
        }
    }

    protected void sendWithIds(SendAction[] actions) {
        for (SendAction action : actions) {
            sendWithId(action);
        }
    }

    protected void sendWithIds(String[][] actions) {
        for (String[] action : actions) {
            sendWithId(new SendAction(action[0], action[1]));
        }
    }

//    封装多个文字比较的断言
//    我们将单个断言的格式定义为一个长度为3的字符串数组，每个字符串分别代表message，预期文本，元素ID，如果预期文本为空，则只判断元素是否存在。

    protected void assertTextWithIds(String[][] assertions) {
        for (String[] assertion : assertions) {
            if (assertion[1] == null || assertion[1].length() == 0) {
                Assert.assertNotNull(driver.findElementById(assertion[2]), assertion[0]);
            } else {
                Assert.assertEquals(assertion[0], assertion[1],
                        driver.findElementById(assertion[2]).getText());
            }
        }
    }

    protected void assertExist(String msg, AndroidElement ae){
        Assert.assertNotNull(ae, msg);
    }

    /**
     * 执行adb命令
     * @param s 要执行的命令
     */
    protected void excuteAdbShell(String s) {
        Runtime runtime=Runtime.getRuntime();
        try{
            runtime.exec(s);
        }catch(Exception e){
            System.out.println("执行命令:"+s+"出错");
        }
    }


    /**
     * 在某个方向滑动直到这个元素出现
     *
     * @param by        控件
     * @param direction 方向，UP DOWN  LEFT RIGHT
     * @param duration  滑动一次持续时间
     */
    public void swipUtilElementAppear(By by, String direction, int duration) {
        Boolean flag = true;
        while (flag) {
            try {
                driver.findElement(by);
                flag = false;
            } catch (Exception e) {
                swip(direction, duration);
            }
        }
    }

    /**
     * 在某个方向上滑动
     *
     * @param direction 方向，UP DOWN LEFT RIGHT
     * @param duration  持续时间
     */
    public void swip(String direction, int duration) {
        switch (direction) {
            case "UP":
                swipeToUp(duration);
                break;
            case "DOWN":
                swipeToDown(duration);
                break;
            case "LEFT":
                swipeToLeft(duration);
                break;
            case "RIGHT":
                swipeToRight(duration);
                break;
        }
    }

    public void swipeToUp(int during){
        swipeToUp(during,SWIPE_DEFAULT_PERCENT);
    }
    /**
     * 向上滑动，
     *
     * @param during
     */
    public void swipeToUp(int during,int percent) {
        int width = getScreenWidth();
        int height = getScreenHeight();
        driver.swipe(width / 2, height * (percent - 1) / percent, width / 2, height / percent, during);
    }

    public void swipeToDown(int during){
        swipeToDown(during,SWIPE_DEFAULT_PERCENT);
    }

    /**
     * 向下滑动，
     *
     * @param during 滑动时间
     */
    public void swipeToDown(int during,int percent) {
        int width = getScreenWidth();
        int height = getScreenHeight();
        driver.swipe(width / 2, height / percent, width / 2, height * (percent - 1) / percent, during);
    }


    public void swipeToLeft(int during){
        swipeToLeft(during,SWIPE_DEFAULT_PERCENT);
    }

    /**
     * 向左滑动，
     *
     * @param during  滑动时间
     * @param percent 位置的百分比，2-10， 例如3就是 从2/3滑到1/3
     */
    public void swipeToLeft(int during, int percent) {
        int width = getScreenWidth();
        int height = getScreenHeight();
        driver.swipe(width * (percent - 1) / percent, height / 2, width / percent, height / 2, during);
    }


    public void swipeToRight(int during) {
        swipeToRight(during, SWIPE_DEFAULT_PERCENT);
    }

    /**
     * 向右滑动，
     *
     * @param during  滑动时间
     * @param percent 位置的百分比，2-10， 例如3就是 从1/3滑到2/3
     */
    public void swipeToRight(int during, int percent) {
        int width = getScreenWidth();
        int height = getScreenHeight();
        driver.swipe(width / percent, height / 2, width * (percent - 1) / percent, height / 2, during);
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public int getScreenWidth() {
        return driver.manage().window().getSize().getWidth();
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public int getScreenHeight() {
        return driver.manage().window().getSize().getHeight();
    }

}
