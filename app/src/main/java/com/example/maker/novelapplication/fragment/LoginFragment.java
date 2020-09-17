package com.example.maker.novelapplication.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.maker.novelapplication.ActionController;
import com.example.maker.novelapplication.R;
import com.example.maker.novelapplication.TransTool;
import com.example.maker.novelapplication.activity.MainActivity;
import com.example.maker.novelapplication.intface.DoView;
import com.example.maker.novelapplication.request.ChangeUser_Request;

import java.io.FileNotFoundException;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.maker.novelapplication.TransTool.bitmapToBase64;

public class LoginFragment extends Fragment {
    private TextView tv_username,tv_userid;
    private List<RelativeLayout> re_list;
    private String imgStream;
    private ImageView img_head;
    private RadioGroup rg;
    private EditText ed_name,ed_pwd,ed_mail,ed_age;
    private RelativeLayout re_exit,re_change;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_user,null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tv_userid = view.findViewById(R.id.tv_userid);
        tv_username = view.findViewById(R.id.tv_username);
        re_exit = view.findViewById(R.id.re_exit);
        imgStream = ActionController.user.getHeadimg();
        re_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActionController.isLogin = false;
                ActionController.loginCheck = false;
                ActionController.user = null;
                SharedPreferences.Editor editor= ActionController.loginShared.edit();
                editor.clear();
                editor.commit();
                getActivity().finish();
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });
        re_change = view.findViewById(R.id.re_change);
        re_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = ed_name.getText().toString();
                final String age = ed_age.getText().toString();
                String mail = ed_mail.getText().toString();
                final String pwd = ed_pwd.getText().toString();
                String sex = "";
                int flag = rg.getCheckedRadioButtonId();
                switch (flag){
                    case R.id.male:sex = "男";break;
                    case R.id.femle:sex = "女";break;
                }
                if(name.equals("")==false&&age.equals("")==false&&mail.equals("")==false&&pwd.equals("")==false){
                    String head = imgStream.replace("+","%2B");
                    String params = "userID="+ActionController.user.getId()+"&name="+name+"&pwd="+pwd+"&age="+age+"&sex="+sex+"&email="+mail+"&img="+head;
                    ChangeUser_Request changeUser_request = new ChangeUser_Request(getContext(),params);
                    changeUser_request.conn(new DoView() {
                        @Override
                        public void setSuccessfulView(Object object) {
                            boolean flag = (boolean) object;
                            if(flag){
                                ActionController.user.setName(name);
                                SharedPreferences.Editor editor = ActionController.loginShared.edit();
                                editor.putString("password",pwd);
                                editor.commit();
                                Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getContext(), "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void setFiledView(Object object) {

                        }
                    });
                }else {
                    Toast.makeText(getContext(), "请填写完整用户信息", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tv_userid.setText("id："+ ActionController.user.getId());
        tv_username.setText(ActionController.user.getName());
        img_head = view.findViewById(R.id.img_head);
        img_head.setImageBitmap(TransTool.base64ToBitmap(ActionController.user.getHeadimg()));
        img_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(intent, 1);
            }
        });
        rg = view.findViewById(R.id.rg);
        ed_name = view.findViewById(R.id.ed_name);
        ed_pwd = view.findViewById(R.id.ed_pwd);
        ed_mail = view.findViewById(R.id.ed_mail);
        ed_age = view.findViewById(R.id.ed_age);
        String sex = ActionController.user.getSex();
        Log.e("makerLog","sex:"+sex);
        for (int i = 0; i < rg.getChildCount(); i++) {
            rg.getChildAt(i).setEnabled(false);
            rg.getChildAt(i).setEnabled(true);

        }
        if(sex.equals("男")){
            rg.check(R.id.male);
        }else {
            rg.check(R.id.femle);
        }
        ed_age.setText(ActionController.user.getAge());
        ed_pwd.setText(ActionController.user.getPassword());
        ed_name.setText(ActionController.user.getName());
        ed_mail.setText(ActionController.user.getMail());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            String img_url = uri.getPath();//这是本机的图片路径
            ContentResolver cr = getActivity().getContentResolver();
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
}
