package com.jqsoft.livebody_verify_lib.bean;

public class GetRecognitionBasePhotoBean {
    private String isExist;
    private String Message;
    private String Photo_Base;

    public GetRecognitionBasePhotoBean(String isExist, String message, String photo_Base) {
        this.isExist = isExist;
        Message = message;
        Photo_Base = photo_Base;
    }

    public GetRecognitionBasePhotoBean() {
    }

    public String getIsExist() {
        return isExist;
    }

    public void setIsExist(String isExist) {
        this.isExist = isExist;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getPhoto_Base() {
        return Photo_Base;
    }

    public void setPhoto_Base(String photo_Base) {
        Photo_Base = photo_Base;
    }
}
