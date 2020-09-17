package com.example.maker.novelapplication.http;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class HttpRequest {
    private String httpResult;
    private int httpCode;
    public String getHttpResult() {
        return httpResult;
    }
    public int getHttpCode() {
        return httpCode;
    }
    public void doPost(String httpURL, String httpInface, String httpParams){
        InputStream inputStream;
        try {
            URL url=new URL(httpURL+httpInface);
            Log.e("makerLog","call:"+url);
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setReadTimeout(20*1000);
            httpURLConnection.setConnectTimeout(20*1000);
            httpURLConnection.setRequestProperty("Content-type","text/html");

            httpURLConnection.connect();

            OutputStream outputStream=httpURLConnection.getOutputStream();
            outputStream.write(httpParams.getBytes());
            outputStream.flush();


            BufferedReader bufferedReader;
            StringBuffer stringBuffer;

            int responseCode = httpURLConnection.getResponseCode();
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
