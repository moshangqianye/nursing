package com.jqsoft.nursing.bean.nursing;

import java.util.List;

/**
 * Created by Administrator on 2018-04-19.
 */

public class OrgnizationListAndSuccessBean {
    private List<OrgnizationBean> list;
    private boolean success;
    public OrgnizationListAndSuccessBean() {
        super();
    }

    public OrgnizationListAndSuccessBean(List<OrgnizationBean> list, boolean success) {
        this.list = list;
        this.success = success;
    }

    public List<OrgnizationBean> getList() {
        return list;
    }

    public void setList(List<OrgnizationBean> list) {
        this.list = list;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
