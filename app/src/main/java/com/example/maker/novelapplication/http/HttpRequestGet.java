package com.example.maker.novelapplication.http;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Maker on 2018/3/14.
 */

public class HttpRequestGet {
    private String httpResult;
    private int httpCode;
    public String getHttpResult() {
        return httpResult;
    }
    public int getHttpCode() {
        return httpCode;
    }

    public void doGet(String httpURL){
        InputStream inputStream;
        try {
            URL url=new URL(httpURL);
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setReadTimeout(20*1000);
            httpURLConnection.setConnectTimeout(20*1000);
            httpURLConnection.setRequestProperty("Content-type","text/html");

            httpURLConnection.connect();

            BufferedReader bufferedReader;
            StringBuffer stringBuffer;

            int responseCode = httpURLConnection.getResponseCode();
            Log.e("zzz",responseCode+"");
            if (responseCode==200){
                inputStream=httpURLConnection.getInputStream();
                bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                stringBuffer=new StringBuffer();
                String len=null;
                while ((len=bufferedReader.readLine())!=null){
                    stringBuffer.append(len);
                }
                httpResult=stringBuffer.toString();
                httpCode=200;
            }else {
                httpCode=0;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
