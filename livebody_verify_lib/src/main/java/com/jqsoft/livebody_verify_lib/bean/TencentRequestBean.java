package com.jqsoft.livebody_verify_lib.bean;

/**
 * @author yedong
 * @date 2019/8/7
 *腾讯云身份证文字识别请求Bean
 */
public class TencentRequestBean {
    private String ImageBase64; // 图片Base64
    private String CardSide; // 身份证的正反面

    public TencentRequestBean() {
    }

    public TencentRequestBean(String imageUrl, String cardSide) {
        ImageBase64 = imageUrl;
        CardSide = cardSide;
    }

    public String getImageBase64() {
        return ImageBase64;
    }

    public void setImageBase64(String imageBase64) {
        ImageBase64 = imageBase64;
    }

    public String getCardSide() {
        return CardSide;
    }

    public void setCardSide(String cardSide) {
        CardSide = cardSide;
    }

    @Override
    public String toString() {
        return "{"+"\""+"ImageBase64"+"\""+":"+"\""+ getImageBase64()+"\""+","+"\""+"CardSide"+"\""+":"+"\""+ getCardSide()+"\""+"}";
    }
}
