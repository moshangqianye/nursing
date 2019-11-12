package com.jqsoft.nursing.bean.grassroots_civil_administration;

import java.io.Serializable;

/**
 * Created by Mars on 2018/1/25.
 */

public class JzzListBean implements Serializable {
    private String id;
    private String name;
//    private  CollectionUrlBean collectionUrl;
    private  String  collectionUrl;

    public JzzListBean() {
    }

    public JzzListBean(String id, String name, String collectionUrl) {
        this.id = id;
        this.name = name;
        this.collectionUrl = collectionUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollectionUrl() {
        return collectionUrl;
    }

    public void setCollectionUrl(String collectionUrl) {
        this.collectionUrl = collectionUrl;
    }
}
