package com.example.maker.novelapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maker.novelapplication.ActionController;
import com.example.maker.novelapplication.R;
import com.example.maker.novelapplication.apdater.Fragment_pager;
import com.example.maker.novelapplication.bean.User;
import com.example.maker.novelapplication.fragment.BookFragment;
import com.example.maker.novelapplication.fragment.IndexFragment;
import com.example.maker.novelapplication.fragment.LoginFragment;
import com.example.maker.novelapplication.fragment.UserFragment;
import com.example.maker.novelapplication.intface.DoView;
import com.example.maker.novelapplication.request.Login_Request;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private String imgStream;
    private byte[] data;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private FragmentPagerAdapter mAdapter;
    private List<LinearLayout> txtList;
    private List<TextView> txts;
    private List<TextView> images;
    private int[] ids = {R.id.lin_film,R.id.lin_index,R.id.lin_user};
    private int[] txtIds = {R.id.tv_book,R.id.tv_index,R.id.tv_user};
    private int[] imageIds = {R.id.img_film,R.id.img_index,R.id.img_user};
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //加载缓存数据
        loadShared();
        initView();
        //判断登录状态
        loginCheck();
        initListener();

       /* imageView = findViewById(R.id.img);
        Button getimgt = findViewById(R.id.getimg);
        getimgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login_Request getImage_request = new Login_Request(MainActivity.this,"");
                getImage_request.conn(new DoView() {
                    @Override
                    public void setSuccessfulView(Object object) {
                        String result = (String) object;
                        Bitmap bitmap=null;
                        byte[]bitmapArray;
                        bitmapArray= Base64.decode(result, Base64.DEFAULT);
                        imgStream = result;
                        data = bitmapArray;
                        bitmap= BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
                        imageView.setImageBitmap(bitmap);//显示图片

                    }

                    @Override
                    public void setFiledView(Object object) {

                    }
                });
            }
        });
        Button selectImg = findViewById(R.id.selectimg);
        Button send = findViewById(R.id.send);
        selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                *//* 开启Pictures画面Type设定为image *//*
                intent.setType("image/*");
                *//* 使用Intent.ACTION_GET_CONTENT这个Action *//*
                intent.setAction(Intent.ACTION_GET_CONTENT);
                *//* 取得相片后返回本画面 *//*
                startActivityForResult(intent, 1);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String params = imgStream.replace("+","%2B");
                AddUser_Request addBook_request = new AddUser_Request(MainActivity.this,
                        "userId=2020&userName=5&userPwd=123&type=1&age=12&sex=女&email=123&img="+params);
                addBook_request.conn(new DoView() {
                    @Override
                    public void setSuccessfulView(Object object) {
                        Log.e("makerLog",object.toString());
                    }

                    @Override
                    public void setFiledView(Object object) {

                    }
                });
                *//*String params = imgStream.replace("+","%2B");
                SetImage_Request setImage_request = new SetImage_Request(MainActivity.this,"img="+params);
                setImage_request.conn(new DoView() {
                    @Override
                    public void setSuccessfulView(Object object) {

                    }

                    @Override
                    public void setFiledView(Object object) {

                    }
                });*//*
            }
        });*/

    }
    private void loadShared() {
        sharedPreferences = getSharedPreferences("userLogin",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        ActionController.loginShared = sharedPreferences;
    }
    private void loginCheck() {

        if(ActionController.loginCheck){
            initFragment();
        }else {

            //获取本地记录的登录信息
            boolean isLogin = sharedPreferences.getBoolean("isLogin",false);
            Log.e("loginLog:", sharedPreferences.getString("money",""));
            if(isLogin){
                String userid = sharedPreferences.getString("id","null");
                String userpwd = sharedPreferences.getString("password","null");
                String params = "userName="+userid+"&userPwd="+userpwd;
                Login_Request userLogin_request = new Login_Request(MainActivity.this,params,userid,userpwd);
                userLogin_request.conn(new DoView() {
                    @Override
                    public void setSuccessfulView(Object object) {
                        User user = (User) object;
                        Log.e("makerLog","mark:"+user.getMark());

                        ActionController.user = user;
                        if(user==null){
                            Toast.makeText(MainActivity.this, "登录失败，请检查账户是否更改密码", Toast.LENGTH_SHORT).show();
                        }else {
                            ActionController.loginCheck = true;
                            Log.e("makerLog","password:"+user.getPassword());
                            editor.putBoolean("isLogin",true);
                            editor.putString("password",user.getPassword());
                            editor.putString("id",user.getId());
                            editor.commit();
                            Log.e("makerLog","shared"+sharedPreferences.getString("password",""));
                            initFragment();
                        }
                    }

                    @Override
                    public void setFiledView(Object object) {
                        Toast.makeText(MainActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                initFragment();
            }
        }
    }

    private void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                clearSelect();
                changeSelectIng(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @SuppressLint("ResourceAsColor")
    private void clearSelect(){
        for(int i=0;i<3;i++){
            txts.get(i).setTextColor(this.getResources().getColor(R.color.mainTxtColor));
            switch (i){
                case 0:images.get(i).setBackgroundResource(R.mipmap.book);break;
                case 1:images.get(i).setBackgroundResource(R.mipmap.index);break;
                case 2:images.get(i).setBackgroundResource(R.mipmap.user);break;
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private void changeSelectIng(int i){
        txts.get(i).setTextColor(this.getResources().getColor(R.color.txtRedColor));
        switch (i){
            case 0:images.get(i).setBackgroundResource(R.mipmap.book_select);break;
            case 1:images.get(i).setBackgroundResource(R.mipmap.index_select);break;
            case 2:images.get(i).setBackgroundResource(R.mipmap.user_select);break;
        }
    }
    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new BookFragment());
        fragments.add(new IndexFragment());
        if(ActionController.loginCheck){
            fragments.add(new LoginFragment());
        }else {
            fragments.add(new UserFragment());
        }

        mAdapter = new Fragment_pager(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(1);
    }


    private void initView() {
        images = new ArrayList<>();
        txts = new ArrayList<>();
        txtList = new ArrayList<>();
        viewPager = findViewById(R.id.main_content);
        for(int i=0;i<ids.length;i++){
            LinearLayout linearLayout = findViewById(ids[i]);
            txtList.add(linearLayout);
            TextView img = findViewById(imageIds[i]);
            images.add(img);
            TextView txt = findViewById(txtIds[i]);
            txts.add(txt);
            final int finalI = i;
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewPager.setCurrentItem(finalI);
                }
            });
        }}

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            String img_url = uri.getPath();//这是本机的图片路径
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));

                *//* 将Bitmap设定到ImageView *//*
                imageView.setImageBitmap(bitmap);
                 imgStream = bitmapToBase64(bitmap);

            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private  String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }*/
}
