package com.jqsoft.nursing.di.ui.selectadress.views;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;


import com.jqsoft.nursing.R;
import com.jqsoft.nursing.arcface.CardImageLiveFaceVerifyActivity;
import com.jqsoft.nursing.di.ui.selectadress.adapter.AddressListAdapter;
import com.jqsoft.nursing.di.ui.selectadress.callback.AddressCallBack;
import com.jqsoft.nursing.di.ui.selectadress.callback.TabOnClickListener;
import com.jqsoft.nursing.di.ui.selectadress.fragment.CityFragment;
import com.jqsoft.nursing.di.ui.selectadress.fragment.CountyFragment;
import com.jqsoft.nursing.di.ui.selectadress.fragment.DistrictFragment;
import com.jqsoft.nursing.di.ui.selectadress.fragment.ProvinceFragment;
import com.jqsoft.nursing.di.ui.selectadress.fragment.StreetFragment;
import com.jqsoft.nursing.di.ui.selectadress.fragment.VillageFragment;
import com.jqsoft.nursing.di.ui.selectadress.manager.AddressManager;
import com.jqsoft.nursing.di.ui.selectadress.tool.DensityUtils;
import com.jqsoft.nursing.di.ui.selectadress.tool.StringUtils;

import java.util.ArrayList;
import java.util.List;




public class SelectAddressPop extends DialogFragment implements AddressCallBack {
    private CardImageLiveFaceVerifyActivity.SelectAddresFinish mSelectAddresFinish;
    private Context context;
    private View view;
    private ViewPager viewPager;
    private PagerSlidingTabStrip pagerTab;
    private FrameLayout popBg;

    private AddressManager.Province province;
    private AddressManager.City city ;
    private AddressManager.County county ;
    private AddressManager.Street street ;
    private AddressManager.Village village ;
    private AddressManager.District district;
    private String defutText;
    private ProvinceFragment mProvinceFragment;
    private CityFragment mCityFragment;
    private DistrictFragment mDistrictFragment;
    private CountyFragment mCountyFragment;
    private StreetFragment mStreetFragment;
    private VillageFragment mVillageFragment;
    public void setSelectAddresFinish(CardImageLiveFaceVerifyActivity.SelectAddresFinish mSelectAddresFinish) {
        this.mSelectAddresFinish = mSelectAddresFinish;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        context = getActivity();
        initView();
        Dialog dialog = new Dialog(context, R.style.CustomDatePickerDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // must be called before set content
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);

        // 设置宽度为屏宽、靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);
        return dialog;
    }

    public void setAddress(String pCode, String cCode,String countyCode,String streetCode,String villageCode,String aCode){
        if (StringUtils.isNoEmpty(pCode) && StringUtils.isNoEmpty(cCode)&&
                StringUtils.isNoEmpty(countyCode)&& StringUtils.isNoEmpty(streetCode)
                && StringUtils.isNoEmpty(villageCode) && StringUtils.isNoEmpty(aCode)){
            province = AddressManager.newInstance().findProvinceByCode(pCode);
            city = province.findCityByCode(cCode);
            county=  city.findCountyByCode(countyCode);
            street=  county.findStreetByCode(streetCode);
            village = street.findVillageByCode(villageCode);
            district =village.findDistrictByCode(aCode);
        }else if (StringUtils.isNoEmpty(pCode) && StringUtils.isNoEmpty(cCode)&&
                StringUtils.isNoEmpty(countyCode)&& StringUtils.isNoEmpty(streetCode)
                && StringUtils.isNoEmpty(villageCode)){
            province = AddressManager.newInstance().findProvinceByCode(pCode);
            city = province.findCityByCode(cCode);
            county=  city.findCountyByCode(countyCode);
            street=  county.findStreetByCode(streetCode);
            village = street.findVillageByCode(villageCode);

        }else if (StringUtils.isNoEmpty(pCode) && StringUtils.isNoEmpty(cCode)&&
                StringUtils.isNoEmpty(countyCode)&& StringUtils.isNoEmpty(streetCode)
               ){
            province = AddressManager.newInstance().findProvinceByCode(pCode);
            city = province.findCityByCode(cCode);
            county=  city.findCountyByCode(countyCode);
            street=  county.findStreetByCode(streetCode);

        }else if (StringUtils.isNoEmpty(pCode) && StringUtils.isNoEmpty(cCode)&&
                StringUtils.isNoEmpty(countyCode)
        ){
            province = AddressManager.newInstance().findProvinceByCode(pCode);
            city = province.findCityByCode(cCode);
            county=  city.findCountyByCode(countyCode);
        }else if (StringUtils.isNoEmpty(pCode) && StringUtils.isNoEmpty(cCode)
        ){
            province = AddressManager.newInstance().findProvinceByCode(pCode);
            city = province.findCityByCode(cCode);
        }else if (StringUtils.isNoEmpty(pCode)

        ){
            province = AddressManager.newInstance().findProvinceByCode(pCode);
            mCityFragment.setCode(province.getCode(),null);
        }
    }

    private void initView() {

        view = LayoutInflater.from(context).inflate(R.layout.select_address_pop_layout,null);
        ImageView ivClose = (ImageView) view.findViewById(R.id.ivClose);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        pagerTab = (PagerSlidingTabStrip) view.findViewById(R.id.pagerTab);
        popBg = (FrameLayout)view.findViewById(R.id.popBg);
        defutText = context.getString(R.string.selset_birthday);
        pagerTab.setTextSize(DensityUtils.sp2px(context, 14));
        pagerTab.setSelectedColor(getResources().getColor(R.color.new_redbg));
        pagerTab.setTextColor(getResources().getColor(R.color.regis_account_exist));

        List<View> lis = new ArrayList<View>();
        mProvinceFragment = new ProvinceFragment(context,this);
        mCityFragment = new CityFragment(context,this);
        mDistrictFragment = new DistrictFragment(context,this);
        mCountyFragment = new CountyFragment(context,this);
        mStreetFragment= new StreetFragment(context,this);
        mVillageFragment= new VillageFragment(context,this);
        lis.add(mProvinceFragment.getListview());
        lis.add(mCityFragment.getListview());
        lis.add(mCountyFragment.getListview());
        lis.add(mStreetFragment.getListview());
        lis.add(mVillageFragment.getListview());
//        lis.add(mDistrictFragment.getListview());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
//                String[] addres = null;
//                if (province!=null&&city!=null&&county!=null&&street!=null&&village!=null) {
//                    addres = new String[]{province.getName(), city.getName(),county.getName(),street.getName(),village.getName()};
//                }else if(province!=null&&city!=null&&county!=null&&street!=null&&village!=null) {
//                    addres = new String[]{province.getName(), city.getName(), county.getName(),street.getName(),village.getName()};
//                }else if(province!=null&&city!=null&&county!=null&&street!=null) {
//                    addres = new String[]{province.getName(), city.getName(), county.getName(),street.getName(),defutText};
//                }else if(province!=null&&city!=null&&county!=null) {
//                    addres = new String[]{province.getName(), city.getName(), county.getName(),defutText};
//                }else if(province!=null&&city!=null) {
//                    addres = new String[]{province.getName(), city.getName(), defutText};
//                }else{
//                    addres = new String[]{province.getName(), defutText};
//                }
//                pagerTab.setTabsText(addres);
//                pagerTab.setCurrentPosition(position);


            }

            @Override
            public void onPageScrollStateChanged(int position) {

            }
        });
        viewPager.setAdapter(new AddressListAdapter(lis));


        String[] addres = null;
        if (province != null && city != null&& county != null&& street != null&& village != null && district != null){
            addres = new String[]{province.getName(),city.getName(),county.getName(),street.getName(),village.getName(),district.getName()};
            mProvinceFragment.setCode(province.getCode());
            mCityFragment.setCode(province.getCode(),city.getCode());
            mCountyFragment.setCode(province.getCode(),city.getCode(),county.getCode());
            mStreetFragment.setCode(province.getCode(),city.getCode(),county.getCode(),street.getCode());
            mVillageFragment.setCode(province.getCode(),city.getCode(),county.getCode(),street.getCode(),village.getCode());
            mDistrictFragment.setCode(province.getCode(),city.getCode(),county.getCode(),street.getCode(),village.getCode(),district.getCode());
            viewPager.setCurrentItem(5);
            pagerTab.setTabsText(addres);
            pagerTab.setCurrentPosition(5);
        }else if (province != null && city != null&& county != null&& street != null&& village != null){
            addres = new String[]{province.getName(),city.getName(),county.getName(),street.getName(),village.getName(),defutText};
            mProvinceFragment.setCode(province.getCode());
            mCityFragment.setCode(province.getCode(),city.getCode());
            mCountyFragment.setCode(province.getCode(),city.getCode(),county.getCode());
            mStreetFragment.setCode(province.getCode(),city.getCode(),county.getCode(),street.getCode());
            mVillageFragment.setCode(province.getCode(),city.getCode(),county.getCode(),street.getCode(),village.getCode());
            mDistrictFragment.setCode(province.getCode(),city.getCode(),county.getCode(),street.getCode(),village.getCode(),null);
            viewPager.setCurrentItem(5);
            pagerTab.setTabsText(addres);
            pagerTab.setCurrentPosition(5);
        }else if (province != null && city != null&& county != null&& street != null){
            addres = new String[]{province.getName(),city.getName(),county.getName(),street.getName(),defutText};
            mProvinceFragment.setCode(province.getCode());
            mCityFragment.setCode(province.getCode(),city.getCode());
            mCountyFragment.setCode(province.getCode(),city.getCode(),county.getCode());
            mStreetFragment.setCode(province.getCode(),city.getCode(),county.getCode(),street.getCode());
            mVillageFragment.setCode(province.getCode(),city.getCode(),county.getCode(),street.getCode(),null);
            viewPager.setCurrentItem(4);
            pagerTab.setTabsText(addres);
            pagerTab.setCurrentPosition(4);
        }else if (province != null && city != null&& county != null){
            addres = new String[]{province.getName(),city.getName(),county.getName(),defutText};
            mProvinceFragment.setCode(province.getCode());
            mCityFragment.setCode(province.getCode(),city.getCode());
            mCountyFragment.setCode(province.getCode(),city.getCode(),county.getCode());
            mStreetFragment.setCode(province.getCode(),city.getCode(),county.getCode(),null);

            viewPager.setCurrentItem(3);
            pagerTab.setTabsText(addres);
            pagerTab.setCurrentPosition(3);
        }else if (province != null && city != null){
            addres = new String[]{province.getName(),city.getName(),defutText};
            mProvinceFragment.setCode(province.getCode());
            mCityFragment.setCode(province.getCode(),city.getCode());
            mCountyFragment.setCode(province.getCode(),city.getCode(),null);

            viewPager.setCurrentItem(2);
            pagerTab.setTabsText(addres);
            pagerTab.setCurrentPosition(2);
        }else if (province != null ){
            addres = new String[]{province.getName(),defutText};
            mProvinceFragment.setCode(province.getCode());
            mCityFragment.setCode(province.getCode(),null);

            viewPager.setCurrentItem(1);
            pagerTab.setTabsText(addres);
            pagerTab.setCurrentPosition(1);
        }else{
            addres = new String[]{defutText};
            viewPager.setCurrentItem(0);
            pagerTab.setTabsText(addres);
            pagerTab.setCurrentPosition(0);
        }


        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        popBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        pagerTab.setTabOnClickListener(new TabOnClickListener() {
            @Override
            public void onClick(View tab, int position) {
                if (defutText.equals(pagerTab.getTabs()[position])){
                    return;
                }
                viewPager.setCurrentItem(position);
                String[] addres = null;
                switch (position){
                    case 0:
                        if (province!=null&&city!=null&&county!=null&&street!=null&&village!=null&&district != null) {
                            addres = new String[]{province.getName(), city.getName(),county.getName(),street.getName(),village.getName(),district.getName()};
                        }else if(province!=null&&city!=null&&county!=null&&street!=null&&village!=null) {
                            addres = new String[]{province.getName(), city.getName(), county.getName(),street.getName(),village.getName(),defutText};
                        }else if(province!=null&&city!=null&&county!=null&&street!=null) {
                            addres = new String[]{province.getName(), city.getName(), county.getName(),street.getName(),defutText};
                        }else if(province!=null&&city!=null&&county!=null) {
                            addres = new String[]{province.getName(), city.getName(), county.getName(),defutText};
                        }else if(province!=null&&city!=null) {
                            addres = new String[]{province.getName(), city.getName(), defutText};
                        }else{
                            addres = new String[]{province.getName(), defutText};
                        }
                        break;
                    case 1:
                        if (province!=null&&city!=null&&county!=null&&street!=null&&village!=null&&district != null) {
                            addres = new String[]{province.getName(), city.getName(),county.getName(),street.getName(),village.getName(),district.getName()};
                        }else if(province!=null&&city!=null&&county!=null&&street!=null&&village!=null) {
                            addres = new String[]{province.getName(), city.getName(), county.getName(),street.getName(),village.getName(),defutText};
                        }else if(province!=null&&city!=null&&county!=null&&street!=null) {
                            addres = new String[]{province.getName(), city.getName(), county.getName(),street.getName(),defutText};
                        }else if(province!=null&&city!=null&&county!=null) {
                            addres = new String[]{province.getName(), city.getName(), county.getName(),defutText};
                        }else {
                            addres = new String[]{province.getName(), city.getName(), defutText};
                        }
                        //mDistrictFragment.resetIndex();
                        break;
                    case 2:
                        if (province!=null&&city!=null&&county!=null&&street!=null&&village!=null&&district != null) {
                            addres = new String[]{province.getName(), city.getName(),county.getName(),street.getName(),village.getName(),district.getName()};
                        }else if(province!=null&&city!=null&&county!=null&&street!=null) {
                            addres = new String[]{province.getName(), city.getName(), county.getName(),street.getName(),defutText};
                        }else if(province!=null&&city!=null&&county!=null) {
                            addres = new String[]{province.getName(), city.getName(), county.getName(),defutText};
                        }else if(province!=null&&city!=null) {
                            addres = new String[]{province.getName(), city.getName(), defutText};
                        }
                    break;
                    case 3:
                        if (district != null&&province!=null&&city!=null&&county!=null&&street!=null&&village!=null) {
                            addres = new String[]{province.getName(), city.getName(),county.getName(),street.getName(),village.getName(),district.getName()};
                        }else if(province!=null&&city!=null&&county!=null&&street!=null&&village!=null) {
                            addres = new String[]{province.getName(), city.getName(), county.getName(),street.getName(),village.getName(),defutText};
                        }else if(province!=null&&city!=null&&county!=null&&street!=null){
                            addres = new String[]{province.getName(), city.getName(), county.getName(),street.getName(), defutText};
                        }
                        break;
                    case 4:
                        if (district != null&&province!=null&&city!=null&&county!=null&&street!=null&&village!=null) {
                            addres = new String[]{province.getName(), city.getName(),county.getName(),street.getName(),village.getName(),district.getName()};
                        }else {
                            addres = new String[]{province.getName(), city.getName(), county.getName(),street.getName(),village.getName(), defutText};
                        }
                        break;
                    case 5:
                        addres = new String[]{province.getName(), city.getName(),county.getName(),street.getName(),village.getName(),district.getName()};

                        break;

                        default:
                }
                pagerTab.setTabsText(addres);
                pagerTab.setCurrentPosition(position);
            }
        });

//		LinearLayout  TabsContainer=pagerTab.getTabsContainer();
//		pagerTab.scrollTo(0,pagerTab.getX());

    }

    @Override
    public void show(FragmentManager manager, String tag) {
        //KeyBoardUtils.closeKeybord((Activity) context);
        super.show(manager, tag);
    }

    @Override
    public void selectProvince(AddressManager.Province province) {
        Toast.makeText(context,"省、市、县暂不允许更改！",Toast.LENGTH_LONG).show();


//        String[] addres = new String[]{province.getName(),defutText};
//        pagerTab.setTabsText(addres);
//        pagerTab.setCurrentPosition(1);
//        viewPager.setCurrentItem(1);
//        if(province != this.province){
//            city = null;
//            district = null;
//        }
//        this.province = province;
//        mCityFragment.setCode(province.getCode(),null);
    }

    @Override
    public void selectCity(AddressManager.City city) {
        Toast.makeText(context,"省、市、县暂不允许更改！",Toast.LENGTH_LONG).show();
//        AddressManager.readCountyOfCityonSelect(city);
//        String[] addres = new String[]{province.getName(),city.getName(),defutText};
//        pagerTab.setTabsText(addres);
//        pagerTab.setCurrentPosition(2);
//        viewPager.setCurrentItem(2);
//        if(city != this.city){
//            county = null;
//        }
//        this.city = city;
//        mCountyFragment.setCode(province.getCode(),city.getCode(),null);
    }

    @Override
    public void selectCounty(AddressManager.County county) {
        Toast.makeText(context,"省、市、县暂不允许更改！",Toast.LENGTH_LONG).show();

//        AddressManager.readStreetOfCountyOnSelect(county);
//        String[] addres = new String[]{province.getName(),city.getName(),county.getName(),defutText};
//        pagerTab.setTabsText(addres);
//        pagerTab.setCurrentPosition(3);
//        viewPager.setCurrentItem(3);
//        if(county != this.county){
//            street = null;
//        }
//        this.county = county;
//        mStreetFragment.setCode(province.getCode(),city.getCode(),county.getCode(),null);
    }

    @Override
    public void selectStreet(AddressManager.Street street) {
//        Toast.makeText(context,"省、市、县、区暂不允许更改！",Toast.LENGTH_LONG).show();
        AddressManager.readVillageOfStreetOnSelectAddressPop(street);
        String[] addres = new String[]{province.getName(),city.getName(),county.getName(),street.getName(),defutText};
        pagerTab.setTabsText(addres);
        pagerTab.setCurrentPosition(4);
        viewPager.setCurrentItem(4);
        if(street != this.street){
            village = null;
        }
        this.street = street;
        mVillageFragment.setCode(province.getCode(),city.getCode(),county.getCode(),street.getCode(),null);
    }

    @Override
    public void selectVillage(AddressManager.Village village) {
        int districtSize=AddressManager.readDistrictOfVillageonSelect(village);

        String[] addres = new String[]{province.getName(),city.getName(),county.getName(),street.getName(),village.getName(),defutText};
        pagerTab.setTabsText(addres);
        pagerTab.setCurrentPosition(5);
        viewPager.setCurrentItem(5);
        if(village != this.village){
            district = null;
        }
        this.village = village;
        mDistrictFragment.setCode(province.getCode(),city.getCode(),county.getCode(),street.getCode(),village.getCode(),null);
        if (districtSize<1){
            mSelectAddresFinish.finish(province.getCode(),city.getCode(),county.getCode(),street.getCode(),village.getCode(),null);
            dismiss();
        }
    }

    @Override
    public void selectDistrict(AddressManager.District district) {
        String[] addres = new String[]{province.getName(),city.getName(),county.getName(),street.getName(),village.getName(),district.getName()};
        pagerTab.setTabsText(addres);
        this.district = district;
        mSelectAddresFinish.finish(province.getCode(),city.getCode(),county.getCode(),street.getCode(),village.getCode(),district.getCode());
        dismiss();
    }


}
