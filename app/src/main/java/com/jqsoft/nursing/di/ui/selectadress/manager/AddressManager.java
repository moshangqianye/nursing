package com.jqsoft.nursing.di.ui.selectadress.manager;

import android.content.Context;


import com.jqsoft.nursing.base.Constant;
import com.jqsoft.nursing.bean.AreaDictionaryUtil;
import com.jqsoft.nursing.bean.DictionaryAreaData;
import com.jqsoft.nursing.di.ui.selectadress.modle.ReceiveAddress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jqsoft.nursing.bean.AreaDictionaryUtil.getAreaDataBeanListFromAreaLevel;


/**
 * 地址管理类，包括更新、删除、添加地址，以及从数据库中读取所有省、市、区信息
 */
public class AddressManager {
    public static final int VERSION = 1;

    private static AddressManager ins = null;

    public synchronized static AddressManager newInstance() {
        if (ins == null) {
            ins = new AddressManager();
        }
        return ins;
    }

    protected AddressManager() {
        initDataFromDB();

    }

    /**
     * 从数据库中读取所有的省、市、区
     */
    private void initDataFromDB() {
        List<DictionaryAreaData> dictionaryAreaData = getAreaDataBeanListFromAreaLevel(Constant.AREA_LEVEL_PROVINCE);
        for (DictionaryAreaData data : dictionaryAreaData) {
            Province province = new Province(data.getAreaname(), data.getStringValue());
            provinces.add(province);
            readCitiesOfProvince(province);
        }


    }

    private List<Province> provinces = new ArrayList<Province>();

    /**
     * 获取数据库中读取的所有省信息
     *
     * @return
     */
    public List<Province> getAllProvinces() {
        return provinces;
    }

    public int getProvinceCount() {
        return provinces.size();
    }

    /**
     * 通过省或直辖市的code找到省或直辖市
     *
     * @param code 省或直辖市的code，如：110000表示北京市
     * @return
     */
    public Province findProvinceByCode(String code) {
        for (Province province : provinces) {
            if (province.getCode().equals(code)) {
                return province;
            }
        }
        return null;
    }

    /**
     * 根据省或直辖市的名称找到省或直辖市的信息
     *
     * @param name 省名称，如：北京市
     * @return
     */
    public Province findProvinceByName(String name) {
        for (Province province : provinces) {
            if (province.getName().equals(name)) {
                return province;
            }
        }
        return null;
    }

    /**
     * 通过省、市和区三个code获得详细地址信息
     * @param provinceCode 省或直辖市code，如110000，表示北京
     * @param cityCode 市code，如110100，表示市辖区
     * @param districtCode，区code，如110101，表示东城区
     * 例如三个code分别是110000, 110100, 110101，则输出为北京市市辖区东城区
     * @return
     */
//	public String getAddress(String provinceCode, String cityCode,String countyCode,String streetCode,String villageCode, String districtCode)
//	{
//		String addr = "";
//		Province province = findProvinceByCode(provinceCode);
//		if(province != null) {
//			addr += province.getName();
//			City city = province.findCityByCode(cityCode);
//			if(city != null) {
//				addr += city.getName();
//				County county = city.findCountyByCode(countyCode);
//				if(county != null) {
//					addr += county.getName();
//					Street  street = county.findStreetByCode(streetCode);
//					if(street != null) {
//						addr += street.getName();
//						Village village = street.findVillageByCode(villageCode);
//						if(village != null) {
//							addr += village.getName();
//							District district = village.findDistrictByCode(districtCode);
//							if(district != null) {
//								addr += district.getName();
//							}
//						}
//
//					}
//
//				}
//
//			}
//		}
//		return addr;
//	}


    /**
     * 通过省、市和区三个code获得详细地址信息
     *
     * @param provinceCode                     省或直辖市code，如110000，表示北京
     * @param cityCode                         市code，如110100，表示市辖区
     * @param districtCode，区code，如110101，表示东城区 例如三个code分别是110000, 110100, 110101，则输出为北京市市辖区东城区
     * @return
     */
    public List<String> getAddress(String provinceCode, String cityCode, String countyCode, String streetCode, String villageCode, String districtCode) {
        String addr = "";
        List<String> list=new ArrayList<>();
        list.clear();
        Province province = findProvinceByCode(provinceCode);
        if (province != null) {
            addr += province.getName();
            list.add(province.getName());
            City city = province.findCityByCode(cityCode);
            if (city != null) {
                addr += city.getName();
                list.add(city.getName());
                County county = city.findCountyByCode(countyCode);
                if (county == null) {
                    readCountyOfCityonSelect(city);
                    county = city.findCountyByCode(countyCode);
                }
                if (county != null) {
                    addr += county.getName();
                    list.add(county.getName());
                    Street street = county.findStreetByCode(streetCode);
                    if (street == null) {
                        readStreetOfCountyOnSelect(county);

                        street = county.findStreetByCode(streetCode);
                    }

                    if (street != null) {

                        addr += street.getName();
                        list.add(street.getName());
                        Village village = street.findVillageByCode(districtCode);
                        if (village == null) {
//                            readDistrictOfVillageonSelect(village);
                            readVillageOfStreetOnSelectAddressPop(street);
                            village = street.findVillageByCode(villageCode);
                        }

                        if (village != null) {
                            addr += village.getName();
                            list.add(village.getName());
                        }

                    }

                }

            }
        }
        return list;
    }

    /**
     * 从数据库读取某个省或直辖市下辖的市信息
     *
     * @param province
     */
    private void readCitiesOfProvince(Province province) {
        List<DictionaryAreaData> dictionaryAreaData = AreaDictionaryUtil.getAreaDataBeanListFromAreaLevelAndParentAreaCode(Constant.AREA_LEVEL_CITY, province.code);
        for (DictionaryAreaData data : dictionaryAreaData) {
            City city = new City(data.getAreaname(), data.getStringValue(), province.getCode());
//			readCountyOfCity(city);
            province.addCity(city);
        }

    }

    /**
     * 从数据库读取某个城市下辖的区信息
     *
     * @param city
     */
    private void readCountyOfCity(City city) {
        List<DictionaryAreaData> dictionaryAreaData = AreaDictionaryUtil.getAreaDataBeanListFromAreaLevelAndParentAreaCode(Constant.AREA_LEVEL_COUNTY, city.code);
        for (DictionaryAreaData data : dictionaryAreaData) {
            County county = new County(data.getAreaname(), data.getStringValue(), city.getProvinceCode());
//			readStreetOfCounty(county);
            city.addCounty(county);
        }

    }

    /**
     * 从数据库读取某个城市下辖的区信息
     *
     * @param city
     */
    public static void readCountyOfCityonSelect(City city) {
        city.clearCounty();
        List<DictionaryAreaData> dictionaryAreaData = AreaDictionaryUtil.getAreaDataBeanListFromAreaLevelAndParentAreaCode(Constant.AREA_LEVEL_COUNTY, city.code);
        for (DictionaryAreaData data : dictionaryAreaData) {
            County county = new County(data.getAreaname(), data.getStringValue(), city.getProvinceCode());
//			readStreetOfCounty(county);
            city.addCounty(county);
        }

    }

    /**
     * 从数据库读取某个County下辖的Street信息
     *
     * @param
     */
    private void readStreetOfCounty(County county) {
        List<DictionaryAreaData> dictionaryAreaData = AreaDictionaryUtil.getAreaDataBeanListFromAreaLevelAndParentAreaCode(Constant.AREA_LEVEL_STREET, county.code);
        for (DictionaryAreaData data : dictionaryAreaData) {
            Street street = new Street(data.getAreaname(), data.getStringValue(), county.getCityCode());
//			readVillageOfStreet(street);
            county.addStreets(street);
        }

    }

    /**
     * 从数据库读取某个County下辖的Street信息
     *
     * @param
     */
    public static void readStreetOfCountyOnSelect(County county) {
        county.clearStreets();
        List<DictionaryAreaData> dictionaryAreaData = AreaDictionaryUtil.getAreaDataBeanListFromAreaLevelAndParentAreaCode(Constant.AREA_LEVEL_STREET, county.code);
        for (DictionaryAreaData data : dictionaryAreaData) {
            Street street = new Street(data.getAreaname(), data.getStringValue(), county.getCityCode());
//			readVillageOfStreet(street);
            county.addStreets(street);
        }


    }

    /**
     * 从数据库读取某个Street下辖的Village信息
     *
     * @param
     */
    private void readVillageOfStreet(Street street) {
//		List<DictionaryAreaData> dictionaryAreaData=AreaDictionaryUtil.getAreaDataBeanListFromAreaLevelAndParentAreaCode(Constant.AREA_LEVEL_VILLAGE, street.code);
//		for (DictionaryAreaData data : dictionaryAreaData) {
//			Village village = new Village(data.getAreaname(), data.getStringValue(), street.getCountyCode());
//			readDistrictOfVillage(village);
//			street.addVillage(village);
//		}

    }

    /**
     * 从数据库读取某个Street下辖的Village信息
     *
     * @param
     */
    public static void readVillageOfStreetOnSelectAddressPop(Street street) {
        street.clearVillage();
        List<DictionaryAreaData> dictionaryAreaData = AreaDictionaryUtil.getAreaDataBeanListFromAreaLevelAndParentAreaCode(Constant.AREA_LEVEL_VILLAGE, street.code);
        for (DictionaryAreaData data : dictionaryAreaData) {
            Village village = new Village(data.getAreaname(), data.getStringValue(), street.getCountyCode());
//			readDistrictOfVillage(village);
            street.addVillage(village);
        }

    }

    /**
     * 从数据库读取某个Village下辖的district信息
     *
     * @param
     */
    public static int readDistrictOfVillageonSelect(Village village) {
        village.clearDistrict();
        List<DictionaryAreaData> dictionaryAreaData = AreaDictionaryUtil.getAreaDataBeanListFromAreaLevelAndParentAreaCode(Constant.AREA_LEVEL_ZU, village.code);
        for (DictionaryAreaData data : dictionaryAreaData) {
            District district = new District(data.getAreaname(), data.getStringValue(), village.getStreetCode(), village.getStreetCode());
            village.addDistrict(district);
        }
        return dictionaryAreaData.size();

    }

    private List<ReceiveAddress> addresses = new ArrayList<ReceiveAddress>();
    //缓存已选中的地址（本地数据） 做容错处理
    private Map<Integer, ReceiveAddress> selectAddresss = new HashMap<Integer, ReceiveAddress>();

    public List<ReceiveAddress> getAllAddresses() {
        return addresses;
    }

    public void clear() {
        selectAddresss.clear();
        clearSelectAddress();
    }

    /**
     * 设置所有地址未选中
     */
    public void clearSelectAddress() {
        for (ReceiveAddress address : addresses) {
            address.isSelected = false;
        }
    }

    /**
     * 是否已选中
     */
    public boolean containsKeySelectAddress(int id) {
        return selectAddresss.containsKey(id);
    }


    /**
     * 设置哪一个地址被选中或取消选中（多选）
     *
     * @param pos
     */
    public void setSelectedAddress(int pos) {
        ReceiveAddress address = addresses.get(pos);
        address.isSelected = !address.isSelected;
        if (address.isSelected) {
            if (!selectAddresss.containsKey(address.id)) {
                selectAddresss.put(address.id, address.clone());
            }
        } else {
            if (selectAddresss.containsKey(address.id)) {
                selectAddresss.remove(address.id);
            }
        }
    }

    /**
     * 设置单个地址选中（单选）
     *
     * @param pos
     */
    public void setSingleSelectedAddress(Context context, int pos) {
        for (int i = 0; i < addresses.size(); ++i) {
            ReceiveAddress address = addresses.get(i);
            address.isSelected = i == pos;
        }
    }

    /**
     * 指定选项是否被选中
     *
     * @param pos
     * @return
     */
    public boolean isSelectAddressPos(int pos) {
        ReceiveAddress address = addresses.get(pos);
        return address.isSelected;
    }

    /**
     * 指定选项被选中
     *
     * @param id
     * @return
     */
    public void selectAddressId(int id) {
        ReceiveAddress address = getAddressById(id);
        address.isSelected = true;
    }

    public List<Integer> getAllSelectedAddressIds() {
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        for (int i = 0; i < addresses.size(); ++i) {
            ReceiveAddress address = addresses.get(i);
            if (address.isSelected) {
                indexes.add(address.id);
            }
        }
        return indexes;
    }

    public ReceiveAddress getAddressById(int id) {
        for (ReceiveAddress address : addresses) {
            if (address.id == id) {
                return address;
            }
        }
        return null;
    }


    /**
     * 省或直辖市类 1
     *
     * @author frontier
     */
    public static class Province {
        private String name;
        private String code;

        public Province(String name, String code) {
            this.name = name;
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }

        /**
         * 省或直辖市下辖的市列表
         */
        private List<City> cities = new ArrayList<City>();

        public void addCity(City city) {
            cities.add(city);
        }

        public void clearCity() {
            cities.clear();
        }

        public List<City> getAllCities() {
            return cities;
        }

        /**
         * 通过市的code查找市信息
         *
         * @param code
         * @return
         */
        public City findCityByCode(String code) {
            for (City city : cities) {
                if (city.getCode().equals(code)) {
                    return city;
                }
            }
            return null;
        }

        /**
         * 通过市的名字查找市的信息
         *
         * @param name
         * @return
         */
        public City findCityByName(String name) {
            for (City city : cities) {
                if (city.getName().equals(name)) {
                    return city;
                }
            }
            return null;
        }

        public int getCityCount() {
            return cities.size();
        }
    }

    /**
     * 城市类 2
     */
    public static class City {
        private String name;
        private String code;
        private String provinceCode;

        public City(String name, String code, String provinceCode) {
            this.name = name;
            this.code = code;
            this.provinceCode = provinceCode;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }

        public String getProvinceCode() {
            return provinceCode;
        }

        /**
         * 城市下辖的County列表
         */
        private List<County> countyList = new ArrayList<County>();

        public void addCounty(County county) {
            countyList.add(county);
        }

        public void clearCounty() {
            countyList.clear();
        }

        public List<County> getAllCounty() {
            return countyList;
        }

        /**
         * 通过区code获得County信息
         *
         * @param code
         * @return
         */
        public County findCountyByCode(String code) {
            for (County county : countyList) {
                if (county.getCode().equals(code)) {
                    return county;
                }
            }
            return null;
        }

        /**
         * 通过区的名称获得County的信息
         *
         * @param name
         * @return
         */
        public County findCountyByName(String name) {
            for (County county : countyList) {
                if (county.getName().equals(name)) {
                    return county;
                }
            }
            return null;
        }

        public int getCountyCount() {
            return countyList.size();
        }
    }

    /**
     * County类 3
     */
    public static class County {
        private String name;
        private String code;
        private String cityCode;

        public County(String name, String code, String cityCode) {
            this.name = name;
            this.code = code;
            this.cityCode = cityCode;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }

        public String getCityCode() {
            return cityCode;
        }

        /**
         * County下辖的Street列表
         */
        private List<Street> streets = new ArrayList<Street>();

        public void addStreets(Street street) {
            streets.add(street);
        }

        public void clearStreets() {
            streets.clear();
        }

        public List<Street> getAllStreets() {
            return streets;
        }

        /**
         * 通过区code获得Street信息
         *
         * @param code
         * @return
         */
        public Street findStreetByCode(String code) {
            for (Street street : streets) {
                if (street.getCode().equals(code)) {
                    return street;
                }
            }
            return null;
        }

        /**
         * 通过区的名称获得Street的信息
         *
         * @param name
         * @return
         */
        public Street findStreetByName(String name) {
            for (Street street : streets) {
                if (street.getName().equals(name)) {
                    return street;
                }
            }
            return null;
        }

        public int getStreetCount() {
            return streets.size();
        }
    }

    /**
     * street类 4
     */
    public static class Street {
        private String name;
        private String code;
        private String countyCode;

        public Street(String name, String code, String countyCode) {
            this.name = name;
            this.code = code;
            this.countyCode = countyCode;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }

        public String getCountyCode() {
            return countyCode;
        }

        /**
         * Street下辖的Village列表
         */
        private List<Village> villages = new ArrayList<Village>();

        public void addVillage(Village village) {
            villages.add(village);
        }

        public void clearVillage() {
            villages.clear();
        }

        public List<Village> getAllVillages() {
            return villages;
        }

        /**
         * 通过区code获得Village信息
         *
         * @param code
         * @return
         */
        public Village findVillageByCode(String code) {
            for (Village village : villages) {
                if (village.getCode().equals(code)) {
                    return village;
                }
            }
            return null;
        }

        /**
         * 通过Village的名称获得Village的信息
         *
         * @param name
         * @return
         */
        public Village findVillageByName(String name) {
            for (Village village : villages) {
                if (village.getName().equals(name)) {
                    return village;
                }
            }
            return null;
        }

        public int getVillageCount() {
            return villages.size();
        }
    }

    /**
     * Village类 5
     */
    public static class Village {
        private String name;
        private String code;
        private String streetCode;

        public Village(String name, String code, String streetCode) {
            this.name = name;
            this.code = code;
            this.streetCode = streetCode;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }

        public String getStreetCode() {
            return streetCode;
        }

        /**
         * Village下辖的组列表
         */
        private List<District> districts = new ArrayList<District>();

        public void addDistrict(District district) {
            districts.add(district);
        }

        public void clearDistrict() {
            districts.clear();
        }

        public List<District> getAllDistricts() {
            return districts;
        }

        /**
         * 通过组code获得组信息
         *
         * @param code
         * @return
         */
        public District findDistrictByCode(String code) {
            for (District district : districts) {
                if (district.getCode().equals(code)) {
                    return district;
                }
            }
            return null;
        }

        /**
         * 通过组的名称获得组的信息
         *
         * @param name
         * @return
         */
        public District findDistrictByName(String name) {
            for (District district : districts) {
                if (district.getName().equals(name)) {
                    return district;
                }
            }
            return null;
        }

        public int getDistrictCount() {
            return districts.size();
        }
    }


    /**
     * 组类 6
     */
    public static class District {
        private String name;
        private String code;
        private String cityCode;
        private String provinceCode;

        public District(String name, String code, String cityCode, String provinceCode) {
            this.name = name;
            this.code = code;
            this.cityCode = cityCode;
            this.provinceCode = provinceCode;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }

        public String getCityCode() {
            return cityCode;
        }

        public String getProvinceCode() {
            return provinceCode;
        }
    }
}
