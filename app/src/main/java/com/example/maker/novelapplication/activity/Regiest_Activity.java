package com.example.maker.novelapplication.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.maker.novelapplication.R;
import com.example.maker.novelapplication.intface.DoView;
import com.example.maker.novelapplication.request.AddUser_Request;

import java.io.FileNotFoundException;

import static com.example.maker.novelapplication.TransTool.bitmapToBase64;


/**
 * Created by Maker on 2019/4/18.
 */

public class Regiest_Activity extends Activity {
    private EditText ed_name,ed_id,ed_pwd;
    private boolean eye_tv_check = false,eye_tv_check_2 = false;
    private Button btn_regiest;
    private TextView tv_clear_u,tv_clear_name,tev_eye;
    private EditText ed_mail,ed_age;
    private RadioGroup rg;
    private String imgStream;
    private ImageView img_head;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiest);
        initView();
        initData();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            String img_url = uri.getPath();//这是本机的图片路径
            ContentResolver cr = getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                img_head.setImageBitmap(bitmap);
                imgStream = bitmapToBase64(bitmap);

            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void initData() {
        img_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(intent, 1);
            }
        });
        btn_regiest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ed_name.getText().toString();
                String pwd = ed_pwd.getText().toString();
                String id = ed_id.getText().toString();
                String mail = ed_mail.getText().toString();
                String age = ed_age.getText().toString();
                int flag = rg.getCheckedRadioButtonId();
                if(name.equals("")||pwd.equals("")||id.equals("")||mail.equals("")||age.equals("")){
                    Toast.makeText(Regiest_Activity.this, "检查是否输入有空", Toast.LENGTH_SHORT).show();
                }else {
                    String sex = "";
                    switch (flag){
                        case R.id.male:sex = "男";break;
                        case R.id.femle:sex = "女";break;
                    }
                    String head = imgStream.replace("+","%2B");
                    String params
                            = "userName="+name+"&type=0"+
                            "&userPwd="+pwd+
                            "&sex="+sex+"&userId="
                            +id+"&email="+mail+"&age="+age+"&img="+head;
                    AddUser_Request addUser_request = new AddUser_Request(Regiest_Activity.this,params);
                    addUser_request.conn(new DoView() {
                        @Override
                        public void setSuccessfulView(Object object) {
                            boolean flag = (boolean) object;
                            if(flag){
                                finish();
                                Toast.makeText(Regiest_Activity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(Regiest_Activity.this, "注册失败", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void setFiledView(Object object) {

                        }
                    });
                }
            }
        });
    }

    private void initView() {
        img_head = findViewById(R.id.headimg);
        ed_id = findViewById(R.id.edt_id);
        ed_name = findViewById(R.id.edt_name);
        ed_pwd = findViewById(R.id.edt_password);
        ed_age = findViewById(R.id.ed_age);
        ed_mail = findViewById(R.id.ed_mail);
        rg = findViewById(R.id.rg);
        btn_regiest = findViewById(R.id.btn_regiest);

    }
}
