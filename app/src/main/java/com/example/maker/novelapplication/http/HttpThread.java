package com.example.maker.novelapplication.http;

import android.os.Handler;

import com.example.maker.novelapplication.intface.GetResult;


public class HttpThread extends Thread {

    private HttpRequest httpRequest;
    private Handler handler=new Handler();

    private String httpURL;
    private String httpInface;
    private String httpParams;
    private GetResult getResult;

    public void setHttpURL(String httpURL) {
        this.httpURL = httpURL;
    }

    public void setHttpInface(String httpInface) {
        this.httpInface = httpInface;
    }

    public void setHttpParams(String httpParams) {
        this.httpParams = httpParams;
    }

    public void setGetResult(GetResult getResult) {
        this.getResult = getResult;
    }

    @Override
    public void run() {

        httpRequest=new HttpRequest();
        httpRequest.doPost(httpURL,httpInface,httpParams);
        final String httpResult = httpRequest.getHttpResult();
        int httpCode = httpRequest.getHttpCode();
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
