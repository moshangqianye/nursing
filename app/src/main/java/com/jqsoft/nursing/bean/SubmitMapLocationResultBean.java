package com.jqsoft.nursing.bean;

/**
 * Created by Administrator on 2018-02-06.
 */

public class SubmitMapLocationResultBean {
    private String mapId;
    public SubmitMapLocationResultBean() {
        super();
    }

    public SubmitMapLocationResultBean(String mapId) {
        this.mapId = mapId;
    }

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }
}
