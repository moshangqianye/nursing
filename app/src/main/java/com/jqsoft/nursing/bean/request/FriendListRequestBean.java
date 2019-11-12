package com.jqsoft.nursing.bean.request;

import com.jqsoft.nursing.bean.base.HttpRequestBaseBean;

/**
 * Created by Administrator on 2017-06-09.
 */

public class FriendListRequestBean extends HttpRequestBaseBean {
    public FriendListRequestBean() {
        super();
    }

    public FriendListRequestBean(String v, String timestamp, String token, String appkey, String sig) {
        super(v, timestamp, token, appkey, sig);
    }
}
