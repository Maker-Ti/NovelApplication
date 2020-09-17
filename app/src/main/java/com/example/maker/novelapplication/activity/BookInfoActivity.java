package com.example.maker.novelapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.maker.novelapplication.ActionController;
import com.example.maker.novelapplication.R;
import com.example.maker.novelapplication.TransTool;
import com.example.maker.novelapplication.bean.Chapter;
import com.example.maker.novelapplication.intface.DoView;
import com.example.maker.novelapplication.request.ChangeUserMark_Request;
import com.example.maker.novelapplication.request.GetChapter;

import java.util.List;

public class BookInfoActivity extends Activity {
    private ImageView bookImg;
    private TextView bookName;
    private TextView bookCreator;
    private TextView bookTime;
    private TextView bookDetail;
    private LinearLayout lin_mark;
    private TextView tv_mark;
    private LinearLayout lin_start;
    private List<Chapter> data;
    private boolean hasMarked = false;
    private String changeInfo = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookinfo);

        initView();
        initData();
        loadListener();

    }

    private void loadListener() {
        lin_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                ActionController.saveBookReadHistory(ActionController.selectedBook.getId());
                startActivity(new Intent(BookInfoActivity.this,ReadingActivity.class));
            }
        });
        lin_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ActionController.loginCheck){

                    if(hasMarked){
                        hasMarked = false;
                    }else {
                        hasMarked = true;
                    }
                    changeInfo = ActionController.setUserMark(hasMarked);
                    ChangeUserMark_Request changeUserMark_request =
                            new ChangeUserMark_Request(BookInfoActivity.this,"userID="+ActionController.user.getId()+"&mark="+changeInfo);
                    changeUserMark_request.conn(new DoView() {
                        @Override
                        public void setSuccessfulView(Object object) {
                            boolean flag = (boolean) object;
                            if(flag){
                                if (hasMarked){
                                    Toast.makeText(BookInfoActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();

                                }else {
                                    Toast.makeText(BookInfoActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();

                                }
                                ActionController.user.setMark(changeInfo);
                                switchMark();
                            }else {
                                Toast.makeText(BookInfoActivity.this, "请求失败", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void setFiledView(Object object) {

                        }
                    });
                }else {
                    Toast.makeText(BookInfoActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void switchMark(){
        if(hasMarked){
            tv_mark.setText("已收藏");
        }else {
            tv_mark.setText("收藏本书");
        }
    }
    private void initData() {
        if(ActionController.loginCheck){
            hasMarked = ActionController.user.getMark().contains(ActionController.selectedBook.getId());
        }
        switchMark();
        bookImg.setImageBitmap(TransTool.base64ToBitmap(ActionController.selectedBook.getImg()));
        bookName.setText(ActionController.selectedBook.getName());
        bookCreator.setText(ActionController.selectedBook.getCreaotrname());
        bookTime.setText(ActionController.selectedBook.getCreatTime());
        bookDetail.setText(ActionController.selectedBook.getDetail());
        GetChapter getChapter = new GetChapter(this,"bookID="+ActionController.selectedBook.getId());
        getChapter.conn(new DoView() {
            @Override
            public void setSuccessfulView(Object object) {
                data = (List<Chapter>) object;
                if(data!=null){
                    ActionController.selectedChapterData = data;
                    ActionController.selectedChapter = data.get(0);
                }else {
                    Toast.makeText(BookInfoActivity.this, "获取章节失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void setFiledView(Object object) {

            }
        });

    }

    private void initView() {
        tv_mark = findViewById(R.id.tv_mark);
        lin_mark = findViewById(R.id.lin_mark);
        bookImg = findViewById(R.id.book_img);
        bookName = findViewById(R.id.book_name);
        bookCreator = findViewById(R.id.book_creator);
        bookTime = findViewById(R.id.book_time);
        bookDetail = findViewById(R.id.book_detail);
        lin_start = findViewById(R.id.lin_start);
    }
}
