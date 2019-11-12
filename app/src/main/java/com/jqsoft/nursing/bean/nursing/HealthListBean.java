package com.jqsoft.nursing.bean.nursing;

import java.io.Serializable;
import java.util.List;

/**
 * @author yedong
 * @date 2019/1/17
 * 健康列表数据model
 */
public class HealthListBean implements Serializable {


    /**
     * Rows : [{"dAddTime":"2019-10-16T13:54:23.8","dBirthTime":"1991-08-14T00:00:00","gKey":"2d6bc57d-e202-4ea9-ba50-1d9e23704b27","iAge":27,"iDelete":0,"iHuJiType":0,"iState":1,"sAddUserId":"c9e80a74-f7ce-4803-b914-54eca951423c","sAddress":"安徽省合肥市庐阳区亳州路街道畅园社区","sCity":"340100","sCityName":"合肥市","sCommunity":"340103001001","sCommunityName":"畅园社区","sCounty":"340103","sCountyName":"庐阳区","sElderPhoto":"","sFaceEdition":"2.1","sIdCard":"341221199108142573","sLatitude":"31.83312","sLongitude":"117.131453","sName":"朱迎州","sNationCode":"1","sNationName":"汉族","sPersonTypeCode":"15","sPersonTypeName":"其他","sProvince":"340000","sProvinceName":"安徽省","sRegisterAddress":"安徽省合肥市庐阳区亳州路街道畅园社区","sRegisterCity":"340100","sRegisterCityName":"合肥市","sRegisterCommunity":"340103001001","sRegisterCommunityName":"畅园社区","sRegisterCounty":"340103","sRegisterCountyName":"庐阳区","sRegisterProvince":"340000","sRegisterProvinceName":"安徽省","sRegisterStreet":"340103001","sRegisterStreetName":"亳州路街道","sSexCode":"1","sSexName":"男","sStreet":"340103001","sStreetName":"亳州路街道"}]
     * Total : 1
     */

    private int Total;
    private List<RowsBean> Rows;

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }

    public List<RowsBean> getRows() {
        return Rows;
    }

    public void setRows(List<RowsBean> Rows) {
        this.Rows = Rows;
    }

    public static class RowsBean implements Serializable{
        /**
         * dAddTime : 2019-10-16T13:54:23.8
         * dBirthTime : 1991-08-14T00:00:00
         * gKey : 2d6bc57d-e202-4ea9-ba50-1d9e23704b27
         * iAge : 27
         * iDelete : 0
         * iHuJiType : 0
         * iState : 1
         * sAddUserId : c9e80a74-f7ce-4803-b914-54eca951423c
         * sAddress : 安徽省合肥市庐阳区亳州路街道畅园社区
         * sCity : 340100
         * sCityName : 合肥市
         * sCommunity : 340103001001
         * sCommunityName : 畅园社区
         * sCounty : 340103
         * sCountyName : 庐阳区
         * sElderPhoto :
         * sFaceEdition : 2.1
         * sIdCard : 341221199108142573
         * sLatitude : 31.83312
         * sLongitude : 117.131453
         * sName : 朱迎州
         * sNationCode : 1
         * sNationName : 汉族
         * sPersonTypeCode : 15
         * sPersonTypeName : 其他
         * sProvince : 340000
         * sProvinceName : 安徽省
         * sRegisterAddress : 安徽省合肥市庐阳区亳州路街道畅园社区
         * sRegisterCity : 340100
         * sRegisterCityName : 合肥市
         * sRegisterCommunity : 340103001001
         * sRegisterCommunityName : 畅园社区
         * sRegisterCounty : 340103
         * sRegisterCountyName : 庐阳区
         * sRegisterProvince : 340000
         * sRegisterProvinceName : 安徽省
         * sRegisterStreet : 340103001
         * sRegisterStreetName : 亳州路街道
         * sSexCode : 1
         * sSexName : 男
         * sStreet : 340103001
         * sStreetName : 亳州路街道
         */

        private String dAddTime;
        private String dBirthTime;
        private String gKey;
        private int iAge;
        private int iDelete;
        private int iHuJiType;
        private int iState;
        private String sAddUserId;
        private String sAddress;
        private String sCity;
        private String sCityName;
        private String sCommunity;
        private String sCommunityName;
        private String sCounty;
        private String sCountyName;
        private String sElderPhoto;
        private String sFaceEdition;
        private String sIdCard;
        private String sLatitude;
        private String sLongitude;
        private String sName;
        private String sNationCode;
        private String sNationName;
        private String sPersonTypeCode;
        private String sPersonTypeName;
        private String sProvince;
        private String sProvinceName;
        private String sRegisterAddress;
        private String sRegisterCity;
        private String sRegisterCityName;
        private String sRegisterCommunity;
        private String sRegisterCommunityName;
        private String sRegisterCounty;
        private String sRegisterCountyName;
        private String sRegisterProvince;
        private String sRegisterProvinceName;
        private String sRegisterStreet;
        private String sRegisterStreetName;
        private String sSexCode;
        private String sSexName;
        private String sStreet;
        private String sStreetName;

        public String getDAddTime() {
            return dAddTime;
        }

        public void setDAddTime(String dAddTime) {
            this.dAddTime = dAddTime;
        }

        public String getDBirthTime() {
            return dBirthTime;
        }

        public void setDBirthTime(String dBirthTime) {
            this.dBirthTime = dBirthTime;
        }

        public String getGKey() {
            return gKey;
        }

        public void setGKey(String gKey) {
            this.gKey = gKey;
        }

        public int getIAge() {
            return iAge;
        }

        public void setIAge(int iAge) {
            this.iAge = iAge;
        }

        public int getIDelete() {
            return iDelete;
        }

        public void setIDelete(int iDelete) {
            this.iDelete = iDelete;
        }

        public int getIHuJiType() {
            return iHuJiType;
        }

        public void setIHuJiType(int iHuJiType) {
            this.iHuJiType = iHuJiType;
        }

        public int getIState() {
            return iState;
        }

        public void setIState(int iState) {
            this.iState = iState;
        }

        public String getSAddUserId() {
            return sAddUserId;
        }

        public void setSAddUserId(String sAddUserId) {
            this.sAddUserId = sAddUserId;
        }

        public String getSAddress() {
            return sAddress;
        }

        public void setSAddress(String sAddress) {
            this.sAddress = sAddress;
        }

        public String getSCity() {
            return sCity;
        }

        public void setSCity(String sCity) {
            this.sCity = sCity;
        }

        public String getSCityName() {
            return sCityName;
        }

        public void setSCityName(String sCityName) {
            this.sCityName = sCityName;
        }

        public String getSCommunity() {
            return sCommunity;
        }

        public void setSCommunity(String sCommunity) {
            this.sCommunity = sCommunity;
        }

        public String getSCommunityName() {
            return sCommunityName;
        }

        public void setSCommunityName(String sCommunityName) {
            this.sCommunityName = sCommunityName;
        }

        public String getSCounty() {
            return sCounty;
        }

        public void setSCounty(String sCounty) {
            this.sCounty = sCounty;
        }

        public String getSCountyName() {
            return sCountyName;
        }

        public void setSCountyName(String sCountyName) {
            this.sCountyName = sCountyName;
        }

        public String getSElderPhoto() {
            return sElderPhoto;
        }

        public void setSElderPhoto(String sElderPhoto) {
            this.sElderPhoto = sElderPhoto;
        }

        public String getSFaceEdition() {
            return sFaceEdition;
        }

        public void setSFaceEdition(String sFaceEdition) {
            this.sFaceEdition = sFaceEdition;
        }

        public String getSIdCard() {
            return sIdCard;
        }

        public void setSIdCard(String sIdCard) {
            this.sIdCard = sIdCard;
        }

        public String getSLatitude() {
            return sLatitude;
        }

        public void setSLatitude(String sLatitude) {
            this.sLatitude = sLatitude;
        }

        public String getSLongitude() {
            return sLongitude;
        }

        public void setSLongitude(String sLongitude) {
            this.sLongitude = sLongitude;
        }

        public String getSName() {
            return sName;
        }

        public void setSName(String sName) {
            this.sName = sName;
        }

        public String getSNationCode() {
            return sNationCode;
        }

        public void setSNationCode(String sNationCode) {
            this.sNationCode = sNationCode;
        }

        public String getSNationName() {
            return sNationName;
        }

        public void setSNationName(String sNationName) {
            this.sNationName = sNationName;
        }

        public String getSPersonTypeCode() {
            return sPersonTypeCode;
        }

        public void setSPersonTypeCode(String sPersonTypeCode) {
            this.sPersonTypeCode = sPersonTypeCode;
        }

        public String getSPersonTypeName() {
            return sPersonTypeName;
        }

        public void setSPersonTypeName(String sPersonTypeName) {
            this.sPersonTypeName = sPersonTypeName;
        }

        public String getSProvince() {
            return sProvince;
        }

        public void setSProvince(String sProvince) {
            this.sProvince = sProvince;
        }

        public String getSProvinceName() {
            return sProvinceName;
        }

        public void setSProvinceName(String sProvinceName) {
            this.sProvinceName = sProvinceName;
        }

        public String getSRegisterAddress() {
            return sRegisterAddress;
        }

        public void setSRegisterAddress(String sRegisterAddress) {
            this.sRegisterAddress = sRegisterAddress;
        }

        public String getSRegisterCity() {
            return sRegisterCity;
        }

        public void setSRegisterCity(String sRegisterCity) {
            this.sRegisterCity = sRegisterCity;
        }

        public String getSRegisterCityName() {
            return sRegisterCityName;
        }

        public void setSRegisterCityName(String sRegisterCityName) {
            this.sRegisterCityName = sRegisterCityName;
        }

        public String getSRegisterCommunity() {
            return sRegisterCommunity;
        }

        public void setSRegisterCommunity(String sRegisterCommunity) {
            this.sRegisterCommunity = sRegisterCommunity;
        }

        public String getSRegisterCommunityName() {
            return sRegisterCommunityName;
        }

        public void setSRegisterCommunityName(String sRegisterCommunityName) {
            this.sRegisterCommunityName = sRegisterCommunityName;
        }

        public String getSRegisterCounty() {
            return sRegisterCounty;
        }

        public void setSRegisterCounty(String sRegisterCounty) {
            this.sRegisterCounty = sRegisterCounty;
        }

        public String getSRegisterCountyName() {
            return sRegisterCountyName;
        }

        public void setSRegisterCountyName(String sRegisterCountyName) {
            this.sRegisterCountyName = sRegisterCountyName;
        }

        public String getSRegisterProvince() {
            return sRegisterProvince;
        }

        public void setSRegisterProvince(String sRegisterProvince) {
            this.sRegisterProvince = sRegisterProvince;
        }

        public String getSRegisterProvinceName() {
            return sRegisterProvinceName;
        }

        public void setSRegisterProvinceName(String sRegisterProvinceName) {
            this.sRegisterProvinceName = sRegisterProvinceName;
        }

        public String getSRegisterStreet() {
            return sRegisterStreet;
        }

        public void setSRegisterStreet(String sRegisterStreet) {
            this.sRegisterStreet = sRegisterStreet;
        }

        public String getSRegisterStreetName() {
            return sRegisterStreetName;
        }

        public void setSRegisterStreetName(String sRegisterStreetName) {
            this.sRegisterStreetName = sRegisterStreetName;
        }

        public String getSSexCode() {
            return sSexCode;
        }

        public void setSSexCode(String sSexCode) {
            this.sSexCode = sSexCode;
        }

        public String getSSexName() {
            return sSexName;
        }

        public void setSSexName(String sSexName) {
            this.sSexName = sSexName;
        }

        public String getSStreet() {
            return sStreet;
        }

        public void setSStreet(String sStreet) {
            this.sStreet = sStreet;
        }

        public String getSStreetName() {
            return sStreetName;
        }

        public void setSStreetName(String sStreetName) {
            this.sStreetName = sStreetName;
        }
    }
}
