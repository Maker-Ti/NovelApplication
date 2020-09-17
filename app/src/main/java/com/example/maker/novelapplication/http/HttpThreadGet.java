package com.example.maker.novelapplication.http;

import android.os.Handler;

import com.example.maker.novelapplication.intface.GetResult;


/**
 * Created by Maker on 2018/3/14.
 */

public class HttpThreadGet extends Thread {

    private Handler handler=new Handler();
    private GetResult getResult;

    private String httpURL;

    public void setHttpURL(String httpURL) {
        this.httpURL = httpURL;
    }
    public void setGetResult(GetResult getResult) {
        this.getResult = getResult;
    }

    @Override
    public void run() {
        HttpRequestGet httpRequestGet=new HttpRequestGet();
        httpRequestGet.doGet(httpURL);
        int httpCode=httpRequestGet.getHttpCode();
        final String httpResult=httpRequestGet.getHttpResult();
        if (httpCode==200){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getResult.successful(httpResult);
                }
            });
        }if (httpCode==0){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    getResult.filed("网络错误");
                }
            });
        }
    }


}
