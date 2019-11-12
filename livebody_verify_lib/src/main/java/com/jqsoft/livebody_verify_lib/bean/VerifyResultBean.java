package com.jqsoft.livebody_verify_lib.bean;

/**
 * Created by Administrator on 2018-08-09.
 */

public class VerifyResultBean {
    String respond;
    String similScore;
    String message;

    public VerifyResultBean(String respond, String similScore, String message) {
        this.respond = respond;
        this.similScore = similScore;
        this.message = message;
    }

    public String getRespond() {
        return respond;
    }

    public void setRespond(String respond) {
        this.respond = respond;
    }

    public String getSimilScore() {
        return similScore;
    }

    public void setSimilScore(String similScore) {
        this.similScore = similScore;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
