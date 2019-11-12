package com.jqsoft.nursing.callback;


import com.jqsoft.nursing.bean.resident.SRCLoginAreaBean;


public interface AddressCallBack {
    public void selectProvince(SRCLoginAreaBean province);
    public void selectCity(SRCLoginAreaBean city);
    public void selectDistrict(SRCLoginAreaBean district);
//    public void selectProvince(AddressManager.Province province);
//    public void selectCity(AddressManager.City city);
//    public void selectDistrict(AddressManager.District district);
}
