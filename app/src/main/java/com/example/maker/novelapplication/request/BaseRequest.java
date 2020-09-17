package com.example.maker.novelapplication.request;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import com.example.maker.novelapplication.http.HttpThread;
import com.example.maker.novelapplication.http.HttpUrl;
import com.example.maker.novelapplication.intface.DoView;
import com.example.maker.novelapplication.intface.GetResult;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public abstract class BaseRequest {
    private HttpThread httpThread;
    private Context context;
    public String params;
    public BaseRequest(Context context,String params) {
        this.context = context;
        this.params = params;
    }

    public void conn(final DoView doView){

        httpThread=new HttpThread();
        httpThread.setHttpURL(setHttpURL());
        httpThread.setHttpInface(params);
        httpThread.setHttpParams("");
        httpThread.setGetResult(new GetResult() {
            @Override
            public void successful(String result) {
                doView.setSuccessfulView(getHttpResult(result));
            }

            @Override
            public void filed(String result) {
                doView.setFiledView(result);
            }
        });
        httpThread.start();
    }

    protected abstract Object getHttpResult(String result);

    protected abstract String setHttpURL();


}
