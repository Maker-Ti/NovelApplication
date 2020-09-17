package com.example.maker.novelapplication.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.maker.novelapplication.ActionController;
import com.example.maker.novelapplication.R;
import com.example.maker.novelapplication.bean.User;
import com.example.maker.novelapplication.intface.DoView;
import com.example.maker.novelapplication.request.Login_Request;


/**
 * Created by Maker on 2019/3/30.
 */

public class LoginActivity extends Activity {
    private boolean eye_tv_check = false;
    private EditText edt_username;
    private EditText edt_password;
    private TextView tev_clear;
    private TextView tev_eye;
    private Button btn_login;
    private TextView tv_regiest;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //加载缓存数据
        loadShared();

        initView();
        initData();


    }

    private void initView() {

        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        tev_clear = findViewById(R.id.tev_clear);
        tev_eye = findViewById(R.id.tev_eye);
        btn_login = findViewById(R.id.btn_login);
        tv_regiest = findViewById(R.id.regiest);
    }


    private void loadShared() {
        sharedPreferences = getSharedPreferences("userLogin",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        ActionController.loginShared = sharedPreferences;
    }

    private void loginConnect(String name,String pwd){
        String params = "userName="+name+"&userPwd="+pwd;
        Login_Request userLogin_request = new Login_Request(LoginActivity.this,params,name,pwd);
        userLogin_request.conn(new DoView() {
            @Override
            public void setSuccessfulView(Object object) {
                User user = (User) object;
                ActionController.user = user;
                if(user==null){
                    Toast.makeText(LoginActivity.this, "登录失败，请检查账户信息", Toast.LENGTH_SHORT).show();
                }else {
                    ActionController.loginCheck = true;
                    Log.e("makerLog","password:"+user.getPassword());
                    editor.putBoolean("isLogin",true);
                    editor.putString("password",user.getPassword());
                    editor.putString("id",user.getId());
                    editor.commit();
                    Log.e("makerLog","shared"+sharedPreferences.getString("password",""));
                    loginTransation();
                }
            }

            @Override
            public void setFiledView(Object object) {
                Toast.makeText(LoginActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loginTransation(){
        btn_login.setText("");
        ObjectAnimator loginAnimX = ObjectAnimator.ofFloat(btn_login,"scaleX",1,15).setDuration(500);
        ObjectAnimator loginAnimY = ObjectAnimator.ofFloat(btn_login,"scaleY",1,15).setDuration(500);
        loginAnimX.start();
        loginAnimY.start();
        loginAnimX.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

    private void initData() {

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edt_username.getText().toString();
                String pwd = edt_password.getText().toString();
                if(name==null&&pwd==null){
                    Toast.makeText(LoginActivity.this, "请填写用户信息", Toast.LENGTH_SHORT).show();
                }else {
                  loginConnect(name,pwd);

                }
            }
        });
        edt_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("txt",s.toString());
                if(s.length()==0){
                    tev_eye.setVisibility(View.GONE);
                }else {
                    tev_eye.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edt_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("txt",s.toString());
                if(s.length()==0){
                    tev_clear.setVisibility(View.GONE);
                }else {
                    tev_clear.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        tev_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_username.setText("");
            }
        });
        tev_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(eye_tv_check == false){
                    eye_tv_check = true;
                    tev_eye.setBackgroundResource(R.mipmap.eye_open);
                    edt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    eye_tv_check = false;
                    tev_eye.setBackgroundResource(R.mipmap.eye_close);
                    edt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        tv_regiest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,Regiest_Activity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
    }
}
