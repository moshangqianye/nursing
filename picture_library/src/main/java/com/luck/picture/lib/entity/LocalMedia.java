package com.luck.picture.lib.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * author：luck
 * project：PictureSelector
 * package：com.luck.picture.entity
 * email：893855882@qq.com
 * data：16/12/31
 */
public class LocalMedia implements Serializable {
    private String path;
    private String compressPath;
    private long duration;
    private long lastUpdateAt;
    private boolean isChecked;
    public int position;
    private int num;
    private int type;
    private boolean compressed;
    private String  weburl;
    List<FileNewBean> fileListBeanList = new ArrayList<>();

    private int webimg;
    private String url;
    private String fileId;

    private String fileName;//当删除选择的文件时根据此属性删除已提交成功路径url中的对应项

    public LocalMedia(String path, long lastUpdateAt, long duration, int type) {
        this.path = path;
        this.duration = duration;
        this.lastUpdateAt = lastUpdateAt;
        this.type = type;
    }

    public LocalMedia(String path, long duration, long lastUpdateAt,
                      boolean isChecked, int position, int num, int type) {
        this.path = path;
        this.duration = duration;
        this.lastUpdateAt = lastUpdateAt;
        this.isChecked = isChecked;
        this.position = position;
        this.num = num;
        this.type = type;
    }

    public LocalMedia(String path, String compressPath, long duration, long lastUpdateAt, boolean isChecked, int position, int num, int type, boolean compressed) {
        this.path = path;
        this.compressPath = compressPath;
        this.duration = duration;
        this.lastUpdateAt = lastUpdateAt;
        this.isChecked = isChecked;
        this.position = position;
        this.num = num;
        this.type = type;
        this.compressed = compressed;

    }

    public LocalMedia() {
    }


    public boolean isCompressed() {
        return compressed;
    }

    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
    }

    public String getCompressPath() {
        return compressPath;
    }

    public void setCompressPath(String compressPath) {
        this.compressPath = compressPath;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getLastUpdateAt() {
        return lastUpdateAt;
    }

    public void setLastUpdateAt(long lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
    }

    public boolean getIsChecked() {
        return this.isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<FileNewBean> getFileListBeanList() {
        return fileListBeanList;
    }

    public void setFileListBeanList(List<FileNewBean> fileListBeanList) {
        this.fileListBeanList = fileListBeanList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getWebimg() {
        return webimg;
    }

    public void setWebimg(int webimg) {
        this.webimg = webimg;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }
}
