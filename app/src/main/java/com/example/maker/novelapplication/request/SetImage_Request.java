package com.example.maker.novelapplication.request;

import android.content.Context;
import android.util.Log;

import com.example.maker.novelapplication.http.HttpUrl;

public class SetImage_Request extends BaseRequest {
    public SetImage_Request(Context context, String params) {
        super(context, params);
    }

    @Override
    protected Object getHttpResult(String result) {
        Log.e("makerLog",result);
        return result;
    }

    @Override
    protected String setHttpURL() {
        return HttpUrl.setImg;
    }
}
