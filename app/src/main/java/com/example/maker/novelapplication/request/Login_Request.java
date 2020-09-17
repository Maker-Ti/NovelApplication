package com.example.maker.novelapplication.request;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.maker.novelapplication.bean.User;
import com.example.maker.novelapplication.http.HttpUrl;

import org.json.JSONException;
import org.json.JSONObject;

public class Login_Request extends BaseRequest {
    private String userid,userpwd;
    public Login_Request(Context context, String params,String userid,String userpwd) {
        super(context, params);
        this.userid = userid;
        this.userpwd = userpwd;
    }

    @Override
    protected Object getHttpResult(String result) {
        Log.e("makerLog",result);
        User user  = null;
        try {
            JSONObject js = new JSONObject(result);
            if(js.getInt("code") == 200&&js.getInt("type") == 0){
                //String name, String age,
                // String sex, String id, String phone,
                // Bitmap headimg, String mail, String password
                user = new User(js.getString("username"),
                        js.getString("age"),
                        js.getString("sex"),
                        userid,
                        "",
                        js.getString("headimg"),
                        js.getString("email"),
                        userpwd,
                        js.getString("mark")
                        );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    protected String setHttpURL() {
        return HttpUrl.userLogin;
    }




}
