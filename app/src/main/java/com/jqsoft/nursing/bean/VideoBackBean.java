package com.jqsoft.nursing.bean;

/**
 * Created by Administrator on 2018/1/30.
 */

public class VideoBackBean {
    private String videoName;//":" upload_0a1d4e4c_b425_48fc_8ffe_30485c7ce9c7_00000003.mp4",
    private String addDate;
    private String videoUrl;

    public VideoBackBean() {
    }

    public VideoBackBean(String videoName, String addDate, String videoUrl) {
        this.videoName = videoName;
        this.addDate = addDate;
        this.videoUrl = videoUrl;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
