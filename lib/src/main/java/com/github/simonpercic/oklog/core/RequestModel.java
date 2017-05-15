package com.github.simonpercic.oklog.core;

/**
 * @author Kale
 * @date 2017/5/12
 */
public class RequestModel {

    public String method;

    public String url;

    public int code;

    public String detailUrl;

    public String time;

    public RequestModel(String method, String url, int code, String time) {
        this.method = method;
        this.url = url;
        this.code = code;
        this.time = time;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }
}
