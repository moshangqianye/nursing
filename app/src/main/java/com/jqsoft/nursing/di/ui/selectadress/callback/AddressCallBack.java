package com.jqsoft.nursing.di.ui.selectadress.callback;


import com.jqsoft.nursing.di.ui.selectadress.manager.AddressManager;

public interface AddressCallBack {
    public void selectProvince(AddressManager.Province province);
    public void selectCity(AddressManager.City city);
    public void selectCounty(AddressManager.County county);
    public void selectStreet(AddressManager.Street street);
    public void selectVillage(AddressManager.Village village);
    public void selectDistrict(AddressManager.District district);
}
