package com.jqsoft.nursing.bean.base_new;

import com.jqsoft.nursing.base.Version;

/**
 * Created by Administrator on 2017-06-27.
 */

public class HttpRequestBaseBean2 {
    private String sign= Version.SIGN;      //JQSOFT65350880
    public HttpRequestBaseBean2() {
        super();
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
