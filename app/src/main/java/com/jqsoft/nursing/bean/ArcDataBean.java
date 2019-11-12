package com.jqsoft.nursing.bean;

/**
 * Arcface 参数
 */
public class ArcDataBean {
    private String NoBrushAgeRange;//首次不刷卡年龄范围(示例：0-18,65-2000)
    private String Action;// 动作(0上下1左右2摆头3满足其一 )
    private String Range;//幅度(示例:45)
    private String Frames;//帧数(示例:50)
    private String Similarity;//相似度(示例:50)(示例: 0-8:0.5,8-50:0.6,50-2000:0.4)
    private String ScreenOf; //人脸占屏幕比率
    private String ManCardVerify;

    private String sAPPShuaKa;//是否调用刷卡接口 1:是，：否，默认为
    private String sAPPTanChuangShowButton; //设置弹窗显示按钮		varchar		20190521新加	1:有身份证，有人脸，：有身份证，无人脸，：无身份证无人脸，：无身份证，有人脸，默认为
    private String sAPPDiaoYueLIS;//APP是否调阅LIS		varchar		20190521新加	1:是，：否，默认为
    private String sAPPDiaoYueBChao;//APP是否调阅B超		varchar		20190521新加	1:是，：否，默认为
    private String sAPPCardType;//如果是公司： 1^地址   如果是腾讯： 2
    private String sCheckFacePhoto	;//是否开启申请删除底片功能	1:开启 ,0:不开启，默认0
    private String sDeletePhoto;//	是否开启申请人工审核照片功能	1:开启 ,0:不开启，默认0
    private String ElderInfoID;
    private String ElderName;



    /**
     * 巡查信息类别显示 判断字段 1显示 0不显示
     */
    private String LkzWsXxlb;

    //识别较低时次数
    private String iAPPFaceNoPassCount;

    //识别较低时阈值
    private String sAPPFaceFaZhi;

    public ArcDataBean() {
        super();
    }

    public String getLkzWsXxlb() {
        return LkzWsXxlb;
    }

    public void setLkzWsXxlb(String lkzWsXxlb) {
        LkzWsXxlb = lkzWsXxlb;
    }

    public ArcDataBean(String noBrushAgeRange, String action, String range, String frames, String similarity, String screenOf, String manCardVerify, String sAPPShuaKa, String sAPPTanChuangShowButton, String sAPPDiaoYueLIS, String sAPPDiaoYueBChao, String sAPPCardType, String iAPPFaceNoPassCount, String sAPPFaceFaZhi) {
        NoBrushAgeRange = noBrushAgeRange;
        Action = action;
        Range = range;
        Frames = frames;
        Similarity = similarity;
        ScreenOf = screenOf;
        ManCardVerify = manCardVerify;
        this.sAPPShuaKa = sAPPShuaKa;
        this.sAPPTanChuangShowButton = sAPPTanChuangShowButton;
        this.sAPPDiaoYueLIS = sAPPDiaoYueLIS;
        this.sAPPDiaoYueBChao = sAPPDiaoYueBChao;
        this.sAPPCardType = sAPPCardType;
        this.iAPPFaceNoPassCount = iAPPFaceNoPassCount;
        this.sAPPFaceFaZhi = sAPPFaceFaZhi;
    }

    public String getsCheckFacePhoto() {
        return sCheckFacePhoto;
    }

    public void setsCheckFacePhoto(String sCheckFacePhoto) {
        this.sCheckFacePhoto = sCheckFacePhoto;
    }

    public String getsDeletePhoto() {
        return sDeletePhoto;
    }

    public void setsDeletePhoto(String sDeletePhoto) {
        this.sDeletePhoto = sDeletePhoto;
    }

    public String getNoBrushAgeRange() {
        return NoBrushAgeRange;
    }

    public void setNoBrushAgeRange(String noBrushAgeRange) {
        NoBrushAgeRange = noBrushAgeRange;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getRange() {
        return Range;
    }

    public void setRange(String range) {
        Range = range;
    }

    public String getFrames() {
        return Frames;
    }

    public void setFrames(String frames) {
        Frames = frames;
    }

    public String getSimilarity() {
        return Similarity;
    }

    public void setSimilarity(String similarity) {
        Similarity = similarity;
    }

    public String getScreenOf() {
        return ScreenOf;
    }

    public void setScreenOf(String screenOf) {
        ScreenOf = screenOf;
    }

    public String getManCardVerify() {
        return ManCardVerify;
    }

    public void setManCardVerify(String manCardVerify) {
        ManCardVerify = manCardVerify;
    }

    public String getsAPPShuaKa() {
        return sAPPShuaKa;
    }

    public void setsAPPShuaKa(String sAPPShuaKa) {
        this.sAPPShuaKa = sAPPShuaKa;
    }

    public String getsAPPTanChuangShowButton() {
        return sAPPTanChuangShowButton;
    }

    public void setsAPPTanChuangShowButton(String sAPPTanChuangShowButton) {
        this.sAPPTanChuangShowButton = sAPPTanChuangShowButton;
    }

    public String getsAPPDiaoYueLIS() {
        return sAPPDiaoYueLIS;
    }

    public void setsAPPDiaoYueLIS(String sAPPDiaoYueLIS) {
        this.sAPPDiaoYueLIS = sAPPDiaoYueLIS;
    }

    public String getsAPPDiaoYueBChao() {
        return sAPPDiaoYueBChao;
    }

    public void setsAPPDiaoYueBChao(String sAPPDiaoYueBChao) {
        this.sAPPDiaoYueBChao = sAPPDiaoYueBChao;
    }

    public String getsAPPCardType() {
        return sAPPCardType;
    }

    public void setsAPPCardType(String sAPPCardType) {
        this.sAPPCardType = sAPPCardType;
    }

    public String getiAPPFaceNoPassCount() {
        return iAPPFaceNoPassCount;
    }

    public void setiAPPFaceNoPassCount(String iAPPFaceNoPassCount) {
        this.iAPPFaceNoPassCount = iAPPFaceNoPassCount;
    }

    public String getsAPPFaceFaZhi() {
        return sAPPFaceFaZhi;
    }

    public void setsAPPFaceFaZhi(String sAPPFaceFaZhi) {
        this.sAPPFaceFaZhi = sAPPFaceFaZhi;
    }

    public String getElderInfoID() {
        return ElderInfoID;
    }

    public void setElderInfoID(String elderInfoID) {
        ElderInfoID = elderInfoID;
    }

    public String getElderName() {
        return ElderName;
    }

    public void setElderName(String elderName) {
        ElderName = elderName;
    }
}
