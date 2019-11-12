package com.jqsoft.nursing.bean;

/**
 * @author yedong
 * @date 2019/8/29
 * 识别身份证实体类
 */
public class ReadIdCardBean {

    private String type; // 正反面  1 正面
    private String imgPath; // 保存图片路径
    private String headPath; // 身份证头像路径
    private String name; // 姓名
    private String sex;  // 性别
    private String folk;  // 民族
    private String birt; // 出生
    private String addr; // 住址
    private String num; // 身份证号
    private String issue;// 签发机关
    private String valid; // 有效期限

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFolk() {
        return folk;
    }

    public void setFolk(String folk) {
        this.folk = folk;
    }

    public String getBirt() {
        return birt;
    }

    public void setBirt(String birt) {
        this.birt = birt;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }
}
