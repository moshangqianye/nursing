package com.jqsoft.nursing.bean.nursing;

/**
 * Created by Administrator on 2018-04-20.
 */

public class HasYearDataAndIndex {
    private boolean hasYearData;
    private int index;
    public HasYearDataAndIndex() {
        super();
    }

    public HasYearDataAndIndex(boolean hasYearData, int index) {
        this.hasYearData = hasYearData;
        this.index = index;
    }

    public boolean isHasYearData() {
        return hasYearData;
    }

    public void setHasYearData(boolean hasYearData) {
        this.hasYearData = hasYearData;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
