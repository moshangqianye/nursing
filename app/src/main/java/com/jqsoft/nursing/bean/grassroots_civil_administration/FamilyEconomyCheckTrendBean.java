package com.jqsoft.nursing.bean.grassroots_civil_administration;

/**
 * Created by Administrator on 2018-01-12.
 */

public class FamilyEconomyCheckTrendBean {
    private String title;
    private String firstValue;
    private String secondValue;
    public FamilyEconomyCheckTrendBean() {
        super();
    }

    public FamilyEconomyCheckTrendBean(String title, String firstValue, String secondValue) {
        this.title = title;
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstValue() {
        return firstValue;
    }

    public void setFirstValue(String firstValue) {
        this.firstValue = firstValue;
    }

    public String getSecondValue() {
        return secondValue;
    }

    public void setSecondValue(String secondValue) {
        this.secondValue = secondValue;
    }
}
