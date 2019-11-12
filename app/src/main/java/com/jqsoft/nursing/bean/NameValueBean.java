package com.jqsoft.nursing.bean;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018-05-22.
 */

public class NameValueBean implements IStringRepresentationAndValue ,Parcelable{
    private String name;
    private String value;
    private boolean selected;
    private String superId; // 地区父类id
    private String number;;
    public NameValueBean() {
    }

    public NameValueBean(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public NameValueBean(String name, String value, boolean selected) {
        this.name = name;
        this.value = value;
        this.selected = selected;
    }

    public NameValueBean(String name, String value, boolean selected, String superId) {
        this.name = name;
        this.value = value;
        this.selected = selected;
        this.superId = superId;
    }

    protected NameValueBean(Parcel in) {
        name = in.readString();
        value = in.readString();
        selected = in.readByte() != 0;
        superId = in.readString();
    }

    public static final Creator<NameValueBean> CREATOR = new Creator<NameValueBean>() {
        @Override
        public NameValueBean createFromParcel(Parcel in) {
            return new NameValueBean(in);
        }

        @Override
        public NameValueBean[] newArray(int size) {
            return new NameValueBean[size];
        }
    };

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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String getStringRepresentation() {
        return name;
    }

    @Override
    public String getStringValue() {
        return value;
    }

    public String getSuperId() {
        return superId;
    }

    public void setSuperId(String superId) {
        this.superId = superId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(value);
        dest.writeByte((byte) (selected ? 1 : 0));
        dest.writeString(superId);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
