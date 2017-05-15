package com.github.simonpercic.oklog.core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.support.annotation.NonNull;

/**
 * @author Kale
 * @date 2017/5/12
 */
public class CustomLogManager extends LogManager {

    private RequestModel model;

    public static List<RequestModel> sRequestList = new ArrayList<>();

    public CustomLogManager(String urlBase, LogInterceptor logInterceptor, boolean useAndroidLog, boolean withRequestBody,
            boolean shortenInfoUrl, @NonNull LogDataConfig logDataConfig) {
        super(urlBase, logInterceptor, useAndroidLog, withRequestBody, shortenInfoUrl, logDataConfig);
    }

    @Override
    public void log(LogDataBuilder data) {

        //转换提日期输出格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        model = new RequestModel(data.getRequestMethod(),
                data.getRequestUrl(), data.getResponseCode(), dateFormat.format(System.currentTimeMillis()));
        super.log(data);
    }

    @Override
    void logDebug(String logUrl, String requestMethod, String requestUrlPath) {
        super.logDebug(logUrl, requestMethod, requestUrlPath);
        model.setDetailUrl(logUrl);
        sRequestList.add(model);
    }

}
