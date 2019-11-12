package com.jqsoft.appium_test.base;

import com.jqsoft.appium_test.encapsulation.BaseAppium;

/**
 * Created by LITP on 2016/9/7.
 */
public class Builder {
    public String deviceName = BaseAppium.deviceName;
    public String platformVersion = BaseAppium.platformVersion;
    public String path = System.getProperty("user.dir") + "/src/main/java/apps/";
    public String appPath = BaseAppium.appPath;
    public String appPackage = BaseAppium.appPackage;
    public String noReset = BaseAppium.noReset;
    public String noSign = BaseAppium.noSign;
    public String unicodeKeyboard = BaseAppium.unicodeKeyboard;
    public String resetKeyboard = BaseAppium.resetKeyboard;
    public String appActivity = BaseAppium.appActivity;

    public Builder setAppPath(String appPath) {
        this.appPath = path + appPath;
        return this;
    }

    public Builder setDeviceName(String deviceName) {
        this.deviceName = deviceName;
        return this;
    }

    public Builder setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
        return this;
    }

    public Builder setApp(String appPath) {
        this.appPath = appPath;
        return this;
    }

    public Builder setAppPackage(String appPackage) {
        this.appPackage = appPackage;
        return this;
    }

    public Builder setNoReset(String noReset) {
        this.noReset = noReset;
        return this;
    }

    public Builder setNoSign(String noSign) {
        this.noSign = noSign;
        return this;
    }

    public Builder setUnicodeKeyboard(String unicodeKeyboard) {
        this.unicodeKeyboard = unicodeKeyboard;
        return this;
    }


    public Builder setResetKeyboard(String resetKeyboard) {
        this.resetKeyboard = resetKeyboard;
        return this;
    }

    public Builder setAppActivity(String appActivity) {
        this.appActivity = appActivity;
        return this;
    }

    public BaseAppium build() {
        return new BaseAppium(this);
    }
}