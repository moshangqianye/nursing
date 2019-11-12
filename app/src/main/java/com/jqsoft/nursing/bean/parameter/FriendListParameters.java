package com.jqsoft.nursing.bean.parameter;

import com.jqsoft.nursing.bean.parameter.base.CommonParameters;

/**
 * Created by Administrator on 2017-06-09.
 */

public class FriendListParameters extends CommonParameters {
    public FriendListParameters() {
        super();
    }

    public FriendListParameters(String appkey, String timestamp, String token, String sig, String v) {
        super(appkey, timestamp, token, sig, v);
    }
}
