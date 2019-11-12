package com.jqsoft.nursing.bean.grassroots_civil_administration;

/**
 * Created by Administrator on 2018-01-15.
 */

public class NameValueBean {
    private String name;
    private String value;
    public NameValueBean() {
        super();
    }

    public NameValueBean(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
