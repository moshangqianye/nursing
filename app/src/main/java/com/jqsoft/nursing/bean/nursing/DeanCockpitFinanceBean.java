package com.jqsoft.nursing.bean.nursing;

/**
 * Created by Administrator on 2018-04-19.
 */

public class DeanCockpitFinanceBean {
    private String Count;
    private String Year;
    public DeanCockpitFinanceBean() {
        super();
    }

    public DeanCockpitFinanceBean(String count, String year) {
        Count = count;
        Year = year;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }
}
