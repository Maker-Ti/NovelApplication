package com.example.maker.novelapplication.request;

import android.content.Context;
import android.util.Log;

import com.example.maker.novelapplication.bean.User;
import com.example.maker.novelapplication.http.HttpUrl;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangeUserMark_Request extends BaseRequest {
    public ChangeUserMark_Request(Context context, String params) {
        super(context, params);

    }

    @Override
    protected Object getHttpResult(String result) {
        Log.e("makerLog",result);
        boolean user  = false;
        try {
            JSONObject js = new JSONObject(result);
            if(js.getInt("msg_code") == 200){
                user = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    protected String setHttpURL() {
        return HttpUrl.changeUserMark;
    }




}
